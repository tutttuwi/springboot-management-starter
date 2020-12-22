package me.tutttuwi.springboot.management.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import me.tutttuwi.springboot.management.entity.AccountIndiv;

@ConfigAutowireable
@Dao
public interface AccountIndivRepository {
  @Select
  AccountIndiv selectByAccountId(String accountId);

  @Insert
  int insert(AccountIndiv accountIndiv);

}
