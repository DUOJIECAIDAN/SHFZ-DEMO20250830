package com.shanhaifangzhou.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 山海方舟管理后台启动类
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@SpringBootApplication
public class ShanHaiFangZhouAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanHaiFangZhouAdminApplication.class, args);
        System.out.println("山海方舟管理后台启动成功！");
    }
}
