package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.ExcelDto;
import com.alibaba.fastjson.JSONObject;
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
                                         @Param("primaryKeys") Map<String, Object> primaryKeys);

    Integer selectCountDataByUniqueKeys(@Param("excelDto") ExcelDto excelDto,
                                        @Param("uniqueKeys") Map<String, Object> uniqueKeys);

//    Integer insertData(@Param("excelDto") ExcelDto excelDto,
//                       @Param("columnList") List<String> columnList,
//                       @Param("valueList") List<String> valueList);

    Integer insertData(@Param("excelDto") ExcelDto excelDto,
                       @Param("keys") List<String> data,
                       @Param("values") List<Object> values);

    Integer updateData(@Param("excelDto") ExcelDto excelDto,
                       @Param("data") Map<String, Object> data,
                       @Param("primaryKeys") Map<String, Object> primaryKeys);

}
