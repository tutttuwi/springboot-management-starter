package me.tutttuwi.springboot.management.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.util.FunctionNameAware;

/**
 * 基底APIコントローラー.
 */
@ResponseStatus(HttpStatus.OK)
@Slf4j
public abstract class AbstractApiController extends BaseController implements FunctionNameAware {

  // TODO: 要確認
  // @Autowired
  // protected ResourceFactory resourceFactory;
}
