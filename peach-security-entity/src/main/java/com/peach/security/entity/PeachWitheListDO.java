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
 * @CreateTime 2025/6/4 22:44
 */
@Data
@Table(name = "PEACH_WITHE_LIST")
public class PeachWitheListDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty("主键")
    private String id;

    @Column(name = "IP_ADDRESS")
    @ApiModelProperty("ip地址")
    private String ipAddress;

    @Column(name = "DESCRIPTION")
    @ApiModelProperty("描述")
    private String description;

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

    @Column(name = "STATUS")
    @ApiModelProperty("状态 1 启用 2 禁用")
    private Integer status;

    @Column(name = "TYPE")
    @ApiModelProperty("类型")
    private String type;

    @Column(name = "SCOPE")
    @ApiModelProperty("作用域")
    private String scope;


    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachWitheListDO.class));
    }
}
