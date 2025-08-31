package com.shanhaifangzhou.admin.notice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知公告实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_notice")
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告标题
     */
    @TableField("notice_title")
    private String noticeTitle;

    /**
     * 公告类型（1：通知，2：公告，3：提醒）
     */
    @TableField("notice_type")
    private Integer noticeType;

    /**
     * 公告内容
     */
    @TableField("notice_content")
    private String noticeContent;

    /**
     * 公告状态（0：草稿，1：发布，2：撤回）
     */
    @TableField("notice_status")
    private Integer noticeStatus;

    /**
     * 公告级别（1：普通，2：重要，3：紧急）
     */
    @TableField("notice_level")
    private Integer noticeLevel;

    /**
     * 发布人ID
     */
    @TableField("publisher_id")
    private Long publisherId;

    /**
     * 发布人姓名
     */
    @TableField("publisher_name")
    private String publisherName;

    /**
     * 发布人部门ID
     */
    @TableField("publisher_dept_id")
    private Long publisherDeptId;

    /**
     * 发布人部门名称
     */
    @TableField("publisher_dept_name")
    private String publisherDeptName;

    /**
     * 发布时间
     */
    @TableField("publish_time")
    private LocalDateTime publishTime;

    /**
     * 生效时间
     */
    @TableField("effective_time")
    private LocalDateTime effectiveTime;

    /**
     * 失效时间
     */
    @TableField("expire_time")
    private LocalDateTime expireTime;

    /**
     * 是否置顶（0：否，1：是）
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 是否弹窗（0：否，1：是）
     */
    @TableField("is_popup")
    private Integer isPopup;

    /**
     * 弹窗次数限制
     */
    @TableField("popup_limit")
    private Integer popupLimit;

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
     * 接收范围类型（1：全部用户，2：指定部门，3：指定角色，4：指定用户）
     */
    @TableField("receive_scope_type")
    private Integer receiveScopeType;

    /**
     * 接收范围（JSON格式）
     */
    @TableField("receive_scope")
    private String receiveScope;

    /**
     * 已读用户数
     */
    @TableField("read_count")
    private Integer readCount;

    /**
     * 总用户数
     */
    @TableField("total_count")
    private Integer totalCount;

    /**
     * 阅读率
     */
    @TableField("read_rate")
    private Double readRate;

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