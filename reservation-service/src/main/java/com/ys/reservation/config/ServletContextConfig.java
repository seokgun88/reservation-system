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
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.ys.reservation.interceptor.LoginInterceptor;
import com.ys.reservation.view.ImageDownloadView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
		"com.ys.reservation.controller", 
		"com.ys.reservaition.interceptor",
		"com.ys.reservation.view" 
})
public class ServletContextConfig extends WebMvcConfigurerAdapter {
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(1);
		return viewResolver;
	}
	
	@Bean
	public ImageDownloadView imageDownloadView() {
		return new ImageDownloadView();
	}
	
	@Bean
	public ViewResolver beanNameViewResolver() {
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		resolver.setOrder(0);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/"); // webapp/resources 경로를 의미
		registry.addResourceHandler("/node_modules/**").addResourceLocations("/node_modules/"); // webapp/resources 경로를 의미
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

