package me.tutttuwi.springboot.management.request;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.annotaion.CheckPasswordRequirement;

@Slf4j
@Data
@Component
public class ForgotpasswordUpdateFormRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @Size(min = 8, max = 30, message = "{input.min.max.size}")
  @CheckPasswordRequirement
  private String password;
  @Size(min = 8, max = 30, message = "{input.min.max.size}")
  @CheckPasswordRequirement
  private String retypePassword;
  private String authKey;

  @AssertTrue(message = "確認用パスワードが異なります")
  public boolean isNotMatchPassword() {
    if (StringUtils.equals(password, retypePassword)) {
      return true;
    }
    return false;
  }
}
