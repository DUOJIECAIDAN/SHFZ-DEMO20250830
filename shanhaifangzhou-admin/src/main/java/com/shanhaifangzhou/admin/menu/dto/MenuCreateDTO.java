package com.shanhaifangzhou.admin.menu.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

/**
 * 菜单创建DTO
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
public class MenuCreateDTO {

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单名称长度不能超过50个字符")
    private String menuName;

    /**
     * 菜单类型（M：目录，C：菜单，F：按钮）
     */
    @NotBlank(message = "菜单类型不能为空")
    @Pattern(regexp = "^[MCF]$", message = "菜单类型只能是M、C、F")
    private String menuType;

    /**
     * 路由地址
     */
    @Size(max = 200, message = "路由地址长度不能超过200个字符")
    private String path;

    /**
     * 组件路径
     */
    @Size(max = 255, message = "组件路径长度不能超过255个字符")
    private String component;

    /**
     * 路由参数
     */
    @Size(max = 255, message = "路由参数长度不能超过255个字符")
    private String query;

    /**
     * 是否为外链（0：否，1：是）
     */
    @Min(value = 0, message = "外链标识值不正确")
    @Max(value = 1, message = "外链标识值不正确")
    private Integer isFrame;

    /**
     * 是否缓存（0：不缓存，1：缓存）
     */
    @Min(value = 0, message = "缓存标识值不正确")
    @Max(value = 1, message = "缓存标识值不正确")
    private Integer isCache;

    /**
     * 菜单状态（0：禁用，1：启用）
     */
    @Min(value = 0, message = "菜单状态值不正确")
    @Max(value = 1, message = "菜单状态值不正确")
    private Integer status;

    /**
     * 显示状态（0：隐藏，1：显示）
     */
    @Min(value = 0, message = "显示状态值不正确")
    @Max(value = 1, message = "显示状态值不正确")
    private Integer visible;

    /**
     * 菜单权限标识
     */
    @Size(max = 100, message = "权限标识长度不能超过100个字符")
    private String perms;

    /**
     * 菜单图标
     */
    @Size(max = 100, message = "菜单图标长度不能超过100个字符")
    private String icon;

    /**
     * 菜单排序
     */
    @Min(value = 0, message = "菜单排序值不能小于0")
    @Max(value = 9999, message = "菜单排序值不能大于9999")
    private Integer sort;

    /**
     * 菜单标题
     */
    @Size(max = 50, message = "菜单标题长度不能超过50个字符")
    private String title;

    /**
     * 菜单别名
     */
    @Size(max = 50, message = "菜单别名长度不能超过50个字符")
    private String alias;

    /**
     * 菜单描述
     */
    @Size(max = 200, message = "菜单描述长度不能超过200个字符")
    private String description;

    /**
     * 是否外链（0：否，1：是）
     */
    @Min(value = 0, message = "外链标识值不正确")
    @Max(value = 1, message = "外链标识值不正确")
    private Integer external;

    /**
     * 是否新窗口打开（0：否，1：是）
     */
    @Min(value = 0, message = "新窗口标识值不正确")
    @Max(value = 1, message = "新窗口标识值不正确")
    private Integer newWindow;

    /**
     * 是否固定标签（0：否，1：是）
     */
    @Min(value = 0, message = "固定标签标识值不正确")
    @Max(value = 1, message = "固定标签标识值不正确")
    private Integer affix;

    /**
     * 是否隐藏面包屑（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏面包屑标识值不正确")
    @Max(value = 1, message = "隐藏面包屑标识值不正确")
    private Integer hideBreadcrumb;

    /**
     * 是否隐藏标签页（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏标签页标识值不正确")
    @Max(value = 1, message = "隐藏标签页标识值不正确")
    private Integer hideTab;

    /**
     * 是否隐藏菜单（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏菜单标识值不正确")
    @Max(value = 1, message = "隐藏菜单标识值不正确")
    private Integer hideMenu;

    /**
     * 是否隐藏子菜单（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏子菜单标识值不正确")
    @Max(value = 1, message = "隐藏子菜单标识值不正确")
    private Integer hideChildren;

    /**
     * 是否隐藏搜索（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏搜索标识值不正确")
    @Max(value = 1, message = "隐藏搜索标识值不正确")
    private Integer hideSearch;

    /**
     * 是否隐藏页脚（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏页脚标识值不正确")
    @Max(value = 1, message = "隐藏页脚标识值不正确")
    private Integer hideFooter;

    /**
     * 是否隐藏设置（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏设置标识值不正确")
    @Max(value = 1, message = "隐藏设置标识值不正确")
    private Integer hideSetting;

    /**
     * 是否隐藏多语言（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏多语言标识值不正确")
    @Max(value = 1, message = "隐藏多语言标识值不正确")
    private Integer hideI18n;

    /**
     * 是否隐藏全屏（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏全屏标识值不正确")
    @Max(value = 1, message = "隐藏全屏标识值不正确")
    private Integer hideFullscreen;

    /**
     * 是否隐藏刷新（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏刷新标识值不正确")
    @Max(value = 1, message = "隐藏刷新标识值不正确")
    private Integer hideRefresh;

    /**
     * 是否隐藏帮助（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏帮助标识值不正确")
    @Max(value = 1, message = "隐藏帮助标识值不正确")
    private Integer hideHelp;

    /**
     * 是否隐藏通知（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏通知标识值不正确")
    @Max(value = 1, message = "隐藏通知标识值不正确")
    private Integer hideNotice;

    /**
     * 是否隐藏用户信息（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏用户信息标识值不正确")
    @Max(value = 1, message = "隐藏用户信息标识值不正确")
    private Integer hideUserInfo;

    /**
     * 是否隐藏退出登录（0：否，1：是）
     */
    @Min(value = 0, message = "隐藏退出登录标识值不正确")
    @Max(value = 1, message = "隐藏退出登录标识值不正确")
    private Integer hideLogout;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}