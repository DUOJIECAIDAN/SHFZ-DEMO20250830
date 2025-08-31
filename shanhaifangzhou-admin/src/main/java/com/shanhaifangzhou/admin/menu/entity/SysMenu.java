package com.shanhaifangzhou.admin.menu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shanhaifangzhou.base.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统菜单实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 路由地址
     */
    @TableField("path")
    private String path;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 路由参数
     */
    @TableField("query")
    private String query;

    /**
     * 是否为外链（0：否，1：是）
     */
    @TableField("is_frame")
    private Integer isFrame;

    /**
     * 是否缓存（0：不缓存，1：缓存）
     */
    @TableField("is_cache")
    private Integer isCache;

    /**
     * 菜单类型（M：目录，C：菜单，F：按钮）
     */
    @TableField("menu_type")
    private String menuType;

    /**
     * 菜单状态（0：显示，1：隐藏）
     */
    @TableField("visible")
    private Integer visible;

    /**
     * 菜单状态（0：正常，1：停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 子菜单列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<SysMenu> children;

    /**
     * 父菜单名称（非数据库字段）
     */
    @TableField(exist = false)
    private String parentName;
}