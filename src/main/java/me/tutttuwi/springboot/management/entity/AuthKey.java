package me.tutttuwi.springboot.management.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class AuthKey implements Serializable {

  /** Eメール認証キー. */
  @Id
  private String authKey;
  /** アカウントID. */
  private String accountId;
  /** 認証タイプ. */
  private String authType;
  /** Eメール認証期限. */
  private Timestamp expireDt;
  /** 作成日時. */
  private Timestamp createDt;
  /** 更新日時. */
  private Timestamp updateDt;

  /**
   * コンストラクタ.
   */
  public AuthKey() {
    createDt = new Timestamp(System.currentTimeMillis());
    updateDt = new Timestamp(System.currentTimeMillis());
  }

}
