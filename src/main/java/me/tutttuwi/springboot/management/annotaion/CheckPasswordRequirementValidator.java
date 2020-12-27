package me.tutttuwi.springboot.management.annotaion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckPasswordRequirementValidator
    implements ConstraintValidator<CheckPasswordRequirement, String> {

  @Autowired
  PasswordValidator passwordValidator;

  @Override
  public void initialize(CheckPasswordRequirement ano) {}

  @Override
  public boolean isValid(String password, ConstraintValidatorContext context) {
    PasswordData pd = new PasswordData(password);
    RuleResult result = passwordValidator.validate(pd);
    boolean isValid = result.isValid();
    return isValid;
  }
}
