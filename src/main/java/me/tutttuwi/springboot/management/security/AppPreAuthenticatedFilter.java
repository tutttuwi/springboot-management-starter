package me.tutttuwi.springboot.management.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class AppPreAuthenticatedFilter extends AbstractPreAuthenticatedProcessingFilter {

  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    //リクエストからユーザーID等のユーザー情報を抽出
    return "user01";
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    //リクエストから証明書情報を抽出。DB等にある場合もある？
    return "";
  }
}
