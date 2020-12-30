package me.tutttuwi.springboot.management.constant;

/**
 * 定数定義.
 */
public enum UtilConst {
  // @formatter:off

  LOCALDATE_FORMAT("yyyy/MM/dd"),
  LOCALDATETIME_FORMAT("yyyy/[]M/[]d []H:[]m:[]s"),
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
  private UtilConst(String key) {
    this.KEY = key;
  }

}
