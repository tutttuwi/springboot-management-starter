package me.tutttuwi.springboot.management.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import groovy.util.logging.Slf4j;
import me.tutttuwi.springboot.management.controller.AbstractApiController;
import me.tutttuwi.springboot.management.dto.combined.SearchUserList;
import me.tutttuwi.springboot.management.request.SearchUserFormApiRequest;
import me.tutttuwi.springboot.management.service.AppSearchUserApiService;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class AppUserApiController extends AbstractApiController {

  @Autowired
  AppSearchUserApiService service;

  /**
   * ユーザー情報一覧取得.
   *
   * @param reqForm requestForm
   * @param result result
   * @return ret {@code List<SearchUserList>}
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "list")
  public Object getUserAll(@Validated SearchUserFormApiRequest reqForm, BindingResult result)
      throws Throwable {
    if (result.hasErrors()) {
      return "Parameter Error Occured!! " + result.getAllErrors();
    }
    List<SearchUserList> ret = service.getUserList(reqForm);
    return ret;
  }

  @Override
  public String getFunctionName() {
    return this.getClass().getName();
  }
}
