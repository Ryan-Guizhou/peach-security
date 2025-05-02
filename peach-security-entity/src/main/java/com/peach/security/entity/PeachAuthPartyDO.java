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
 * @CreateTime 2025/05/02 15:39
 */
@Data
@Table(name = "PEACH_AUTH_PARTY")
public class PeachAuthPartyDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "ROLE_CODE")
    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    @Column(name = "ROLE_TYPE")
    @ApiModelProperty(value = "角色类型(role)")
    private String roleType;

    @Column(name = "FISCAL")
    @ApiModelProperty(value = "年度")
    private Integer fiscal;

    @Column(name = "PARTY_CODE")
    @ApiModelProperty(value = "参与者代码")
    private String partyCode;

    @Column(name = "PARTY_TYPE")
    @ApiModelProperty(value = "参与者类型(user/org/position)")
    private String partyType;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "最新修改时间")
    private String modifyTime;

    @Column(name = "IS_DELETE")
    @ApiModelProperty(value = "是否删除")
    private String isDelete;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建名称")
    private String creator;

    @Column(name = "CREATOR_CODE")
    @ApiModelProperty(value = "创建者编码")
    private String creatorCode;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachAuthPartyDO.class));
    }
}
