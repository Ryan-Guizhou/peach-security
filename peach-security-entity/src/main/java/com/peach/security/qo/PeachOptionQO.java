package com.peach.security.qo;

import com.peach.common.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/5/23 23:08
 */
@Data
public class PeachOptionQO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private List<String> idList;


}
