package com.shanhaifangzhou.admin.dept.dto;

import com.shanhaifangzhou.base.mybatis.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 部门查询DTO
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptQueryDTO extends PageQuery {

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 部门类型
     */
    private Integer deptType;

    /**
     * 部门状态
     */
    private Integer status;

    /**
     * 显示状态
     */
    private Integer visible;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门负责人姓名
     */
    private String leaderName;

    /**
     * 部门负责人电话
     */
    private String leaderPhone;

    /**
     * 部门负责人邮箱
     */
    private String leaderEmail;

    /**
     * 部门联系电话
     */
    private String phone;

    /**
     * 部门邮箱
     */
    private String email;

    /**
     * 是否系统内置
     */
    private Integer isSystem;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}