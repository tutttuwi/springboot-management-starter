package me.tutttuwi.springboot.management.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.ValidateConst;

@Slf4j
@Data
@Component
public class PasswordGenerateFormApiRequest implements Serializable {
  private static final long serialVersionUID = 1L;

  @NotBlank
  @Size(min = ValidateConst.PW_MIN, max = ValidateConst.PW_MAX, message = "{input.min.max.size}")
  private String password;
  @Size(max = ValidateConst.SECRET_MAX, message = "{input.max.size}")
  private String secret;
  @Size(max = ValidateConst.ITERATIONS_MAX, message = "{input.max.size}")
  private String iterations;
  @Size(max = ValidateConst.HASH_WIDTH_MAX, message = "{input.max.size}")
  private String hashWidth;

}
