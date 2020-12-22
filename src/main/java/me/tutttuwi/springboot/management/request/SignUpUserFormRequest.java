package me.tutttuwi.springboot.management.request;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import lombok.Data;
import me.tutttuwi.springboot.management.annotaion.CheckPasswordRequirement;
import me.tutttuwi.springboot.management.annotaion.CheckUserIdExisted;

@Data
@Component
public class SignUpUserFormRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 15, message = "{}")
    private String fstName;
    @NotBlank
    @Size(max = 15)
    private String lstName;
    @NotBlank
    @Size(min = 4, max = 10, message = "{input.min.max.size}")
    @CheckUserIdExisted
    private String userId;
    @NotBlank
    @Size(max = 60, message = "{input.max.size}")
    @Email
    private String email;
    @Size(min = 8, max = 30, message = "{input.min.max.size}")
    @CheckPasswordRequirement
    private String password;
    @Size(min = 8, max = 30, message = "{input.min.max.size}")
    @CheckPasswordRequirement
    private String retypePassword;
    private Boolean isAgreeTerms;

    @AssertTrue(message = "確認用パスワードが異なります")
    public boolean isNotMatchPassword() {
        if (StringUtils.equals(password, retypePassword)) {
            return true;
        }
        return false;
    }
}
