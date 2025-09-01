package com.shanhaifangzhou.base.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 安全工具类
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户ID
     */
    public static Long getCurrentUserId() {
        if (StpUtil.isLogin()) {
            return StpUtil.getLoginIdAsLong();
        }
        return null;
    }

    /**
     * 获取当前登录用户名
     */
    public static String getCurrentUsername() {
        if (StpUtil.isLogin()) {
            return StpUtil.getExtra("username").toString();
        }
        return null;
    }

    /**
     * 获取当前登录用户昵称
     */
    public static String getCurrentNickname() {
        if (StpUtil.isLogin()) {
            Object nickname = StpUtil.getExtra("nickname");
            return nickname != null ? nickname.toString() : null;
        }
        return null;
    }

    /**
     * 获取当前登录用户邮箱
     */
    public static String getCurrentEmail() {
        if (StpUtil.isLogin()) {
            Object email = StpUtil.getExtra("email");
            return email != null ? email.toString() : null;
        }
        return null;
    }

    /**
     * 获取当前登录用户手机号
     */
    public static String getCurrentPhone() {
        if (StpUtil.isLogin()) {
            Object phone = StpUtil.getExtra("phone");
            return phone != null ? phone.toString() : null;
        }
        return null;
    }

    /**
     * 获取当前登录用户头像
     */
    public static String getCurrentAvatar() {
        if (StpUtil.isLogin()) {
            Object avatar = StpUtil.getExtra("avatar");
            return avatar != null ? avatar.toString() : null;
        }
        return null;
    }

    /**
     * 获取当前登录用户部门ID
     */
    public static Long getCurrentDeptId() {
        if (StpUtil.isLogin()) {
            Object deptId = StpUtil.getExtra("deptId");
            return deptId != null ? Long.valueOf(deptId.toString()) : null;
        }
        return null;
    }

    /**
     * 获取当前登录用户租户ID
     */
    public static Long getCurrentTenantId() {
        if (StpUtil.isLogin()) {
            Object tenantId = StpUtil.getExtra("tenantId");
            return tenantId != null ? Long.valueOf(tenantId.toString()) : null;
        }
        return null;
    }

    /**
     * 检查是否已登录
     */
    public static boolean isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 检查是否有指定权限
     */
    public static boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        return StpUtil.hasPermission(permission);
    }

    /**
     * 检查是否有指定角色
     */
    public static boolean hasRole(String role) {
        if (StrUtil.isBlank(role)) {
            return false;
        }
        return StpUtil.hasRole(role);
    }

    /**
     * 获取当前用户的所有权限
     */
    public static String[] getPermissions() {
        if (StpUtil.isLogin()) {
            return StpUtil.getPermissionList().toArray(new String[0]);
        }
        return new String[0];
    }

    /**
     * 获取当前用户的所有角色
     */
    public static String[] getRoles() {
        if (StpUtil.isLogin()) {
            return StpUtil.getRoleList().toArray(new String[0]);
        }
        return new String[0];
    }

    /**
     * 获取当前用户的Token
     */
    public static String getToken() {
        return StpUtil.getTokenValue();
    }

    /**
     * 获取当前用户的Token信息
     */
    public static Object getTokenInfo() {
        return StpUtil.getTokenInfo();
    }
}