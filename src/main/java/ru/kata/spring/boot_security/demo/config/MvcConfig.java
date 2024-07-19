package ru.kata.spring.boot_security.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/login").setViewName("/auth/login");
        registry.addViewController("/admin/startAdminPage").setViewName("/admin/startAdminPage");
        registry.addViewController("/user/startUserPage").setViewName("/user/startUserPage");

    }
}
