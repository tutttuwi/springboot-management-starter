package me.tutttuwi.springboot.management.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.Getter;
import lombok.Setter;

public class ClientInfoMdcPutFilter extends OncePerRequestFilter {

  // TODO: SpringBootのFilter登録方法確認した上で実装
  private static final String FORWARDED_FOR_HEADER_NAME = "X-Forwarded-For";

  @Setter
  @Getter
  private String mdcKey = FORWARDED_FOR_HEADER_NAME;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // String remoteIp = Optional.ofNullable(request.getHeader(FORWARDED_FOR_HEADER_NAME))
    // .orElse(request.getRemoteAddr());
    // MDC.put(mdcKey, remoteIp);
    try {
      filterChain.doFilter(request, response);
    } finally {
      // MDC.remove(mdcKey);
    }

  }
}
