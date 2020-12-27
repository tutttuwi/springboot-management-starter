package me.tutttuwi.springboot.management.request;

import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class ApiCommonRequest implements Serializable {

  /** 相関チェック共通化用. */
  public abstract String correlationCheck();

  /** API共通チェック処理記述領域. */
}
