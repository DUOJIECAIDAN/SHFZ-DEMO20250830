package com.shanhaifangzhou.admin.role.dto;

import com.shanhaifangzhou.base.mybatis.page.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 角色查询DTO
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQueryDTO extends PageQuery {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色状态
     */
    private Integer status;

    /**
     * 角色类型
     */
    private Integer roleType;

    /**
     * 角色级别
     */
    private Integer roleLevel;

    /**
     * 是否系统内置
     */
    private Integer isSystem;

    /**
     * 数据范围
     */
    private Integer dataScope;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;
}