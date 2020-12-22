package me.tutttuwi.springboot.management.entity;

import java.sql.Timestamp;

import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.jdbc.entity.NamingType;

import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
//@Table(name = "user_idpw")
public class AccountInfo {

  /** AccountID */
  @Id
  private String accountId;
  /** ユーザID */
  private String userId;
  /** PW */
  private String password;
  /** ユーザ区分 */
  private String userKb;
  /** 有効FLG */
  private boolean enabled;
  /** アカウント有効期限FLG */
  private boolean accountNonExpired;
  /** クレデンシャル有効期限FLG */
  private boolean credentialsNonExpired;
  /** アカウントロックFLG */
  private boolean accountNonLocked;
  /** 権限 */
  private String roleId;
  /** 作成日時 */
  private Timestamp createDt;
  /** 更新日時 */
  private Timestamp updateDt;

  public AccountInfo() {
    // TODO: 初期処理はサービス内に記載する
    userKb = "01";
    enabled = false;
    accountNonExpired = true;
    credentialsNonExpired = true;
    accountNonLocked = true;
    roleId = "ROLE_USER";
    createDt = new Timestamp(System.currentTimeMillis());
    updateDt = new Timestamp(System.currentTimeMillis());
  }

}
