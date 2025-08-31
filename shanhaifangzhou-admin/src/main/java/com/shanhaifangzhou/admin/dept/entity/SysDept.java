package com.shanhaifangzhou.admin.dept.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.shanhaifangzhou.base.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统部门实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
public class SysDept extends BaseEntity {

    /**
     * 部门ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父部门ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 部门编码
     */
    @TableField("dept_code")
    private String deptCode;

    /**
     * 部门类型（1：公司，2：部门，3：小组）
     */
    @TableField("dept_type")
    private Integer deptType;

    /**
     * 部门状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 显示状态（0：隐藏，1：显示）
     */
    @TableField("visible")
    private Integer visible;

    /**
     * 部门排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 部门负责人ID
     */
    @TableField("leader_id")
    private Long leaderId;

    /**
     * 部门负责人姓名
     */
    @TableField("leader_name")
    private String leaderName;

    /**
     * 部门负责人电话
     */
    @TableField("leader_phone")
    private String leaderPhone;

    /**
     * 部门负责人邮箱
     */
    @TableField("leader_email")
    private String leaderEmail;

    /**
     * 部门联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 部门邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 部门地址
     */
    @TableField("address")
    private String address;

    /**
     * 部门描述
     */
    @TableField("description")
    private String description;

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
     * 部门图标
     */
    @TableField("dept_icon")
    private String deptIcon;

    /**
     * 部门颜色
     */
    @TableField("dept_color")
    private String deptColor;

    /**
     * 扩展字段
     */
    @TableField("ext_data")
    private String extData;

    /**
     * 子部门列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<SysDept> children;

    /**
     * 父部门名称（非数据库字段）
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 角色ID列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 角色名称列表（非数据库字段）
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 用户数量（非数据库字段）
     */
    @TableField(exist = false)
    private Integer userCount;

    /**
     * 是否选中（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean checked;

    /**
     * 是否半选（非数据库字段）
     */
    @TableField(exist = false)
    private Boolean indeterminate;
}