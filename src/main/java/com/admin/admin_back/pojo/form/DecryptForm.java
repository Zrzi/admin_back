package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;

/**
 * @author 陈群矜
 */
public class DecryptForm {

    /**
     * 待解密的内容
     */
    private String content;

    /**
     * 经过Rsa加密后的Aes密钥
     */
    private String encryptedKey;

    public DecryptForm() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEncryptedKey() {
        return encryptedKey;
    }

    public void setEncryptedKey(String encryptedKey) {
        this.encryptedKey = encryptedKey;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
