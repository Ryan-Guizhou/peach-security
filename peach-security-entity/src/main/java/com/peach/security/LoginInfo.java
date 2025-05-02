package com.peach.security;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 11:18
 */
@Data
public class LoginInfo implements Serializable {

    private static final long serialVersionUID = -7489971563035642073L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名称
     */
    private String userName;


    /**
     * 令牌
     */
    private String token;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 邮件
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

}
