package me.tutttuwi.springboot.management.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import me.tutttuwi.springboot.management.dao.AuthKeyRepository;
import me.tutttuwi.springboot.management.entity.AuthKey;
import me.tutttuwi.springboot.management.entity.Numbering;

@Component
public class AppUtil {

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  AuthKeyRepository authKeyDao;

  /**
   * パスワード検証.
   *
   * @param password String
   * @return
   */
  public boolean checkPasswordRequirement(String password) {
    boolean isValid = false;
    return isValid;

  }

  /**
   * パスワード暗号化処理
   *
   * @param password
   * @return
   */
  public String encryptPassword(String password) {
    return passwordEncoder.encode(password);
  }

  /**
   * 採番テーブルの情報を基に次の番号を採番し、キー文字列を返却する
   *
   * @param numbering
   * @return
   * @throws Throwable
   */
  public String getNextNumStr(Numbering nextNumbering) throws Throwable {
    long nextNum = nextNumbering.getNumIssued();
    String prefixChar = StringUtils.trim(nextNumbering.getPrefixChar());
    String nextNumStr =
        StringUtils.leftPad(String.valueOf(nextNum), nextNumbering.getNumDigits(), "0");
    if (StringUtils.isNotEmpty(prefixChar)) {
      nextNumStr = prefixChar + nextNumStr.substring(prefixChar.length());
    }
    return nextNumStr;
  }

  /**
   * 採番テーブルの情報を基に次の番号を採番する
   *
   * @param numbering
   * @return
   * @throws Throwable
   */
  public Numbering getNextNum(Numbering numbering) throws Throwable {
    long nextNum = numbering.getNumIssued() + 1; // increment
    long maxNum = numbering.getNumMax();
    long minNum = numbering.getNumMin();
    if (nextNum < minNum && nextNum > maxNum) {
      throw new Exception(); // TODO: 採番エラー
    }
    numbering.setNumIssued(nextNum);
    numbering.setUpdateDt(new Timestamp(System.currentTimeMillis()));
    return numbering;
  }

  /**
   * 現在時刻取得<br/>
   * DBやAPIから時刻を取得するケースを鑑みてメソッドをUTILに分ける
   *
   * @return
   */
  public Timestamp getTimeNow() {
    return new Timestamp(System.currentTimeMillis());
  }

  /**
   * 認証キー情報の期限切れチェック
   *
   * @param key
   * @return
   * @throws Throwable
   */
  @Transactional
  public boolean isAuthKeyExpired(String key) throws Throwable {
    AuthKey authSignUpEmail = authKeyDao.selectByAuthKey(key);
    if (Objects.isNull(authSignUpEmail)) {
      return true;
      // throw new NotFoundException("認証キーに対応するデータが見つかりませんでした。"); //TODO: エラー共通化
    }
    if (LocalDateTime.now().isAfter(authSignUpEmail.getExpireDt().toLocalDateTime())) {
      return true;
    }
    return false;
  }
}
