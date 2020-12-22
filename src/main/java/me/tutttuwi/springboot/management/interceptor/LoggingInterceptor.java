package me.tutttuwi.springboot.management.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {

  public void postHandler(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) {
    if (log.isInfoEnabled()) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      Method method = ((HandlerMethod) handler).getMethod();
      log.info("[SUCCESS CONTROLLER] {}.{}", method.getDeclaringClass().getSimpleName(), method.getName());
    }

  }
}
