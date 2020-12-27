package me.tutttuwi.springboot.management.controller.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import groovy.util.logging.Slf4j;
import me.tutttuwi.springboot.management.controller.AbstractApiController;
import me.tutttuwi.springboot.management.request.PasswordGenerateFormApiRequest;

@Slf4j
@RestController
@RequestMapping("/api/pw")
public class AppPwGenApiController extends AbstractApiController {

  @Value("${settings.password.secret}")
  private String secret;

  @Value("${settings.password.iterations}")
  private int iterations;

  @Value("${settings.password.hashWidth}")
  private int hashWidth;

  /**
   * getUser.
   *
   * @param inputForm requestForm
   * @param result result
   * @return encode string
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "generate")
  public String getUser(@Validated PasswordGenerateFormApiRequest inputForm, BindingResult result)
      throws Throwable {
    if (result.hasErrors()) {
      return "Parameter Error Occured!! " + result.getAllErrors();
    }
    return new Pbkdf2PasswordEncoder(secret, iterations, hashWidth).encode(inputForm.getPassword());
  }

  @Override
  public String getFunctionName() {
    return this.getClass().getName();
  }
}
