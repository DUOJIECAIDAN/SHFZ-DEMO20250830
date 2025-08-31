package com.shanhaifangzhou.base.mybatis.page;

import lombok.Data;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import cn.hutool.core.util.StrUtil;

/**
 * 分页查询对象
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码（从1开始）
     */
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码必须大于0")
    private Long current = 1L;

    /**
     * 每页大小
     */
    @NotNull(message = "每页大小不能为空")
    @Min(value = 1, message = "每页大小必须大于0")
    @Max(value = 1000, message = "每页大小不能超过1000")
    private Long size = 10L;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方向（asc/desc）
     */
    private String orderDirection = "desc";

    /**
     * 搜索关键词
     */
    private String keyword;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 获取偏移量
     */
    public Long getOffset() {
        return (current - 1) * size;
    }

    /**
     * 获取排序SQL
     */
    public String getOrderBySql() {
        if (StrUtil.isBlank(orderBy)) {
            return "";
        }
        String direction = "desc".equalsIgnoreCase(orderDirection) ? "DESC" : "ASC";
        return String.format("ORDER BY %s %s", orderBy, direction);
    }
}