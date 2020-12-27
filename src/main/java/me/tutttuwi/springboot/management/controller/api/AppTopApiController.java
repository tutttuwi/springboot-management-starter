package me.tutttuwi.springboot.management.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.controller.AbstractApiController;

@Slf4j
@RestController
@RequestMapping("api")
public class AppTopApiController extends AbstractApiController {

  @GetMapping(value = "user")
  public String getUser() throws Throwable {
    return "user";
  }

  @Override
  public String getFunctionName() {
    return this.getClass().getName();
  }
}
