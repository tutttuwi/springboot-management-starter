package me.tutttuwi.springboot.management.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.builder.SelectBuilder;
import org.springframework.util.MultiValueMap;

import me.tutttuwi.springboot.management.entity.AccountConnection;

@ConfigAutowireable
@Dao
public interface AccountConnectionRepository {
  //  @Select
  //  List<Account> selectAll();

  @Select
  List<String> findUserIdsWithConnection(String providerId, String providerUserId);

  @Select
  List<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserId);

  //@Select
  //AccountConnection selectById(String userId);

  @Select
  List<AccountConnection> findAllConnections(String userId);

  @Select
  List<AccountConnection> findConnections(String userId, String providerId);

  default List<AccountConnection> findConnectionsToUsers(MultiValueMap<String, String> providerUsers,
      String userId) {
    Config config = Config.get(this);
    SelectBuilder builder = SelectBuilder.newInstance(config);
    builder.sql("select");
    builder.sql("user_id,");
    builder.sql("provider_id,");
    builder.sql("provider_user_id,");
    builder.sql("display_name,");
    builder.sql("profile_url,");
    builder.sql("image_url,");
    builder.sql("access_token,");
    builder.sql("secret,");
    builder.sql("refresh_token,");
    builder.sql("expire_time");
    builder.sql("from account_connection");
    builder.sql("where user_id = ").param(String.class, userId).sql("and");
    for (Iterator<Entry<String, List<String>>> it = providerUsers.entrySet().iterator(); it.hasNext();) {
      Entry<String, List<String>> entry = it.next();
      String providerId = entry.getKey();
      builder.sql("provider_id = ").param(String.class, providerId).sql("and");
      builder.sql("provider_user_id in (").params(String.class, entry.getValue()).sql(")");
      if (it.hasNext()) {
        builder.sql(" or ");
      }
    }
    builder.sql("order by providerId, rank");
    return builder.getEntityResultList(AccountConnection.class);
  };

  @Select
  List<AccountConnection> getConnection(String userId, String providerId, String providerUserId);

  @Select
  List<AccountConnection> findPrimaryConnection(String userId, String providerId);

  @Select
  int selectAddConnection(String userId, String providerId);

  @Insert(excludeNull = true)
  int insert(AccountConnection accountConnection);

  @Update(excludeNull = true)
  int update(AccountConnection accountConnection);

  @Update(sqlFile = true)
  int updateConnection(AccountConnection accountConnection);

  @Delete(sqlFile = true)
  int deleteRemoveConnections(String userId, String providerId);

  @Delete(sqlFile = true)
  int deleteRemoveConnection(String userId, String providerId, String providerUserId);

}
