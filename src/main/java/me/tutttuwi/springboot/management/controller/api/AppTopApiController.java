package me.tutttuwi.springboot.management.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.tutttuwi.springboot.management.controller.AbstractApiController;

@RestController
@RequestMapping("api")
public class AppTopApiController extends AbstractApiController {

  private final static Logger logger = LoggerFactory.getLogger(AppTopApiController.class);

  @GetMapping(value = "user")
  public String getUser() throws Throwable {
    return "user";
  }

  @Override
  public String getFunctionName() {
    return this.getClass().getName();
  }
}
