package me.tutttuwi.springboot.management.thymleaf;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

  //  @Bean("TextClassLoaderTemplateResolver")
  //  public ClassLoaderTemplateResolver templateResolver() {
  //    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
  //    resolver.setOrder(Integer.valueOf(1));
  //    resolver.setPrefix("/templates/");
  //    resolver.setSuffix(".html");
  //    resolver.setTemplateMode(TemplateMode.HTML);
  //    resolver.setCharacterEncoding("UTF-8");
  //    resolver.setCacheable(false);
  //    return resolver;
  //  }
  //
  //  @Bean
  //  public SpringTemplateEngine templateEngine() {
  //    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
  //    //    templateEngine.setDialect(new LayoutDialect());
  //    // SpringSecurityとThymeleafの連携
  //    templateEngine.setDialect(new SpringSecurityDialect());
  //    templateEngine.setDialect(new Java8TimeDialect());
  //    templateEngine.setTemplateResolver(templateResolver());
  //    return templateEngine;
  //  }
  //
  //  @Bean
  //  public ViewResolver viewResolver() {
  //    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
  //    viewResolver.setTemplateEngine(templateEngine());
  //    viewResolver.setCharacterEncoding("UTF-8");
  //    return viewResolver;
  //  }
}