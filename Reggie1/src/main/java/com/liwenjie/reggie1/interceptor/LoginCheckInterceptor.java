package com.liwenjie.reggie1.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/29 23:18
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    /**
     * 目标执行之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截到请求:{}", request.getRequestURL());
        HttpSession session = request.getSession();
        Object employee = session.getAttribute("employee");
        if (employee != null) {
            // 放行
            return true;
        }
        request.getRequestDispatcher("backend/page/login/login.html").forward(request, response);
        // 拦截
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
