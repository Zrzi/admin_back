package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.dto.SqlColumnInfoDto;
import com.admin.admin_back.pojo.dto.SqlConstraintDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询数据库表名、列名
 * @author 陈群矜
 */
@Mapper
@Resource
public interface SqlMapper {

    List<String> findSqlTableNames(@Param("dataBaseName") String dataBaseName);

    List<String> findSqlColumnNames(@Param("dataBaseName") String dataBaseName, @Param("sqlTableName") String sqlTableName);

    SqlColumnInfoDto findSqlColumnInfo(@Param("dataBaseName") String dataBaseName,
                                       @Param("sqlTableName") String sqlTableName,
                                       @Param("sqlColumnName") String sqlColumnName);

    List<SqlColumnInfoDto> findSqlColumnInfos(@Param("dataBaseName") String dataBaseName,
                                              @Param("sqlTableName") String sqlTableName);

    List<SqlConstraintDto> findSqlConstraintByType(@Param("dataBaseName") String dataBaseName,
                                                   @Param("sqlTableName") String sqlTableName,
                                                   @Param("constraintType") String constraintType);

}
