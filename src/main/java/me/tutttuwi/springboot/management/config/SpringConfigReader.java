package me.tutttuwi.springboot.management.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class SpringConfigReader {
  @Value("${settings.password.secret}")
  private String passwordSecret;
  @Value("${settings.password.iterations}")
  private String passwordIterations;
  @Value("${settings.password.hashWidth}")
  private String passwordHashWidth;

  @Value("${settings.forgotpassword.email.url}")
  private String passwordEmailUrl;
  @Value("${settings.forgotpassword.email.validhour}")
  private int passwordEmailValidhour;
  @Value("${settings.forgotpassword.email.cancelurl}")
  private String passwordEmailCancelUrl;

  @Value("${settings.auth.email.url}")
  private String authEmailUrl;
  @Value("${settings.auth.email.validhour}")
  private int authEmailValidhour;

  @Value("${settings.db.numbering.key.accountid}")
  private String numKey;

}