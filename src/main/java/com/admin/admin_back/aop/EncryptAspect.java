package com.admin.admin_back.aop;

import com.admin.admin_back.annotations.SecurityAnnotation;
import com.admin.admin_back.pojo.Result;
import com.admin.admin_back.pojo.common.ResponseMessage;
import com.admin.admin_back.utils.AesUtil;
import com.admin.admin_back.utils.RsaUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author 陈群矜
 */
@Order(0)
@Aspect
@Component
public class EncryptAspect {

    @Autowired
    private RsaUtil rsaUtil;

    @Autowired
    private AesUtil aesUtil;

    @Pointcut("@annotation(com.admin.admin_back.annotations.SecurityAnnotation)")
    public void encryptPointCut() {}

    @Around("encryptPointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
            String method = request.getMethod();
            Object[] args = pjp.getArgs();
            if (StringUtils.equals(method, HttpMethod.POST.toString())) {
                MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
                SecurityAnnotation annotation = methodSignature.getMethod().getAnnotation(SecurityAnnotation.class);
                if (annotation.encrypted()) {
                    if (args.length > 0) {
                        String bodyString = getBodyString(request);
                        JSONObject jsonObject = JSON.parseObject(bodyString);
                        String key = jsonObject.getString("key");
                        String data = jsonObject.getString("data");
                        if (StringUtils.isNotBlank(key)) {
                            String decryptedKey = rsaUtil.decryptByPrivateKey(key);
                            String decryptedData = aesUtil.decrypt(data, decryptedKey);
                            args[0] = JSON.parseObject(decryptedData, args[0].getClass());
                        } else {
                            args[0] = JSON.parseObject(bodyString, args[0].getClass());
                        }
                    }
                }
            }
            return pjp.proceed(args);
        } catch (Throwable exception) {
            return new Result<>(ResponseMessage.SYSTEM_ERROR);
        }
    }

    private String getBodyString(HttpServletRequest request) {
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        try (InputStream inputStream = request.getInputStream()) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
