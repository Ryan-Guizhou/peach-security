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
 * @CreateTime 2025/05/02 00:30
 */
@Data
@Table(name = "PEACH_APP_RESOURCE")
public class PeachAppResourceDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "RESOURCE_ID")
    @ApiModelProperty(value = "资源ID")
    private String resourceId;

    @Column(name = "FUNC_CODE")
    @ApiModelProperty(value = "功能编码")
    private String funcCode;

    @Column(name = "RESOURCE_TYPE")
    @ApiModelProperty(value = "资源类型")
    private String resourceType;

    @Column(name = "RESOURCE_CODE")
    @ApiModelProperty(value = "资源编码")
    private String resourceCode;

    @Column(name = "RESOURCE_NAME")
    @ApiModelProperty(value = "资源名称")
    private String resourceName;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "修改时间")
    private String modifyTime;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @Column(name = "IS_DELETE")
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @Column(name = "CREATOR_CODE")
    @ApiModelProperty(value = "创建者编码")
    private String creatorCode;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建者名称")
    private String creator;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachAppResourceDO.class));
    }
}
