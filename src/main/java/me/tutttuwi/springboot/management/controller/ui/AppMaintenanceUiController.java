package me.tutttuwi.springboot.management.controller.ui;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.WebConst;
import me.tutttuwi.springboot.management.controller.AbstractUiController;
import me.tutttuwi.springboot.management.request.SignUpUserFormRequest;
import me.tutttuwi.springboot.management.service.AppMaintenanceService;
import me.tutttuwi.springboot.management.service.AppSignUpService;
import me.tutttuwi.springboot.management.session.CommonSession;
import me.tutttuwi.springboot.management.session.SignUpUserSession;

@Slf4j
@Controller
@RequestMapping("maintenance")
@SessionAttributes({"scopedTarget.commonSession", "scopedTarget.signUpUserSession"})
public class AppMaintenanceUiController extends AbstractUiController {

  /**
   * PAGE NAME Final String.
   */
  private static final String PAGE_NAME = "maintenance";

  @Autowired
  AppMaintenanceService service;

  @Autowired
  CommonSession commonSession;
  @Autowired
  SignUpUserSession signUpUserSession;
  @Autowired
  AppSignUpService signUpService;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
  }

  @ModelAttribute
  SignUpUserSession createSignUpUserSession() {
    return signUpUserSession;
  }

  /**
   * maintenance routing.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/{path}")
  public String maintenance(@PathVariable("path") String path) throws Throwable {
    String itemKey = PAGE_NAME + "_" + path;
    commonSession.getSidebarModel().setActive(PAGE_NAME, itemKey);
    return WebConst.PAGE_URL + "/" + itemKey;
  }

  /**
   * ユーザー情報登録.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/userregedit")
  public String userRegEdit(SignUpUserFormRequest inputForm) throws Throwable {
    commonSession.getSidebarModel().setActive(PAGE_NAME, "maintenance_userregedit");
    StringBuilder sb = new StringBuilder();
    return sb.append(WebConst.PAGE_URL).append("/").append("maintenance_userregedit").toString();
  }

  /**
   * ユーザー情報編集.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/userregedit/{id}")
  public String userRegEdit(SignUpUserFormRequest inputForm, @PathVariable("id") String userId)
      throws Throwable {
    boolean isEdit = StringUtils.isNoneEmpty(userId);
    if (isEdit) {
      service.setSignUpUser(userId, signUpUserSession);
    }
    StringBuilder sb = new StringBuilder();
    return sb.append(WebConst.PAGE_URL).append("/").append("maintenance_userregedit").toString();
  }

  /**
   * ユーザー情報登録・編集完了.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @PostMapping(value = "/userregedit/complete")
  public String userRegEditComplete(SignUpUserFormRequest inputForm, BindingResult result)
      throws Throwable {
    if (result.hasErrors()) {
      return "maintenance/userregedit";
    }
    signUpUserSession.setSignUpUserFormRequest(inputForm);
    signUpService.registUserInfo(inputForm);
    StringBuilder sb = new StringBuilder();
    return sb.append(WebConst.PAGE_URL).append("/").append("maintenance_userregedit").toString();
  }

  @Override
  public String getFunctionName() {
    return WebConst.FNC_MAINTENANCE;
  }
}
