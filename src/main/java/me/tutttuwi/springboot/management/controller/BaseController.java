package me.tutttuwi.springboot.management.controller;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ma.glasnost.orika.MapperFactory;
import me.tutttuwi.springboot.management.util.MessageUtils;

/**
 * {@code BaseController} 基底コントローラは、<br/>
 * すべてのコントローラ（UI/API）のスーパークラスとして定義し、<br/>
 * コントローラ内で使用する共通処理の実装を保持します.
 *
 * @author Tomo
 *
 */
public class BaseController {

  public static final String VALIDATION_ERROR = "ValidationError";

  @Autowired
  protected ApplicationContext applicationContext;

  @Autowired
  MapperFactory mapperFactory;

  /**
   * 入力エラーの共通メッセージを返します.
   *
   * @return
   */
  protected String getValidationErrorMessage() {
    return getMessage(VALIDATION_ERROR);
  }

  /**
   * コンテキストを返します.
   *
   * @return
   */
  protected ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * メッセージを取得します.
   *
   * @param key メッセージキー文字列
   * @param args メッセージ引数文字列
   * @return
   */
  protected String getMessage(String key, Object... args) {
    return MessageUtils.getMsg(key, args);
  }

  /**
   * ロケールを指定してメッセージを取得します.
   *
   * @param key メッセージキー文字列
   * @param args メッセージ引数文字配列
   * @param locale メッセージロケール
   * @return
   */
  protected String getMessage(String key, Object[] args, Locale locale) {
    return MessageUtils.getMsg(key, args, locale);
  }
}
