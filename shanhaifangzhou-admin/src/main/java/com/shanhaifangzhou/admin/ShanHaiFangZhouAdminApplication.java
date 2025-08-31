package com.shanhaifangzhou.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 山海方舟管理系统启动类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class ShanHaiFangZhouAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanHaiFangZhouAdminApplication.class, args);
        System.out.println("=================================");
        System.out.println("山海方舟管理系统启动成功！");
        System.out.println("=================================");
    }
}
