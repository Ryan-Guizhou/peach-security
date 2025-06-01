package com.peach.security.vo;

import com.peach.security.entity.PeachMenuDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/1 15:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuVO extends PeachMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 已认证的资源
     */
    private List<ResourceVO> authResourceList;
}
