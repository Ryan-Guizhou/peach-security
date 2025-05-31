package com.peach.security.filter;

import com.peach.common.request.AbstractWrapperFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 权限过滤器
 * @CreateTime 2025/5/30 19:43
 */
@Slf4j
public class PermissionFilter extends AbstractWrapperFilter implements Ordered {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
