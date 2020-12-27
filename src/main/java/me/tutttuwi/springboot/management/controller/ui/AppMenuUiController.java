package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.controller.AbstractUiController;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.security.AccountUserDetails;
import me.tutttuwi.springboot.management.service.AppMenuService;
import me.tutttuwi.springboot.management.session.CommonSession;

@Slf4j
@Controller
@RequestMapping("")
@SessionAttributes("scopedTarget.userInfoSession")
public class AppMenuUiController extends AbstractUiController {

  @Autowired
  AppMenuService service;

  @Autowired
  CommonSession commonSession;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
  }

  // @RequestMapping()は使用しません
  /**
   * menu.
   *
   * @param userDetails AccountUserDetails
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/menu")
  public String menu(@AuthenticationPrincipal AccountUserDetails userDetails) throws Throwable {

    AccountIndiv accountIndiv = service.getUserInfo(userDetails);
    commonSession.setUsername(accountIndiv.getLstName() + "　" + accountIndiv.getFstName());
    log.info("userId :" + userDetails.getAccount().getUserId());
    log.info("CALL : MENU PAGE");

    commonSession.setSidebarModel(service.getMenuInfo());

    return "menu";
  }

  @Override
  public String getFunctionName() {
    // return this.getClass().getSimpleName();
    return "F_menu";
  }
}
