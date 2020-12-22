package me.tutttuwi.springboot.management.social;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.dao.AccountConnectionRepository;

@Slf4j
//@Service
public class Doma2UsersConnectionRepository implements UsersConnectionRepository {

  private final JdbcTemplate jdbcTemplate;

  private final ConnectionFactoryLocator connectionFactoryLocator;

  private final TextEncryptor textEncryptor;

  private ConnectionSignUp connectionSignUp;

  private AccountConnectionRepository accountConnectionRepository;

  // MEMO: tablePrefixは使用しない
  //private String tablePrefix = "";

  /**
   * Doma2用のUserConnectionRepositoryクラスを作成
   * @param dataSource
   * @param connectionFactoryLocator
   * @param textEncryptor
   */
  public Doma2UsersConnectionRepository(DataSource dataSource, ConnectionFactoryLocator connectionFactoryLocator,
      TextEncryptor textEncryptor, AccountConnectionRepository accountConnectionRepository) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.connectionFactoryLocator = connectionFactoryLocator;
    this.textEncryptor = textEncryptor;
    this.accountConnectionRepository = accountConnectionRepository;
  }

  /**
   * ユーザーIDが存在しない場合、新しいローカルユーザプロファイルを作成するために実行するコマンドをコネクションにマッピングします。
   * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
   * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
   * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
   * @param connectionSignUp a {@link ConnectionSignUp} object
   * @see #findUserIdsWithConnection(Connection)
   */
  public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
    this.connectionSignUp = connectionSignUp;
  }

  /**
   * Sets a table name prefix. This will be prefixed to all the table names before queries are executed. Defaults to "".
   * This is can be used to qualify the table name with a schema or to distinguish Spring Social tables from other application tables.
   * @param tablePrefix the tablePrefix to set
   */
  //  public void setTablePrefix(String tablePrefix) {
  //    this.tablePrefix = tablePrefix;
  //  }

  @Override
  public List<String> findUserIdsWithConnection(Connection<?> connection) {
    ConnectionKey key = connection.getKey();
    String providerId = key.getProviderId();
    String providerUserId = key.getProviderUserId();
    List<String> localUserIds = accountConnectionRepository.findUserIdsWithConnection(providerId, providerUserId);
    if (localUserIds.size() == 0 && connectionSignUp != null) {
      String newUserId = connectionSignUp.execute(connection);
      if (newUserId != null) {
        createConnectionRepository(newUserId).addConnection(connection);
        return Arrays.asList(newUserId);
      }
    }
    return localUserIds;
  }

  @Override
  public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
    final List<String> localUserIdsList = accountConnectionRepository.findUserIdsConnectedTo(providerId,
        providerUserIds);
    final Set<String> localUserIds = new HashSet<String>(localUserIdsList);
    return localUserIds;
  }

  @Override
  public Doma2ConnectionRepository createConnectionRepository(String userId) {
    if (userId == null) {
      throw new IllegalArgumentException("userId cannot be null");
    }
    //    return new Doma2ConnectionRepository(userId, jdbcTemplate, connectionFactoryLocator, textEncryptor,
    //        tablePrefix);
    // MEMO: tablePrefixは使用しない
    return new Doma2ConnectionRepository(userId, jdbcTemplate, connectionFactoryLocator, textEncryptor,
        accountConnectionRepository);
  }

}