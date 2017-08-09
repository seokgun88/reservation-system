package com.yg.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.ServletWrappingController;

@Configuration
@ComponentScan(basePackages = {
		"com.yg.reservation.dao",
		"com.yg.reservation.service"
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}
