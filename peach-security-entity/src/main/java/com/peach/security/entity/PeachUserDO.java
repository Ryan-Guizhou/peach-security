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
 * @CreateTime 2025/05/01 18:37
 */
@Data
@Table(name = "PEACH_USER")
public class PeachUserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "用户ID")
    private String id;

    @Column(name = "USER_ACCOUNT")
    @ApiModelProperty(value = "用户编码")
    private String userAccount;

    @Column(name = "USER_NAME")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @Column(name = "PASSWORD")
    @ApiModelProperty(value = "用户密码")
    private String password;

    @Column(name = "EMAIL")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @Column(name = "USER_AVATAR")
    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @Column(name = "USER_PROFILE")
    @ApiModelProperty(value = "用户简介")
    private String userProfile;

    @Column(name = "PHONE")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @Column(name = "MODIFIER_TIME")
    @ApiModelProperty(value = "更新时间")
    private String modifierTime;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建者编码")
    private String creator;

    @Column(name = "CREATOR_NAME")
    @ApiModelProperty(value = "创建者名称")
    private String creatorName;

    @Column(name = "MODIFIER")
    @ApiModelProperty(value = "更新者编码")
    private String modifier;

    @Column(name = "MODIFIER_NAME")
    @ApiModelProperty(value = "更新者名称")
    private String modifierName;

    @Column(name = "IS_DELETED")
    @ApiModelProperty(value = "是否删除")
    private Integer isDeleted;

    @Column(name = "STATUS")
    @ApiModelProperty(value = "用户状态,1:启用,2:禁用")
    private Integer status;

    @Column(name = "INVLIDATE")
    @ApiModelProperty(value = "过期时间")
    private java.util.Date invlidate;

    @Column(name = "IDENTITY_CODE")
    @ApiModelProperty(value = "身份证号")
    private String identityCode;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachUserDO.class));
    }
}
