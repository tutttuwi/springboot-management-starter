package me.tutttuwi.springboot.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ma.glasnost.orika.MapperFactory;
import me.tutttuwi.springboot.management.config.SpringConfigReader;
import me.tutttuwi.springboot.management.dao.AccountEmailRepository;
import me.tutttuwi.springboot.management.dao.AccountIndivRepository;
import me.tutttuwi.springboot.management.dao.AuthKeyRepository;
import me.tutttuwi.springboot.management.dao.NumberingRepository;
import me.tutttuwi.springboot.management.dao.combined.SearchUserRepository;
import me.tutttuwi.springboot.management.dto.combined.SearchUserList;
import me.tutttuwi.springboot.management.request.SearchUserFormApiRequest;
import me.tutttuwi.springboot.management.util.AppMailTemplate;
import me.tutttuwi.springboot.management.util.AppMailer;
import me.tutttuwi.springboot.management.util.AppUtil;

@Transactional
@Service
public class AppSearchUserApiService {

  @Autowired
  SearchUserRepository searchUserDao;
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
   * ユーザ情報一覧取得.
   *
   * @param inputForm ForgotpasswordFormRequest
   */
  @Transactional
  public List<SearchUserList> getUserList(SearchUserFormApiRequest inputForm) throws Throwable {
    List<SearchUserList> result = searchUserDao.selectUserList(inputForm.getFstNm(),
        inputForm.getLstNm(), inputForm.getUserId(), inputForm.getEmail());
    return result;
  }

}
