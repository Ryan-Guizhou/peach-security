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
 * @CreateTime 2025/05/02 01:15
 */
@Data
@Table(name = "PEACH_AUTH_FUNCTION")
public class PeachAuthFunctionDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AUTH_FUNC_ID")
    @ApiModelProperty(value = "主键ID")
    private String authFuncId;

    @Column(name = "PARTY_CODE")
    @ApiModelProperty(value = "参与者代码")
    private String partyCode;

    @Column(name = "PARTY_TYPE")
    @ApiModelProperty(value = "参与者类型")
    private String partyType;

    @Column(name = "FUNC_CODE")
    @ApiModelProperty(value = "功能代码")
    private String funcCode;

    @Column(name = "STATE")
    @ApiModelProperty(value = "功能状态")
    private String state;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

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
        System.out.println(MapperGenerator.genMapper(PeachAuthFunctionDO.class));
    }
}
