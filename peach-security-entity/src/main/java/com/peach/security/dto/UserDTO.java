package com.peach.security.dto;

import com.peach.common.generator.EntityGenerator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 16:29
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -7594164027250213006L;

    @ApiModelProperty(value = "用户名",required = true)
    private String username;

    @ApiModelProperty(value = "用户账号",required = true)
    private String userAccount;

    @ApiModelProperty(value = "电子邮箱",required = true)
    private String email;

    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @ApiModelProperty(value = "用户密码",required = true)
    private String password;

    @ApiModelProperty(value = "用户简介",required = false)
    private String userProfile;

    public static void main(String[] args) {
        EntityGenerator.generateEntity("PEACH_USER");
    }
}
