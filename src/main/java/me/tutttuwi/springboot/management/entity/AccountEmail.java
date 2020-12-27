package me.tutttuwi.springboot.management.entity;

import java.sql.Timestamp;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
// @Table(name = "user_idpw")
public class AccountEmail {

  /** SEQ. */
  private String accountId;
  /** メールアドレス. */
  private String emailAddr;
  /** メール区分. */
  private String emailKb;
  /** メールタイプ. */
  private String emailType;
  /** 作成日時. */
  private Timestamp createDt;
  /** 更新日時. */
  private Timestamp updateDt;

  public AccountEmail() {
    createDt = new Timestamp(System.currentTimeMillis());
    updateDt = new Timestamp(System.currentTimeMillis());
  }

}
