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
 * @CreateTime 2025/6/1 13:07
 */
@Data
@Table(name = "PEACH_CONFIG")
public class PeachConfigDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "主键")
    private String id;

    @Column(name = "validateType")
    @ApiModelProperty(value = "认证类型")
    private String validateType;

    @Column(name = "TITLE")
    @ApiModelProperty(value = "标题")
    private String title;

    @Column(name = "BACKGROUND_URL")
    @ApiModelProperty(value = "背景图片地址")
    private String backgroundUrl;

    @Column(name = "COPY_RIGHT")
    @ApiModelProperty(value = "版权信息")
    private String copyRight;

    @Column(name = "LOGO_URL")
    @ApiModelProperty(value = "logo图片")
    private String logoUrl;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建者")
    private String creator;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @Column(name = "UPDATE_TIME")
    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @Column(name = "COLOR")
    @ApiModelProperty(value = "系统统一色调")
    private String color;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachConfigDO.class));
    }

}
