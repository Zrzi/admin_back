package com.admin.admin_back.service.impl;

import com.admin.admin_back.service.ExcelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestExcelServiceImpl {

    @Autowired
    private ExcelService excelService;

    /**
     * 结论
     * excelService.testAsync() 插入数据成功
     * excelHelper.testAsync(code) 出现异常，没有更新数据
     * 属于不同事务，隔离
     */
    @Test
    public void test() {
        excelService.testAsync();
    }

}
