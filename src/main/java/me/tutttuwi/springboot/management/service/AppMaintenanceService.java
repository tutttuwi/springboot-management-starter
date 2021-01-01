package me.tutttuwi.springboot.management.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import me.tutttuwi.springboot.management.config.SpringConfigReader;
import me.tutttuwi.springboot.management.constant.MsgConst;
import me.tutttuwi.springboot.management.dao.AccountEmailRepository;
import me.tutttuwi.springboot.management.dao.AccountIndivRepository;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;
import me.tutttuwi.springboot.management.dao.AuthKeyRepository;
import me.tutttuwi.springboot.management.dao.NumberingRepository;
import me.tutttuwi.springboot.management.entity.AccountEmail;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.entity.AccountInfo;
import me.tutttuwi.springboot.management.exception.NoDataFoundException;
import me.tutttuwi.springboot.management.request.SignUpUserFormRequest;
import me.tutttuwi.springboot.management.session.SignUpUserSession;
import me.tutttuwi.springboot.management.util.AppMailTemplate;
import me.tutttuwi.springboot.management.util.AppMailer;
import me.tutttuwi.springboot.management.util.AppUtil;
import me.tutttuwi.springboot.management.util.MessageUtils;

@Slf4j
@Transactional
@Service
public class AppMaintenanceService {

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

  /**
   * ユーザ情報設定.
   *
   * @param signUpUserSession ユーザ登録情報セッション
   */
  public void setSignUpUser(String userId, SignUpUserSession signUpUserSession) {

    AccountInfo accountInfo = accountInfoDao.selectById(userId);
    if (Objects.isNull(accountInfo)) {
      throw new NoDataFoundException(MessageUtils.getMsg(MsgConst.ERROR_DATA_NOT_FOUND.KEY));
    }
    String accountId = accountInfo.getAccountId();
    AccountIndiv accountIndiv = accountIndivDao.selectByAccountId(accountId);
    AccountEmail accountEmail = accountEmailDao.selectByAccountId(accountId);

    SignUpUserFormRequest form = signUpUserSession.getSignUpUserFormRequest();
    form.setEmail(accountEmail.getEmailAddr());
    form.setFstName(accountIndiv.getFstName());
    form.setLstName(accountIndiv.getLstName());
    form.setUserId(accountInfo.getUserId());

  }
}
