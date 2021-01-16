package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.WebConst;
import me.tutttuwi.springboot.management.controller.AbstractUiController;
import me.tutttuwi.springboot.management.security.AccountUserDetails;
import me.tutttuwi.springboot.management.service.AppMenuService;
import me.tutttuwi.springboot.management.session.CommonSession;

@Slf4j
@Controller
@RequestMapping("")
@SessionAttributes("scopedTarget.commonSession")
public class AppMenuUiController extends AbstractUiController {

  @Autowired
  AppMenuService service;

  @Autowired
  CommonSession commonSession;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
  }

  /**
   * メニューコントローラ.
   *
   * @param userDetails AccountUserDetails
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/menu")
  public String menu(@AuthenticationPrincipal AccountUserDetails userDetails) throws Throwable {
    return "page/dashboard1";
  }

  @Override
  public String getFunctionName() {
    return WebConst.FNC_MENU;
  }
}
