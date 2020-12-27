package me.tutttuwi.springboot.management.interceptor;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {

  private static final String SESSION_ID = "SESSION_ID";
  private static final String LOGIN_USER_ID = "LOGIN_USER_ID";



  /**
   * 【処理前】ログ出力用インターセプター.<br/>
   * セッション情報設定
   *
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // コントローラーの動作前

    // ログインユーザID
    Principal auth = request.getUserPrincipal();
    String userId = "";
    if (auth != null) {
      userId = auth.getName();
    }
    if (!"".equals(userId)) {
      MDC.put(LOGIN_USER_ID, userId);
    } else {
      MDC.put(LOGIN_USER_ID, "");
    }

    // セッションID
    HttpSession session = request.getSession(false);
    if (session != null) {
      MDC.put(SESSION_ID, session.getId());
    }

    return true;
  }

  /**
   * 【処理後】ログ出力用インターセプター.
   *
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param handler Object
   * @param modelAndView ModelAndView
   */
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) {
    // HandlerMethod handlerMethod = (HandlerMethod) handler;
    // Method method = ((HandlerMethod) handler).getMethod();
    // log.info("[SUCCESS CONTROLLER] {}.{}", method.getDeclaringClass().getSimpleName(),
    // method.getName());
  }
}
