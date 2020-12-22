package me.tutttuwi.springboot.management.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class AppConfigInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    //    servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy("springSecurityFilterChain"))
    //        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/authenticate");
  }
}