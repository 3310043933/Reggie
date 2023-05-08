package com.liwenjie.reggie1.config;


import com.liwenjie.reggie1.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/28 21:17
 */
@Configuration // 配置类注解
@Slf4j
@Import(BackendConfig.class)
public class WebMVCConfig extends WebMvcConfigurationSupport {

    // 设置静态资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("front/**").addResourceLocations("classpath:/front/");
        log.info("静态资源映射已开始处理");
    }


    /**
     * 扩展消息转换器, 解决javaLong型转json数据时精度丢失问题
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器");
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器, 底层使用Jackson 对象转为 json
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将消息转换器追加到converters中
        converters.add(0, converter);
    }
}
