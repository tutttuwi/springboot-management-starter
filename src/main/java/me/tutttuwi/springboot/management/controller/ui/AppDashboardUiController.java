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
@RequestMapping("dashboard")
@SessionAttributes("scopedTarget.commonSession")
public class AppDashboardUiController extends AbstractUiController {

  /**
   * PAGE NAME Final String.
   */
  private static final String PAGE_NAME = "dashboard";

  @Autowired
  CommonSession commonSession;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
  }

  /**
   * dashboard routing.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/{path}")
  public String dashboard(@PathVariable("path") String path) throws Throwable {
    commonSession.getSidebarModel().setActive(PAGE_NAME, path);
    return WebConst.PAGE_URL + "/" + path;
  }

  @Override
  public String getFunctionName() {
    return WebConst.FNC_DASHBOARD;
  }
}
