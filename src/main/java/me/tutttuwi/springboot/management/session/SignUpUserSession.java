package me.tutttuwi.springboot.management.session;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;
import me.tutttuwi.springboot.management.request.SignUpUserFormRequest;

@Data
@Component
@SessionScope
public class SignUpUserSession implements Serializable {
  private SignUpUserFormRequest signUpUserFormRequest;
}
