package com.admin.admin_back.controller;

import com.admin.admin_back.annotations.LogAnnotation;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.pojo.form.DecryptForm;
import com.admin.admin_back.utils.AesUtil;
import com.admin.admin_back.utils.RsaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 安全相关接口，提供给其它系统使用
 * 目前的想法是，前端获取Rsa公钥，先用AES加密数据，再用Rsa公钥加密AES密钥
 * 所以，后端先用Rsa私钥机密获取AES密钥，再解密获取真实数据
 * @author 陈群矜
 */
@Api(tags = "安全相关接口")
@Controller
public class SecurityController {

    private final static Logger logger = LogManager.getLogger(SecurityController.class);

    @Autowired
    private RsaUtil rsaUtil;

    @Autowired
    private AesUtil aesUtil;

    @ApiOperation("获取服务器RSA公钥")
    @LogAnnotation
    @ResponseBody
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
    // todo @NoRepeatSubmit
    @LogAnnotation(outEnabled = false)
    @ResponseBody
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

    /**
     * 返回的类型为application/octet-stream，restTemplate可以用Resource接受
     * @param file 待解密的文件
     * @param encryptedKey 被RSA公钥加密的AES密钥
     * @param response
     */
    @ApiOperation("解密文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(name = "encryptedKey", value = "密钥", required = true)
    })
    // todo @NoRepeatSubmit
    @LogAnnotation(inEnabled = false, outEnabled = false)
    @PostMapping(value = "/decryptFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void decryptFile(@RequestPart("file") MultipartFile file,
                            @RequestParam("encryptedKey") String encryptedKey,
                            HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        if (Objects.isNull(file) || file.isEmpty()) {
            // 文件为空
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        if (StringUtils.isBlank(encryptedKey)) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        String originalFilename = file.getOriginalFilename();
        response.addHeader("Content-disposition", "attachment; filename=" + originalFilename);
        try (InputStream input = file.getInputStream(); OutputStream output = response.getOutputStream()) {
            String key = rsaUtil.decryptByPrivateKey(encryptedKey);
            aesUtil.decryptFile(input, output, key);
        } catch (Exception exception) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            logger.warn(exception.getMessage());
        }
    }

}
