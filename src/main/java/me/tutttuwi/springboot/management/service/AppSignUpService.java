package me.tutttuwi.springboot.management.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import me.tutttuwi.springboot.management.config.SpringConfigReader;
import me.tutttuwi.springboot.management.dao.AccountEmailRepository;
import me.tutttuwi.springboot.management.dao.AccountIndivRepository;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;
import me.tutttuwi.springboot.management.dao.AuthKeyRepository;
import me.tutttuwi.springboot.management.dao.NumberingRepository;
import me.tutttuwi.springboot.management.entity.AccountEmail;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.entity.AccountInfo;
import me.tutttuwi.springboot.management.entity.AuthKey;
import me.tutttuwi.springboot.management.entity.Numbering;
import me.tutttuwi.springboot.management.request.SignUpUserFormRequest;
import me.tutttuwi.springboot.management.util.AppMailTemplate;
import me.tutttuwi.springboot.management.util.AppMailer;
import me.tutttuwi.springboot.management.util.AppMessageSourceAccessor;
import me.tutttuwi.springboot.management.util.AppUtil;

@Transactional
@Service
public class AppSignUpService {

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
     * アカウント情報登録
     * @param form
     */
    @Transactional
    public void registUserInfo(SignUpUserFormRequest form) throws Throwable {

        // LOCK取得
        Numbering numbering = new Numbering();
        numbering = numberingDao.selectByNumKey(confReader.getNumKey(), SelectOptions.get().forUpdate());
        Numbering nextNumbering = util.getNextNum(numbering);
        String aid = util.getNextNumStr(numbering);
        numberingDao.update(nextNumbering);

        AccountInfo accountInfo = new AccountInfo();
        BoundMapperFacade<SignUpUserFormRequest, AccountInfo> boundMapperFacadeInfo = mapperFactory
                .getMapperFacade(SignUpUserFormRequest.class, AccountInfo.class);
        accountInfo = boundMapperFacadeInfo.map(form);
        accountInfo.setAccountId(aid);
        accountInfo.setPassword(util.encryptPassword(accountInfo.getPassword()));
        System.out.println("accountInfo : " + accountInfo);
        accountInfoDao.insert(accountInfo);

        AccountEmail accountEmail = new AccountEmail();
        accountEmail.setAccountId(aid);
        accountEmail.setEmailAddr(form.getEmail());
        accountEmail.setEmailKb("01"); // TODO: 区分決め メイン
        accountEmail.setEmailType("10"); // TODO: 区分決め TEXT or HTML
        accountEmailDao.insert(accountEmail);

        AccountIndiv accountIndiv = new AccountIndiv();
        accountIndiv.setAccountId(aid);
        accountIndiv.setFstName(form.getFstName());
        accountIndiv.setLstName(form.getLstName());
        accountIndivDao.insert(accountIndiv);

        AuthKey authKey = new AuthKey();
        String key = UUID.randomUUID().toString();
        authKey.setAccountId(aid);
        authKey.setAuthKey(key);
        authKey.setAuthType("001"); //TODO: 定数定義していく 001はサインアップ用
        //    authSignUpEmail.setExpireDt(Timestamp.valueOf(LocalDateTime.now().plusHours(Integer.parseInt(VALID_HOUR))));
        authKey.setExpireDt(Timestamp.valueOf(LocalDateTime.now().plusHours(confReader.getAuthEmailValidhour())));
        authKeyDao.insert(authKey);

        // TODO: 個別設定 メール送信機能実装用
        // sendAuthEmail(accountIndiv, key);
    }

    private void sendAuthEmail(AccountIndiv accountIndiv, String emailAuthKey) {
        Context context = new Context();
        String emailAuthUrl = UriComponentsBuilder.fromUriString(confReader.getAuthEmailUrl())
                .queryParam("key", emailAuthKey)
                .toUriString();
        context.setVariable("fstName", accountIndiv.getFstName());
        context.setVariable("lstName", accountIndiv.getLstName());
        context.setVariable("auth_url", emailAuthUrl);
        mailer.send(mailer -> mailer.from(msg.getMsg("mail.signup.from"))
                .to(msg.getMsg("mail.signup.to"))
                .subject(msg.getMsg("mail.signup.subject"))
                .body(mailTempl.getText(msg.getMsg("mail.signup.body.path"), context)));
    }

    @Transactional
    public boolean isAuthKeyExpired(String key) throws Throwable {
        return util.isAuthKeyExpired(key);
    }

    @Transactional
    public void activateUserInfo(String emailAuthKey) throws Throwable {
        AuthKey authSignUpEmail = authKeyDao.selectByAuthKey(emailAuthKey);
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(authSignUpEmail.getAccountId());
        accountInfo.setEnabled(true); //TODO: 定数管理
        accountInfo.setUpdateDt(util.getTimeNow());
        accountInfoDao.update(accountInfo);

        authKeyDao.delete(authSignUpEmail);
        // TODO: history残す？
    }
}
