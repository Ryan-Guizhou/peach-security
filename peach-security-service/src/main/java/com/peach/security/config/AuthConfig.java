package com.peach.security.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.peach.common.enums.StatusEnum;
import com.peach.common.response.Response;
import com.peach.security.util.FilterUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.servlet.DispatcherType;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description sa-token 配置类
 * @CreateTime 2025/5/30 19:44
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "sa-token")
public class AuthConfig {

    @Value("${isShare:false}")
    private Boolean isShare;

    @Value("${maxLoginCount:-1}")
    private Integer maxLoginCount;

    @Value("${timeout:6000}")
    private Long timeout;

    @Value("${tokenName:Authorization}")
    private String tokenName;

    @Value("${activeTimeout:-1}")
    private Integer activeTimeout;

    @Value("${isLog:true}")
    private Boolean isLog;

    @Value("${isConcurrent:true}")
    private Boolean isConcurrent;

    @Value("${isReadCookie:false}")
    private Boolean isReadCookie;


    /**
     * 优先注入sa-token相关配置信息
     * @return
     */
    @Bean
    @Primary
    public SaTokenConfig saTokenConfig() {
        SaTokenConfig saTokenConfig = new SaTokenConfig();
        saTokenConfig.setIsShare(isShare);
        saTokenConfig.setTokenName(tokenName);
        saTokenConfig.setTimeout(timeout);
        saTokenConfig.setActivityTimeout(activeTimeout);
        saTokenConfig.setIsConcurrent(isConcurrent);
        saTokenConfig.setMaxLoginCount(maxLoginCount);
        saTokenConfig.setIsReadCookie(isReadCookie);
        saTokenConfig.setIsLog(isLog);
        return saTokenConfig;
    }

    /**
     * 注入sa-token的相关过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<SaServletFilter> saServletFilter() {
        // 注册过滤器链代理
        final FilterRegistrationBean<SaServletFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SaServletFilter() // 指定 拦截路由 与 放行路由
                .addInclude("/**").addExclude(FilterUtil.getExcludePathList().toArray(new String[0]))
                // 认证函数: 每次请求执行
                .setAuth(obj -> {
//                    SaRouter.match("/**").check(StpUtil::checkLogin);
                })
                .setError(e -> {
                    // 设置响应头
                    SaHolder.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
                    if (e instanceof SaTokenException) {
                        String token;
                        String requestPath;
                        SaTokenException exception;
                        try {
                            token = SaHolder.getRequest().getHeader("Authorization");
                            if (StringUtils.isNotBlank(token)) {
                                StpUtil.logoutByTokenValue(token);
                            }
                            requestPath = SaHolder.getRequest().getRequestPath();
                            exception = (SaTokenException) e;
                            if (FilterUtil.getCheckPathList().contains(requestPath) && StringUtils.isBlank(token)) {
                                log.error("检测登录时，前端未传入token！");
                                return JSONUtil.toJsonStr(Response.commonResponse(StatusEnum.PARAM_ERROR, "token is null！"));
                            }
                            log.info(requestPath + ",token:" + token + ",code:" + exception.getCode() + ",message:" + exception.getMessage());
                        } catch (Exception ex) {
                            log.error("error:",e);
                        }
                    } else {
                        log.error("未知异常：" + e);
                    }
                    // 使用封装的 JSON 工具类转换数据格式
                    return JSONUtil.toJsonStr(Response.commonResponse(StatusEnum.PARAM_ERROR, "token is null！"));
                }));
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }
}
