package com.shanhaifangzhou.admin.report.controller;

import com.shanhaifangzhou.base.common.result.Result;
import com.shanhaifangzhou.admin.report.service.SysReportService;
import com.shanhaifangzhou.admin.report.dto.ReportQueryDTO;
import com.shanhaifangzhou.admin.report.dto.ReportCreateDTO;
import com.shanhaifangzhou.admin.report.dto.ReportUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 报表管理控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Tag(name = "报表管理", description = "报表管理相关接口")
public class SysReportController {

    private final SysReportService reportService;

    /**
     * 分页查询报表列表
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询报表列表", description = "根据条件分页查询报表列表")
    public Result<Map<String, Object>> getReportPage(ReportQueryDTO queryDTO) {
        try {
            Map<String, Object> result = reportService.getReportPage(queryDTO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("分页查询报表列表失败", e);
            return Result.error("分页查询报表列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取报表详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取报表详情", description = "根据ID获取报表详细信息")
    public Result<Map<String, Object>> getReportDetail(@PathVariable Long id) {
        try {
            Map<String, Object> report = reportService.getReportDetail(id);
            return Result.success(report);
        } catch (Exception e) {
            log.error("获取报表详情失败", e);
            return Result.error("获取报表详情失败：" + e.getMessage());
        }
    }

    /**
     * 创建报表
     */
    @PostMapping
    @Operation(summary = "创建报表", description = "创建新的报表")
    public Result<Long> createReport(@Valid @RequestBody ReportCreateDTO createDTO) {
        try {
            Long reportId = reportService.createReport(createDTO);
            return Result.success(reportId);
        } catch (Exception e) {
            log.error("创建报表失败", e);
            return Result.error("创建报表失败：" + e.getMessage());
        }
    }

    /**
     * 更新报表
     */
    @PutMapping
    @Operation(summary = "更新报表", description = "更新现有报表信息")
    public Result<Boolean> updateReport(@Valid @RequestBody ReportUpdateDTO updateDTO) {
        try {
            boolean success = reportService.updateReport(updateDTO);
            return Result.success(success);
        } catch (Exception e) {
            log.error("更新报表失败", e);
            return Result.error("更新报表失败：" + e.getMessage());
        }
    }

    /**
     * 删除报表
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除报表", description = "根据ID删除报表")
    public Result<Boolean> deleteReport(@PathVariable Long id) {
        try {
            boolean success = reportService.deleteReport(id);
            return Result.success(success);
        } catch (Exception e) {
            log.error("删除报表失败", e);
            return Result.error("删除报表失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除报表
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除报表", description = "批量删除多个报表")
    public Result<Boolean> batchDeleteReports(@RequestBody List<Long> ids) {
        try {
            boolean success = reportService.batchDeleteReports(ids);
            return Result.success(success);
        } catch (Exception e) {
            log.error("批量删除报表失败", e);
            return Result.error("批量删除报表失败：" + e.getMessage());
        }
    }

    /**
     * 执行报表查询
     */
    @PostMapping("/{id}/execute")
    @Operation(summary = "执行报表查询", description = "执行报表查询并返回数据")
    public Result<Map<String, Object>> executeReport(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> data = reportService.executeReport(id, params);
            return Result.success(data);
        } catch (Exception e) {
            log.error("执行报表查询失败", e);
            return Result.error("执行报表查询失败：" + e.getMessage());
        }
    }

    /**
     * 导出报表数据
     */
    @PostMapping("/{id}/export")
    @Operation(summary = "导出报表数据", description = "导出报表数据到Excel或PDF")
    public void exportReport(@PathVariable Long id, @RequestBody Map<String, Object> params, HttpServletResponse response) {
        try {
            reportService.exportReport(id, params, response);
        } catch (Exception e) {
            log.error("导出报表数据失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 预览报表
     */
    @PostMapping("/{id}/preview")
    @Operation(summary = "预览报表", description = "预览报表效果")
    public Result<Map<String, Object>> previewReport(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> preview = reportService.previewReport(id, params);
            return Result.success(preview);
        } catch (Exception e) {
            log.error("预览报表失败", e);
            return Result.error("预览报表失败：" + e.getMessage());
        }
    }

    /**
     * 获取报表分类列表
     */
    @GetMapping("/categories")
    @Operation(summary = "获取报表分类列表", description = "获取所有报表分类")
    public Result<List<Map<String, Object>>> getReportCategories() {
        try {
            List<Map<String, Object>> categories = reportService.getReportCategories();
            return Result.success(categories);
        } catch (Exception e) {
            log.error("获取报表分类列表失败", e);
            return Result.error("获取报表分类列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取数据源列表
     */
    @GetMapping("/data-sources")
    @Operation(summary = "获取数据源列表", description = "获取可用的数据源列表")
    public Result<List<Map<String, Object>>> getDataSources() {
        try {
            List<Map<String, Object>> dataSources = reportService.getDataSources();
            return Result.success(dataSources);
        } catch (Exception e) {
            log.error("获取数据源列表失败", e);
            return Result.error("获取数据源列表失败：" + e.getMessage());
        }
    }

    /**
     * 测试数据源连接
     */
    @PostMapping("/test-data-source")
    @Operation(summary = "测试数据源连接", description = "测试数据源连接是否正常")
    public Result<Boolean> testDataSource(@RequestBody Map<String, Object> dataSourceConfig) {
        try {
            boolean success = reportService.testDataSource(dataSourceConfig);
            return Result.success(success);
        } catch (Exception e) {
            log.error("测试数据源连接失败", e);
            return Result.error("测试数据源连接失败：" + e.getMessage());
        }
    }

    /**
     * 获取图表类型列表
     */
    @GetMapping("/chart-types")
    @Operation(summary = "获取图表类型列表", description = "获取支持的图表类型")
    public Result<List<Map<String, Object>>> getChartTypes() {
        try {
            List<Map<String, Object>> chartTypes = reportService.getChartTypes();
            return Result.success(chartTypes);
        } catch (Exception e) {
            log.error("获取图表类型列表失败", e);
            return Result.error("获取图表类型列表失败：" + e.getMessage());
        }
    }

    /**
     * 复制报表
     */
    @PostMapping("/{id}/copy")
    @Operation(summary = "复制报表", description = "复制现有报表")
    public Result<Long> copyReport(@PathVariable Long id, @RequestBody Map<String, Object> copyParams) {
        try {
            Long newReportId = reportService.copyReport(id, copyParams);
            return Result.success(newReportId);
        } catch (Exception e) {
            log.error("复制报表失败", e);
            return Result.error("复制报表失败：" + e.getMessage());
        }
    }

    /**
     * 发布报表
     */
    @PostMapping("/{id}/publish")
    @Operation(summary = "发布报表", description = "发布报表供用户访问")
    public Result<Boolean> publishReport(@PathVariable Long id) {
        try {
            boolean success = reportService.publishReport(id);
            return Result.success(success);
        } catch (Exception e) {
            log.error("发布报表失败", e);
            return Result.error("发布报表失败：" + e.getMessage());
        }
    }

    /**
     * 取消发布报表
     */
    @PostMapping("/{id}/unpublish")
    @Operation(summary = "取消发布报表", description = "取消发布报表")
    public Result<Boolean> unpublishReport(@PathVariable Long id) {
        try {
            boolean success = reportService.unpublishReport(id);
            return Result.success(success);
        } catch (Exception e) {
            log.error("取消发布报表失败", e);
            return Result.error("取消发布报表失败：" + e.getMessage());
        }
    }
}