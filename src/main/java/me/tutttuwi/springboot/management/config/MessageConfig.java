package me.tutttuwi.springboot.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Validation用プロパティとメッセージプロパティを統合.
 *
 * @author Tomo
 *
 */
@Configuration
public class MessageConfig implements WebMvcConfigurer {
  @Autowired
  private MessageSource messageSource;

  @Override
  public org.springframework.validation.Validator getValidator() {
    return validator();
  }

  /**
   * method {@code validator}.
   *
   * @return
   */
  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource);
    return localValidatorFactoryBean;
  }
}
