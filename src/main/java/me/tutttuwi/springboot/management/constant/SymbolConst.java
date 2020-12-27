package me.tutttuwi.springboot.management.constant;

/**
 * AlphaNumeric Const Class.
 *
 * @author Tomo
 *
 */
public enum SymbolConst {
  UNDER_SCORE("_"), DOT("."), SHARP("#");

  public final String VALUE;

  private SymbolConst(String value) {
    this.VALUE = value;
  }
}
