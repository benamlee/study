package com.university.config;

// ==========================================
// AppConfig.java - Spring 核心配置
//
// 这是 Spring 版本的"最重要的配置文件"
// 它替代了 Java EE 版本的：
//   1. persistence.xml（JPA 配置）
//   2. @DataSourceDefinition（数据源定义）
//   3. server.xml 中部分配置
//
// @Configuration = 这是一个配置类
// @EnableTransactionManagement = 启用声明式事务管理
//   替代 EJB 容器管理事务（CMT）
// @ComponentScan = 自动扫描 @Service @Controller 等
// ==========================================

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.university")
public class AppConfig {

    // ==========================================
    // 【数据源 Bean】
    // 替代 Java EE 中的 @DataSourceDefinition
    //
    // 这里我们使用 HikariCP 连接池创建 H2 数据源
    // 对比：Java EE 版通过 @DataSourceDefinition 注解
    // 让 WebSphere 帮我们创建数据源
    //
    // @Bean = 告诉 Spring：这个方法返回的对象
    // 要注册到 Spring 容器中
    // ==========================================
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.h2.Driver");
        // H2 内存数据库连接地址（和 Java EE 版一致）
        ds.setJdbcUrl("jdbc:h2:mem:universitydb;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("sa");
        ds.setMaximumPoolSize(10); // 最大连接数
        return ds;
    }

    // ==========================================
    // 【EntityManagerFactory Bean】
    // 替代 Java EE 中容器的 @PersistenceContext 功能
    //
    // LocalContainerEntityManagerFactoryBean 
    // 是 Spring 提供的 JPA EntityManagerFactory 工厂
    // 它做的事情和 Java EE 容器一样：
    //   1. 扫描 @Entity 类
    //   2. 根据实体自动建表
    //   3. 管理 EntityManager 生命周期
    //
    // HibernateJpaVendorAdapter 告诉 Spring
    // 用 Hibernate 作为 JPA 实现
    // ==========================================
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        // 扫描哪个包下面的 @Entity 类
        emf.setPackagesToScan("com.university.student.entity",
                              "com.university.staff.entity",
                              "com.university.finance.entity");
        // 使用 Hibernate 作为 JPA 实现
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // ==========================================
        // Hibernate 专属属性
        // hibernate.hbm2ddl.auto
        //   = create-drop：启动时建表，关闭时删表
        //   （对应 Java EE 版 persistence.xml 中的 
        //    javax.persistence.schema-generation.database.action = drop-and-create）
        // hibernate.show_sql = true：在控制台打印 SQL
        // hibernate.format_sql = true：格式化 SQL
        // ==========================================
        Properties props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        // 使用 H2 数据库方言
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        emf.setJpaProperties(props);

        return emf;
    }

    // ==========================================
    // 【事务管理器 Bean】
    // 替代 Java EE 中 EJB 容器的容器管理事务（CMT）
    //
    // 在 Java EE 中，@Stateless 的方法
    // 默认就有事务管理，不需要写任何代码
    //
    // 在 Spring 中，我们需要：
    //   1. 创建 JpaTransactionManager
    //   2. 在 Service 的方法上写 @Transactional
    // 原理是一样的：方法开始时开启事务，
    // 方法成功时提交，抛异常时回滚
    // ==========================================
    @Bean
    public PlatformTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean emf) {
        return new JpaTransactionManager(emf.getObject());
    }

    // ==========================================
    // 异常转换：将 Hibernate 异常转换为 Spring 的
    // DataAccessException，方便统一处理
    // ==========================================
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
