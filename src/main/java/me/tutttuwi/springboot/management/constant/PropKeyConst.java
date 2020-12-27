package me.tutttuwi.springboot.management.constant;

/**
 * PropKey Management Enum Class.<br/>
 * if you add property message or something, just add class definition that match key value.<br/>
 *
 * @author tutttuwi
 *
 */
public enum PropKeyConst {
  // @formatter:off

  // MESSAGE
  ERROR_COMMON_REQUIRED_INPUT("error.common.required.input"),
  ERROR_FORM_LOGIN_UNMATCH("error.form.login.unmatch"),
  FIELDNAME_LOGIN_PASSWORD("fieldname.login.password"),
  // CONFIGIRATION
  DB_CLASS("db.class"),
  DB_URL("db.url"),
  DB_USER("db.user"),
  DB_PASS("db.pass"),
  DB_SCHEMA("db.schema"),
  MAINTENANCE_DAYOFWEEK("maintenance.dayofweek"),
  MAINTENANCE_START("maintenance.start"),
  MAINTENANCE_END("maintenance.end"),
  PASS_SALT("pass.salt");

  // @formatter:on

  /**
   * Property Key Name.
   */
  public final String KEY;

  /**
   * Constractor.
   *
   */
  private PropKeyConst(String key) {
    this.KEY = key;
  }
}
