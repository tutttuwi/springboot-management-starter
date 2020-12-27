package me.tutttuwi.springboot.management.util;

/**
 * 機能名のマーカーインターフェース.
 */
public interface FunctionNameAware {

  /**
   * 機能名を返します.
   *
   * @return
   */
  String getFunctionName();
}
