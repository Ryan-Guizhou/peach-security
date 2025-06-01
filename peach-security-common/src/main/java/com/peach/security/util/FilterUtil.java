package com.peach.security.util;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/30 20:17
 */
public class FilterUtil {

    /**
     *
     */
    public static List<String> CHECK_PATH_LIST = Lists.newArrayList();

    public static List<String> EXCLUDE_PATH_LIST = Arrays.asList("/doc.html", "/swagger-ui.html", "/swagger-resources/**",
            "/v2/api-docs", "/v3/api-docs", "/webjars/**",
            "/favicon.ico", "/error", "/csrf",
            "/.well-known/appspecific/com.chrome.devtools.json",
            "/**/*.js", "/**/*.css", "/**/*.png","/login","/init","/encrypt");

    public static List<String> getCheckPathList() {
        return CHECK_PATH_LIST;
    }

    public static List<String> getExcludePathList() {
        return EXCLUDE_PATH_LIST;
    }
//
//    static {
//        // knife4j相关的API
//        EXCLUDE_PATH_LIST.add("/doc.html");
//        EXCLUDE_PATH_LIST.add("/v2/api-docs");
//        EXCLUDE_PATH_LIST.add("/swagger-resources/**");
//        EXCLUDE_PATH_LIST.add("/swagger-ui.html");
//        EXCLUDE_PATH_LIST.add("/swagger-ui/**");
//        EXCLUDE_PATH_LIST.add("/webjars/**");
//        EXCLUDE_PATH_LIST.add("/favicon.ico");
//    }

}
