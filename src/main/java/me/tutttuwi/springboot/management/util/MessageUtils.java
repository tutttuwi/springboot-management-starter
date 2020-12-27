package me.tutttuwi.springboot.management.util;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageUtils {

  private static MessageSource messageSource;

  @Autowired
  public void setMessageSource(MessageSource messageSource) {
    MessageUtils.messageSource = messageSource;
  }

  /**
   * メッセージを取得します.
   *
   * @param key String
   * @return
   */
  public static String getMsg(String key) {
    Locale locale = LocaleContextHolder.getLocale();
    return MessageUtils.messageSource.getMessage(key, null, locale);
  }

  /**
   * メッセージを取得します.
   *
   * @param key String
   * @param args Object...
   * @return
   */
  public static String getMsg(String key, Object... args) {
    Locale locale = LocaleContextHolder.getLocale();
    return MessageUtils.messageSource.getMessage(key, args, locale);
  }

  /**
   * ロケールを指定してメッセージを取得します.
   *
   * @param key String
   * @param locale Locale
   * @param args Object...
   * @return
   */
  public static String getMsg(String key, Locale locale, Object... args) {
    return MessageUtils.messageSource.getMessage(key, args, locale);
  }

  /**
   * メッセージを取得します.
   *
   * @param resolvable MessageSourceResolvable
   * @return
   */
  public static String getMsg(MessageSourceResolvable resolvable) {
    Locale locale = LocaleContextHolder.getLocale();
    return MessageUtils.messageSource.getMessage(resolvable, locale);
  }
}
