package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.request.SignUpUserFormRequest;
import me.tutttuwi.springboot.management.service.AppSignUpService;
import me.tutttuwi.springboot.management.session.SignUpUserSession;

@Slf4j
@Controller
@RequestMapping("/user/signup")
@SessionAttributes("scopedTarget.signUpUserSession")
public class AppSignUpUiController {

  @Autowired
  SignUpUserSession signUpUserSession;

  @Autowired
  AppSignUpService service;

  @ModelAttribute
  SignUpUserSession createSession() {
    System.out.println("create session");
    return signUpUserSession;
  }

  /**
   * index.
   *
   * @param inputForm SignUpUserFormRequest
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "")
  public String index(SignUpUserFormRequest inputForm) throws Throwable { // 必ず初期化用にフォームオブジェクト渡すこと
    System.out
        .println("SignUpUserFormRequest(index):" + signUpUserSession.getSignUpUserFormRequest());
    return "user/signup/input_userinfo";
  }

  /**
   * input.
   *
   * @param inputForm SignUpUserFormRequest
   * @param result BindingResult
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @PostMapping(value = "/input")
  public String input(@Validated SignUpUserFormRequest inputForm, BindingResult result)
      throws Throwable {
    if (result.hasErrors()) {
      return "user/signup/input_userinfo";
    }
    signUpUserSession.setSignUpUserFormRequest(inputForm);
    return "user/signup/confirm_userinfo";
  }

  /**
   * confirm.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @PostMapping(value = "/confirm")
  public String confirm() throws Throwable {
    System.out.println(
        "SignUpUserFormRequest(before complete)" + signUpUserSession.getSignUpUserFormRequest());
    SignUpUserFormRequest form = signUpUserSession.getSignUpUserFormRequest();
    service.registUserInfo(form);
    return "redirect:/user/signup/complete";
  }

  /**
   * complete.
   *
   * @param sessionStatus SessionStatus
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/complete")
  public String complete(SessionStatus sessionStatus) throws Throwable {
    sessionStatus.setComplete();
    System.out.println(
        "SignUpUserFormRequest(after complete):" + signUpUserSession.getSignUpUserFormRequest());
    return "user/signup/complete_userinfo";
  }

  /**
   * emailauth.
   *
   * @param emailAuthKey String
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/emailauth")
  public String emailauth(@RequestParam(name = "key", required = true) String emailAuthKey)
      throws Throwable {
    if (service.isAuthKeyExpired(emailAuthKey)) {
      return "user/signup/warn_emailauthkeyexpired";
    }
    service.activateUserInfo(emailAuthKey);
    return "user/signup/complete_useractivated";
  }
}
