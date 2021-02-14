package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.request.ForgotpasswordFormRequest;
import me.tutttuwi.springboot.management.request.ForgotpasswordUpdateFormRequest;
import me.tutttuwi.springboot.management.service.AppForgotpasswordService;

@Slf4j
@Controller
@RequestMapping("/user/forgotpassword")
public class AppForgotPasswordUiController {

  @Autowired
  AppForgotpasswordService service;

  @GetMapping(value = "")
  public String index(ForgotpasswordFormRequest inputForm) {
    return "user/forgotpassword/input_userinfo";
  }

  /**
   * input.
   *
   * @param inputForm ForgotpasswordFormRequest
   * @param result BindingResult
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @PostMapping(value = "/input")
  public String input(@Validated ForgotpasswordFormRequest inputForm, BindingResult result)
      throws Throwable {
    if (result.hasErrors()) {
      return "user/forgotpassword/input_userinfo";
    }
    // TODO: 個別設定 メール送信処理
    // service.sendPasswordUpdateEmail(inputForm);
    return "user/forgotpassword/complete_userinfo";
  }

  // @PostMapping(value = "/confirm")
  // public String confirm() {
  // return "";
  // }

  /**
   * inputPassword.
   *
   * @param key key
   * @param inputForm ForgotpasswordUpdateFormRequest
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/inputpassword")
  public String inputPassword(@RequestParam(name = "key", required = true) String key,
      ForgotpasswordUpdateFormRequest inputForm) throws Throwable {
    if (service.isAuthKeyExpired(key)) {
      return "user/signup/warn_emailauthkeyexpired";
    }
    inputForm.setAuthKey(key);
    return "user/forgotpassword/input_updatepassword";
  }

  /**
   * cancelPassword.
   *
   * @param key key
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/cancelpassword")
  public String cancelPassword(@RequestParam(name = "key", required = true) String key)
      throws Throwable {
    if (service.isAuthKeyExpired(key)) {
      return "user/signup/warn_emailauthkeyexpired";
    }
    service.deleteAuthKey(key);
    return "user/forgotpassword/cancel_updatepassword";
  }

  /**
   * updatePassword.
   *
   * @param inputForm ForgotpasswordUpdateFormRequest
   * @param result BindingResult
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @PostMapping(value = "/updatepassword")
  public String updatePassword(@Validated ForgotpasswordUpdateFormRequest inputForm,
      BindingResult result) throws Throwable {
    if (result.hasErrors()) {
      return "user/forgotpassword/input_updatepassword";
    }
    service.updatePassword(inputForm);
    return "user/forgotpassword/complete_updatepassword";
  }
}
