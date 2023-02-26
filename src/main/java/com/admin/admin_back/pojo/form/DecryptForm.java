package com.admin.admin_back.pojo.form;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 陈群矜
 */
@ApiModel("解密接口相关信息")
public class DecryptForm {

    /**
     * 待解密的内容
     */
    @ApiModelProperty(value = "待解密的内容", required = true)
    private String content;

    /**
     * 经过Rsa加密后的Aes密钥
     */
    @ApiModelProperty(value = "经过Rsa加密后的Aes密钥", required = true)
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
