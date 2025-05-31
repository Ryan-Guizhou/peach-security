package com.peach.security;

import lombok.Data;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/30 20:08
 */
@Data
public class CurrentContextEntity {

    /**
     * 语言
     */
    private String language;

    /**
     * 当前登录用户信息
     */
    private CurrentUserDO currentUserDO;
}
