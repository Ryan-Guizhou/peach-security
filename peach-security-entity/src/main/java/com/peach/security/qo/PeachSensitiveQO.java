package com.peach.security.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/6/4 23:55
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PeachSensitiveQO implements Serializable {

    private static final long serialVersionUID = 1L;

    List<String> idList;

    public PeachSensitiveQO() {

    }
}
