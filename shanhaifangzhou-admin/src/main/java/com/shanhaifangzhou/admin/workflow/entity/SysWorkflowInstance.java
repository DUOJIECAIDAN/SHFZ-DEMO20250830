package com.shanhaifangzhou.admin.workflow.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作流实例实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_workflow_instance")
public class SysWorkflowInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实例ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工作流ID
     */
    @TableField("workflow_id")
    private Long workflowId;

    /**
     * 工作流名称
     */
    @TableField("workflow_name")
    private String workflowName;

    /**
     * 实例标题
     */
    @TableField("instance_title")
    private String instanceTitle;

    /**
     * 业务类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 业务ID
     */
    @TableField("business_id")
    private String businessId;

    /**
     * 发起人ID
     */
    @TableField("initiator_id")
    private Long initiatorId;

    /**
     * 发起人姓名
     */
    @TableField("initiator_name")
    private String initiatorName;

    /**
     * 发起人部门ID
     */
    @TableField("initiator_dept_id")
    private Long initiatorDeptId;

    /**
     * 发起人部门名称
     */
    @TableField("initiator_dept_name")
    private String initiatorDeptName;

    /**
     * 当前节点ID
     */
    @TableField("current_node_id")
    private String currentNodeId;

    /**
     * 当前节点名称
     */
    @TableField("current_node_name")
    private String currentNodeName;

    /**
     * 当前审批人ID
     */
    @TableField("current_approver_id")
    private Long currentApproverId;

    /**
     * 当前审批人姓名
     */
    @TableField("current_approver_name")
    private String currentApproverName;

    /**
     * 实例状态（1：进行中，2：已完成，3：已拒绝，4：已撤回，5：已超时）
     */
    @TableField("instance_status")
    private Integer instanceStatus;

    /**
     * 优先级（1：低，2：中，3：高，4：紧急）
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 发起时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 预计完成时间
     */
    @TableField("expected_end_time")
    private LocalDateTime expectedEndTime;

    /**
     * 实际耗时（分钟）
     */
    @TableField("actual_duration")
    private Long actualDuration;

    /**
     * 审批意见
     */
    @TableField("approval_opinion")
    private String approvalOpinion;

    /**
     * 流程数据（JSON格式）
     */
    @TableField("process_data")
    private String processData;

    /**
     * 附件信息（JSON格式）
     */
    @TableField("attachments")
    private String attachments;

    /**
     * 是否紧急（0：否，1：是）
     */
    @TableField("is_urgent")
    private Integer isUrgent;

    /**
     * 紧急原因
     */
    @TableField("urgent_reason")
    private String urgentReason;

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