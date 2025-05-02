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
 * @CreateTime 2025/03/22 18:34
 */
@Data
@Table(name = "PEACH_ROUTER")
public class PeachRouterDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROUTER_ID")
    @ApiModelProperty(value = "主键，唯一标识路由")
    private String routerId;

    @Column(name = "ROUTER_CODE")
    @ApiModelProperty(value = "路由代码，唯一标识路由")
    private String routerCode;

    @Column(name = "ROUTER_NAME")
    @ApiModelProperty(value = "路由名称，便于管理")
    private String routerName;

    @Column(name = "ROUTER_URL")
    @ApiModelProperty(value = "路由路径，前端访问路径")
    private String routerUrl;

    @Column(name = "FILE_PATH")
    @ApiModelProperty(value = "文件路径，前端组件路径")
    private String filePath;

    @Column(name = "IS_AUTH")
    @ApiModelProperty(value = "是否需要授权：1-是，0-否")
    private Integer isAuth;

    @Column(name = "IS_CACHE")
    @ApiModelProperty(value = "是否缓存：1-是，0-否")
    private Integer isCache;

    @Column(name = "MODULE_CODE")
    @ApiModelProperty(value = "模块代码，用于归类")
    private String moduleCode;

    @Column(name = "ROUTER_LEVEL")
    @ApiModelProperty(value = "路由级次，标识层级")
    private Integer routerLevel;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachRouterDO.class));
    }
}
