package com.shanhaifangzhou.admin.message.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息管理实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_message")
public class SysMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息标题
     */
    @TableField("message_title")
    private String messageTitle;

    /**
     * 消息内容
     */
    @TableField("message_content")
    private String messageContent;

    /**
     * 消息类型（1：系统消息，2：业务消息，3：通知消息，4：提醒消息）
     */
    @TableField("message_type")
    private Integer messageType;

    /**
     * 消息级别（1：普通，2：重要，3：紧急）
     */
    @TableField("message_level")
    private Integer messageLevel;

    /**
     * 消息状态（1：草稿，2：已发送，3：已撤回）
     */
    @TableField("message_status")
    private Integer messageStatus;

    /**
     * 发送人ID
     */
    @TableField("sender_id")
    private Long senderId;

    /**
     * 发送人姓名
     */
    @TableField("sender_name")
    private String senderName;

    /**
     * 发送人部门ID
     */
    @TableField("sender_dept_id")
    private Long senderDeptId;

    /**
     * 发送人部门名称
     */
    @TableField("sender_dept_name")
    private String senderDeptName;

    /**
     * 接收范围类型（1：全部用户，2：指定角色，3：指定部门，4：指定用户，5：在线用户）
     */
    @TableField("receive_scope_type")
    private Integer receiveScopeType;

    /**
     * 接收范围（JSON格式）
     */
    @TableField("receive_scope")
    private String receiveScope;

    /**
     * 发送时间
     */
    @TableField("send_time")
    private LocalDateTime sendTime;

    /**
     * 计划发送时间
     */
    @TableField("plan_send_time")
    private LocalDateTime planSendTime;

    /**
     * 是否定时发送（0：否，1：是）
     */
    @TableField("is_scheduled")
    private Integer isScheduled;

    /**
     * 是否弹窗显示（0：否，1：是）
     */
    @TableField("is_popup")
    private Integer isPopup;

    /**
     * 弹窗持续时间（秒）
     */
    @TableField("popup_duration")
    private Integer popupDuration;

    /**
     * 是否邮件通知（0：否，1：是）
     */
    @TableField("is_email")
    private Integer isEmail;

    /**
     * 是否短信通知（0：否，1：是）
     */
    @TableField("is_sms")
    private Integer isSms;

    /**
     * 是否站内信（0：否，1：是）
     */
    @TableField("is_site_message")
    private Integer isSiteMessage;

    /**
     * 是否推送通知（0：否，1：是）
     */
    @TableField("is_push")
    private Integer isPush;

    /**
     * 消息模板ID
     */
    @TableField("template_id")
    private Long templateId;

    /**
     * 消息模板名称
     */
    @TableField("template_name")
    private String templateName;

    /**
     * 消息参数（JSON格式）
     */
    @TableField("message_params")
    private String messageParams;

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
     * 消息链接
     */
    @TableField("message_link")
    private String messageLink;

    /**
     * 附件信息（JSON格式）
     */
    @TableField("attachments")
    private String attachments;

    /**
     * 总接收人数
     */
    @TableField("total_receiver_count")
    private Integer totalReceiverCount;

    /**
     * 已读人数
     */
    @TableField("read_count")
    private Integer readCount;

    /**
     * 未读人数
     */
    @TableField("unread_count")
    private Integer unreadCount;

    /**
     * 阅读率
     */
    @TableField("read_rate")
    private Double readRate;

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