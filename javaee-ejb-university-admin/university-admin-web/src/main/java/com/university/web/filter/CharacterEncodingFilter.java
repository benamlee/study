package com.university.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

// ==========================================
// CharacterEncodingFilter.java - 字符编码过滤器
//
// @WebFilter = 声明这是一个"过滤器"
//  过滤器 = 在请求到达 Servlet 之前，
//  或响应返回客户端之前，做一些预处理
//
// 这里的作用：把所有请求和响应都设为 UTF-8
// 编码，防止中文乱码
// ==========================================

@WebFilter(
    filterName = "CharacterEncodingFilter",
    urlPatterns = "/*",        // 拦截所有请求
    initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8")
    }
)
public class CharacterEncodingFilter implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化时读取配置的编码
        String enc = filterConfig.getInitParameter("encoding");
        if (enc != null && !enc.isEmpty()) {
            encoding = enc;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // ==========================================
        // 在请求到达 Servlet 之前：
        // 设置请求的字符编码为 UTF-8
        // 这样 Servlet 读取中文参数时才不会乱码
        // ==========================================
        request.setCharacterEncoding(encoding);

        // ==========================================
        // 在响应返回客户端之前：
        // 设置响应的字符编码为 UTF-8
        // 这样浏览器显示中文时才不会乱码
        // ==========================================
        response.setCharacterEncoding(encoding);

        // ==========================================
        // chain.doFilter() = 继续执行下一个过滤器
        // 或者调用目标 Servlet
        // 如果没有这一行，请求就卡在这里了
        // ==========================================
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 过滤器销毁时调用（一般不需要额外处理）
    }
}
