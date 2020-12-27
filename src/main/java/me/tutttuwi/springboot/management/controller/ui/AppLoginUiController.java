package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.WebConst;
import me.tutttuwi.springboot.management.controller.AbstractUiController;
import me.tutttuwi.springboot.management.request.LoginFormRequest;

@Slf4j
@Controller
@RequestMapping("")
public class AppLoginUiController extends AbstractUiController {

  @GetMapping(value = "/")
  public String index(@ModelAttribute LoginFormRequest form) throws Throwable {
    return "login";
  }

  @GetMapping(value = "/login")
  public String indexLogin(@ModelAttribute LoginFormRequest form) throws Throwable {
    return "login";
  }

  /**
   * login.
   *
   * @param form LoginFormRequest
   * @param result BindingResult
   * @return HTML Path String
   */
  @PostMapping(value = "/login")
  public String login(@Validated @ModelAttribute LoginFormRequest form, BindingResult result) {
    if (result.hasErrors()) {
      return "login";
    }
    return "forward:" + WebConst.LOGIN_PROCESSING_URL;
    // return "redirect:" + "/menu";
  }

  /**
   * ログイン成功.
   *
   * @param form LoginFormRequest
   * @param attributes RedirectAttributes
   * @return
   */
  @PostMapping(WebConst.LOGIN_SUCCESS_URL)
  public String loginSuccess(@ModelAttribute LoginFormRequest form, RedirectAttributes attributes) {
    // attributes.addFlashAttribute(GLOBAL_MESSAGE, getMessage("login.success"));
    return "redirect:/menu";
  }

  /**
   * ログイン失敗.
   *
   * @param form LoginFormRequest
   * @return
   */
  @GetMapping(WebConst.LOGIN_FAILURE_URL)
  public String loginFailure(@ModelAttribute LoginFormRequest form) {
    // model.addAttribute(GLOBAL_MESSAGE, getMessage("login.failed"));
    return "login?error";
  }

  /**
   * タイムアウトした時.
   *
   * @param form LoginFormRequest
   * @return
   */
  @GetMapping(WebConst.LOGIN_TIMEOUT_URL)
  public String loginTimeout(@ModelAttribute LoginFormRequest form) {
    // model.addAttribute(GLOBAL_MESSAGE, getMessage("login.timeout"));
    return "login?timeout";
  }

  /**
   * ログアウト.
   *
   * @return
   */
  @GetMapping(WebConst.LOGOUT_SUCCESS_URL)
  public String logout(@ModelAttribute LoginFormRequest form, RedirectAttributes attributes) {
    // attributes.addFlashAttribute(GLOBAL_MESSAGE, getMessage("logout.success"));
    return "redirect:/login";
  }

  @Override
  public boolean authorityRequired() {
    // 権限チェックを求めない
    return false;
  }

  @Override
  public String getFunctionName() {
    return "F_LOGIN";
  }

}
