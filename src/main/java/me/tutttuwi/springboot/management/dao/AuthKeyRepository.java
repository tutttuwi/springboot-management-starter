package me.tutttuwi.springboot.management.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import me.tutttuwi.springboot.management.entity.AuthKey;

@ConfigAutowireable
@Dao
public interface AuthKeyRepository {
  @Select
  AuthKey selectByAuthKey(String authKey);

  // @Select
  // List<AuthKey> selectAll(AuthKey authSignUpEmail);

  @Insert
  int insert(AuthKey authSignUpEmail);

  @Update
  int update(AuthKey authSignUpEmail);

  @Delete
  int delete(AuthKey authSignUpEmail);

}
