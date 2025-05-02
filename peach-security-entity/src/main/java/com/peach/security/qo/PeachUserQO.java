package com.peach.security.qo;

import com.peach.security.entity.PeachUserDO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/2 17:57
 */
@Data
public class PeachUserQO extends PeachUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> userIdList;

    public PeachUserQO() {}
}
