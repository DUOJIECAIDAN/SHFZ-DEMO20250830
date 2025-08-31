package com.shanhaifangzhou.admin.dept.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.shanhaifangzhou.admin.dept.entity.SysDept;
import com.shanhaifangzhou.admin.dept.dto.DeptQueryDTO;
import com.shanhaifangzhou.admin.dept.dto.DeptCreateDTO;
import com.shanhaifangzhou.admin.dept.dto.DeptUpdateDTO;
import com.shanhaifangzhou.admin.dept.service.SysDeptService;
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
 * 系统部门管理控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/system/dept")
@RequiredArgsConstructor
@Tag(name = "部门管理", description = "系统部门管理相关接口")
@Validated
public class SysDeptController {

    private final SysDeptService deptService;

    /**
     * 分页查询部门列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询部门列表", description = "根据条件分页查询部门列表")
    public Result<PageResult<SysDept>> getDeptPage(@Valid DeptQueryDTO queryDTO) {
        try {
            IPage<SysDept> page = deptService.getDeptPage(queryDTO);
            PageResult<SysDept> result = PageResult.of(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
            return Result.success(result);
        } catch (Exception e) {
            log.error("分页查询部门列表失败", e);
            return Result.error("查询部门列表失败：" + e.getMessage());
        }
    }

    /**
     * 查询所有部门列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询所有部门", description = "获取所有部门列表")
    public Result<List<SysDept>> getAllDepts() {
        try {
            List<SysDept> depts = deptService.getAllDepts();
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询所有部门失败", e);
            return Result.error("查询所有部门失败：" + e.getMessage());
        }
    }

    /**
     * 查询部门树结构
     */
    @GetMapping("/tree")
    @Operation(summary = "查询部门树", description = "获取部门树形结构")
    public Result<List<SysDept>> getDeptTree() {
        try {
            List<SysDept> deptTree = deptService.getDeptTree();
            return Result.success(deptTree);
        } catch (Exception e) {
            log.error("查询部门树失败", e);
            return Result.error("查询部门树失败：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询部门详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询部门详情", description = "根据部门ID查询部门详细信息")
    public Result<SysDept> getDeptById(@PathVariable @NotNull Long id) {
        try {
            SysDept dept = deptService.getById(id);
            if (dept == null) {
                return Result.error("部门不存在");
            }
            return Result.success(dept);
        } catch (Exception e) {
            log.error("查询部门详情失败，部门ID：{}", id, e);
            return Result.error("查询部门详情失败：" + e.getMessage());
        }
    }

    /**
     * 创建部门
     */
    @PostMapping
    @Operation(summary = "创建部门", description = "创建新的系统部门")
    public Result<Void> createDept(@Valid @RequestBody DeptCreateDTO createDTO) {
        try {
            boolean success = deptService.createDept(createDTO);
            if (success) {
                return Result.success("部门创建成功");
            } else {
                return Result.error("部门创建失败");
            }
        } catch (Exception e) {
            log.error("创建部门失败", e);
            return Result.error("创建部门失败：" + e.getMessage());
        }
    }

    /**
     * 更新部门
     */
    @PutMapping
    @Operation(summary = "更新部门", description = "更新系统部门信息")
    public Result<Void> updateDept(@Valid @RequestBody DeptUpdateDTO updateDTO) {
        try {
            boolean success = deptService.updateDept(updateDTO);
            if (success) {
                return Result.success("部门更新成功");
            } else {
                return Result.error("部门更新失败");
            }
        } catch (Exception e) {
            log.error("更新部门失败", e);
            return Result.error("更新部门失败：" + e.getMessage());
        }
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除部门", description = "根据部门ID删除部门")
    public Result<Void> deleteDept(@PathVariable @NotNull Long id) {
        try {
            boolean success = deptService.deleteDept(id);
            if (success) {
                return Result.success("部门删除成功");
            } else {
                return Result.error("部门删除失败");
            }
        } catch (Exception e) {
            log.error("删除部门失败，部门ID：{}", id, e);
            return Result.error("删除部门失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除部门
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除部门", description = "批量删除多个部门")
    public Result<Void> batchDeleteDepts(@RequestBody @NotEmpty List<Long> ids) {
        try {
            boolean success = deptService.batchDeleteDepts(ids);
            if (success) {
                return Result.success("批量删除部门成功");
            } else {
                return Result.error("批量删除部门失败");
            }
        } catch (Exception e) {
            log.error("批量删除部门失败，部门ID列表：{}", ids, e);
            return Result.error("批量删除部门失败：" + e.getMessage());
        }
    }

    /**
     * 更新部门状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新部门状态", description = "启用或禁用部门")
    public Result<Void> updateDeptStatus(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer status) {
        try {
            boolean success = deptService.updateDeptStatus(id, status);
            if (success) {
                return Result.success("部门状态更新成功");
            } else {
                return Result.error("部门状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新部门状态失败，部门ID：{}，状态：{}", id, status, e);
            return Result.error("更新部门状态失败：" + e.getMessage());
        }
    }

    /**
     * 更新部门显示状态
     */
    @PutMapping("/{id}/visible")
    @Operation(summary = "更新部门显示状态", description = "显示或隐藏部门")
    public Result<Void> updateDeptVisible(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer visible) {
        try {
            boolean success = deptService.updateDeptVisible(id, visible);
            if (success) {
                return Result.success("部门显示状态更新成功");
            } else {
                return Result.error("部门显示状态更新失败");
            }
        } catch (Exception e) {
            log.error("更新部门显示状态失败，部门ID：{}，显示状态：{}", id, visible, e);
            return Result.error("更新部门显示状态失败：" + e.getMessage());
        }
    }

    /**
     * 更新部门排序
     */
    @PutMapping("/{id}/sort")
    @Operation(summary = "更新部门排序", description = "更新部门显示顺序")
    public Result<Void> updateDeptSort(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Integer sort) {
        try {
            boolean success = deptService.updateDeptSort(id, sort);
            if (success) {
                return Result.success("部门排序更新成功");
            } else {
                return Result.error("部门排序更新失败");
            }
        } catch (Exception e) {
            log.error("更新部门排序失败，部门ID：{}，排序：{}", id, sort, e);
            return Result.error("更新部门排序失败：" + e.getMessage());
        }
    }

    /**
     * 根据父部门ID查询子部门列表
     */
    @GetMapping("/parent/{parentId}")
    @Operation(summary = "查询子部门", description = "根据父部门ID查询子部门列表")
    public Result<List<SysDept>> getDeptsByParentId(@PathVariable @NotNull Long parentId) {
        try {
            List<SysDept> depts = deptService.getDeptsByParentId(parentId);
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询子部门失败，父部门ID：{}", parentId, e);
            return Result.error("查询子部门失败：" + e.getMessage());
        }
    }

    /**
     * 根据角色ID查询部门列表
     */
    @GetMapping("/role/{roleId}")
    @Operation(summary = "查询角色部门", description = "根据角色ID查询部门列表")
    public Result<List<SysDept>> getDeptsByRoleId(@PathVariable @NotNull Long roleId) {
        try {
            List<SysDept> depts = deptService.getDeptsByRoleId(roleId);
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询角色部门失败，角色ID：{}", roleId, e);
            return Result.error("查询角色部门失败：" + e.getMessage());
        }
    }

    /**
     * 根据用户ID查询部门列表
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "查询用户部门", description = "根据用户ID查询部门列表")
    public Result<List<SysDept>> getDeptsByUserId(@PathVariable @NotNull Long userId) {
        try {
            List<SysDept> depts = deptService.getDeptsByUserId(userId);
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询用户部门失败，用户ID：{}", userId, e);
            return Result.error("查询用户部门失败：" + e.getMessage());
        }
    }

    /**
     * 根据部门编码查询部门
     */
    @GetMapping("/code/{deptCode}")
    @Operation(summary = "查询编码部门", description = "根据部门编码查询部门")
    public Result<SysDept> getDeptByCode(@PathVariable @NotNull String deptCode) {
        try {
            SysDept dept = deptService.getDeptByCode(deptCode);
            return Result.success(dept);
        } catch (Exception e) {
            log.error("查询编码部门失败，部门编码：{}", deptCode, e);
            return Result.error("查询编码部门失败：" + e.getMessage());
        }
    }

    /**
     * 根据部门名称查询部门
     */
    @GetMapping("/name/{deptName}")
    @Operation(summary = "查询名称部门", description = "根据部门名称查询部门")
    public Result<SysDept> getDeptByName(@PathVariable @NotNull String deptName) {
        try {
            SysDept dept = deptService.getDeptByName(deptName);
            return Result.success(dept);
        } catch (Exception e) {
            log.error("查询名称部门失败，部门名称：{}", deptName, e);
            return Result.error("查询名称部门失败：" + e.getMessage());
        }
    }

    /**
     * 获取启用的部门列表
     */
    @GetMapping("/enabled")
    @Operation(summary = "查询启用部门", description = "获取所有启用的部门列表")
    public Result<List<SysDept>> getEnabledDepts() {
        try {
            List<SysDept> depts = deptService.getEnabledDepts();
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询启用部门失败", e);
            return Result.error("查询启用部门失败：" + e.getMessage());
        }
    }

    /**
     * 获取显示的部门列表
     */
    @GetMapping("/visible")
    @Operation(summary = "查询显示部门", description = "获取所有显示的部门列表")
    public Result<List<SysDept>> getVisibleDepts() {
        try {
            List<SysDept> depts = deptService.getVisibleDepts();
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询显示部门失败", e);
            return Result.error("查询显示部门失败：" + e.getMessage());
        }
    }

    /**
     * 根据部门类型查询部门列表
     */
    @GetMapping("/type/{deptType}")
    @Operation(summary = "查询类型部门", description = "根据部门类型查询部门列表")
    public Result<List<SysDept>> getDeptsByType(@PathVariable @NotNull Integer deptType) {
        try {
            List<SysDept> depts = deptService.getDeptsByType(deptType);
            return Result.success(depts);
        } catch (Exception e) {
            log.error("查询类型部门失败，部门类型：{}", deptType, e);
            return Result.error("查询类型部门失败：" + e.getMessage());
        }
    }

    /**
     * 检查部门编码是否唯一
     */
    @GetMapping("/check-code")
    @Operation(summary = "检查部门编码唯一性", description = "检查部门编码是否已存在")
    public Result<Boolean> checkDeptCodeUnique(
            @RequestParam @NotNull String deptCode,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = deptService.checkDeptCodeUnique(deptCode, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查部门编码唯一性失败，部门编码：{}", deptCode, e);
            return Result.error("检查部门编码唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 检查部门名称是否唯一
     */
    @GetMapping("/check-name")
    @Operation(summary = "检查部门名称唯一性", description = "检查同级部门下部门名称是否已存在")
    public Result<Boolean> checkDeptNameUnique(
            @RequestParam @NotNull String deptName,
            @RequestParam(required = false) Long parentId,
            @RequestParam(required = false) Long excludeId) {
        try {
            boolean unique = deptService.checkDeptNameUnique(deptName, parentId, excludeId);
            return Result.success(unique);
        } catch (Exception e) {
            log.error("检查部门名称唯一性失败，部门名称：{}", deptName, e);
            return Result.error("检查部门名称唯一性失败：" + e.getMessage());
        }
    }

    /**
     * 获取部门统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取部门统计", description = "获取部门相关的统计信息")
    public Result<Map<String, Object>> getDeptStats() {
        try {
            Map<String, Object> stats = deptService.getDeptStats();
            return Result.success(stats);
        } catch (Exception e) {
            log.error("获取部门统计信息失败", e);
            return Result.error("获取部门统计信息失败：" + e.getMessage());
        }
    }

    /**
     * 复制部门
     */
    @PostMapping("/{id}/copy")
    @Operation(summary = "复制部门", description = "复制指定部门，创建新部门")
    public Result<Void> copyDept(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull String newDeptName,
            @RequestParam(required = false) Long newParentId) {
        try {
            boolean success = deptService.copyDept(id, newDeptName, newParentId);
            if (success) {
                return Result.success("部门复制成功");
            } else {
                return Result.error("部门复制失败");
            }
        } catch (Exception e) {
            log.error("复制部门失败，部门ID：{}", id, e);
            return Result.error("复制部门失败：" + e.getMessage());
        }
    }

    /**
     * 移动部门
     */
    @PutMapping("/{id}/move")
    @Operation(summary = "移动部门", description = "将部门移动到新的父部门下")
    public Result<Void> moveDept(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Long newParentId) {
        try {
            boolean success = deptService.moveDept(id, newParentId);
            if (success) {
                return Result.success("部门移动成功");
            } else {
                return Result.error("部门移动失败");
            }
        } catch (Exception e) {
            log.error("移动部门失败，部门ID：{}", id, e);
            return Result.error("移动部门失败：" + e.getMessage());
        }
    }

    /**
     * 展开/折叠部门
     */
    @PutMapping("/{id}/toggle")
    @Operation(summary = "切换部门展开状态", description = "展开或折叠部门")
    public Result<Void> toggleDeptExpand(
            @PathVariable @NotNull Long id,
            @RequestParam @NotNull Boolean expanded) {
        try {
            boolean success = deptService.toggleDeptExpand(id, expanded);
            if (success) {
                return Result.success("部门状态切换成功");
            } else {
                return Result.error("部门状态切换失败");
            }
        } catch (Exception e) {
            log.error("切换部门展开状态失败，部门ID：{}", id, e);
            return Result.error("切换部门展开状态失败：" + e.getMessage());
        }
    }

    /**
     * 获取部门用户数量
     */
    @GetMapping("/{id}/user-count")
    @Operation(summary = "获取部门用户数量", description = "获取指定部门下的用户数量")
    public Result<Integer> getDeptUserCount(@PathVariable @NotNull Long id) {
        try {
            Integer count = deptService.getDeptUserCount(id);
            return Result.success(count);
        } catch (Exception e) {
            log.error("获取部门用户数量失败，部门ID：{}", id, e);
            return Result.error("获取部门用户数量失败：" + e.getMessage());
        }
    }

    /**
     * 获取部门角色数量
     */
    @GetMapping("/{id}/role-count")
    @Operation(summary = "获取部门角色数量", description = "获取指定部门分配的角色数量")
    public Result<Integer> getDeptRoleCount(@PathVariable @NotNull Long id) {
        try {
            Integer count = deptService.getDeptRoleCount(id);
            return Result.success(count);
        } catch (Exception e) {
            log.error("获取部门角色数量失败，部门ID：{}", id, e);
            return Result.error("获取部门角色数量失败：" + e.getMessage());
        }
    }
}