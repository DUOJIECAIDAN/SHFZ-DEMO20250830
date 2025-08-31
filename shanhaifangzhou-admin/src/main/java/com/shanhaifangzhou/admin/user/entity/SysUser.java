package com.shanhaifangzhou.admin.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shanhaifangzhou.base.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    @TableField("password")
    private String password;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别（0：未知，1：男，2：女）
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @TableField("last_login_ip")
    private String lastLoginIp;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

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
     * 部门名称（非数据库字段）
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 岗位ID
     */
    @TableField("post_id")
    private Long postId;

    /**
     * 岗位名称（非数据库字段）
     */
    @TableField(exist = false)
    private String postName;

    /**
     * 用户类型（0：系统用户，1：业务用户）
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 登录类型（0：用户名密码，1：手机号，2：邮箱）
     */
    @TableField("login_type")
    private Integer loginType;

    /**
     * 是否首次登录（0：否，1：是）
     */
    @TableField("is_first_login")
    private Integer isFirstLogin;

    /**
     * 密码错误次数
     */
    @TableField("password_error_count")
    private Integer passwordErrorCount;

    /**
     * 密码错误时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("password_error_time")
    private LocalDateTime passwordErrorTime;

    /**
     * 账号锁定时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("lock_time")
    private LocalDateTime lockTime;

    /**
     * 账号锁定原因
     */
    @TableField("lock_reason")
    private String lockReason;

    /**
     * 在线状态（0：离线，1：在线）
     */
    @TableField("online_status")
    private Integer onlineStatus;

    /**
     * 最后活跃时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("last_active_time")
    private LocalDateTime lastActiveTime;

    /**
     * 用户来源（0：系统创建，1：注册，2：导入）
     */
    @TableField("user_source")
    private Integer userSource;

    /**
     * 扩展字段
     */
    @TableField("ext_data")
    private String extData;
}