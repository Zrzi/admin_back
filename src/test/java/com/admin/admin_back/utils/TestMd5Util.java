package com.admin.admin_back.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMd5Util {

    @Test
    public void testEncrypt() {
        String password = "111111";
        System.out.println(Md5Util.encrypt(password));
    }

}
