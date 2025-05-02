package com.peach.security.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:14
 */
@Data
public class PeachRoleQO implements Serializable {

    private String roleId;

    private String roleCode;

    private String roleName;

    private String userCode;

    private List<String> roleIdList;

    public PeachRoleQO() {}
}
