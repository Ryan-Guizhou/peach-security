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
 * @CreateTime 2025/05/02 00:39
 */
@Data
@Table(name = "PEACH_AUTH_RESOURCE")
public class PeachAuthResourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESOURCE_ID")
    @ApiModelProperty(value = "逻辑ID，唯一标识资源")
    private String resourceId;

    @Column(name = "PARTY_CODE")
    @ApiModelProperty(value = "参与者代码，标识用户或角色")
    private String partyCode;

    @Column(name = "FUNC_CODE")
    @ApiModelProperty(value = "功能代码，标识系统功能")
    private String funcCode;

    @Column(name = "OP_TYPE")
    @ApiModelProperty(value = "操作类型，如：READ、WRITE、DELETE")
    private String opType;

    @Column(name = "RESOURCE_CODE")
    @ApiModelProperty(value = "资源编码，标识具体资源")
    private String resourceCode;

    @Column(name = "RESOURCE_NAME")
    @ApiModelProperty(value = "资源名称，资源的展示名称")
    private String resourceName;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "最新修改时间")
    private String modifyTime;

    @Column(name = "IS_DELETE")
    @ApiModelProperty(value = "是否删除：0-未删除，1-已删除")
    private Integer isDelete;

    @Column(name = "CREATOR_CODE")
    @ApiModelProperty(value = "创建者编码")
    private String creatorCode;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建者名称")
    private String creator;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachAuthResourceDO.class));
    }
}
