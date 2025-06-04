package com.peach.security.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PeachMenuQO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuId;

    private List<String> roleIdList;

    private List<String> menuIdList;

    public PeachMenuQO() {}
}
