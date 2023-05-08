package com.liwenjie.reggie1.filter;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/29 23:06
 */

import com.alibaba.fastjson.JSON;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.common.ThreadLocalContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成了登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    // 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String[] uris = new String[]{
                "/employee/login",
                "/employee/logout",
                // 静态资源
                "/backend/**",
                "/front/**",
                "/favicon.ico",
                "/user/sendMsg",
                "/user/login"
        };
        // 1. 获取本次URI
        String uri = request.getRequestURI();
        // 2. 判断本次请求是否需要处理
        // 判断请求是否为静态资源, 静态资源直接通过, ajax请求再判断
        Boolean check = check(uris, uri);
        // 3. 不需要处理直接放行
        if (check) {
            log.info("资源不需要处理, 直接放行:{}", uri);
            filterChain.doFilter(request, response);
            return;
        }
        // 4. 判断登录状态, 已登录直接放行
        Object employee = request.getSession().getAttribute("employee");
        if (employee != null) {
            log.info("当前已登录employee, ID为:{}", employee);
            // 数据存储到线程中
            ThreadLocalContext.setCurrentId((Long) employee);
            filterChain.doFilter(request, response);
            return;
        }
        // 用户登录判断
        Object user = request.getSession().getAttribute("user");
        if (user != null) {
            log.info("当前已登录user, ID为:{}", user);
            // 数据存储到线程中
            ThreadLocalContext.setCurrentId((Long) user);
            filterChain.doFilter(request, response);
            return;
        }
        // 5. 未登录返回登录页面
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("拦截到请求:{}", uri);
    }

    private Boolean check(String[] uris, String uri) {
        for (String s : uris) {
            boolean match = PATH_MATCHER.match(s, uri);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
