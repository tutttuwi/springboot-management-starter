package me.tutttuwi.springboot.management.resouce;

import java.io.Serializable;
import lombok.Data;

@Data
public class ApiResouce implements Serializable {

  private static final long serialVersionUID = 1L;

  private String id;
  private String password;

  // @AssertTrue(message = "開始日は終了日以前を入力してください。")
  // public boolean isDateValid() {
  // if (enddate >= startdate)
  // return true;
  // return false;
  // }
}
