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
public class LoginRequestInfo implements Serializable {

    private static final long serialVersionUID = -7489971563035642073L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码滑块令牌
     */
    private String token;

    /**
     * 请求Ip地址
     */
    private String remoteIp;

    /**
     * 验证类型
     */
    private String validateType;

    /**
     * 滑块验证X轴坐标
     */
    private Integer X;

}
