package com.shanhaifangzhou.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 山海方舟主应用类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@SpringBootApplication
public class ShanHaiFangZhouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShanHaiFangZhouApplication.class, args);
        System.out.println("山海方舟企业级权限管理系统启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("测试接口: http://localhost:8080/test/hello");
        System.out.println("健康检查: http://localhost:8080/test/health");
    }
}