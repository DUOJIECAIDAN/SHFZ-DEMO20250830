package com.shanhaifangzhou.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shanhaifangzhou.base.entity.BaseEntity;
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
     * 用户名
     */
    @TableField("username")
    private String username;

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
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 性别（0男 1女 2未知）
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 岗位ID
     */
    @TableField("post_id")
    private Long postId;

    /**
     * 用户类型（0系统用户 1注册用户）
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 最后登录IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_time")
    private LocalDateTime loginTime;

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * 角色名称列表
     */
    @TableField(exist = false)
    private List<String> roleNames;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 岗位名称
     */
    @TableField(exist = false)
    private String postName;
}