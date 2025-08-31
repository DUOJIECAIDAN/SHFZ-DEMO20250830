package com.shanhaifangzhou.admin.generator.controller;

import com.shanhaifangzhou.base.common.result.Result;
import com.shanhaifangzhou.admin.generator.service.CodeGeneratorService;
import com.shanhaifangzhou.admin.generator.dto.GenerateCodeDTO;
import com.shanhaifangzhou.admin.generator.dto.TableInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/generator")
@RequiredArgsConstructor
@Tag(name = "代码生成器", description = "代码生成器相关接口")
public class CodeGeneratorController {

    private final CodeGeneratorService codeGeneratorService;

    /**
     * 获取数据库表列表
     */
    @GetMapping("/tables")
    @Operation(summary = "获取数据库表列表", description = "获取数据库中所有表信息")
    public Result<List<TableInfoDTO>> getTableList() {
        try {
            List<TableInfoDTO> tables = codeGeneratorService.getTableList();
            return Result.success(tables);
        } catch (Exception e) {
            log.error("获取数据库表列表失败", e);
            return Result.error("获取数据库表列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取表字段信息
     */
    @GetMapping("/tables/{tableName}/columns")
    @Operation(summary = "获取表字段信息", description = "获取指定表的字段详细信息")
    public Result<List<Map<String, Object>>> getTableColumns(@PathVariable String tableName) {
        try {
            List<Map<String, Object>> columns = codeGeneratorService.getTableColumns(tableName);
            return Result.success(columns);
        } catch (Exception e) {
            log.error("获取表字段信息失败", e);
            return Result.error("获取表字段信息失败：" + e.getMessage());
        }
    }

    /**
     * 预览生成的代码
     */
    @PostMapping("/preview")
    @Operation(summary = "预览生成的代码", description = "预览根据配置生成的代码")
    public Result<Map<String, String>> previewCode(@RequestBody GenerateCodeDTO generateCodeDTO) {
        try {
            Map<String, String> codeMap = codeGeneratorService.previewCode(generateCodeDTO);
            return Result.success(codeMap);
        } catch (Exception e) {
            log.error("预览代码失败", e);
            return Result.error("预览代码失败：" + e.getMessage());
        }
    }

    /**
     * 生成代码并下载
     */
    @PostMapping("/download")
    @Operation(summary = "生成代码并下载", description = "根据配置生成代码并打包下载")
    public void downloadCode(@RequestBody GenerateCodeDTO generateCodeDTO, HttpServletResponse response) {
        try {
            codeGeneratorService.downloadCode(generateCodeDTO, response);
        } catch (Exception e) {
            log.error("下载代码失败", e);
            // 设置错误响应
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 生成代码到指定目录
     */
    @PostMapping("/generate")
    @Operation(summary = "生成代码到指定目录", description = "根据配置生成代码到指定目录")
    public Result<String> generateCode(@RequestBody GenerateCodeDTO generateCodeDTO) {
        try {
            String result = codeGeneratorService.generateCode(generateCodeDTO);
            return Result.success(result);
        } catch (Exception e) {
            log.error("生成代码失败", e);
            return Result.error("生成代码失败：" + e.getMessage());
        }
    }

    /**
     * 获取模板列表
     */
    @GetMapping("/templates")
    @Operation(summary = "获取模板列表", description = "获取可用的代码模板列表")
    public Result<List<Map<String, Object>>> getTemplateList() {
        try {
            List<Map<String, Object>> templates = codeGeneratorService.getTemplateList();
            return Result.success(templates);
        } catch (Exception e) {
            log.error("获取模板列表失败", e);
            return Result.error("获取模板列表失败：" + e.getMessage());
        }
    }

    /**
     * 获取数据库连接信息
     */
    @GetMapping("/database/info")
    @Operation(summary = "获取数据库连接信息", description = "获取当前数据库连接信息")
    public Result<Map<String, Object>> getDatabaseInfo() {
        try {
            Map<String, Object> dbInfo = codeGeneratorService.getDatabaseInfo();
            return Result.success(dbInfo);
        } catch (Exception e) {
            log.error("获取数据库连接信息失败", e);
            return Result.error("获取数据库连接信息失败：" + e.getMessage());
        }
    }

    /**
     * 测试数据库连接
     */
    @PostMapping("/database/test")
    @Operation(summary = "测试数据库连接", description = "测试指定的数据库连接")
    public Result<Boolean> testDatabaseConnection(@RequestBody Map<String, String> connectionInfo) {
        try {
            boolean isConnected = codeGeneratorService.testDatabaseConnection(connectionInfo);
            return Result.success(isConnected);
        } catch (Exception e) {
            log.error("测试数据库连接失败", e);
            return Result.error("测试数据库连接失败：" + e.getMessage());
        }
    }
}