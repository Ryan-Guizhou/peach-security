package com.peach.security.exception;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description 校验异常
 * @CreateTime 04 3月 2025 21:30
 */
public class RegisterException extends RuntimeException{

    public RegisterException(String message) {
        super(message);
    }
}
