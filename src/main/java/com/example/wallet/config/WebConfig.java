package com.example.wallet.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LogRepository logRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor(logRepository);
        registry.addInterceptor(loggingInterceptor);
    }
}
