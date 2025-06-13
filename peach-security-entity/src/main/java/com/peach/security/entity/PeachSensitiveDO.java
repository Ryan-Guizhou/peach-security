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
 * @CreateTime 2025/06/11 22:41
 */
@Data
@Table(name = "PEACH_SENSITIVE")
public class PeachSensitiveDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键ID")
    private String id;

    @Column(name = "WORD")
    @ApiModelProperty(value = "敏感词内容")
    private String word;

    @Column(name = "TYPE")
    @ApiModelProperty(value = "敏感词类型（如：political=政治类、porn=色情类、violence=暴力类等）")
    private String type;

    @Column(name = "MATCH_TYPE")
    @ApiModelProperty(value = "匹配类型（0=精准匹配，1=模糊匹配）")
    private Integer matchType;

    @Column(name = "STATUS")
    @ApiModelProperty(value = "状态（0=禁用，1=启用）")
    private Integer status;

    @Column(name = "DESCRIPTION")
    @ApiModelProperty(value = "敏感词说明或备注")
    private String description;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建人编码")
    private String creator;

    @Column(name = "CREATORNAME")
    @ApiModelProperty(value = "创建人姓名")
    private String creatorname;

    @Column(name = "MODIFIER")
    @ApiModelProperty(value = "修改人编码")
    private String modifier;

    @Column(name = "MODIFIERNAME")
    @ApiModelProperty(value = "修改人姓名")
    private String modifiername;

    @Column(name = "CREATETIME")
    @ApiModelProperty(value = "创建时间（字符串格式）")
    private String createtime;

    @Column(name = "MODIFYTIME")
    @ApiModelProperty(value = "修改时间（字符串格式）")
    private String modifytime;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachSensitiveDO.class));
    }
}
