package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.ExcelColumnDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Resource
public interface ExcelColumnMapper {

    List<ExcelColumnDto> findExcelColumnsByExcelId(@Param("excelId") String excelId);

    ExcelColumnDto findExcelColumnByColumnNames(@Param("excelColumn") String excelColumn, @Param("sqlColumn") String sqlColumn);

    Integer insertExcelColumn(@Param("excelColumnDto") ExcelColumnDto excelColumnDto);

    /**
     * 批量插入ExcelColumnDto数据
     * @param excelColumnDtos 数据列表
     * @return 影响行数
     */
    Integer batchInsertExcelColumns(List<ExcelColumnDto> excelColumnDtos);

    Integer deleteExcelColumn(@Param("excelColumnDto") ExcelColumnDto excelColumnDto);

    Integer batchDeleteExcelColumns(@Param("excelId") String excelId);

}
