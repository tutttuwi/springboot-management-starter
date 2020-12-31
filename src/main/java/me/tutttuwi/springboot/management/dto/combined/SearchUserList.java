package me.tutttuwi.springboot.management.dto.combined;

import java.sql.Timestamp;
import org.seasar.doma.Entity;
import org.seasar.doma.jdbc.entity.NamingType;
import lombok.Data;

@Data
@Entity(naming = NamingType.SNAKE_UPPER_CASE)
public class SearchUserList {

  /** AccountID. */
  private String accountId;
  /** ユーザID. */
  private String userId;
  /** 氏名(名). */
  private String fstName;
  /** 氏名(姓). */
  private String lstName;
  /** メールアドレス. */
  private String emailAddr;
  /** 作成日時. */
  private Timestamp createDt;
  /** 更新日時. */
  private Timestamp updateDt;

  public SearchUserList() {}

}
