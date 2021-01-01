package me.tutttuwi.springboot.management.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class AccountIndiv implements Serializable {
  /** アカウントID. */
  private String accountId;
  /** 氏名（名）. */
  private String lstName;
  /** 氏名（姓）. */
  private String fstName;
  /** 作成日時. */
  private Timestamp createDt;
  /** 更新日時. */
  private Timestamp updateDt;

  public AccountIndiv() {
    createDt = new Timestamp(System.currentTimeMillis());
    updateDt = new Timestamp(System.currentTimeMillis());
  }
}
