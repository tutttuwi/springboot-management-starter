package me.tutttuwi.springboot.management.request;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.ValidateConst;

@Slf4j
@Data
@Component
public class SearchUserFormApiRequest extends AbstractApiRequestParameter implements Serializable {
  private static final long serialVersionUID = 1L;

  @Size(max = ValidateConst.FULL_NAME_MAX, message = "{input.min.max.size}")
  private String lstNm;

  @Size(max = ValidateConst.FULL_NAME_MAX, message = "{input.min.max.size}")
  private String fstNm;

  @Size(max = ValidateConst.USER_ID_MAX, message = "{input.max.size}")
  private String userId;

  // @Size(max = ValidateConst.ITERATIONS_MAX, message = "{input.max.size}")
  // private String tel;

  @Size(max = ValidateConst.EMAIL_ADDR_MAX, message = "{input.max.size}")
  @Email
  private String email;

}
