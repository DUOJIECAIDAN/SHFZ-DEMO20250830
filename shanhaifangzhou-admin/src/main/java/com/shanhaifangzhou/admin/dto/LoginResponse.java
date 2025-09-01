package com.shanhaifangzhou.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 登录响应DTO
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@Schema(description = "登录响应")
public class LoginResponse {

    @Schema(description = "访问令牌")
    private String token;

    @Schema(description = "用户信息")
    private UserInfo userInfo;

    @Schema(description = "菜单列表")
    private List<MenuInfo> menus;

    @Schema(description = "权限列表")
    private List<String> permissions;

    @Data
    @Schema(description = "用户信息")
    public static class UserInfo {
        @Schema(description = "用户ID")
        private Long id;

        @Schema(description = "用户名")
        private String username;

        @Schema(description = "昵称")
        private String nickname;

        @Schema(description = "邮箱")
        private String email;

        @Schema(description = "手机号")
        private String phone;

        @Schema(description = "头像")
        private String avatar;

        @Schema(description = "性别")
        private Integer sex;

        @Schema(description = "部门ID")
        private Long deptId;

        @Schema(description = "部门名称")
        private String deptName;

        @Schema(description = "角色列表")
        private List<String> roles;
    }

    @Data
    @Schema(description = "菜单信息")
    public static class MenuInfo {
        @Schema(description = "菜单ID")
        private Long id;

        @Schema(description = "菜单名称")
        private String menuName;

        @Schema(description = "父菜单ID")
        private Long parentId;

        @Schema(description = "显示顺序")
        private Integer orderNum;

        @Schema(description = "路由地址")
        private String path;

        @Schema(description = "组件路径")
        private String component;

        @Schema(description = "路由参数")
        private String query;

        @Schema(description = "是否为外链")
        private Integer isFrame;

        @Schema(description = "是否缓存")
        private Integer isCache;

        @Schema(description = "菜单类型")
        private String menuType;

        @Schema(description = "菜单状态")
        private String visible;

        @Schema(description = "权限标识")
        private String perms;

        @Schema(description = "菜单图标")
        private String icon;

        @Schema(description = "子菜单")
        private List<MenuInfo> children;
    }
}