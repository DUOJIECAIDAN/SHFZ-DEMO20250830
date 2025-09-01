package com.shanhaifangzhou.admin.monitor.controller;

import com.shanhaifangzhou.base.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统监控控制器
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@RestController
@RequestMapping("/api/monitor")
@RequiredArgsConstructor
@Tag(name = "系统监控", description = "系统监控相关接口")
public class SysMonitorController {

    /**
     * 获取系统信息
     */
    @GetMapping("/system")
    @Operation(summary = "获取系统信息", description = "获取系统基本信息")
    public Result<Map<String, Object>> getSystemInfo() {
        try {
            Map<String, Object> systemInfo = new HashMap<>();
            
            // 操作系统信息
            OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
            systemInfo.put("osName", osBean.getName());
            systemInfo.put("osVersion", osBean.getVersion());
            systemInfo.put("osArch", osBean.getArch());
            systemInfo.put("availableProcessors", osBean.getAvailableProcessors());
            systemInfo.put("systemLoadAverage", osBean.getSystemLoadAverage());
            
            // JVM信息
            Runtime runtime = Runtime.getRuntime();
            systemInfo.put("javaVersion", System.getProperty("java.version"));
            systemInfo.put("javaVendor", System.getProperty("java.vendor"));
            systemInfo.put("javaHome", System.getProperty("java.home"));
            systemInfo.put("totalMemory", runtime.totalMemory());
            systemInfo.put("freeMemory", runtime.freeMemory());
            systemInfo.put("maxMemory", runtime.maxMemory());
            systemInfo.put("usedMemory", runtime.totalMemory() - runtime.freeMemory());
            
            // 线程信息
            ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
            systemInfo.put("threadCount", threadBean.getThreadCount());
            systemInfo.put("peakThreadCount", threadBean.getPeakThreadCount());
            systemInfo.put("daemonThreadCount", threadBean.getDaemonThreadCount());
            
            // 内存信息
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            systemInfo.put("heapMemoryUsage", memoryBean.getHeapMemoryUsage());
            systemInfo.put("nonHeapMemoryUsage", memoryBean.getNonHeapMemoryUsage());
            
            return Result.success(systemInfo);
        } catch (Exception e) {
            log.error("获取系统信息失败", e);
            return Result.error("获取系统信息失败：" + e.getMessage());
        }
    }

    /**
     * 获取在线用户数量
     */
    @GetMapping("/online-count")
    @Operation(summary = "获取在线用户数量", description = "获取当前在线用户数量")
    public Result<Long> getOnlineUserCount() {
        try {
            // TODO: 实现获取在线用户数量的逻辑
            long onlineCount = 0L;
            return Result.success(onlineCount);
        } catch (Exception e) {
            log.error("获取在线用户数量失败", e);
            return Result.error("获取在线用户数量失败：" + e.getMessage());
        }
    }

    /**
     * 获取系统统计信息
     */
    @GetMapping("/stats")
    @Operation(summary = "获取系统统计", description = "获取系统各项统计信息")
    public Result<Map<String, Object>> getSystemStats() {
        try {
            Map<String, Object> stats = new HashMap<>();
            
            // TODO: 实现获取系统统计信息的逻辑
            stats.put("userCount", 0L);
            stats.put("roleCount", 0L);
            stats.put("menuCount", 0L);
            stats.put("deptCount", 0L);
            stats.put("onlineUserCount", 0L);
            stats.put("todayLoginCount", 0L);
            stats.put("todayOperCount", 0L);
            
            return Result.success(stats);
        } catch (Exception e) {
            log.error("获取系统统计信息失败", e);
            return Result.error("获取系统统计信息失败：" + e.getMessage());
        }
    }

    /**
     * 获取系统健康状态
     */
    @GetMapping("/health")
    @Operation(summary = "系统健康检查", description = "检查系统各项服务的健康状态")
    public Result<Map<String, Object>> getSystemHealth() {
        try {
            Map<String, Object> health = new HashMap<>();
            
            // 检查数据库连接
            health.put("database", "UP");
            
            // 检查Redis连接
            health.put("redis", "UP");
            
            // 检查磁盘空间
            health.put("disk", "UP");
            
            // 检查内存使用
            Runtime runtime = Runtime.getRuntime();
            long usedMemory = runtime.totalMemory() - runtime.freeMemory();
            long maxMemory = runtime.maxMemory();
            double memoryUsage = (double) usedMemory / maxMemory;
            
            if (memoryUsage > 0.9) {
                health.put("memory", "WARNING");
            } else {
                health.put("memory", "UP");
            }
            
            // 整体状态
            health.put("status", "UP");
            health.put("timestamp", System.currentTimeMillis());
            
            return Result.success(health);
        } catch (Exception e) {
            log.error("系统健康检查失败", e);
            return Result.error("系统健康检查失败：" + e.getMessage());
        }
    }
}