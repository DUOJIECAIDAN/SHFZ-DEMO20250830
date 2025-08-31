package com.shanhaifangzhou.admin.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_workflow")
public class SysWorkflow implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工作流ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工作流名称
     */
    @TableField("workflow_name")
    private String workflowName;

    /**
     * 工作流编码
     */
    @TableField("workflow_code")
    private String workflowCode;

    /**
     * 工作流描述
     */
    @TableField("description")
    private String description;

    /**
     * 工作流类型（1：审批流程，2：业务流程，3：其他流程）
     */
    @TableField("workflow_type")
    private Integer workflowType;

    /**
     * 工作流状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 工作流版本
     */
    @TableField("version")
    private Integer version;

    /**
     * 是否最新版本（0：否，1：是）
     */
    @TableField("is_latest")
    private Integer isLatest;

    /**
     * 工作流定义（JSON格式）
     */
    @TableField("workflow_definition")
    private String workflowDefinition;

    /**
     * 流程发起人角色
     */
    @TableField("initiator_role")
    private String initiatorRole;

    /**
     * 流程发起人部门
     */
    @TableField("initiator_dept")
    private String initiatorDept;

    /**
     * 审批超时时间（小时）
     */
    @TableField("timeout_hours")
    private Integer timeoutHours;

    /**
     * 是否允许撤回（0：否，1：是）
     */
    @TableField("allow_withdraw")
    private Integer allowWithdraw;

    /**
     * 是否允许转办（0：否，1：是）
     */
    @TableField("allow_transfer")
    private Integer allowTransfer;

    /**
     * 是否允许加签（0：否，1：是）
     */
    @TableField("allow_add_sign")
    private Integer allowAddSign;

    /**
     * 流程图标
     */
    @TableField("workflow_icon")
    private String workflowIcon;

    /**
     * 流程颜色
     */
    @TableField("workflow_color")
    private String workflowColor;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}