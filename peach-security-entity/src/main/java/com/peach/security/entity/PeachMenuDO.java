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
 * @CreateTime 2025/05/02 19:11
 */
@Data
@Table(name = "PEACH_MENU")
public class PeachMenuDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MENU_ID")
    @ApiModelProperty(value = "菜单ID")
    private String menuId;

    @Column(name = "MENU_NAME")
    @ApiModelProperty(value = "菜单名称")
    private String menuName;

    @Column(name = "MENU_CODE")
    @ApiModelProperty(value = "菜单编码")
    private String menuCode;

    @Column(name = "IS_LEAF")
    @ApiModelProperty(value = "是否叶子节点（1: 是, 0: 否）")
    private Integer isLeaf;

    @Column(name = "MENU_URL")
    @ApiModelProperty(value = "菜单URL")
    private String menuUrl;

    @Column(name = "PARENT_MENU_ID")
    @ApiModelProperty(value = "父菜单ID")
    private String parentMenuId;

    @Column(name = "MENU_LEVEL")
    @ApiModelProperty(value = "菜单级别")
    private Integer menuLevel;

    @Column(name = "SORT_NO")
    @ApiModelProperty(value = "显示顺序")
    private Integer sortNo;

    @Column(name = "COLLAPSE_ICON")
    @ApiModelProperty(value = "折叠图标")
    private String collapseIcon;

    @Column(name = "EXPAND_ICON")
    @ApiModelProperty(value = "展开图标")
    private String expandIcon;

    @Column(name = "SUBCOUNT")
    @ApiModelProperty(value = "子菜单数量")
    private Integer subcount;

    @Column(name = "FUNC_CODE")
    @ApiModelProperty(value = "功能编码")
    private String funcCode;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @Column(name = "MODIFY_TIME")
    @ApiModelProperty(value = "最新修改时间")
    private String modifyTime;

    @Column(name = "IS_DELETED")
    @ApiModelProperty(value = "是否删除（1: 是, 0: 否）")
    private Integer isDeleted;

    @Column(name = "IS_DISABLE")
    @ApiModelProperty(value = "菜单是否禁用（1: 禁用, 0: 否）")
    private Integer isDisable;

    @Column(name = "IS_SHOW")
    @ApiModelProperty(value = "是否显示（1: 显示, 0: 不显示）")
    private Integer isShow;

    @Column(name = "MENU_ICON")
    @ApiModelProperty(value = "菜单图标")
    private String menuIcon;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建者名称")
    private String creator;

    @Column(name = "CREATOR_CODE")
    @ApiModelProperty(value = "创建者编码")
    private String creatorCode;

    public static void main(String[] args) {
        System.out.println(MapperGenerator.genMapper(PeachMenuDO.class));
    }
}
