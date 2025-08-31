package com.shanhaifangzhou.admin.menu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shanhaifangzhou.base.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
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
     * 菜单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父菜单ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 菜单类型（M：目录，C：菜单，F：按钮）
     */
    @TableField("menu_type")
    private String menuType;

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
     * 菜单状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 显示状态（0：隐藏，1：显示）
     */
    @TableField("visible")
    private Integer visible;

    /**
     * 菜单权限标识
     */
    @TableField("perms")
    private String perms;

    /**
     * 菜单图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 菜单排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 菜单标题
     */
    @TableField("title")
    private String title;

    /**
     * 菜单别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 菜单描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否外链（0：否，1：是）
     */
    @TableField("external")
    private Integer external;

    /**
     * 是否新窗口打开（0：否，1：是）
     */
    @TableField("new_window")
    private Integer newWindow;

    /**
     * 是否固定标签（0：否，1：是）
     */
    @TableField("affix")
    private Integer affix;

    /**
     * 是否隐藏面包屑（0：否，1：是）
     */
    @TableField("hide_breadcrumb")
    private Integer hideBreadcrumb;

    /**
     * 是否隐藏标签页（0：否，1：是）
     */
    @TableField("hide_tab")
    private Integer hideTab;

    /**
     * 是否隐藏菜单（0：否，1：是）
     */
    @TableField("hide_menu")
    private Integer hideMenu;

    /**
     * 是否隐藏子菜单（0：否，1：是）
     */
    @TableField("hide_children")
    private Integer hideChildren;

    /**
     * 是否隐藏搜索（0：否，1：是）
     */
    @TableField("hide_search")
    private Integer hideSearch;

    /**
     * 是否隐藏页脚（0：否，1：是）
     */
    @TableField("hide_footer")
    private Integer hideFooter;

    /**
     * 是否隐藏设置（0：否，1：是）
     */
    @TableField("hide_setting")
    private Integer hideSetting;

    /**
     * 是否隐藏多语言（0：否，1：是）
     */
    @TableField("hide_i18n")
    private Integer hideI18n;

    /**
     * 是否隐藏全屏（0：否，1：是）
     */
    @TableField("hide_fullscreen")
    private Integer hideFullscreen;

    /**
     * 是否隐藏刷新（0：否，1：是）
     */
    @TableField("hide_refresh")
    private Integer hideRefresh;

    /**
     * 是否隐藏帮助（0：否，1：是）
     */
    @TableField("hide_help")
    private Integer hideHelp;

    /**
     * 是否隐藏通知（0：否，1：是）
     */
    @TableField("hide_notice")
    private Integer hideNotice;

    /**
     * 是否隐藏用户信息（0：否，1：是）
     */
    @TableField("hide_user_info")
    private Integer hideUserInfo;

    /**
     * 是否隐藏退出登录（0：否，1：是）
     */
    @TableField("hide_logout")
    private Integer hideLogout;

    /**
     * 扩展字段
     */
    @TableField("ext_data")
    private String extData;

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

    /**
     * 角色ID列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 角色名称列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 是否选中（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean checked;

    /**
     * 是否半选（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean indeterminate;
}