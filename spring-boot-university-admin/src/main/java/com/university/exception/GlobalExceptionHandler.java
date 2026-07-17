package com.university.exception;

// ==========================================
// GlobalExceptionHandler.java - 全局异常处理器
//
// ★ 这是 Spring Boot / Spring MVC 特有的功能 ★
//
// 对比三个版本的异常处理：
//
//   Java EE 版 (StudentServlet.java)：
//     try {
//         // ... 业务逻辑
//     } catch (Exception e) {
//         e.printStackTrace();
//         response.sendError(...);
//     }
//     // 每个 Servlet 都要重复写 try-catch！
//
//   Spring MVC 版 (StudentController.java)：
//     没有统一的异常处理
//     （也可以在 Controller 中写 try-catch）
//
//   Spring Boot 版（本文件）：
//     @ControllerAdvice = 全局捕获异常
//     所有 Controller 抛出的异常自动集中到这里
//     不需要在每个 Controller 中写 try-catch！
//
// 这就是 AOP（面向切面编程）思想：
//   将"异常处理"这个横切关注点
//   从业务代码中分离出来。
// ==========================================

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// @ControllerAdvice = 全局控制器增强
// 所有 @Controller 抛出的异常都会经过这里
//
// 原理：Spring AOP 在 Controller 执行时
// 自动织入异常处理逻辑

@Slf4j
// 日志记录
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    // @ExceptionHandler = 处理指定类型的异常
    // Exception.class = 捕获所有异常
    //
    // 可以写多个方法处理不同类型的异常：
    //   @ExceptionHandler(DataAccessException.class)  // 数据库异常
    //   @ExceptionHandler(ValidationException.class)  // 校验异常
    //
    // 这里简化为一个通用异常处理
    public String handleException(Exception e, Model model) {
        // 记录异常日志（替代 System.out.println）
        log.error("系统异常: ", e);

        // 将错误信息传递给错误页面
        model.addAttribute("errorMessage", "系统内部错误：" + e.getMessage());
        model.addAttribute("errorDetail", e.getClass().getName());

        // 返回错误页面（需要创建一个 error.jsp）
        // 如果没有 error.jsp，Spring Boot 有默认的 Whitelabel Error Page
        return "error";
    }
}
