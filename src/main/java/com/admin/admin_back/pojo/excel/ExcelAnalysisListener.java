package com.admin.admin_back.pojo.excel;

import com.admin.admin_back.mapper.ExcelColumnMapper;
import com.admin.admin_back.mapper.ExcelMapper;
import com.admin.admin_back.mapper.SqlMapper;
import com.admin.admin_back.pojo.constant.Constant;
import com.admin.admin_back.pojo.dto.*;
import com.admin.admin_back.pojo.exception.ExcelDataException;
import com.admin.admin_back.pojo.exception.ExcelNameNotFoundException;
import com.admin.admin_back.service.LogTask;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
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
    private final List<ExcelDataDto> dataList = new ArrayList<>();

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
     * 存储需要特殊处理的excel列名
     */
    private final Set<String> specialExcelColumns = new HashSet<>();

    /**
     * 存储excel列名 -> sql列名 的映射关系
     */
    private final Map<String, String> excelSqlColumnMapper = new HashMap<>();

    /**
     * 存储sql列名 -> 详细信息 的映射关系
     */
    private final Map<String, SqlColumnInfoDto> sqlColumnInfoMap = new HashMap<>();

    /**
     *
     */
    private final Set<String> primaryKeys = new HashSet<>();

    /**
     * 唯一性列表
     * 认为 主键 = 唯一性 + 非空
     */
    private final List<List<String>> uniqueKeys = new ArrayList<>();

    /**
     * 非空字段名称列表
     * 认为 主键 = 唯一性 + 非空
     */
    private final Set<String> nonNullKeys = new HashSet<>();

    /**
     * 自增字段
     */
    private final Set<String> autoIncrementKeys = new HashSet<>();

    /**
     * 通过构造函数传入dao
     */
    private final ExcelMapper excelMapper;

    /**
     * 通过构造函数传入dao
     */
    private final ExcelColumnMapper excelColumnMapper;

    /**
     * 通过构造函数传入dao
     */
    private final SqlMapper sqlMapper;

    /**
     * 通过构造函数传入日志service
     */
    private final LogTask logTask;

    public ExcelAnalysisListener(ExcelMapper excelMapper,
                                 ExcelColumnMapper excelColumnMapper,
                                 SqlMapper sqlMapper,
                                 LogTask logTask) {
        this.excelMapper = excelMapper;
        this.excelColumnMapper = excelColumnMapper;
        this.sqlMapper = sqlMapper;
        this.logTask = logTask;
    }

    public ExcelDto getExcelDto() {
        return excelDto;
    }

    public List<ExcelDataDto> getDataList() {
        return dataList;
    }

    public List<List<String>> getUniqueKeys() {
        return uniqueKeys;
    }

    @Override
    public void invoke(LinkedHashMap<Integer, String> linkedHashMap, AnalysisContext analysisContext) {
        if (isColumnName) {
            handleColumnNames(linkedHashMap);
        } else {
            if (specialExcelColumns.isEmpty()) {
                handleData(linkedHashMap);
            } else {
                final String excelName = excelDto.getExcelName();
                switch (excelDto.getExcelName()) {
                    case "选课课程管理信息":
                        handleCourseSelectionInfo(linkedHashMap);
                        break;
                    default:
                        logTask.logInfo("暂时不支持处理" + excelName + "的特殊情况");
                        throw new ExcelDataException("暂时不支持处理" + excelName + "的特殊情况");
                }
            }
        }
    }

    private void handleColumnNames(LinkedHashMap<Integer, String> linkedHashMap) {
        if (linkedHashMap.isEmpty()) {
            return;
        }
        this.isColumnName = false;
        for (Integer key : linkedHashMap.keySet()) {
            String excelColumnName = linkedHashMap.get(key);
            if (!excelSqlColumnMapper.containsKey(excelColumnName)) {
                logTask.logInfo(excelColumnName + "对应的配置不存在");
            }
            columnNames.add(excelColumnName);
        }
    }

    private void handleData(LinkedHashMap<Integer, String> linkedHashMap) {
        ++nums;
        ExcelDataDto excelDataDto = new ExcelDataDto();
        Map<String, Object> data = excelDataDto.getData();
        for (Integer key : linkedHashMap.keySet()) {
            if (key < columnNames.size()) {
                String excelColumnName = columnNames.get(key);
                String sqlColumnName = excelSqlColumnMapper.getOrDefault(excelColumnName, null);
                if (StringUtils.isBlank(sqlColumnName)) {
                    continue;
                }
                String val = linkedHashMap.get(key);
                String dataType = sqlColumnInfoMap.get(sqlColumnName).getDataType();
                Object value = parseVal(val, dataType);
                data.put(sqlColumnName, value);
            }
        }
        checkData(excelDataDto);
        dataList.add(excelDataDto);
    }

    private void handleCourseSelectionInfo(LinkedHashMap<Integer, String> linkedHashMap) {
        ++nums;
        Map<String, Object> data = new HashMap<>();
        List<ExcelDataDto> extraData = new ArrayList<>();
        String courseId = null;
        String courseOrderId = null;
        for (Integer key : linkedHashMap.keySet()) {
            if (key < columnNames.size()) {
                String excelColumnName = columnNames.get(key);
                String val = linkedHashMap.get(key);
                if (specialExcelColumns.contains(excelColumnName)) {
                    int length = 0;
                    int size = 0;
                    switch (excelColumnName) {
                        case "课程编号":
                            courseId = val;
                            break;
                        case "课序号":
                            courseOrderId = val;
                            break;
                        case "上课时间":
                            String[] times = val.split(",");
                            length = times.length;
                            size = extraData.size();
                            if (length < size) {
                                throw new ExcelDataException("第" + nums + "行，上课时间数量过少。");
                            } else if (length > size) {
                                if (size == 0) {
                                    for (int i=0; i<length-size; ++i) {
                                        extraData.add(new ExcelDataDto());
                                    }
                                } else {
                                    throw new ExcelDataException("第" + nums + "行，上课时间数量过多。");
                                }
                            }
                            for (int i=0; i<length; ++i) {
                                String time = times[i].trim();
                                String[] splitTime = time.split(" ");
                                if (splitTime.length != 2) {
                                    throw new ExcelDataException("第" + nums + "行，上课时间格式错误。");
                                }
                                extraData.get(i).getData().put("weekN", splitTime[0]);
                                extraData.get(i).getData().put("classsegment", splitTime[1]);
                            }
                            break;
                        case "上课地点":
                            String[] places = val.split(",");
                            length = places.length;
                            size = extraData.size();
                            if (length < size) {
                                throw new ExcelDataException("第" + nums + "行，上课地点数量过少。");
                            } else if (length > size) {
                                if (size == 0) {
                                    for (int i=0; i<length-size; ++i) {
                                        extraData.add(new ExcelDataDto());
                                    }
                                } else {
                                    throw new ExcelDataException("第" + nums + "行，上课地点数量过多。");
                                }
                            }
                            for (int i=0; i<length; ++i) {
                                String place = places[i];
                                extraData.get(i).getData().put("classroom", place);
                            }
                            break;
                        default:
                            logTask.logInfo("暂时不支持处理" + excelColumnName + "的特殊情况");
                            throw new ExcelDataException("暂时不支持处理" + excelColumnName + "的特殊情况");
                    }
                }
                if (excelSqlColumnMapper.containsKey(excelColumnName)) {
                    String sqlColumnName = excelSqlColumnMapper.getOrDefault(excelColumnName, null);
                    if (StringUtils.isBlank(sqlColumnName)) {
                        continue;
                    }
                    String dataType = sqlColumnInfoMap.get(sqlColumnName).getDataType();
                    Object value = parseVal(val, dataType);
                    data.put(sqlColumnName, value);
                }
            }
        }
        if (extraData.isEmpty() || StringUtils.isBlank(courseId) || StringUtils.isBlank(courseOrderId)) {
            throw new ExcelDataException("第" + nums + "行，缺少一些指定的配置。");
        }
        for (ExcelDataDto excelDataDto : extraData) {
            String keyid = courseId + courseOrderId + excelDataDto.getData().get("classsegment");
            excelDataDto.getData().put("keyid", keyid);
            checkData(excelDataDto);
            dataList.add(excelDataDto);
        }
    }

    /**
     * 根据主键检查数据
     * 主键不能为空，主键不能重复
     * @param excelDataDto 数据
     */
    private void checkData(ExcelDataDto excelDataDto) {
        Map<String, Object> keys = excelDataDto.getPrimaryKeys();
        Map<String, Object> data = excelDataDto.getData();

        // 检查字段是否为空
        for (String columnName : nonNullKeys) {
            Object value = data.get(columnName);
            if (checkIfNull(value)) {
                throw new ExcelDataException("第" + nums + "行，" + columnName + "为空");
            }
            if (primaryKeys.contains(columnName)) {
                // 将主键的sql列名和对应的值存入excelDataDto.primaryKeys中
                keys.put(columnName, value);
            }
        }

        // 检查重复
        for (ExcelDataDto other : dataList) {
            Map<String, Object> otherData = other.getData();
            for (List<String> uniqueKeyList : this.uniqueKeys) {
                boolean isEqual = true;
                for (String uniqueKey : uniqueKeyList) {
                    String value1 = Optional.ofNullable(data.get(uniqueKey)).orElse(Constant.BLANK_STRING).toString();
                    String value2 = Optional.ofNullable(otherData.get(uniqueKey)).orElse(Constant.BLANK_STRING).toString();
                    if (!StringUtils.equals(value1, value2)) {
                        isEqual = false;
                        break;
                    }
                }
                if (isEqual) {
                    throw new ExcelDataException("第" + nums + "行，" + uniqueKeyList + "与Excel表内数据重复");
                }
            }
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        String excelName = headMap.getOrDefault(0, "");
        if (StringUtils.isBlank(excelName)) {
            // 减少一次数据库查询数据次数
            throw new ExcelNameNotFoundException();
        }
        this.excelDto = excelMapper.findExcelByExcelName(excelName);
        if (Objects.isNull(this.excelDto)) {
            throw new ExcelNameNotFoundException();
        }
        init(Constant.TABLE_SCHEMA, excelDto.getSqlName());
        List<ExcelColumnDto> excelColumnDtos = excelColumnMapper.findExcelColumnsByExcelId(excelDto.getExcelId());
        for (ExcelColumnDto excelColumnDto : excelColumnDtos) {
            String excelColumnName = excelColumnDto.getExcelColumn();
            String sqlColumnName = excelColumnDto.getSqlColumn();
            if (autoIncrementKeys.contains(sqlColumnName)) {
                continue;
            }
            if (excelColumnDto.getIsSpecial().equals(Constant.IS_SPECIAL)) {
                specialExcelColumns.add(excelName);
            } else {
                excelSqlColumnMapper.put(excelColumnName, sqlColumnName);
            }
        }
    }

    /**
     * 初始化uniqueKeys和nonNullKeys
     * @param dataBaseName 数据库名称
     * @param sqlTableName 数据库表名
     */
    private void init(String dataBaseName, String sqlTableName) {
        /*
         * 从`INFORMATION_SCHEMA`.`COLUMNS`获取数据
         * column_name
         * is_nullable 判断非空
         * data_type 数据类型，之后读取数据时使用
         * extra 判断自增
         */
        List<SqlColumnInfoDto> sqlColumnInfos = sqlMapper.findSqlColumnInfos(dataBaseName, sqlTableName);
        sqlColumnInfos.forEach(sqlColumnInfo -> {
            String columnName = sqlColumnInfo.getColumnName();
            if (StringUtils.equals(sqlColumnInfo.getExtra(), Constant.AUTO_INCREMENT)) {
                // 自增键
                autoIncrementKeys.add(columnName);
            } else {
                if (!StringUtils.equals(sqlColumnInfo.getIsNullable(), Constant.IS_NULLABLE)) {
                    // 非空 或者 主键字段
                    nonNullKeys.add(columnName);
                }
            }
            sqlColumnInfoMap.put(columnName, sqlColumnInfo);
        });

        List<SqlConstraintDto> primaryConstraintKeys = sqlMapper
                .findSqlConstraintByType(dataBaseName, sqlTableName, Constant.CONSTRAINT_TYPE_PRIMARY_KEY);
        List<SqlConstraintDto> uniqueConstraintKeys = sqlMapper
                .findSqlConstraintByType(dataBaseName, sqlTableName, Constant.CONSTRAINT_TYPE_UNIQUE);
        Map<String, List<String>> uniqueConstraintKeysMap = new HashMap<>();
        for (SqlConstraintDto sqlConstraint : uniqueConstraintKeys) {
            String constraintName = sqlConstraint.getConstraintName();
            String columnName = sqlConstraint.getColumnName();
            if (uniqueConstraintKeysMap.containsKey(constraintName)) {
                uniqueConstraintKeysMap.get(constraintName).add(columnName);
            } else {
                List<String> list = new ArrayList<String>() {{
                    add(columnName);
                }};
                uniqueConstraintKeysMap.put(constraintName, list);
            }
        }
        for (SqlConstraintDto sqlConstraint : primaryConstraintKeys) {
            String constraintName = sqlConstraint.getConstraintName();
            String columnName = sqlConstraint.getColumnName();
            if (autoIncrementKeys.contains(columnName)) {
                continue;
            }
            nonNullKeys.add(columnName);
            primaryKeys.add(columnName);
            if (uniqueConstraintKeysMap.containsKey(constraintName)) {
                uniqueConstraintKeysMap.get(constraintName).add(columnName);
            } else {
                List<String> list = new ArrayList<String>() {{
                    add(columnName);
                }};
                uniqueConstraintKeysMap.put(constraintName, list);
            }
        }
        for (Map.Entry<String, List<String>> entry : uniqueConstraintKeysMap.entrySet()) {
            uniqueKeys.add(entry.getValue());
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (nums == 0) {
            throw new ExcelDataException("Excel表内数据为空");
        }
    }

    /**
     * 将value转换为对应类型
     * @param val value
     * @param dataType 对应类型
     * @return {@code val}转换后的类型
     * @throws ExcelDataException 类型转换失败后抛出异常
     */
    private Object parseVal(String val, String dataType) {
        Object value = null;
        switch (dataType) {
            case Constant.DATE_TYPE_DATE:
                value = handleCastToDate(val, true);
                if (Objects.isNull(value)) {
                    throw new ExcelDataException("第" + nums + "行，日期格式错误");
                }
                break;
            case Constant.DATE_TYPE_DATE_TIME:
                value = handleCastToDate(val, false);
                if (Objects.isNull(value)) {
                    throw new ExcelDataException("第" + nums + "行，日期格式错误");
                }
                break;
            default:
                value = val;
                break;
        }
        return value;
    }

    /**
     * 将字符串转换为日期
     * @param dateStr 日期字符串
     * @param isDate true表示date类型，false表示datetime类型
     * @return 转换后的日期结果，如果不符合规则，返回null
     */
    private Date handleCastToDate(String dateStr, boolean isDate) {
        Date result = null;
        SimpleDateFormat[] formats = isDate ? Constant.SIMPLE_DATE_FORMATS : Constant.SIMPLE_DATE_TIME_FORMATS;
        for (SimpleDateFormat format : formats) {
            try {
                format.setLenient(false);
                result = format.parse(dateStr);
                break;
            } catch (Exception ignored) {

            }
        }
        return result;
    }

    /**
     * 检查字段是否为空
     * @param value 待检验的字段
     * @return true表示字段为空；false表示value不为空
     */
    private boolean checkIfNull(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }
        if (value instanceof String) {
            String string = (String) value;
            return StringUtils.isBlank(string);
        }
        return false;
    }

}