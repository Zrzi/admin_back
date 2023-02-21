package com.admin.admin_back.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

/**
 * @author 陈群矜
 */
@Component
public class RsaUtil {

    private final static String ALGORITHM = "RSA";
    private final static String PUBLIC_KEY = "RSA_PUBLIC_KEY";
    private final static String PRIVATE_KEY = "RSA_PRIVATE_KEY";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 私钥解密
     * @param text 待解密的文本
     * @return 解密后的文本
     * @throws Exception
     */
    public String decryptByPrivateKey(String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec =
                new PKCS8EncodedKeySpec(Base64.decodeBase64(getPrivateKey()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    /**
     * 公钥加密
     * @param text 待加密的文本
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encryptByPublicKey(String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec =
                new X509EncodedKeySpec(Base64.decodeBase64(getPublicKey()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    public String getPublicKey() {
        return (String) Optional.ofNullable(redisTemplate.opsForValue().get(PUBLIC_KEY)).orElseGet(String::new);
    }

    private String getPrivateKey() {
        return (String) Optional.ofNullable(redisTemplate.opsForValue().get(PRIVATE_KEY)).orElseGet(String::new);
    }

    private void generateKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(publicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(privateKey.getEncoded());
        redisTemplate.opsForValue().set(PUBLIC_KEY, publicKeyString);
        redisTemplate.opsForValue().set(PRIVATE_KEY, privateKeyString);
    }

    @PostConstruct
    public void postConstruct() throws NoSuchAlgorithmException {
        this.generateKey();
    }

}
