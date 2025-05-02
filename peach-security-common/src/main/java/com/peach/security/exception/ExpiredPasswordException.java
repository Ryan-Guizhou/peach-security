package com.peach.security.exception;


import com.peach.security.common.SecurityStatusEnum;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 密码过期异常
 * @CreateTime 2025/5/1 14:18
 */
public class ExpiredPasswordException extends AuthorityException {

    public ExpiredPasswordException(String message) {
        super(message);
    }

    public ExpiredPasswordException(SecurityStatusEnum securityStatusEnum) {
        super(securityStatusEnum);
    }
}
