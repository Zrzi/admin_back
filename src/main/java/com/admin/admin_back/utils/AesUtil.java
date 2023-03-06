package com.admin.admin_back.utils;

import com.admin.admin_back.pojo.constant.Constant;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author 陈群矜
 */
@Component
public class AesUtil {

    /**
     * 16位
     * AES密钥，只在测试中使用，生产时，key由前端传入
     */
    public final static String KEY = "1234567890123456";

    /**
     * 参数分别代表 算法名称/加密模式/数据填充方式
     */
    private static final String ALGORITHM = Constant.AES_ALGORITHM;

    /**
     * AES加密
     * @param text 待加密的文本
     * @param key 密钥
     * @return 加密后的数据
     * @throws Exception
     */
    public String encrypt(String text, String key) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] bytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(bytes);
    }

    /**
     * AES解密
     * @param text 待解密的文本
     * @param key 密钥
     * @return 解密后的文本
     * @throws Exception
     */
    public String decrypt(String text, String key) throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        byte[] encryptBytes = Base64.decodeBase64(text);
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    /**
     * 加密文件
     * @param in 输入流 外部关闭
     * @param out 输出流 外部关闭
     * @param key 密钥
     * @throws Exception
     */
    public void encryptFile(InputStream in, OutputStream out, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        try (CipherInputStream cin = new CipherInputStream(in, cipher)) {
            byte[] cache = new byte[1024];
            int read = 0;
            while ((read = cin.read(cache)) != -1) {
                out.write(cache, 0, read);
                out.flush();
            }
        } catch (Exception exception) {
            throw new Exception();
        }
    }

    /**
     * 解密文件
     * @param in 输入流 外部关闭
     * @param out 输出流 外部关闭
     * @param key 密钥
     * @throws Exception
     */
    public void decryptFile(InputStream in, OutputStream out, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"));
        try (CipherOutputStream cout = new CipherOutputStream(out, cipher)) {
            byte[] cache = new byte[1024];
            int read = 0;
            while ((read = in.read(cache)) != -1) {
                cout.write(cache, 0, read);
                cout.flush();
            }
        } catch (Exception exception) {
            throw new Exception();
        }
    }

}
