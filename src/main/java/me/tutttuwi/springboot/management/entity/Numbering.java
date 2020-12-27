package me.tutttuwi.springboot.management.entity;

import java.sql.Timestamp;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class Numbering {

  /** ナンバリングキー. */
  @Id
  private String numKey;
  /** 会員コード. */
  private Long numIssued;
  /** 採番最大値. */
  private Long numMax;
  /** 採番最小値. */
  private Long numMin;
  /** 桁数. */
  private Integer numDigits;
  /** 接頭辞文字列. */
  private String prefixChar;
  /** 作成日時. */
  private Timestamp createDt;
  /** 更新日時. */
  private Timestamp updateDt;

  public Numbering() {
    createDt = new Timestamp(System.currentTimeMillis());
    updateDt = new Timestamp(System.currentTimeMillis());
  }

}
