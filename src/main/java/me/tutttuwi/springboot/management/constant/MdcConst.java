package me.tutttuwi.springboot.management.constant;

/**
 * 定数定義.
 */
public enum MdcConst {
  // @formatter:off

  MDC_LOGIN_USER_ID("LOGIN_USER_ID"),
  MDC_FUNCTION_NAME("FUNCTION_NAME"),
  ;

  // @formatter:on

  /**
   * Property Key Name.
   */
  public final String KEY;

  /**
   * Constractor.
   *
   */
  private MdcConst(String key) {
    this.KEY = key;
  }

}
