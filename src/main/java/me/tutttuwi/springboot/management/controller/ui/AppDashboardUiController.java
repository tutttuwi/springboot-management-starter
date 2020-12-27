package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.controller.AbstractUiController;
import me.tutttuwi.springboot.management.session.CommonSession;

@Slf4j
@Controller
@RequestMapping("dashboard")
@SessionAttributes("scopedTarget.commonSession")
public class AppDashboardUiController extends AbstractUiController {

  @Autowired
  CommonSession commonSession;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
  }

  /**
   * dashboard1.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/dashboard1")
  public String dashboard1() throws Throwable {
    commonSession.getSidebarModel().setActive("ダッシュボード", "ダッシュボード１");
    return "page/dashboard1";
  }

  /**
   * dashboard2.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/dashboard2")
  public String dashboard2() throws Throwable {
    commonSession.getSidebarModel().setActive("ダッシュボード", "ダッシュボード２");
    return "page/dashboard2";
  }


  @Override
  public String getFunctionName() {
    // return this.getClass().getSimpleName();
    return "F_dashboard";
  }
}
