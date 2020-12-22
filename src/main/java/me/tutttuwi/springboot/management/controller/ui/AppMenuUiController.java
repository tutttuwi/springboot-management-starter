package me.tutttuwi.springboot.management.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.controller.AbstractUiController;

@Slf4j
@Controller
@RequestMapping("")
public class AppMenuUiController extends AbstractUiController {

  // @RequestMapping()は使用しません
  @GetMapping(value = "/menu")
  public String menu() throws Throwable {
    log.info("CALL : MENU PAGE");
    return "menu";
  }

  @Override
  public String getFunctionName() {
    //  return this.getClass().getSimpleName();
    return "F_menu";
  }
}
