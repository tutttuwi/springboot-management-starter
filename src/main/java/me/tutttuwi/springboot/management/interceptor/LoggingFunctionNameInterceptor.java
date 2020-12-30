package me.tutttuwi.springboot.management.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.MdcConst;
import me.tutttuwi.springboot.management.util.FunctionNameAware;

/**
 * 機能名をログに出力する.
 */
@Slf4j
public class LoggingFunctionNameInterceptor extends BaseHandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // コントローラーの動作前

    val fna = getBean(handler, FunctionNameAware.class);
    if (fna != null) {
      val functionName = fna.getFunctionName();
      MDC.put(MdcConst.MDC_FUNCTION_NAME.KEY, functionName);
    }

    return true;
  }
}
