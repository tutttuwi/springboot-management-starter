package me.tutttuwi.springboot.management.dao.combined;

import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import me.tutttuwi.springboot.management.dto.combined.SearchUserList;

@ConfigAutowireable
@Dao
public interface SearchUserRepository {
  // @Select
  // List<Account> selectAll();

  @Select
  List<SearchUserList> selectUserList(String fstNm, String lstNm, String userId, String emailAddr);

  // @Select
  // AccountInfo selectById(String userId);
  //
  // @Select
  // String selectByIdForSeq(String userId);
  //
  // @Insert
  // int insert(AccountInfo accountInfo);
  //
  // @Update(excludeNull = true)
  // int update(AccountInfo accountInfo);
}
