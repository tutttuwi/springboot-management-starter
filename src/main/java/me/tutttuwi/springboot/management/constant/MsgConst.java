package me.tutttuwi.springboot.management.constant;

/**
 * 定数定義.
 */
public enum MsgConst {
  // @formatter:off

  GLOBAL("GlobalMessage"),
  VALIDATION_ERROR("ValidationError"),
  OPTIMISTIC_LOCKING_FAILURE_ERROR("OptimisticLockingFailureError"),
  DOUBLE_SUBMIT_ERROR("DoubleSubmitError"),

  WARN_DATA_NOT_FOUND("WARN.DATA.NOT_FOUND"),
  ERROR_DATA_NOT_FOUND("ERROR.DATA.NOT_FOUND"),
  ERROR_USER_NOT_FOUND("ERROR.USER.NOT_FOUND"),
  ERROR_UNEXPECTED("UnexpectedError"),
  DELETED("Deleted"),
  SUCCESS("Success")
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
  private MsgConst(String key) {
    this.KEY = key;
  }
}
