package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.ExcelDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 陈群矜
 */
@Mapper
@Resource
public interface DataMapper {

    Integer selectCountDataByPrimaryKeys(@Param("excelDto") ExcelDto excelDto,
                                         @Param("map") Map<String, String> map);

    Integer insertData(@Param("excelDto") ExcelDto excelDto,
                       @Param("columnList") List<String> columnList,
                       @Param("valueList") List<String> valueList);

    Integer updateData(@Param("excelDto") ExcelDto excelDto,
                       @Param("keys") Map<String, String> keys,
                       @Param("primaryKeys") Map<String, String> primaryKeys);

}
