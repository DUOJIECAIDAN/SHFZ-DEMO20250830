package com.shanhaifangzhou.admin.menu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shanhaifangzhou.admin.menu.entity.SysMenu;
import com.shanhaifangzhou.admin.menu.dto.MenuQueryDTO;
import com.shanhaifangzhou.admin.menu.dto.MenuCreateDTO;
import com.shanhaifangzhou.admin.menu.dto.MenuUpdateDTO;
import com.shanhaifangzhou.admin.menu.service.SysMenuService;
import com.shanhaifangzhou.base.common.result.Result;
import com.shanhaifangzhou.base.common.result.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单管理控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/system/menu")
@RequiredArgsConstructor
@Tag(name = "菜单管理", description = "系统菜单管理相关接口")
@Validated
public class SysMenuController {

    private final SysMenuService menuService;

    /**
     * 分页查询菜单列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询菜单列表", description = "根据条件分页查询菜单列表")
    public Result<PageResult<SysMenu>> getMenuPage(@Valid MenuQueryDTO queryDTO) {
        try {
            IPage<SysMenu> page = menuService.getMenuPage(queryDTO);
            PageResult<SysMenu> result = PageResult.of(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
            return Result.success(result);
        } catch (Exception e) {
            log.error("分页查询菜单列表失败", e);
            return Result.error("查询菜单列表失败：" + e.getMessage());
        }
    }

    /**
     * 查询所有菜单列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有菜单", description = "获取所有菜单列表")
    public Result<List<SysMenu>> getAllMenus() {
        try {
            List<SysMenu> menus = menuService.getAllMenus();
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询所有菜单失败", e);
            return Result.error("查询所有菜单失败：" + e.getMessage());
        }
    }

    /**
     * 查询菜单树结构
     */
    @GetMapping("/tree")
    @Operation(summary = "查询菜单树", description = "获取菜单树形结构")
    public Result<List<SysMenu>> getMenuTree() {
        try {
            List<SysMenu> menuTree = menuService.getMenuTree();
            return Result.success(menuTree);
        } catch (Exception e) {
            log.error("查询菜单树失败", e);
            return Result.error("查询菜单树失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询菜单详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询菜单详情", description = "根据菜单ID查询菜单详细信息")
    public Result<SysMenu> getMenuById(@PathVariable @NotNull Long id) {
        try {
            SysMenu menu = menuService.getById(id);
            if (menu == null) {
                return Result.error("菜单不存在");
            }
            return Result.success(menu);
        } catch (Exception e) {
            log.error("查询菜单详情失败，菜单ID：{}", id, e);
            return Result.error("查询菜单详情失败：" + e.getMessage());
        }
    }

    /**
     * 创建菜单
     */
    @PostMapping
    @Operation(summary = "创建菜单", description = "创建新的系统菜单")
    public Result<Void> createMenu(@Valid @RequestBody MenuCreateDTO createDTO) {
        try {
            boolean success = menuService.createMenu(createDTO);
            if (success) {
                return Result.success("菜单创建成功");
            } else {
                return Result.error("菜单创建失败");
            }
        } catch (Exception e) {
            log.error("创建菜单失败", e);
            return Result.error("创建菜单失败：" + e.getMessage());
        }
    }

    /**
     * 更新菜单
     */
    @PutMapping
    @Operation(summary = "更新菜单", description = "更新系统菜单信息")
    public Result<Void> updateMenu(@Valid @RequestBody MenuUpdateDTO updateDTO) {
        try {
            boolean success = menuService.updateMenu(updateDTO);
            if (success) {
                return Result.success("菜单更新成功");
            } else {
                return Result.error("菜单更新失败");
            }
        } catch (Exception e) {
            log.error("更新菜单失败", e);
            return Result.error("更新菜单失败：" + e.getMessage());
        }
    }

    /**
     * 删除菜单
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜单", description = "根据菜单ID删除菜单")
    public Result<Void> deleteMenu(@PathVariable @NotNull Long id) {
        try {
            boolean success = menuService.deleteMenu(id);
            if (success) {
                return Result.success("菜单删除成功");
            } else {
                return Result.error("菜单删除失败");
            }
        } catch (Exception e) {
            log.error("删除菜单失败，菜单ID：{}", id, e);
            return Result.error("删除菜单失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除菜单
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除菜单", description = "批量删除多个菜单")
    public Result<Void> batchDeleteMenus(@RequestBody @NotEmpty List<Long> ids) {
        try {
            boolean success = menuService.batchDeleteMenus(ids);
            if (success) {
                return Result.success("批量删除菜单成功");
            } else {
                return Result.error("批量删除菜单失败");
            }
        } catch (Exception e) {
            log.error("批量删除菜单失败，菜单ID列表：{}", ids, e);
            return Result.error("批量删除菜单失败：" + e.getMessage());
        }
    }

    /**
     * 更新菜单状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新菜单状态", description = "启用或禁用菜单")
    public Result<Void> updateMenuStatus(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer status) {
        try {
            boolean success = menuService.updateMenuStatus(id, status);
            if (success) {
                return Result.success("菜单状态更新成功");
            } else {
                return Result.error("菜单状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新菜单状态失败，菜单ID：{}，状态：{}", id, status, e);
            return Result.error("更新菜单状态失败：" + e.getMessage());
        }
    }

    /**
     * 更新菜单显示状态
     */
    @PutMapping("/{id}/visible")
    @Operation(summary = "更新菜单显示状态", description = "显示或隐藏菜单")
    public Result<Void> updateMenuVisible(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer visible) {
        try {
            boolean success = menuService.updateMenuVisible(id, visible);
            if (success) {
                return Result.success("菜单显示状态更新成功");
            } else {
                return Result.error("菜单显示状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新菜单显示状态失败，菜单ID：{}，显示状态：{}", id, visible, e);
            return Result.error("更新菜单显示状态失败：" + e.getMessage());
        }
    }

    /**
     * 更新菜单排序
     */
    @PutMapping("/{id}/sort")
    @Operation(summary = "更新菜单排序", description = "更新菜单显示顺序")
    public Result<Void> updateMenuSort(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer sort) {
        try {
            boolean success = menuService.updateMenuSort(id, sort);
            if (success) {
                return Result.success("菜单排序更新成功");
            } else {
                return Result.error("菜单排序更新失败");
            }
        } catch (Exception e) {
            log.error("更新菜单排序失败，菜单ID：{}，排序：{}", id, sort, e);
            return Result.error("更新菜单排序失败：" + e.getMessage());
        }
    }

    /**
     * 根据父菜单ID查询子菜单列表
     */
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "查询子菜单", description = "根据父菜单ID查询子菜单列表")
    public Result<List<SysMenu>> getMenusByParentId(@PathVariable @NotNull Long parentId) {
        try {
            List<SysMenu> menus = menuService.getMenusByParentId(parentId);
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询子菜单失败，父菜单ID：{}", parentId, e);
            return Result.error("查询子菜单失败：" + e.getMessage());
        }
    }

    /**
     * 根据角色ID查询菜单列表
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色菜单", description = "根据角色ID查询菜单列表")
    public Result<List<SysMenu>> getMenusByRoleId(@PathVariable @NotNull Long roleId) {
        try {
            List<SysMenu> menus = menuService.getMenusByRoleId(roleId);
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询角色菜单失败，角色ID：{}", roleId, e);
            return Result.error("查询角色菜单失败：" + e.getMessage());
        }
    }

    /**
     * 根据用户ID查询菜单列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户菜单", description = "根据用户ID查询菜单列表")
    public Result<List<SysMenu>> getMenusByUserId(@PathVariable @NotNull Long userId) {
        try {
            List<SysMenu> menus = menuService.getMenusByUserId(userId);
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询用户菜单失败，用户ID：{}", userId, e);
            return Result.error("查询用户菜单失败：" + e.getMessage());
        }
    }

    /**
     * 根据权限标识查询菜单
     */
    @GetMapping("/perms/{perms}")
    @Operation(summary = "查询权限菜单", description = "根据权限标识查询菜单")
    public Result<SysMenu> getMenuByPerms(@PathVariable @NotNull String perms) {
        try {
            SysMenu menu = menuService.getMenuByPerms(perms);
            return Result.success(menu);
        } catch (Exception e) {
            log.error("查询权限菜单失败，权限标识：{}", perms, e);
            return Result.error("查询权限菜单失败：" + e.getMessage());
        }
    }

    /**
     * 根据路径查询菜单
     */
    @GetMapping("/path/{path}")
    @Operation(summary = "查询路径菜单", description = "根据路由路径查询菜单")
    public Result<SysMenu> getMenuByPath(@PathVariable @NotNull String path) {
        try {
            SysMenu menu = menuService.getMenuByPath(path);
            return Result.success(menu);
        } catch (Exception e) {
            log.error("查询路径菜单失败，路径：{}", path, e);
            return Result.error("查询路径菜单失败：" + e.getMessage());
        }
    }

    /**
     * 根据组件路径查询菜单
     */
    @GetMapping("/component/{component}")
    @Operation(summary = "查询组件菜单", description = "根据组件路径查询菜单")
    public Result<SysMenu> getMenuByComponent(@PathVariable @NotNull String component) {
        try {
            SysMenu menu = menuService.getMenuByComponent(component);
            return Result.success(menu);
        } catch (Exception e) {
            log.error("查询组件菜单失败，组件路径：{}", component, e);
            return Result.error("查询组件菜单失败：" + e.getMessage());
        }
    }

    /**
     * 获取启用的菜单列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询启用菜单", description = "获取所有启用的菜单列表")
    public Result<List<SysMenu>> getEnabledMenus() {
        try {
            List<SysMenu> menus = menuService.getEnabledMenus();
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询启用菜单失败", e);
            return Result.error("查询启用菜单失败：" + e.getMessage());
        }
    }

    /**
     * 获取显示的菜单列表
     */
    @GetMapping("/visible")
    @Operation(summary = "查询显示菜单", description = "获取所有显示的菜单列表")
    public Result<List<SysMenu>> getVisibleMenus() {
        try {
            List<SysMenu> menus = menuService.getVisibleMenus();
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询显示菜单失败", e);
            return Result.error("查询显示菜单失败：" + e.getMessage());
        }
    }

    /**
     * 根据菜单类型查询菜单列表
     */
    @GetMapping("/type/{menuType}")
    @Operation(summary = "查询类型菜单", description = "根据菜单类型查询菜单列表")
    public Result<List<SysMenu>> getMenusByType(@PathVariable @NotNull String menuType) {
        try {
            List<SysMenu> menus = menuService.getMenusByType(menuType);
            return Result.success(menus);
        } catch (Exception e) {
            log.error("查询类型菜单失败，菜单类型：{}", menuType, e);
            return Result.error("查询类型菜单失败：" + e.getMessage());
        }
    }

    /**
     * 检查菜单名称是否唯一
     */
    @GetMapping("/check-name")
    @Operation(summary = "检查菜单名称唯一性", description = "检查同级菜单下菜单名称是否已存在")
    public Result<Boolean> checkMenuNameUnique(
            @RequestParam @NotNull String menuName,
            @RequestParam(required = false) Long parentId,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = menuService.checkMenuNameUnique(menuName, parentId, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查菜单名称唯一性失败，菜单名称：{}", menuName, e);
            return Result.error("检查菜单名称唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 检查权限标识是否唯一
     */
    @GetMapping("/check-perms")
    @Operation(summary = "检查权限标识唯一性", description = "检查权限标识是否已存在")
    public Result<Boolean> checkPermsUnique(
            @RequestParam @NotNull String perms,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = menuService.checkPermsUnique(perms, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查权限标识唯一性失败，权限标识：{}", perms, e);
            return Result.error("检查权限标识唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 检查路径是否唯一
     */
    @GetMapping("/check-path")
    @Operation(summary = "检查路径唯一性", description = "检查路由路径是否已存在")
    public Result<Boolean> checkPathUnique(
            @RequestParam @NotNull String path,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = menuService.checkPathUnique(path, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查路径唯一性失败，路径：{}", path, e);
            return Result.error("检查路径唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 检查组件路径是否唯一
     */
    @GetMapping("/check-component")
    @Operation(summary = "检查组件路径唯一性", description = "检查组件路径是否已存在")
    public Result<Boolean> checkComponentUnique(
            @RequestParam @NotNull String component,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = menuService.checkComponentUnique(component, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查组件路径唯一性失败，组件路径：{}", component, e);
            return Result.error("检查组件路径唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 获取菜单统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取菜单统计", description = "获取菜单相关的统计信息")
    public Result<Map<String, Object>> getMenuStats() {
        try {
            Map<String, Object> stats = menuService.getMenuStats();
            return Result.success(stats);
        } catch (Exception e) {
            log.error("获取菜单统计信息失败", e);
            return Result.error("获取菜单统计信息失败：" + e.getMessage());
        }
    }

    /**
     * 复制菜单
     */
    @PostMapping("/{id}/copy")
    @Operation(summary = "复制菜单", description = "复制指定菜单，创建新菜单")
    public Result<Void> copyMenu(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull String newMenuName,
            @RequestParam(required = false) Long newParentId) {
        try {
            boolean success = menuService.copyMenu(id, newMenuName, newParentId);
            if (success) {
                return Result.success("菜单复制成功");
            } else {
                return Result.error("菜单复制失败");
            }
        } catch (Exception e) {
            log.error("复制菜单失败，菜单ID：{}", id, e);
            return Result.error("复制菜单失败：" + e.getMessage());
        }
    }

    /**
     * 移动菜单
     */
    @PutMapping("/{id}/move")
    @Operation(summary = "移动菜单", description = "将菜单移动到新的父菜单下")
    public Result<Void> moveMenu(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Long newParentId) {
        try {
            boolean success = menuService.moveMenu(id, newParentId);
            if (success) {
                return Result.success("菜单移动成功");
            } else {
                return Result.error("菜单移动失败");
            }
        } catch (Exception e) {
            log.error("移动菜单失败，菜单ID：{}", id, e);
            return Result.error("移动菜单失败：" + e.getMessage());
        }
    }

    /**
     * 展开/折叠菜单
     */
    @PutMapping("/{id}/toggle")
    @Operation(summary = "切换菜单展开状态", description = "展开或折叠菜单")
    public Result<Void> toggleMenuExpand(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Boolean expanded) {
        try {
            boolean success = menuService.toggleMenuExpand(id, expanded);
            if (success) {
                return Result.success("菜单状态切换成功");
            } else {
                return Result.error("菜单状态切换失败");
            }
        } catch (Exception e) {
            log.error("切换菜单展开状态失败，菜单ID：{}", id, e);
            return Result.error("切换菜单展开状态失败：" + e.getMessage());
        }
    }
}