package me.tutttuwi.springboot.management.constant;

/**
 * 定数定義
 */
public interface ValidateConst {
  /** ---- バリデータ定数. ---- **/
  public static final int PW_MAX = 32;
  public static final int PW_MIN = 8;
  public static final int SECRET_MAX = 256;
  public static final int SECRET_MIN = 1;
  public static final int ITERATIONS_MAX = 256;
  public static final int ITERATIONS_MIN = 1;
  public static final int HASH_WIDTH_MAX = 256;
  public static final int HASH_WIDTH_MIN = 1;
}
