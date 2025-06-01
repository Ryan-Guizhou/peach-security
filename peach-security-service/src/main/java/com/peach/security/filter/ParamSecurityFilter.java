package com.peach.security.filter;

import com.peach.common.request.AbstractWrapperFilter;
import com.peach.common.util.StringUtil;
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
 * @Description //TODO
 * @CreateTime 2025/5/30 19:53
 */
@Slf4j
public class ParamSecurityFilter extends AbstractWrapperFilter implements Ordered {

    /**
     * 分页字段
     */
    private static final String PARAM_FIELD = "pageSize";

    /**
     * 最大的分页参数值为100
     */
    private static final Integer MAX_PAGE_SIZE = 100;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String pageSize = request.getParameter(PARAM_FIELD);
        if (pageSize != null) {
            int size = Integer.parseInt(pageSize);
            pageSize = StringUtil.getStringValue(Math.min(size, MAX_PAGE_SIZE));
            request.setAttribute(PARAM_FIELD, pageSize);
            filterChain.doFilter(request, response);
        }else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
