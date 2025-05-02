package com.peach.security.qo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/1 20:20
 */
@Data
public class PeachMenuQO implements Serializable {

    private String menuId;

    private List<String> roleIdList;

    private List<String> menuIdList;

    public PeachMenuQO() {}
}
