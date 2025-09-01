package com.shanhaifangzhou.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

/**
 * 简化测试应用类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@SpringBootApplication(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class,
    RedisAutoConfiguration.class
})
public class SimpleTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleTestApplication.class, args);
        System.out.println("简化测试应用启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("测试接口: http://localhost:8080/test/hello");
        System.out.println("系统信息: http://localhost:8080/system/info");
    }
}