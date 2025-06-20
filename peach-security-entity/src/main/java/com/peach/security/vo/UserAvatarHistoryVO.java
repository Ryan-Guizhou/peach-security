package com.peach.security.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/20 23:57
 */
@Data
public class UserAvatarHistoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String avatarUrl;

}
