package me.tutttuwi.springboot.management.request;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.annotaion.CheckForgotPasswordRequirement;

@Slf4j
@Data
@Component
@CheckForgotPasswordRequirement(emailProperty = "email", userIdProperty = "userId",
    correlationErrorProperty = "correlationError")
public class ForgotpasswordFormRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotBlank
  @Size(min = 4, max = 10, message = "{input.min.max.size}")
  private String userId;
  @NotBlank
  @Size(max = 60, message = "{input.max.size}")
  @Email
  private String email;

  /** 相関エラー領域用変数 (定義がないとバインドできないため). */
  private String correlationError;

}
