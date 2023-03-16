package com.admin.admin_back.filters;

import com.admin.admin_back.pojo.RequestWrapper;
import com.admin.admin_back.pojo.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 陈群矜
 */
@Order(2)
@Component
public class ChannelFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (StringUtils.equals(request.getMethod(), HttpMethod.GET.name())) {
            // GET请求数据都在url上，不需要读取请求体，所以放行
            filterChain.doFilter(request, response);
            return;
        }
        if (check(request.getRequestURI())) {
            // 上传文件，放行
            filterChain.doFilter(request, response);
            return;
        }
        RequestWrapper requestWrapper = new RequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    /**
     * 判断是否需要放行
     * @param uri 请求uri
     * @return {@code true}表示需要放行
     */
    private boolean check(String uri) {
        for (String string : Constant.CHANNEL_FILTER_URI) {
            if (StringUtils.equals(uri, string)) {
                return true;
            }
        }
        return false;
    }

}
