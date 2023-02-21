package com.admin.admin_back.filters;

import com.admin.admin_back.pojo.RequestWrapper;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 陈群矜
 */
@Component
@WebFilter(filterName = "channelFilter", urlPatterns = {"/*"})
public class ChannelFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("channel filter init 。。。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = null;
        RequestWrapper requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            httpServletRequest = (HttpServletRequest) request;
        }
        if (Objects.isNull(httpServletRequest)) {
            // 非HttpServletRequest，放行
            filterChain.doFilter(request, response);
            return;
        }
        if (ServletFileUpload.isMultipartContent(httpServletRequest)) {
            // 上传文件，放行
            filterChain.doFilter(httpServletRequest, response);
            return;
        }
        requestWrapper = new RequestWrapper(httpServletRequest);
        filterChain.doFilter(requestWrapper, response);
    }

}
