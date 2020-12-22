package me.tutttuwi.springboot.management.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginFormRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotBlank
  private String id;
  @Size(min = 8, max = 30, message = "{input.min.max.size}")
  private String password;

}
