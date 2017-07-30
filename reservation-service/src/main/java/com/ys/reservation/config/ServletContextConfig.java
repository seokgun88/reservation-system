package com.ys.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.ys.reservation.interceptor.LoginInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.ys.reservation.controller", "com.ys.reservaition.interceptor" })
public class ServletContextConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/"); // webapp/resources 경로를 의미
	}
	
    @Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = 
        		new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1073741824); // 1024 * 1024 * 1024
        return multipartResolver;
    }
    
    @Bean
    public LoginInterceptor loginInterceptor() {
    	return new LoginInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(loginInterceptor())
    		.excludePathPatterns("/login", "/oauth2callback", "/", "/products/*", "/api/**");
    }
}

