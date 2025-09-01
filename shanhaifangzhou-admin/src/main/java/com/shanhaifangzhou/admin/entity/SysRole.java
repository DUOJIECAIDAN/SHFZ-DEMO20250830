package com.shanhaifangzhou.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shanhaifangzhou.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统角色实体
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 显示顺序
     */
    @TableField("role_sort")
    private Integer roleSort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限 5：仅本人数据权限）
     */
    @TableField("data_scope")
    private Integer dataScope;

    /**
     * 菜单树选择项是否关联显示
     */
    @TableField("menu_check_strictly")
    private Boolean menuCheckStrictly;

    /**
     * 部门树选择项是否关联显示
     */
    @TableField("dept_check_strictly")
    private Boolean deptCheckStrictly;

    /**
     * 角色状态（0正常 1停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 菜单ID列表
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 部门ID列表
     */
    @TableField(exist = false)
    private List<Long> deptIds;
}