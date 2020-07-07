package com.seya330.ranchat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	//jsp 뷰 리졸버 세팅
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
		// TODO Auto-generated method stub
		String jsVer = "1";
		registry.addResourceHandler("/resources/**")
		.addResourceLocations("/resources/")
		.setCachePeriod(-1);
		
		super.addResourceHandlers(registry);
	}
	
	
}
