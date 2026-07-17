package com.university;

// ==========================================
// UniversityApplication.java - Spring Boot 启动入口
//
// 这个类替代了 Spring MVC 版本中的：
//   1. WebAppInitializer.java（应用初始化）
//   2. 部分 AppConfig.java（组件扫描）
//
// @SpringBootApplication 是一个组合注解，
// 相当于下面三个注解一起使用：
//
//   1. @Configuration           → 标记为配置类
//   2. @EnableAutoConfiguration → 启动自动配置（核心！）
//   3. @ComponentScan           → 扫描 @Service @Controller 等
//
// 对比 Spring MVC 版：
//   我们需要手动写 WebAppInitializer.java 来创建
//   DispatcherServlet 和注册 Spring 容器。
//
// 对比 Java EE 版：
//   EJB 容器（WebSphere）负责管理 Bean 生命周期，
//   开发者只需要写 @Stateless 即可。
//   类似地，Spring Boot 自动管理一切，
//   开发者只需要写 @SpringBootApplication。
// ==========================================

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
// @SpringBootApplication = 以上三个注解的合成
//
// 额外说明：Spring Boot 应用可以打成 war 包部署到
// 外部 Tomcat，此时需要继承 SpringBootServletInitializer
// 并重写 configure() 方法。
// 如果直接用 java -jar 运行内嵌 Tomcat，则不需要。
public class UniversityApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 当部署到外部 Tomcat 时，此方法告诉容器
        // 如何启动 Spring Boot 应用
        return builder.sources(UniversityApplication.class);
    }

    public static void main(String[] args) {
        // ==========================================
        // main() 方法 = 应用入口
        //
        // 对比：
        //   Java EE 版：启动 WebSphere 服务器，
        //               EAR 包自动部署
        //   Spring MVC 版：mvn tomcat7:run
        //                  启动外部 Tomcat
        //   Spring Boot 版：直接运行 main() 方法
        //                   内嵌 Tomcat 自动启动
        //
        // 三种启动方式，但效果一样：
        // 启动一个 Web 服务器，加载应用，
        // 等待 HTTP 请求。
        // ==========================================
        SpringApplication.run(UniversityApplication.class, args);
    }
}
