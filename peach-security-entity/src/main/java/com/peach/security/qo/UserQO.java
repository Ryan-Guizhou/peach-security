package com.peach.security.qo;

import com.peach.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/3/14 11:29
 */
@Data
public class UserQO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -789127319232464470L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户名称
     */
    private String userName;


    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 密码
     */
    private String password;
}
