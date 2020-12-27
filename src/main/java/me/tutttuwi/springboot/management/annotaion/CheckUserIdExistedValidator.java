package me.tutttuwi.springboot.management.annotaion;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;

public class CheckUserIdExistedValidator
    implements ConstraintValidator<CheckUserIdExisted, String> {

  @Autowired
  AccountInfoRepository accountRepository;

  @Override
  public void initialize(CheckUserIdExisted ano) {}

  @Override
  public boolean isValid(String id, ConstraintValidatorContext context) {
    boolean isNotExist = Objects.isNull(accountRepository.selectById(id));
    return isNotExist;
  }
}
