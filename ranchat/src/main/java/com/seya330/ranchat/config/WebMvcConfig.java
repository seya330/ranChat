package com.seya330.ranchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Bean
  public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    resolver.setContentType("text/html; charset=UTF-8");
    resolver.setOrder(1);
    resolver.setRedirectHttp10Compatible(false);

    return resolver;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**")
        .addResourceLocations("/resources/")
        .setCachePeriod(-1);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowCredentials(false)
        .allowedOriginPatterns("*")
        .allowedMethods("*")
        .maxAge(3600);
  }

  //DeferredResult 를 사용하는 Async 로직에 타임아웃 60초 설정.
  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    WebMvcConfigurer.super.configureAsyncSupport(configurer);
    configurer.setDefaultTimeout(30000);
  }
}
