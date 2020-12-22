package me.tutttuwi.springboot.management.annotaion;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.dao.AccountEmailRepository;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;
import me.tutttuwi.springboot.management.entity.AccountEmail;
import me.tutttuwi.springboot.management.entity.AccountInfo;

@Slf4j
public class CheckForgotPasswordRequirementValidator
    implements ConstraintValidator<CheckForgotPasswordRequirement, Object> {

  private String userIdProperty;
  private String emailProperty;
  private String correlationErrorProperty;
  private String message;

  @Autowired
  AccountInfoRepository accountInfoDao;
  @Autowired
  AccountEmailRepository accountEmailDao;

  @Override
  public void initialize(CheckForgotPasswordRequirement ano) {
    // アノテーション経由で渡されたプロパティを受け取りたい場合、ここでクラス変数に格納しておく
    this.userIdProperty = ano.userIdProperty();
    this.emailProperty = ano.emailProperty();
    this.correlationErrorProperty = ano.correlationErrorProperty();
    this.message = ano.message();
  }

  @Override
  public boolean isValid(Object form, ConstraintValidatorContext context) {
    if (Objects.isNull(form)) {
      return true;
    }
    BeanWrapper beanWrapper = new BeanWrapperImpl(form);
    String userId = (String) beanWrapper.getPropertyValue(userIdProperty);
    String email = (String) beanWrapper.getPropertyValue(emailProperty);
    AccountInfo accountInfo = accountInfoDao.selectById(userId);
    if (Objects.isNull(accountInfo)) {
      return true;
    }
    AccountEmail accountEmail = accountEmailDao.selectByAccountId(accountInfo.getAccountId());
    if (Objects.isNull(accountEmail)) {
      log.error("異常データ : " + this);
      return true;
    }
    if (StringUtils.equals(email, accountEmail.getEmailAddr())) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(message)
        .addPropertyNode(correlationErrorProperty)
        .addConstraintViolation();
    return false;
  }
}