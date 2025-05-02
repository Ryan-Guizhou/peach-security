package com.peach.security.entity;

import com.peach.common.generator.MapperGenerator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 2025/05/02 18:13
 */
@Data
@Table(name = "PEACH_ROLE")
public class PeachRoleDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROLE_ID")
    @ApiModelProperty(value = "角色ID，主键")
    private String roleId;

    @Column(name = "ROLE_CODE")
    @ApiModelProperty(value = "角色编码，唯一标识角色")
    private String roleCode;

    @Column(name = "ROLE_NAME")
    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建人ID")
    private String creator;

    @Column(name = "CREATOR_NAME")
    @ApiModelProperty(value = "创建人姓名")
    private String creatorName;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间，格式: yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @Column(name = "MODIFIER")
    @ApiModelProperty(value = "修改人ID")
    private String modifier;

    @Column(name = "MODIFIER_NAME")
    @ApiModelProperty(value = "修改人姓名")
    private String modifierName;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "修改时间，格式: yyyy-MM-dd HH:mm:ss")
    private String modifyTime;

    @Column(name = "IS_DELETED")
    @ApiModelProperty(value = "是否删除：0-未删除，1-已删除")
    private Integer isDeleted;

    @Column(name = "ROLE_DESC")
    @ApiModelProperty(value = "角色描述，详细说明角色用途")
    private String roleDesc;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachRoleDO.class));
    }
}
