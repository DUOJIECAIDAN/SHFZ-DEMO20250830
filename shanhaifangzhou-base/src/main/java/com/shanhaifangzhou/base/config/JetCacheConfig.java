package com.shanhaifangzhou.base.config;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * JetCache配置类
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.shanhaifangzhou")
public class JetCacheConfig {
    
    // JetCache配置通过application.yml进行配置
    // 支持本地缓存(Caffeine) + 远程缓存(Redis)的两级缓存模式
}