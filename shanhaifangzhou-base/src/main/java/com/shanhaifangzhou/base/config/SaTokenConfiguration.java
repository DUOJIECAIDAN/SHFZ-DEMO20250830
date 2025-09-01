package com.shanhaifangzhou.base.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token配置类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {

    /**
     * 注册Sa-Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 指定需要登录认证的路径
            SaRouter.match("/**")
                    // 排除不需要登录的路径
                    .notMatch("/auth/login")
                    .notMatch("/auth/captcha")
                    .notMatch("/auth/register")
                    .notMatch("/doc.html")
                    .notMatch("/webjars/**")
                    .notMatch("/swagger-resources/**")
                    .notMatch("/v3/api-docs/**")
                    .notMatch("/favicon.ico")
                    .notMatch("/error")
                    // 检查是否登录
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }

    /**
     * Sa-Token 参数配置
     */
    @Bean
    public SaTokenConfig getSaTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("Authorization"); // token名称
        config.setTimeout(30 * 24 * 60 * 60); // token有效期，单位s 默认30天, -1代表永不过期
        config.setActivityTimeout(-1); // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true); // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true); // 在多个项目中共享token时, 是否允许登录验证
        config.setTokenStyle("1"); // token风格
        config.setIsLog(false); // 是否输出操作日志
        return config;
    }

    /**
     * Sa-Token 整合 jwt (Simple 简单模式)
     */
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }
}