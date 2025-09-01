package com.shanhaifangzhou.admin.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.dev33.satoken.dao.SaTokenDaoDefaultImpl;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 内存模式Sa-Token配置
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Configuration
public class MemorySaTokenConfig implements WebMvcConfigurer {

    /**
     * 配置Sa-Token使用内存模式
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(SaTokenDao.class)
    public SaTokenDao saTokenDao() {
        return new SaTokenDaoDefaultImpl();
    }

    /**
     * 注册Sa-Token拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 指定需要登录认证的路径
            SaRouter.match("/**")
                    .notMatch("/auth/login", "/auth/captcha", "/test/**", "/system/info", "/doc.html", "/webjars/**", "/v3/api-docs/**")
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }
}