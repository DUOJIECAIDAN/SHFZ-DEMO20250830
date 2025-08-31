package com.shanhaifangzhou.admin.dept.dto;

import lombok.Data;

import jakarta.validation.constraints.*;

/**
 * 部门更新DTO
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
public class DeptUpdateDTO {

    /**
     * 部门ID
     */
    @NotNull(message = "部门ID不能为空")
    private Long id;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称长度不能超过50个字符")
    private String deptName;

    /**
     * 部门编码
     */
    @NotBlank(message = "部门编码不能为空")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "部门编码只能包含大写字母、数字和下划线")
    @Size(max = 50, message = "部门编码长度不能超过50个字符")
    private String deptCode;

    /**
     * 部门类型（1：公司，2：部门，3：小组）
     */
    @NotNull(message = "部门类型不能为空")
    @Min(value = 1, message = "部门类型值不正确")
    @Max(value = 3, message = "部门类型值不正确")
    private Integer deptType;

    /**
     * 部门状态（0：禁用，1：启用）
     */
    @Min(value = 0, message = "部门状态值不正确")
    @Max(value = 1, message = "部门状态值不正确")
    private Integer status;

    /**
     * 显示状态（0：隐藏，1：显示）
     */
    @Min(value = 0, message = "显示状态值不正确")
    @Max(value = 1, message = "显示状态值不正确")
    private Integer visible;

    /**
     * 部门排序
     */
    @Min(value = 0, message = "部门排序值不能小于0")
    @Max(value = 9999, message = "部门排序值不能大于9999")
    private Integer sort;

    /**
     * 部门负责人ID
     */
    private Long leaderId;

    /**
     * 部门负责人姓名
     */
    @Size(max = 50, message = "部门负责人姓名长度不能超过50个字符")
    private String leaderName;

    /**
     * 部门负责人电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "部门负责人电话格式不正确")
    private String leaderPhone;

    /**
     * 部门负责人邮箱
     */
    @Email(message = "部门负责人邮箱格式不正确")
    @Size(max = 50, message = "部门负责人邮箱长度不能超过50个字符")
    private String leaderEmail;

    /**
     * 部门联系电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "部门联系电话格式不正确")
    private String phone;

    /**
     * 部门邮箱
     */
    @Email(message = "部门邮箱格式不正确")
    @Size(max = 50, message = "部门邮箱长度不能超过50个字符")
    private String email;

    /**
     * 部门地址
     */
    @Size(max = 200, message = "部门地址长度不能超过200个字符")
    private String address;

    /**
     * 部门描述
     */
    @Size(max = 500, message = "部门描述长度不能超过500个字符")
    private String description;

    /**
     * 部门图标
     */
    @Size(max = 100, message = "部门图标长度不能超过100个字符")
    private String deptIcon;

    /**
     * 部门颜色
     */
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "部门颜色格式不正确")
    private String deptColor;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}