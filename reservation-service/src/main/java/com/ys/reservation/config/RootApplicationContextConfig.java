package com.ys.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {
		"com.ys.reservation.dao",
		"com.ys.reservation.service"
})
@Import({DbConfig.class})
public class RootApplicationContextConfig {
}
