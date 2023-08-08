package com.example.configs;

import com.example.configs.interceptors.SiteConfigInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing
public class MvcConfig implements WebMvcConfigurer {



    @Value("${file.upload.path}")
    private String fileUploadPath;

    // 사이트 설정 유지 인터셉터
    private final SiteConfigInterceptor siteConfigInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { // 정적인 경로를 설정할 때
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + fileUploadPath);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("main/index");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(siteConfigInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.common", "messages.validations", "messages.errors" );

        return ms;
    }

    @Bean
    public HiddenHttpMethodFilter httpMethodFilter() {
        // GET, POST 외에 DELETE, PATCH, PUT MAPPING도 사용할 수 있게 해줌
        return new HiddenHttpMethodFilter();
    }


}
