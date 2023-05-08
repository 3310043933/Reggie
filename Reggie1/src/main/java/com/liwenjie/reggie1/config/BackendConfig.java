package com.liwenjie.reggie1.config;

import com.liwenjie.reggie1.interceptor.LoginCheckInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/29 23:21
 */
@Slf4j
//@Configuration
//@EnableWebMvc           // 局部MVC配置 WebMvcConfigurer 与 WebMvcConfigurationSupport冲突 需要 EnableWebMvc
public class BackendConfig implements WebMvcConfigurer {
    /**
     * 配置拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始配置拦截器:{}", LoginCheckInterceptor.class);
        registry.addInterceptor(new LoginCheckInterceptor())
                .addPathPatterns("/backend/api/**")
                .excludePathPatterns("backend/**", "backend/page/login/login.html");
    }
}
