package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.WebConst;
import me.tutttuwi.springboot.management.controller.AbstractUiController;
import me.tutttuwi.springboot.management.session.CommonSession;

@Slf4j
@Controller
@RequestMapping("maintenance")
@SessionAttributes("scopedTarget.commonSession")
public class AppMaintenanceUiController extends AbstractUiController {

  /**
   * PAGE NAME Final String.
   */
  private static final String PAGE_NAME = "maintenance";

  @Autowired
  CommonSession commonSession;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
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

  @Override
  public String getFunctionName() {
    return WebConst.FNC_MAINTENANCE;
  }
}
