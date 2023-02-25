package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.annotations.NoRepeatSubmit;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.form.DecryptForm;
import com.admin.admin_back.utils.AesUtil;
import com.admin.admin_back.utils.RsaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 安全相关接口，提供给其它系统使用
 * 目前的想法是，前端获取Rsa公钥，先用AES加密数据，再用Rsa公钥加密AES密钥
 * 所以，后端先用Rsa私钥机密获取AES密钥，再解密获取真实数据
 * @author 陈群矜
 */
@Api(tags = "安全相关接口")
@RestController
public class SecurityController {

    @Autowired
    private RsaUtil rsaUtil;

    @Autowired
    private AesUtil aesUtil;

    @ApiOperation("获取服务器RSA公钥")
    @LogAnnotation
    @GetMapping("/getRsaPublicKey")
    public Result<?> getRsaPublicKey() {
        try {
            String publicKey = rsaUtil.getPublicKey();
            if (StringUtils.isBlank(publicKey)) {
                return new Result<>(ResponseMessage.SYSTEM_ERROR);
            }
            Result<String> result = new Result<>(ResponseMessage.SUCCESS);
            result.setData(publicKey);
            return result;
        } catch (Exception exception) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

    @ApiOperation("解密接口")
    @NoRepeatSubmit
    @LogAnnotation(outEnabled = false)
    @PostMapping("/decrypt")
    public Result<?> decrypt(@RequestBody DecryptForm decryptForm) {
        try {
            String content = decryptForm.getContent();
            String encryptedKey = decryptForm.getEncryptedKey();
            if (StringUtils.isBlank(content)) {
                return new Result<>(ResponseMessage.CONTENT_NOT_FOUND);
            }
            if (StringUtils.isBlank(encryptedKey)) {
                return new Result<>(ResponseMessage.KEY_NOT_FOUND);
            }
            String key = rsaUtil.decryptByPrivateKey(encryptedKey);
            String data = aesUtil.decrypt(content, key);
            return new Result<>(ResponseMessage.SUCCESS, data);
        } catch (Exception exception) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

}
