package com.shanhaifangzhou.admin.role.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shanhaifangzhou.base.common.entity.BaseEntity;
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
     * 角色描述
     */
    @TableField("role_desc")
    private String roleDesc;

    /**
     * 角色类型（1：系统角色，2：业务角色）
     */
    @TableField("role_type")
    private Integer roleType;

    /**
     * 状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 菜单ID列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Long> menuIds;

    /**
     * 菜单名称列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<String> menuNames;
}