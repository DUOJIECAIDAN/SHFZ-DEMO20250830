package com.shanhaifangzhou.admin.role.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shanhaifangzhou.base.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
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
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 角色类型（0：系统角色，1：业务角色）
     */
    @TableField("role_type")
    private Integer roleType;

    /**
     * 角色状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 角色排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 数据范围（1：全部数据权限，2：自定义数据权限，3：本部门数据权限，4：本部门及以下数据权限，5：仅本人数据权限）
     */
    @TableField("data_scope")
    private Integer dataScope;

    /**
     * 角色级别（1：超级管理员，2：系统管理员，3：普通管理员，4：普通用户）
     */
    @TableField("role_level")
    private Integer roleLevel;

    /**
     * 是否系统内置（0：否，1：是）
     */
    @TableField("is_system")
    private Integer isSystem;

    /**
     * 是否可删除（0：否，1：是）
     */
    @TableField("is_deletable")
    private Integer isDeletable;

    /**
     * 是否可编辑（0：否，1：是）
     */
    @TableField("is_editable")
    private Integer isEditable;

    /**
     * 角色图标
     */
    @TableField("role_icon")
    private String roleIcon;

    /**
     * 角色颜色
     */
    @TableField("role_color")
    private String roleColor;

    /**
     * 扩展字段
     */
    @TableField("ext_data")
    private String extData;

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

    /**
     * 用户数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer userCount;

    /**
     * 部门ID列表（非数据库字段，用于数据权限）
     */
    @TableField(exist = false)
    private List<Long> deptIds;
}