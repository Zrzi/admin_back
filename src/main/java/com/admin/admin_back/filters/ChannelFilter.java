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
@WebFilter(filterName = "channelFilter", urlPatterns = {"/*"})
public class ChannelFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("channel filter init 。。。");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest request = null;
        RequestWrapper requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            request = (HttpServletRequest) servletRequest;
        }
        if (Objects.isNull(request)) {
            // 非HttpServletRequest，放行
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (ServletFileUpload.isMultipartContent(request)) {
            // 上传文件，放行
            filterChain.doFilter(request, servletResponse);
            return;
        }
        requestWrapper = new RequestWrapper(request);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

}
