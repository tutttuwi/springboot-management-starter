package me.tutttuwi.springboot.management.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import me.tutttuwi.springboot.management.entity.AccountEmail;

@ConfigAutowireable
@Dao
public interface AccountEmailRepository {
  // @Select
  // List<Account> selectAll();

  @Select
  AccountEmail selectByAccountId(String accountId);

  @Insert
  int insert(AccountEmail accountEmail);

  @Update
  int update(AccountEmail accountEmail);

  @Delete
  int delete(AccountEmail accountEmail);

}
