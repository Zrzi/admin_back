package com.admin.admin_back.interceptor;

//import com.admin.admin_back.annotations.SecurityAnnotation;
//import com.admin.admin_back.pojo.RequestWrapper;
//import com.admin.admin_back.pojo.Result;
//import com.admin.admin_back.pojo.common.ResponseMessage;
//import com.admin.admin_back.utils.AesUtil;
//import com.admin.admin_back.utils.RsaUtil;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.Objects;
//import java.util.Optional;

/**
 * @author 陈群矜
 */
//@Component
//public class SecurityInterceptor implements HandlerInterceptor {
//
//    private final static Logger LOGGER = LogManager.getLogger(SecurityInterceptor.class);
//
//    @Autowired
//    private RsaUtil rsaUtil;
//
//    @Autowired
//    private AesUtil aesUtil;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        // 默认情况是不进行解密
//        boolean decode = false ;
//        // 如果不是映射到方法直接通过
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod ();
//        String httpMethod = request.getMethod() ;
//        SecurityAnnotation annotation  = handlerMethod.getMethodAnnotation(SecurityAnnotation.class) ;
//
//        // 不拦截GET请求
//        if (StringUtils.equals(httpMethod, HttpMethod.GET.toString())) {
//            return true;
//        } else if (StringUtils.equals(httpMethod, HttpMethod.POST.toString())) {
//            String contentType = request.getContentType();
//            // 如果类型是空就放行
//            if (StringUtils.isBlank(contentType)) {
//                return true;
//            }
//            // 如果类型不是application/json，放行
//            if (!contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
//                return true;
//            }
//            // 没有SecurityAnnotation注释，放行
//            if (Objects.isNull(annotation)) {
//                return true;
//            }
//            RequestWrapper requestWrapper = new RequestWrapper(request);
//            String jsonParamBody = requestWrapper.getBodyString(request);
//            JSONObject jsonObject = JSON.parseObject(jsonParamBody);
//            Object decrypted = jsonObject.get("decrypted");
//            if (Objects.isNull(decrypted) || StringUtils.isBlank(decrypted.toString())) {
//                // decrypted字段为空，没有加密，放行
//                return true;
//            }
//            String content = Optional.ofNullable(jsonObject.get("content")).orElseGet(String::new).toString();
//            String decryptedKey = Optional.of(jsonObject.get("content")).orElseGet(String::new).toString();
//            if (StringUtils.isBlank(content)) {
//                Result<?> result = new Result<>(ResponseMessage.CONTENT_NOT_FOUND);
//                handleFalse(response, result);
//                return false;
//            }
//            if (StringUtils.isBlank(decryptedKey)) {
//                Result<?> result = new Result<>(ResponseMessage.KEY_NOT_FOUND);
//                handleFalse(response, result);
//                return false;
//            }
//            try {
//                // 获取aes密钥
//                String key = rsaUtil.decryptByPrivateKey(decryptedKey);
//                // 获取原始数据
//                String dataStr = aesUtil.decrypt(content, key);
//                // 将原始数据放入request body中
//                requestWrapper.setBody(dataStr);
//            } catch (Exception exception) {
//                LOGGER.warn("SecurityInterceptor执行过程中出现异常，错误信息：{}", exception.getMessage());
//                Result<?> result = new Result<>(ResponseMessage.SYSTEM_ERROR);
//                handleFalse(response, result);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void handleFalse(HttpServletResponse response, Result<?> result) throws IOException {
//        response.setStatus(200);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(JSON.toJSONString(result));
//        response.getWriter().flush();
//    }
//
//}
