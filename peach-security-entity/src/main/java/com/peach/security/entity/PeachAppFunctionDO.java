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
 * @CreateTime 2025/05/02 01:04
 */
@Data
@Table(name = "PEACH_APP_FUNCTION")
public class PeachAppFunctionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "FUNC_ID")
    @ApiModelProperty(value = "功能ID")
    private String funcId;

    @Column(name = "FUNC_CODE")
    @ApiModelProperty(value = "功能编码")
    private String funcCode;

    @Column(name = "PARENT_FUNC_CODE")
    @ApiModelProperty(value = "父功能编码")
    private String parentFuncCode;

    @Column(name = "FUNC_NAME")
    @ApiModelProperty(value = "功能名称")
    private String funcName;

    @Column(name = "FUNC_DESC")
    @ApiModelProperty(value = "功能描述")
    private String funcDesc;

    @Column(name = "FUNC_URL")
    @ApiModelProperty(value = "功能URL")
    private String funcUrl;

    @Column(name = "FUNC_SEQ")
    @ApiModelProperty(value = "功能序列号")
    private String funcSeq;

    @Column(name = "FUNC_TYPE")
    @ApiModelProperty(value = "功能类型")
    private String funcType;

    @Column(name = "IS_MENU")
    @ApiModelProperty(value = "是否菜单")
    private Integer isMenu;

    @Column(name = "IS_AUTHORIZE")
    @ApiModelProperty(value = "是否访问授权")
    private Integer isAuthorize;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @Column(name = "IS_DISABLE")
    @ApiModelProperty(value = "是否禁用")
    private Integer isDisable;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "最新修改时间")
    private String modifyTime;

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
        System.out.println(MapperGenerator.genMapper(PeachAppFunctionDO.class));
    }
}
