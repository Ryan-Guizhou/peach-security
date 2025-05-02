package com.peach.security.exception;

import com.peach.common.util.StringUtil;
import com.peach.security.common.SecurityStatusEnum;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 用户登录异常
 * @CreateTime 2025/5/1 14:18
 */
public class AuthorityException extends RuntimeException {

    protected SecurityStatusEnum securityStatusEnum;

    protected String errorMsg = StringUtil.EMPTY;

    public AuthorityException(String message) {
        super(message);
    }

    public AuthorityException(SecurityStatusEnum securityStatusEnum) {
        this.securityStatusEnum = securityStatusEnum;
    }

    public AuthorityException(SecurityStatusEnum securityStatusEnum, String msg) {
        this.securityStatusEnum = securityStatusEnum;
        this.errorMsg = msg;
    }

    public SecurityStatusEnum getSecurityStatusEnum() {
        return securityStatusEnum;
    }

    public String getExtMsg() {
        if (StringUtil.isBlank(errorMsg)){
            return securityStatusEnum.getMsg();
        }
        return errorMsg;
    }
}
