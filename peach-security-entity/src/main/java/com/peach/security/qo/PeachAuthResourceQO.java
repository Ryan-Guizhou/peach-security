package com.peach.security.qo;

import com.peach.security.entity.PeachAuthResourceDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/05/02 00:39
 */
@Data
public class PeachAuthResourceQO extends PeachAuthResourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "已授权资源ID集合")
    private List<String> resourceIdList;

    @ApiModelProperty(value = "已授权资源功能编码集合")
    private List<String> funcCodeList;

    @ApiModelProperty(value = "已授权资源编码集合")
    private List<String> resourceCodeList;

}
