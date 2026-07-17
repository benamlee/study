package com.university.config;

// ==========================================
// WebMvcConfig.java - Spring MVC 配置
//
// 替代 Java EE 版本中的 web.xml 部分配置
// 和 Servlet 的手动转发逻辑
//
// @EnableWebMvc = 启用 Spring MVC 功能
// 让 @Controller @RequestMapping 等注解生效
// ==========================================

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    // ==========================================
    // 视图解析器：Controller 返回视图名时，
    // Spring 自动拼装成 /pages/xxx.jsp 路径
    //
    // 例如 Controller 返回 "student/list"
    // → 框架自动转发到 /pages/student/list.jsp
    //
    // 对比 Java EE 版：
    // Servlet 中手动写：
    //   request.getRequestDispatcher("/pages/student/list.jsp")
    //          .forward(request, response);
    // ==========================================
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/pages/");   // JSP 文件前缀路径
        resolver.setSuffix(".jsp");      // JSP 文件后缀
        return resolver;
    }

    // ==========================================
    // 静态资源处理：让 /css/ 等静态资源
    // 由容器默认 Servlet 处理，而不是 Spring MVC
    //
    // Controller 只处理业务请求，
    // CSS/JS/图片等静态文件直接返回
    // ==========================================
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    }
}
