package com.shanhaifangzhou.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

/**
 * 简化应用类 - 不依赖Redis和数据库
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class,
    RedisAutoConfiguration.class,
    RedisRepositoriesAutoConfiguration.class
})
public class SimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
        System.out.println("==========================================");
        System.out.println("山海方舟企业级权限管理系统启动成功！");
        System.out.println("==========================================");
        System.out.println("访问地址: http://localhost:8080/api");
        System.out.println("测试接口: http://localhost:8080/api/test/hello");
        System.out.println("系统信息: http://localhost:8080/api/system/info");
        System.out.println("API文档: http://localhost:8080/api/doc.html");
        System.out.println("==========================================");
    }
}