package com.peach.security;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/23 23:59
 */
@Data
public class CurrentUserDO implements Serializable {

    private String userId;

    private String account;

    private String userName;

    private String teamId;

    private String spaceId;

    private String language;

}
