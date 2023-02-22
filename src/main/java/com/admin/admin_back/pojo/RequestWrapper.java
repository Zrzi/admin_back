package com.admin.admin_back.pojo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author 陈群矜
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    /**
     * 存储body数据的容器
     */
    private byte[] body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // 将body数据存储起来
        String bodyStr = getBodyString(request);
        body = bodyStr.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        String contentType = request.getContentType();
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(contentType)
                && (contentType.contains("multipart/form-data")
                || contentType.contains("x-www-form-urlencoded"))) {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                builder.append(name).append('=').append(request.getParameter(name)).append('&');
            }
            int length = builder.length();
            if (builder.charAt(length - 1) == '&') {
                builder.deleteCharAt(length - 1);
            }
            return builder.toString();
        }
        try {
            byte[] bytes = StreamUtils.copyToByteArray(request.getInputStream());
            builder.append(new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException ignored) {

        }
        return builder.toString();
    }

    /**
     * 修改body 将json 重新设置成body
     * @param val
     */
    public void setBody(String val){
        body = val.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

}
