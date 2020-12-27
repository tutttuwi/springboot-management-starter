package me.tutttuwi.springboot.management.entity;

import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data()
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
// @Table(name = "user_idpw")
public class AccountConnection {

  /** AccountID. */
  // @Id
  // private String accountId;
  /** ユーザID. */
  private String userId;
  /** PW. */
  private String providerId;
  /** ユーザ区分. */
  private String providerUserId;
  /** 有効FLG. */
  private int rank;
  /** 表示名. */
  private String displayName;
  /** プロファイルURL. */
  private String profileUrl;
  /** イメージURL. */
  private String imageUrl;
  /** アクセストークン. */
  private String accessToken;
  /** シークレット. */
  private String secret;
  /** リフレッシュトークン. */
  private String refreshToken;
  /** 有効期限. */
  private long expireTime;

  public AccountConnection() {}

}
