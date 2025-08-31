package com.shanhaifangzhou.admin.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shanhaifangzhou.admin.user.entity.SysUser;
import com.shanhaifangzhou.admin.user.dto.UserQueryDTO;
import com.shanhaifangzhou.admin.user.dto.UserCreateDTO;
import com.shanhaifangzhou.admin.user.dto.UserUpdateDTO;
import com.shanhaifangzhou.admin.user.service.SysUserService;
import com.shanhaifangzhou.base.common.result.Result;
import com.shanhaifangzhou.base.common.result.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * 系统用户管理控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/system/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "系统用户管理相关接口")
@Validated
public class SysUserController {

    private final SysUserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询用户列表", description = "根据条件分页查询用户列表")
    public Result<PageResult<SysUser>> getUserPage(@Valid UserQueryDTO queryDTO) {
        try {
            IPage<SysUser> page = userService.getUserPage(queryDTO);
            PageResult<SysUser> result = PageResult.of(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
            return Result.success(result);
        } catch (Exception e) {
            log.error("分页查询用户列表失败", e);
            return Result.error("查询用户列表失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询用户详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询用户详情", description = "根据用户ID查询用户详细信息")
    public Result<SysUser> getUserById(@PathVariable @NotNull Long id) {
        try {
            SysUser user = userService.getById(id);
            if (user == null) {
                return Result.error("用户不存在");
            }
            return Result.success(user);
        } catch (Exception e) {
            log.error("查询用户详情失败，用户ID：{}", id, e);
            return Result.error("查询用户详情失败：" + e.getMessage());
        }
    }

    /**
     * 创建用户
     */
    @PostMapping
    @Operation(summary = "创建用户", description = "创建新的系统用户")
    public Result<Void> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
        try {
            boolean success = userService.createUser(createDTO);
            if (success) {
                return Result.success("用户创建成功");
            } else {
                return Result.error("用户创建失败");
            }
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return Result.error("创建用户失败：" + e.getMessage());
        }
    }

    /**
     * 更新用户
     */
    @PutMapping
    @Operation(summary = "更新用户", description = "更新系统用户信息")
    public Result<Void> updateUser(@Valid @RequestBody UserUpdateDTO updateDTO) {
        try {
            boolean success = userService.updateUser(updateDTO);
            if (success) {
                return Result.success("用户更新成功");
            } else {
                return Result.error("用户更新失败");
            }
        } catch (Exception e) {
            log.error("更新用户失败", e);
            return Result.error("更新用户失败：" + e.getMessage());
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户", description = "根据用户ID删除用户")
    public Result<Void> deleteUser(@PathVariable @NotNull Long id) {
        try {
            boolean success = userService.deleteUser(id);
            if (success) {
                return Result.success("用户删除成功");
            } else {
                return Result.error("用户删除失败");
            }
        } catch (Exception e) {
            log.error("删除用户失败，用户ID：{}", id, e);
            return Result.error("删除用户失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除用户", description = "批量删除多个用户")
    public Result<Void> batchDeleteUsers(@RequestBody @NotEmpty List<Long> ids) {
        try {
            boolean success = userService.batchDeleteUsers(ids);
            if (success) {
                return Result.success("批量删除用户成功");
            } else {
                return Result.error("批量删除用户失败");
            }
        } catch (Exception e) {
            log.error("批量删除用户失败，用户ID列表：{}", ids, e);
            return Result.error("批量删除用户失败：" + e.getMessage());
        }
    }

    /**
     * 重置用户密码
     */
    @PutMapping("/{id}/password/reset")
    @Operation(summary = "重置用户密码", description = "重置指定用户的密码")
    public Result<Void> resetPassword(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull String newPassword) {
        try {
            boolean success = userService.resetPassword(id, newPassword);
            if (success) {
                return Result.success("密码重置成功");
            } else {
                return Result.error("密码重置失败");
            }
        } catch (Exception e) {
            log.error("重置用户密码失败，用户ID：{}", id, e);
            return Result.error("重置密码失败：" + e.getMessage());
        }
    }

    /**
     * 修改用户密码
     */
    @PutMapping("/{id}/password/change")
    @Operation(summary = "修改用户密码", description = "用户修改自己的密码")
    public Result<Void> changePassword(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull String oldPassword,
            @RequestParam @NotNull String newPassword) {
        try {
            boolean success = userService.changePassword(id, oldPassword, newPassword);
            if (success) {
                return Result.success("密码修改成功");
            } else {
                return Result.error("密码修改失败");
            }
        } catch (Exception e) {
            log.error("修改用户密码失败，用户ID：{}", id, e);
            return Result.error("修改密码失败：" + e.getMessage());
        }
    }

    /**
     * 更新用户状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新用户状态", description = "启用或禁用用户")
    public Result<Void> updateUserStatus(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer status) {
        try {
            boolean success = userService.updateUserStatus(id, status);
            if (success) {
                return Result.success("用户状态更新成功");
            } else {
                return Result.error("用户状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新用户状态失败，用户ID：{}，状态：{}", id, status, e);
            return Result.error("更新用户状态失败：" + e.getMessage());
        }
    }

    /**
     * 锁定用户账号
     */
    @PutMapping("/{id}/lock")
    @Operation(summary = "锁定用户账号", description = "锁定指定用户账号")
    public Result<Void> lockUser(
            @PathVariable @NotNull Long id,
            @RequestParam String reason) {
        try {
            boolean success = userService.lockUser(id, reason);
            if (success) {
                return Result.success("用户账号锁定成功");
            } else {
                return Result.error("用户账号锁定失败");
            }
        } catch (Exception e) {
            log.error("锁定用户账号失败，用户ID：{}", id, e);
            return Result.error("锁定用户账号失败：" + e.getMessage());
        }
    }

    /**
     * 解锁用户账号
     */
    @PutMapping("/{id}/unlock")
    @Operation(summary = "解锁用户账号", description = "解锁指定用户账号")
    public Result<Void> unlockUser(@PathVariable @NotNull Long id) {
        try {
            boolean success = userService.unlockUser(id);
            if (success) {
                return Result.success("用户账号解锁成功");
            } else {
                return Result.error("用户账号解锁失败");
            }
        } catch (Exception e) {
            log.error("解锁用户账号失败，用户ID：{}", id, e);
            return Result.error("解锁用户账号失败：" + e.getMessage());
        }
    }

    /**
     * 强制下线用户
     */
    @PutMapping("/{id}/force-logout")
    @Operation(summary = "强制下线用户", description = "强制指定用户下线")
    public Result<Void> forceLogout(@PathVariable @NotNull Long id) {
        try {
            boolean success = userService.forceLogout(id);
            if (success) {
                return Result.success("用户强制下线成功");
            } else {
                return Result.error("用户强制下线失败");
            }
        } catch (Exception e) {
            log.error("强制下线用户失败，用户ID：{}", id, e);
            return Result.error("强制下线用户失败：" + e.getMessage());
        }
    }

    /**
     * 分配用户角色
     */
    @PostMapping("/{id}/roles")
    @Operation(summary = "分配用户角色", description = "为指定用户分配角色")
    public Result<Void> assignUserRoles(
            @PathVariable @NotNull Long id,
            @RequestBody @NotEmpty List<Long> roleIds) {
        try {
            boolean success = userService.assignUserRoles(id, roleIds);
            if (success) {
                return Result.success("角色分配成功");
            } else {
                return Result.error("角色分配失败");
            }
        } catch (Exception e) {
            log.error("分配用户角色失败，用户ID：{}，角色ID列表：{}", id, roleIds, e);
            return Result.error("分配角色失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户角色ID列表
     */
    @GetMapping("/{id}/roles")
    @Operation(summary = "获取用户角色", description = "获取指定用户的角色ID列表")
    public Result<List<Long>> getUserRoles(@PathVariable @NotNull Long id) {
        try {
            List<Long> roleIds = userService.getUserRoleIds(id);
            return Result.success(roleIds);
        } catch (Exception e) {
            log.error("获取用户角色失败，用户ID：{}", id, e);
            return Result.error("获取用户角色失败：" + e.getMessage());
        }
    }

    /**
     * 根据部门ID查询用户列表
     */
    @GetMapping("/dept/{deptId}")
    @Operation(summary = "查询部门用户", description = "根据部门ID查询用户列表")
    public Result<List<SysUser>> getUsersByDeptId(@PathVariable @NotNull Long deptId) {
        try {
            List<SysUser> users = userService.getUsersByDeptId(deptId);
            return Result.success(users);
        } catch (Exception e) {
            log.error("查询部门用户失败，部门ID：{}", deptId, e);
            return Result.error("查询部门用户失败：" + e.getMessage());
        }
    }

    /**
     * 根据角色ID查询用户列表
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色用户", description = "根据角色ID查询用户列表")
    public Result<List<SysUser>> getUsersByRoleId(@PathVariable @NotNull Long roleId) {
        try {
            List<SysUser> users = userService.getUsersByRoleId(roleId);
            return Result.success(users);
        } catch (Exception e) {
            log.error("查询角色用户失败，角色ID：{}", roleId, e);
            return Result.error("查询角色用户失败：" + e.getMessage());
        }
    }

    /**
     * 获取在线用户列表
     */
    @GetMapping("/online")
    @Operation(summary = "查询在线用户", description = "获取当前在线用户列表")
    public Result<List<SysUser>> getOnlineUsers() {
        try {
            List<SysUser> users = userService.getOnlineUsers();
            return Result.success(users);
        } catch (Exception e) {
            log.error("查询在线用户失败", e);
            return Result.error("查询在线用户失败：" + e.getMessage());
        }
    }

    /**
     * 检查用户名是否唯一
     */
    @GetMapping("/check-username")
    @Operation(summary = "检查用户名唯一性", description = "检查用户名是否已存在")
    public Result<Boolean> checkUsernameUnique(
            @RequestParam @NotNull String username,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = userService.checkUsernameUnique(username, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查用户名唯一性失败，用户名：{}", username, e);
            return Result.error("检查用户名唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 检查邮箱是否唯一
     */
    @GetMapping("/check-email")
    @Operation(summary = "检查邮箱唯一性", description = "检查邮箱是否已存在")
    public Result<Boolean> checkEmailUnique(
            @RequestParam @NotNull String email,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = userService.checkEmailUnique(email, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查邮箱唯一性失败，邮箱：{}", email, e);
            return Result.error("检查邮箱唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 检查手机号是否唯一
     */
    @GetMapping("/check-phone")
    @Operation(summary = "检查手机号唯一性", description = "检查手机号是否已存在")
    public Result<Boolean> checkPhoneUnique(
            @RequestParam @NotNull String phone,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = userService.checkPhoneUnique(phone, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查手机号唯一性失败，手机号：{}", phone, e);
            return Result.error("检查手机号唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取用户统计", description = "获取用户相关的统计信息")
    public Result<Map<String, Object>> getUserStats() {
        try {
            Map<String, Object> stats = userService.getUserStats();
            return Result.success(stats);
        } catch (Exception e) {
            log.error("获取用户统计信息失败", e);
            return Result.error("获取用户统计信息失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户登录统计
     */
    @GetMapping("/login-stats")
    @Operation(summary = "获取登录统计", description = "获取用户登录统计信息")
    public Result<List<Map<String, Object>>> getUserLoginStats(
            @RequestParam(defaultValue = "7") Integer days) {
        try {
            List<Map<String, Object>> stats = userService.getUserLoginStats(days);
            return Result.success(stats);
        } catch (Exception e) {
            log.error("获取用户登录统计失败", e);
            return Result.error("获取用户登录统计失败：" + e.getMessage());
        }
    }
}