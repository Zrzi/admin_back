package com.admin.admin_back.mapper;

import com.admin.admin_back.pojo.constant.Constant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSqlMapper {

    @Autowired
    private SqlMapper sqlMapper;

    @Test
    public void testFindSqlConstraints() {
        System.out.println(sqlMapper.findSqlConstraintByType(Constant.TABLE_SCHEMA, "admin_system", Constant.CONSTRAINT_TYPE_PRIMARY_KEY));
        System.out.println(sqlMapper.findSqlConstraintByType(Constant.TABLE_SCHEMA, "admin_system", Constant.CONSTRAINT_TYPE_UNIQUE));
    }

    @Test
    public void testFindSqlColumnInfos() {
        System.out.println(sqlMapper.findSqlColumnInfos(Constant.TABLE_SCHEMA, "admin_system"));
    }

}
