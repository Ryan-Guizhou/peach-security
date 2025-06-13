package com.peach.security.filter;

import cn.dev33.satoken.stp.StpUtil;
import com.peach.common.request.AbstractWrapperFilter;
import com.peach.common.util.StringUtil;
import com.peach.security.CurrentContext;
import com.peach.security.CurrentContextEntity;
import com.peach.security.constant.LanguageConstant;
import com.peach.security.util.FilterUtil;
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
 * @Description 上下文过滤器
 * @CreateTime 2025/5/30 19:41
 */
@Slf4j
public class ContextFilter extends AbstractWrapperFilter implements Ordered {


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 1. 解析 token
        String ticket = getTokenFromRequest(request);

        // 2. 解析语言
        String language = resolveLanguage(request);

        // 3. 构建上下文实体
        CurrentContextEntity context = new CurrentContextEntity();
        context.setLanguage(language);

        // 4. 填充当前用户信息（如果不是排除路径）
        if (!FilterUtil.getExcludePathList().contains(requestURI)) {
//            try {
//                Object loginId = StringUtil.isNotBlank(ticket) ? StpUtil.getLoginId(ticket) : StpUtil.getLoginId();
//                if (ObjectUtil.isNotNull(loginId)) {
//                    String userId = StringUtil.getStringValue(loginId).split(":")[2];
//                    CurrentUserDO currentUser = CaffeineUtil.get(userId, () -> {
//                        // 实际实现中应填充从数据库查用户信息逻辑
//                        IUserService instance = InstanceLazyLoader.getInstance(IUserService.class);
//                        PeachUserDO userInfo = instance.getUserInfo(userId);
//                        CurrentUserDO currentUserDO = new CurrentUserDO();
//                        currentUserDO.setUserId(userId);
//                        currentUserDO.setAccount(userInfo.getUserAccount());
//                        currentUserDO.setUserName(userInfo.getUserName());
//                        currentUserDO.setLanguage(language);
//                        return currentUserDO;
//                    });
//                    currentUser.setLanguage(language);
//                    context.setCurrentUserDO(currentUser);
//                } else {
//                    throw new RuntimeException("User not logged in, loginId: " + loginId);
//                }
//            } catch (Exception e) {
//                log.error("Failed to get user information. requestURI: [{}], ticket: [{}]", requestURI, ticket, e);
//                throw new RuntimeException("Failed to get user information", e);
//            }
        }

        // 5. 写入上下文
        CurrentContext.setCurrentEntity(context);
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Request processing failed:", e);
            throw e;
        } finally {
            CurrentContext.cleaCurrentEntity();
        }
    }

    /**
     * 提取 token
     * @param request
     * @return
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        try {
            String token = StpUtil.getTokenValue();
            if (StringUtil.isNotBlank(token)){
                return token;
            }
        } catch (Exception e) {
            log.warn("Token retrieval failed: {}", e.getMessage());
        }
        return request.getHeader("Authorization");
    }


    /**
     * 提取语言类型
     * @param request
     * @return
     */
    private String resolveLanguage(HttpServletRequest request) {
        String language = request.getHeader("language");
        if (StringUtil.isBlank(language)) {
            language = request.getHeader("Language");
        }
        if (StringUtil.isBlank(language)) {
            String acceptLang = request.getHeader("Accept-Language");
            if (StringUtil.isNotBlank(acceptLang)) {
                try {
                    String[] parts = acceptLang.toLowerCase().split(",");
                    for (String part : parts) {
                        String lang = part.split(";")[0].trim();
                        if (lang.startsWith("zh")) {
                            return "zh";
                        }
                        if (lang.startsWith("en")){
                            return "en";
                        }
                        if (lang.startsWith("ja")) {
                            return "ja";
                        }
                    }
                } catch (Exception e) {
                    log.warn("Parse Accept-Language failed: {}", e.getMessage());
                }
            }
        }
        return StringUtil.getStringValue(language, LanguageConstant.DEFAULT_LANGUAGE);
    }


    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public void destroy() {
        log.info("ConextFilter destroy");
    }
}
