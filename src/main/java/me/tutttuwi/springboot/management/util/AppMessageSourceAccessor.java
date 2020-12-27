package me.tutttuwi.springboot.management.util;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class AppMessageSourceAccessor {

  // TODO: MessageUtilsに入れ替える
  @Autowired
  MessageSource messege;

  public String getMsg(String code, String[] args) {
    return messege.getMessage(code, args, Locale.getDefault());
  }

  public String getMsg(String code, String def) {
    return messege.getMessage(code, null, def, Locale.getDefault());
  }

  public String getMsg(String code, String[] args, String def) {
    return messege.getMessage(code, args, def, Locale.getDefault());
  }

  public String getMsg(String code) {
    return messege.getMessage(code, null, null, Locale.getDefault());
  }
}
