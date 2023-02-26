package com.admin.admin_back.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAesUtil {

    @Autowired
    private AesUtil aesUtil;

    @Test
    public void testAesUtil() throws Exception {
        String key = AesUtil.KEY;
        JSONObject object = new JSONObject();
        object.put("userNo", "0000000000000000");
        object.put("password", "111111");
        String objectString = JSON.toJSONString(object);
        System.out.println(objectString);
        String encrypted = aesUtil.encrypt(objectString, key);
        System.out.println(encrypted);
        String decrypted = aesUtil.decrypt(encrypted, key);
        System.out.println(decrypted);
    }

    @Test
    public void testEncryptFile() {
        String srcPath = "D:\\申请\\cornell成绩单_1.pdf";
        String destPath = "D:\\申请\\cornell成绩单_2.pdf";
        File src = new File(srcPath);
        File dest = new File(destPath);
        InputStream input = null;
        OutputStream output = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            input = new FileInputStream(src);
            output = new FileOutputStream(dest);
            aesUtil.encryptFile(input, output, AesUtil.KEY);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testDecryptFile() {
        String srcPath = "D:\\申请\\cornell成绩单_2.pdf";
        String destPath = "D:\\申请\\cornell成绩单_3.pdf";
        File src = new File(srcPath);
        File dest = new File(destPath);
        InputStream input = null;
        OutputStream output = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            input = new FileInputStream(src);
            output = new FileOutputStream(dest);
            aesUtil.decryptFile(input, output, AesUtil.KEY);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
