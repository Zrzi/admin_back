package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.ExcelDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈群矜
 */
@Mapper
@Resource
public interface ExcelMapper {

    List<ExcelDto> findExcels();

    ExcelDto findExcelByExcelId(@Param("excelId") String excelId);

    Integer insertExcelDto(@Param("excelDto") ExcelDto excelDto);

    Integer updateExcelDto(@Param("excelDto") ExcelDto excelDto);

    Integer deleteExcelDto(@Param("excelDto") ExcelDto excelDto);

}
