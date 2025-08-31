package com.shanhaifangzhou.admin.role.dto;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * 角色创建DTO
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
public class RoleCreateDTO {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(max = 50, message = "角色名称长度不能超过50个字符")
    private String roleName;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    @Pattern(regexp = "^[A-Z_]+$", message = "角色编码只能包含大写字母和下划线")
    @Size(max = 50, message = "角色编码长度不能超过50个字符")
    private String roleCode;

    /**
     * 角色描述
     */
    @Size(max = 200, message = "角色描述长度不能超过200个字符")
    private String roleDesc;

    /**
     * 角色类型（0：系统角色，1：业务角色）
     */
    @Min(value = 0, message = "角色类型值不正确")
    @Max(value = 1, message = "角色类型值不正确")
    private Integer roleType;

    /**
     * 角色状态（0：禁用，1：启用）
     */
    @Min(value = 0, message = "角色状态值不正确")
    @Max(value = 1, message = "角色状态值不正确")
    private Integer status;

    /**
     * 角色排序
     */
    @Min(value = 0, message = "角色排序值不能小于0")
    @Max(value = 9999, message = "角色排序值不能大于9999")
    private Integer sort;

    /**
     * 数据范围（1：全部数据权限，2：自定义数据权限，3：本部门数据权限，4：本部门及以下数据权限，5：仅本人数据权限）
     */
    @Min(value = 1, message = "数据范围值不正确")
    @Max(value = 5, message = "数据范围值不正确")
    private Integer dataScope;

    /**
     * 角色级别（1：超级管理员，2：系统管理员，3：普通管理员，4：普通用户）
     */
    @Min(value = 1, message = "角色级别值不正确")
    @Max(value = 4, message = "角色级别值不正确")
    private Integer roleLevel;

    /**
     * 角色图标
     */
    @Size(max = 100, message = "角色图标长度不能超过100个字符")
    private String roleIcon;

    /**
     * 角色颜色
     */
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "角色颜色格式不正确")
    private String roleColor;

    /**
     * 菜单ID列表
     */
    private List<Long> menuIds;

    /**
     * 部门ID列表（用于数据权限）
     */
    private List<Long> deptIds;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}