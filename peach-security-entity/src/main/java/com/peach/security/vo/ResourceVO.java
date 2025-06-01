package com.peach.security.vo;

import com.peach.security.entity.PeachAppResourceDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/1 15:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceVO extends PeachAppResourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

}
