package me.tutttuwi.springboot.management.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class AppSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

  public AppSecurityInitializer() {
    super(AppSecurityConfig.class);
  }
}
