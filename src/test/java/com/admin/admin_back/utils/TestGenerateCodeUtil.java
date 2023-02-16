package com.admin.admin_back.utils;

import com.admin.admin_back.pojo.enums.CodeTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestGenerateCodeUtil {

    @Test
    public void testGenerateCodeRole() {
        System.out.println("生成角色ID");
        System.out.println(GenerateCodeUtil.generateCode(CodeTypeEnum.ROLE));
    }

    @Test
    public void testGenerateCodeSystem() {
        System.out.println("生成系统ID");
        System.out.println(GenerateCodeUtil.generateCode(CodeTypeEnum.SYSTEM));
    }

    @Test
    public void testGenerateCodeResource() {
        System.out.println("生成资源ID");
        System.out.println(GenerateCodeUtil.generateCode(CodeTypeEnum.RESOURCE));
    }

}
