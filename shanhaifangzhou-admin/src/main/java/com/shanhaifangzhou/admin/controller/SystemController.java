package com.shanhaifangzhou.admin.controller;

import com.shanhaifangzhou.admin.user.entity.SysUser;
import com.shanhaifangzhou.admin.role.entity.SysRole;
import com.shanhaifangzhou.admin.menu.entity.SysMenu;
import com.shanhaifangzhou.base.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统管理控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Tag(name = "系统管理", description = "系统管理相关接口")
@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
@Slf4j
public class SystemController {

    /**
     * 获取系统信息
     */
    @GetMapping("/info")
    @Operation(summary = "获取系统信息", description = "获取系统基本信息")
    public Result<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "山海方舟企业级权限管理系统");
        info.put("version", "2.0.0");
        info.put("description", "基于Spring Boot 3.3.x + Sa-Token的现代化权限管理系统");
        info.put("timestamp", System.currentTimeMillis());
        return Result.success(info);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/user/info")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的详细信息")
    public Result<Map<String, Object>> getCurrentUserInfo() {
        // 这里应该从Sa-Token获取当前用户信息
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", 1L);
        userInfo.put("username", "admin");
        userInfo.put("nickname", "系统管理员");
        userInfo.put("email", "admin@shanhaifangzhou.com");
        userInfo.put("avatar", "https://example.com/avatar.jpg");
        userInfo.put("roles", Arrays.asList("admin", "user"));
        userInfo.put("permissions", Arrays.asList("system:*", "user:*", "role:*"));
        return Result.success(userInfo);
    }

    /**
     * 获取用户菜单
     */
    @GetMapping("/user/menus")
    @Operation(summary = "获取用户菜单", description = "获取当前用户的菜单列表")
    public Result<List<Map<String, Object>>> getUserMenus() {
        List<Map<String, Object>> menus = new ArrayList<>();
        
        // 系统管理菜单
        Map<String, Object> systemMenu = new HashMap<>();
        systemMenu.put("id", 1L);
        systemMenu.put("name", "系统管理");
        systemMenu.put("path", "/system");
        systemMenu.put("component", "Layout");
        systemMenu.put("icon", "setting");
        systemMenu.put("sort", 1);
        
        List<Map<String, Object>> systemChildren = new ArrayList<>();
        
        // 用户管理
        Map<String, Object> userMenu = new HashMap<>();
        userMenu.put("id", 11L);
        userMenu.put("name", "用户管理");
        userMenu.put("path", "user");
        userMenu.put("component", "system/user/index");
        userMenu.put("icon", "user");
        userMenu.put("sort", 1);
        systemChildren.add(userMenu);
        
        // 角色管理
        Map<String, Object> roleMenu = new HashMap<>();
        roleMenu.put("id", 12L);
        roleMenu.put("name", "角色管理");
        roleMenu.put("path", "role");
        roleMenu.put("component", "system/role/index");
        roleMenu.put("icon", "peoples");
        roleMenu.put("sort", 2);
        systemChildren.add(roleMenu);
        
        // 菜单管理
        Map<String, Object> menuMenu = new HashMap<>();
        menuMenu.put("id", 13L);
        menuMenu.put("name", "菜单管理");
        menuMenu.put("path", "menu");
        menuMenu.put("component", "system/menu/index");
        menuMenu.put("icon", "tree-table");
        menuMenu.put("sort", 3);
        systemChildren.add(menuMenu);
        
        systemMenu.put("children", systemChildren);
        menus.add(systemMenu);
        
        return Result.success(menus);
    }

    /**
     * 获取用户权限
     */
    @GetMapping("/user/permissions")
    @Operation(summary = "获取用户权限", description = "获取当前用户的所有权限")
    public Result<List<String>> getUserPermissions() {
        List<String> permissions = Arrays.asList(
            "system:*",
            "user:*",
            "role:*",
            "menu:*",
            "dept:*",
            "log:*"
        );
        return Result.success(permissions);
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    @Operation(summary = "系统健康检查", description = "检查系统运行状态")
    public Result<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", System.currentTimeMillis());
        health.put("version", "2.0.0");
        health.put("uptime", System.currentTimeMillis() - 1000000); // 模拟运行时间
        return Result.success(health);
    }
}