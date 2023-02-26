package com.admin.admin_back.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRsaUtil {

    @Autowired
    private RsaUtil rsaUtil;

    @Test
    public void testRsaUtil() throws Exception {
        JSONObject object = new JSONObject();
        object.put("userNo", "0000000000000000");
        object.put("password", "111111");
        String objectString = JSON.toJSONString(object);
        System.out.println(objectString);
        String encoded = rsaUtil.encryptByPublicKey(objectString);
        System.out.println(encoded);
        String decoded = rsaUtil.decryptByPrivateKey(encoded);
        System.out.println(decoded);
    }

}
