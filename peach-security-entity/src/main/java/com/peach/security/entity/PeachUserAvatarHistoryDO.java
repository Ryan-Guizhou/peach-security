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
 * @CreateTime 2025/6/20 23:41
 */
@Data
@Table(name = "PEACH_USER_AVATAR_HISHTORY")
public class PeachUserAvatarHistoryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "USER_ID")
    @ApiModelProperty("用户主键")
    private String userId;

    @Column(name = "AVATAR_URL")
    @ApiModelProperty("头像URL")
    private String avatarUrl;

    @Column(name = "CHANGE_REASON")
    @ApiModelProperty("更换原因(用户上传:USER_UPLOAD,系统默认:SYSTEM_UPLOAD)")
    private String changeReason;

    @Column(name = "IS_CURRENT")
    @ApiModelProperty("是否当前头像(1:是,0:否)")
    private Integer isCurrent;

    @Column(name = "FILE_MD5")
    @ApiModelProperty("头像的MD5值")
    private String fileMd5;

    @Column(name = "CREATOR")
    @ApiModelProperty("创建者编码")
    private String creator;

    @Column(name = "CREATOR_NAME")
    @ApiModelProperty("创建者名称")
    private String creatorName;

    @Column(name = "MODIFIER")
    @ApiModelProperty("更新者")
    private String modifier;

    @Column(name = "MODIFIER_NAME")
    @ApiModelProperty("更新者名称")
    private String modifierName;

    @Column(name = "CREATOR_TIME")
    @ApiModelProperty("创建时间")
    private String createTime;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty("创建者时间")
    private String modifyTime;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachUserAvatarHistoryDO.class));
    }
}
