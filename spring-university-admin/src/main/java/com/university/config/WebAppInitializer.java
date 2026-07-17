package com.university.config;

// ==========================================
// WebAppInitializer.java - Web 应用启动入口
//
// 这个类完全替代了 Java EE 版本中的 web.xml！
//
// Servlet 3.0+ 支持"纯 Java 配置"启动 Web 应用，
// 不需要任何 XML 文件。
//
// 当 Tomcat 启动时，会自动扫描实现了
// WebApplicationInitializer 接口的类，
// 然后调用 onStartup() 方法来初始化。
//
// Spring 提供了 AbstractAnnotationConfigDispatcherServletInitializer
// 让我们更方便地配置 DispatcherServlet。
// ==========================================

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        // ==========================================
        // 1. 创建 Spring 根容器（加载 AppConfig）
        //    AppConfig 包含：数据源、JPA、事务等配置
        // ==========================================
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);

        // 注册监听器，让 Servlet 容器能感知 Spring 容器
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // ==========================================
        // 2. 创建 Spring MVC 的子容器（加载 WebMvcConfig）
        //    WebMvcConfig 包含：视图解析器、静态资源处理等
        // ==========================================
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebMvcConfig.class);

        // 3. 创建 DispatcherServlet（Spring MVC 的核心）
        //    DispatcherServlet 替代了 Java EE 中手动写的 Servlet
        //    它会自动根据 @RequestMapping 的配置分发请求
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        // 拦截所有请求（/ 开头的 URL），包括 /student/* /staff/* /finance/*
        registration.addMapping("/");

        // ==========================================
        // 4. 注册字符编码过滤器（解决中文乱码）
        //    对比 Java EE 版中的 @WebFilter 注解
        // ==========================================
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        javax.servlet.FilterRegistration.Dynamic filterReg =
                servletContext.addFilter("characterEncodingFilter", encodingFilter);
        filterReg.addMappingForUrlPatterns(null, false, "/*");

        // ==========================================
        // 注意：这个文件就是"启动入口"，不需要 web.xml
        // 也不需要 @WebServlet 注解（因为 Spring MVC
        // 用 @Controller 代替 Servlet）
        // ==========================================
    }
}
