package com.admin.admin_back.pojo.excel;

import com.admin.admin_back.mapper.ExcelColumnMapper;
import com.admin.admin_back.mapper.ExcelMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.ExcelColumnDto;
import com.admin.admin_back.pojo.dto.ExcelDto;
import com.admin.admin_back.pojo.exception.ExcelDataException;
import com.admin.admin_back.pojo.exception.ExcelNameExistException;
import com.admin.admin_back.pojo.exception.SqlColumnNameNotFoundException;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author 陈群矜
 */
public class ExcelAnalysisListener extends AnalysisEventListener<LinkedHashMap<Integer, String>> {

    /**
     * Excel映射配置信息
     */
    private ExcelDto excelDto = null;

    /**
     * Excel表格中的数据
     */
    private final List<JSONObject> dataList = new ArrayList<>();

    /**
     * 代表数据中第nums行
     */
    private int nums = 0;

    /**
     * 初始化为true
     * 当isColumnName = true时，读取的信息为excel列名；
     * 否则，读取的是数据
     */
    private boolean isColumnName = true;

    /**
     * 按照顺序存储列名
     */
    private final List<String> columnNames = new ArrayList<>();

    /**
     * 主键
     */
    private final List<ExcelColumnDto> primaryKeys = new ArrayList<>();

    /**
     * 存储excel列名 -> sql列名 的映射关系
     */
    private final Map<String, String> excelSqlColumnMapper = new HashMap<>();

    /**
     * 通过构造函数传入dao
     */
    private final ExcelMapper excelMapper;

    /**
     * 通过构造函数传入dao
     */
    private final ExcelColumnMapper excelColumnMapper;

    public ExcelAnalysisListener(ExcelMapper excelMapper, ExcelColumnMapper excelColumnMapper) {
        this.excelMapper = excelMapper;
        this.excelColumnMapper = excelColumnMapper;
    }

    public ExcelDto getExcelDto() {
        return excelDto;
    }

    public List<JSONObject> getDataList() {
        return dataList;
    }

    @Override
    public void invoke(LinkedHashMap<Integer, String> linkedHashMap, AnalysisContext analysisContext) {
        if (isColumnName) {
            handleColumnNames(linkedHashMap);
        } else {
            handleData(linkedHashMap);
        }
    }

    private void handleColumnNames(LinkedHashMap<Integer, String> linkedHashMap) {
        this.isColumnName = false;
        for (Integer key : linkedHashMap.keySet()) {
            String excelColumnName = linkedHashMap.get(key);
            String sqlColumnName = excelSqlColumnMapper.get(excelColumnName);
            if (StringUtils.isBlank(sqlColumnName)) {
                throw new SqlColumnNameNotFoundException(excelColumnName);
            }
            columnNames.add(sqlColumnName);
        }
    }

    private void handleData(LinkedHashMap<Integer, String> linkedHashMap) {
        ++nums;
        JSONObject data = new JSONObject();
        for (Integer key : linkedHashMap.keySet()) {
            if (key < columnNames.size()) {
                data.put(columnNames.get(key), linkedHashMap.get(key));
            }
        }
        checkData(data);
        dataList.add(data);
    }

    /**
     * 根据主键检查数据
     * 主键不能为空，主键不能重复
     * @param data 数据
     */
    private void checkData(JSONObject data) {
        // 如果没有配置主键，说明数据正确，直接返回
        if (CollectionUtils.isEmpty(primaryKeys)) {
            return;
        }

        // 检查主键字段是否为空
        for (ExcelColumnDto primaryKey : primaryKeys) {
            String value = (String) data.get(primaryKey.getSqlColumn());
            if (StringUtils.isBlank(value)) {
                throw new ExcelDataException("第" + nums + "行，" + primaryKey.getExcelColumn() + "为空");
            }
        }

        // 检查重复
        for (JSONObject object : dataList) {
            boolean isEqual = true;
            for (ExcelColumnDto primaryKey : primaryKeys) {
                String value1 = (String) data.get(primaryKey.getSqlColumn());
                String value2 = (String) object.get(primaryKey.getSqlColumn());
                if (!StringUtils.equals(value1, value2)) {
                    isEqual = false;
                    break;
                }
            }
            if (isEqual) {
                throw new ExcelDataException("第" + nums + "行，主键与Excel表内数据重复");
            }
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        String excelName = headMap.getOrDefault(0, "");
        this.excelDto = excelMapper.findExcelByExcelName(excelName);
        if (Objects.isNull(this.excelDto)) {
            throw new ExcelNameExistException();
        }
        List<ExcelColumnDto> excelColumnDtos = excelColumnMapper.findExcelColumnsByExcelId(excelDto.getExcelId());
        for (ExcelColumnDto excelColumnDto : excelColumnDtos) {
            excelSqlColumnMapper.put(excelColumnDto.getExcelColumn(), excelColumnDto.getSqlColumn());
            if (excelColumnDto.getIsPrimaryKey() == Constant.IS_PRIMARY_KEY) {
                primaryKeys.add(excelColumnDto);
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (nums == 0) {
            throw new ExcelDataException("Excel表内数据为空");
        }
    }

}