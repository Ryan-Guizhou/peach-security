package com.peach.security.qo;

import com.peach.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/23 23:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PeachRouterQO extends BaseEntity {

    private String routerId;

    private String routerCode;

    public PeachRouterQO() {

    }
}
