package me.tutttuwi.springboot.management.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import me.tutttuwi.springboot.management.entity.AccountInfo;

@ConfigAutowireable
@Dao
public interface AccountInfoRepository {
  // @Select
  // List<Account> selectAll();

  @Select
  AccountInfo selectByIdAndPw(String userId, String password);

  @Select
  AccountInfo selectById(String userId);

  @Select
  String selectByIdForSeq(String userId);

  @Insert
  int insert(AccountInfo accountInfo);

  @Update(excludeNull = true)
  int update(AccountInfo accountInfo);
}
