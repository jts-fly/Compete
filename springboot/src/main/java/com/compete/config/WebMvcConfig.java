package com.compete.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 接口幂等性拦截器-可添加多个
        registry.addInterceptor(autoIdempotentInterceptor())
                //这里配置要拦截的路径
                .addPathPatterns("/**")
                //这里配置不要拦截的路径
                .excludePathPatterns("/**/login");
    }

    @Bean
    public LoginInterceptor autoIdempotentInterceptor() {
        return new LoginInterceptor();
    }

}

