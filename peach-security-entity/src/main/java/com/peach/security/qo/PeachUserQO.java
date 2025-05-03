package com.peach.security.qo;

import com.peach.common.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
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
public class PeachUserQO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> userIdList;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否删除 1 删除 0 未删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "用户状态")
    private Integer status;

    public PeachUserQO() {}
}
