package me.tutttuwi.springboot.management.entity;

import java.sql.Timestamp;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class AccountHistory {

  /** SEQ. */
  private Long seq;
  /** ユーザーエージェント. */
  private String userAgent;
  /** 操作区分. */
  private String opeKb;
  /** 作成日時. */
  private Timestamp createDt;
  /** 更新日時. */
  private Timestamp updateDt;

  public AccountHistory() {
    createDt = new Timestamp(System.currentTimeMillis());
    updateDt = new Timestamp(System.currentTimeMillis());
  }

}
