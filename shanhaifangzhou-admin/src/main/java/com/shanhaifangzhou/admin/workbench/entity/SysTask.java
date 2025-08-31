package com.shanhaifangzhou.admin.workbench.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 工作台任务实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_task")
public class SysTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务标题
     */
    @TableField("task_title")
    private String taskTitle;

    /**
     * 任务描述
     */
    @TableField("task_description")
    private String taskDescription;

    /**
     * 任务类型（1：待办任务，2：进行中任务，3：已完成任务，4：已取消任务）
     */
    @TableField("task_type")
    private Integer taskType;

    /**
     * 任务优先级（1：低，2：中，3：高，4：紧急）
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 任务状态（1：待处理，2：处理中，3：已完成，4：已取消，5：已暂停）
     */
    @TableField("task_status")
    private Integer taskStatus;

    /**
     * 任务进度（0-100）
     */
    @TableField("progress")
    private Integer progress;

    /**
     * 负责人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 负责人姓名
     */
    @TableField("assignee_name")
    private String assigneeName;

    /**
     * 创建人ID
     */
    @TableField("creator_id")
    private Long creatorId;

    /**
     * 创建人姓名
     */
    @TableField("creator_name")
    private String creatorName;

    /**
     * 所属部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 所属部门名称
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 关联业务类型
     */
    @TableField("business_type")
    private String businessType;

    /**
     * 关联业务ID
     */
    @TableField("business_id")
    private String businessId;

    /**
     * 计划开始时间
     */
    @TableField("plan_start_time")
    private LocalDateTime planStartTime;

    /**
     * 计划完成时间
     */
    @TableField("plan_end_time")
    private LocalDateTime planEndTime;

    /**
     * 实际开始时间
     */
    @TableField("actual_start_time")
    private LocalDateTime actualStartTime;

    /**
     * 实际完成时间
     */
    @TableField("actual_end_time")
    private LocalDateTime actualEndTime;

    /**
     * 预计工时（小时）
     */
    @TableField("estimated_hours")
    private Double estimatedHours;

    /**
     * 实际工时（小时）
     */
    @TableField("actual_hours")
    private Double actualHours;

    /**
     * 任务标签
     */
    @TableField("task_tags")
    private String taskTags;

    /**
     * 附件信息（JSON格式）
     */
    @TableField("attachments")
    private String attachments;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 是否置顶（0：否，1：是）
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 是否提醒（0：否，1：是）
     */
    @TableField("is_remind")
    private Integer isRemind;

    /**
     * 提醒时间
     */
    @TableField("remind_time")
    private LocalDateTime remindTime;

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