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
 * @CreateTime 2025/5/31 18:10
 */
@Data
@Table(name = "PEACH_LANGUAGE")
public class PeachLanguageDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @ApiModelProperty(value = "逐渐")
    private String id;

    @Column(name = "LANGUAGE")
    @ApiModelProperty(value = "语言类型")
    private String language;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachLanguageDO.class));
    }
}
