package me.tutttuwi.springboot.management.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;

import ma.glasnost.orika.MapperFactory;
import me.tutttuwi.springboot.management.config.SpringConfigReader;
import me.tutttuwi.springboot.management.dao.AccountEmailRepository;
import me.tutttuwi.springboot.management.dao.AccountIndivRepository;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;
import me.tutttuwi.springboot.management.dao.AuthKeyRepository;
import me.tutttuwi.springboot.management.dao.NumberingRepository;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.entity.AccountInfo;
import me.tutttuwi.springboot.management.entity.AuthKey;
import me.tutttuwi.springboot.management.request.ForgotpasswordFormRequest;
import me.tutttuwi.springboot.management.request.ForgotpasswordUpdateFormRequest;
import me.tutttuwi.springboot.management.util.AppMailTemplate;
import me.tutttuwi.springboot.management.util.AppMailer;
import me.tutttuwi.springboot.management.util.AppMessageSourceAccessor;
import me.tutttuwi.springboot.management.util.AppUtil;

@Transactional
@Service
public class AppForgotpasswordService {

  @Autowired
  AppMessageSourceAccessor msg;
  @Autowired
  AccountInfoRepository accountInfoDao;
  @Autowired
  AccountEmailRepository accountEmailDao;
  @Autowired
  AccountIndivRepository accountIndivDao;
  @Autowired
  AuthKeyRepository authKeyDao;
  @Autowired
  MapperFactory mapperFactory;
  @Autowired
  NumberingRepository numberingDao;

  @Autowired
  AppUtil util;
  @Autowired
  AppMailer mailer;
  @Autowired
  AppMailTemplate mailTempl;

  @Autowired
  SpringConfigReader confReader;
  //  @Value("${settings.auth.email.url}")
  //  String emailAuthUrl;
  //  @Value("${settings.auth.email.validhour}")
  //  int VALID_HOUR;

  //  @Transactional(readOnly = true)
  //  public Xxxx getInfo(String id) {
  //    return XxxxDao.getInfo(id);
  //  }

  /**
   * パスワード更新用メール送信
   * @param inputForm
   */
  @Transactional
  public void sendPasswordUpdateEmail(ForgotpasswordFormRequest inputForm) throws Throwable {
    AccountInfo accountInfo = accountInfoDao.selectById(inputForm.getUserId());
    AccountIndiv accountIndiv = accountIndivDao.selectByAccountId(accountInfo.getAccountId());
    String aid = accountInfo.getAccountId();

    AuthKey authKey = new AuthKey();
    String key = UUID.randomUUID().toString();
    authKey.setAccountId(aid);
    authKey.setAuthKey(key);
    authKey.setAuthType("002"); //TODO: 定数定義していく 002はパスワード更新用
    authKey.setExpireDt(Timestamp.valueOf(LocalDateTime.now().plusHours(confReader.getPasswordEmailValidhour())));
    authKeyDao.insert(authKey);

    Context context = new Context();
    String authUrl = UriComponentsBuilder.fromUriString(confReader.getPasswordEmailUrl())
        .queryParam("key", key)
        .toUriString();
    String cancelUrl = UriComponentsBuilder.fromUriString(confReader.getPasswordEmailCancelUrl())
        .queryParam("key", key)
        .toUriString();
    context.setVariable("fstName", accountIndiv.getFstName());
    context.setVariable("lstName", accountIndiv.getLstName());
    context.setVariable("auth_url", authUrl);
    context.setVariable("cancel_url", cancelUrl);
    mailer.send(mailer -> mailer.from(msg.getMsg("mail.forgotpassword.from"))
        .to(msg.getMsg("mail.forgotpassword.to"))
        .subject(msg.getMsg("mail.forgotpassword.subject"))
        .body(mailTempl.getText(msg.getMsg("mail.forgotpassword.body.path"), context)));

  }

  @Transactional
  public boolean isAuthKeyExpired(String key) throws Throwable {
    return util.isAuthKeyExpired(key);
  }

  /**
   * 認証キー情報削除
   * @param key
   * @throws Throwable
   */
  @Transactional
  public void deleteAuthKey(String key) throws Throwable {
    AuthKey authKey = new AuthKey();
    authKey.setAuthKey(key);
    authKeyDao.delete(authKey);
  }

  @Transactional
  public void updatePassword(ForgotpasswordUpdateFormRequest inputForm) throws Throwable {
    AuthKey authKey = new AuthKey();
    authKey.setAuthKey(inputForm.getAuthKey());
    authKeyDao.delete(authKey);

    AccountInfo accountInfo = new AccountInfo();
    accountInfo.setPassword(inputForm.getPassword());
    accountInfo.setUpdateDt(util.getTimeNow());
    accountInfoDao.update(accountInfo);

    // TODO: HISTORY残す？
  }
}
