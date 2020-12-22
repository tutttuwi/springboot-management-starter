package me.tutttuwi.springboot.management.controller;

import static me.tutttuwi.springboot.management.constant.WebConst.*;

import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.util.Authorizable;
import me.tutttuwi.springboot.management.util.FunctionNameAware;

/**
 * 基底UIコントローラー
 */
@Slf4j
public abstract class AbstractUiController extends BaseController implements FunctionNameAware, Authorizable {

  @Override
  public boolean authorityRequired() {
    return true;
  }

  /**
   * 入力チェックエラーがある場合はtrueを返します。
   *
   * @param model
   * @return
   */
  public boolean hasErrors(Model model) {
    val errors = model.asMap().get(MAV_ERRORS);

    if (errors != null && errors instanceof BeanPropertyBindingResult) {
      val br = ((BeanPropertyBindingResult) errors);

      if (br.hasErrors()) {
        return true;
      }
    }

    return false;
  }

  /**
   * リダイレクト先に入力エラーを渡します。
   *
   * @param attributes
   * @param result
   */
  public void setFlashAttributeErrors(RedirectAttributes attributes, BindingResult result) {
    attributes.addFlashAttribute(MAV_ERRORS, result);
  }
}
