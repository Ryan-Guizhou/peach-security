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
 * @CreateTime 2025/06/04 23:01
 */
@Data
@Table(name = "PEACH_OPTION")
public class PeachOptionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "OPT_CODE")
    @ApiModelProperty(value = "值集编码（如 GENDER）")
    private String optCode;

    @Column(name = "OPT_VALUE")
    @ApiModelProperty(value = "值（如 MALE）")
    private String optValue;

    @Column(name = "OPT_DESC")
    @ApiModelProperty(value = "描述")
    private String optDesc;

    @Column(name = "PARENT_CODE")
    @ApiModelProperty(value = "父级值，用于层级关系")
    private String parentCode;

    @Column(name = "STATUS")
    @ApiModelProperty(value = "状态：1-启用，0-禁用")
    private Integer status;

    @Column(name = "SORT_ORDER")
    @ApiModelProperty(value = "排序号，越小越靠前")
    private Integer sortOrder;

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
        System.out.println(MapperGenerator.genMapper(PeachOptionDO.class));
    }
}
