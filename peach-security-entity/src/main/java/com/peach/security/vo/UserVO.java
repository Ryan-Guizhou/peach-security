package com.peach.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 26 2月 2025 20:11
 */
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = -5036117724476161563L;

    private String id;

    private String userAccount;

    private String userName;

    private String email;

    private String userAvatar;

    private String userProfile;

    private String phone;

    private String createTime;

    private String modifierTime;

    private String creator;

    private String creatorName;

    private String modifier;

    private String modifierName;

    private Integer isDeleted;

    private Integer status;

}
