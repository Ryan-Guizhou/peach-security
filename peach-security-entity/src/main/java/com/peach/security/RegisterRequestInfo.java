package com.peach.security;

import com.peach.common.anno.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 11:18
 */
@Data
public class RegisterRequestInfo implements Serializable {

    private static final long serialVersionUID = -7489971563035642073L;

    /**
     * 用户账号
     */
    @NotBlank
    private String userAccount;

    /**
     * 密码
     */
    @javax.validation.constraints.NotBlank
    private String password;


    /**
     * 重复密码
     */
    @javax.validation.constraints.NotBlank
    private String repeatPassword;

}
