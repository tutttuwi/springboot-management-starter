package me.tutttuwi.springboot.management.social;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.dao.AccountConnectionRepository;
import me.tutttuwi.springboot.management.entity.AccountConnection;

@Slf4j
//@Service
public class Doma2ConnectionRepository implements ConnectionRepository {

  private final String userId;

  private final JdbcTemplate jdbcTemplate;

  private final ConnectionFactoryLocator connectionFactoryLocator;

  private final TextEncryptor textEncryptor;

  private final AccountConnectionRepository accountConnectionRepository;

  // MEMO: tablePrefixは使用しない
  //private final String tablePrefix;

  //  public Doma2ConnectionRepository(String userId, JdbcTemplate jdbcTemplate,
  //      ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor, String tablePrefix) {
  public Doma2ConnectionRepository(String userId, JdbcTemplate jdbcTemplate,
      ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor,
      AccountConnectionRepository accountConnectionRepository) {
    this.userId = userId;
    this.jdbcTemplate = jdbcTemplate;
    this.connectionFactoryLocator = connectionFactoryLocator;
    this.textEncryptor = textEncryptor;
    this.accountConnectionRepository = accountConnectionRepository;
    // MEMO: tablePrefixは使用しない
    //    this.tablePrefix = tablePrefix;
  }

  @Override
  public MultiValueMap<String, Connection<?>> findAllConnections() {
    List<AccountConnection> acList = accountConnectionRepository.findAllConnections(userId);
    List<Connection<?>> resultList = connectionMapper.map(acList);
    //    List<Connection<?>> resultList = jdbcTemplate
    //        .query(selectFromUserConnection() + " where userId = ? order by providerId, rank", connectionMapper, userId);
    MultiValueMap<String, Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
    Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
    for (String registeredProviderId : registeredProviderIds) {
      connections.put(registeredProviderId, Collections.<Connection<?>> emptyList());
    }
    for (Connection<?> connection : resultList) {
      String providerId = connection.getKey().getProviderId();
      if (connections.get(providerId).size() == 0) {
        connections.put(providerId, new LinkedList<Connection<?>>());
      }
      connections.add(providerId, connection);
    }
    return connections;
  }

  @Override
  public List<Connection<?>> findConnections(String providerId) {
    List<AccountConnection> acList = accountConnectionRepository.findConnections(userId, providerId);
    return connectionMapper.map(acList);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> List<Connection<A>> findConnections(Class<A> apiType) {
    List<?> connections = findConnections(getProviderId(apiType));
    return (List<Connection<A>>) connections;
  }

  @Override
  public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
    if (providerUsers == null || providerUsers.isEmpty()) {
      throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
    }
    List<AccountConnection> acList = accountConnectionRepository.findConnectionsToUsers(providerUsers, userId);
    List<Connection<?>> resultList = connectionMapper.map(acList);
    MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
    for (Connection<?> connection : resultList) {
      String providerId = connection.getKey().getProviderId();
      List<String> userIds = providerUsers.get(providerId);
      List<Connection<?>> connections = connectionsForUsers.get(providerId);
      if (connections == null) {
        connections = new ArrayList<Connection<?>>(userIds.size());
        for (int i = 0; i < userIds.size(); i++) {
          connections.add(null);
        }
        connectionsForUsers.put(providerId, connections);
      }
      String providerUserId = connection.getKey().getProviderUserId();
      int connectionIndex = userIds.indexOf(providerUserId);
      connections.set(connectionIndex, connection);
    }
    return connectionsForUsers;
  }

  @Override
  public Connection<?> getConnection(ConnectionKey connectionKey) {
    try {
      List<AccountConnection> acList = accountConnectionRepository.getConnection(userId, connectionKey.getProviderId(),
          connectionKey.getProviderUserId());
      List<Connection<?>> resultList = connectionMapper.map(acList);
      return resultList.get(0);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchConnectionException(connectionKey);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
    String providerId = getProviderId(apiType);
    return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
    String providerId = getProviderId(apiType);
    Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
    if (connection == null) {
      throw new NotConnectedException(providerId);
    }
    return connection;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
    String providerId = getProviderId(apiType);
    return (Connection<A>) findPrimaryConnection(providerId);
  }

  @Override
  @Transactional
  public void addConnection(Connection<?> connection) {
    try {
      ConnectionData data = connection.createData();
      int rank = accountConnectionRepository.selectAddConnection(userId, data.getProviderId());
      AccountConnection accountConnection = new AccountConnection();
      accountConnection.setUserId(userId);
      accountConnection.setProviderId(data.getProviderId());
      accountConnection.setProviderUserId(data.getProviderUserId());
      accountConnection.setRank(rank);
      accountConnection.setDisplayName(data.getDisplayName());
      accountConnection.setProfileUrl(data.getProfileUrl());
      accountConnection.setImageUrl(data.getImageUrl());
      accountConnection.setAccessToken(encrypt(data.getAccessToken()));
      accountConnection.setSecret(encrypt(data.getSecret()));
      accountConnection.setRefreshToken(encrypt(data.getRefreshToken()));
      accountConnection.setExpireTime(data.getExpireTime());
      accountConnectionRepository.insert(accountConnection);
    } catch (DuplicateKeyException e) {
      throw new DuplicateConnectionException(connection.getKey());
    }
  }

  @Override
  @Transactional
  public void updateConnection(Connection<?> connection) {
    ConnectionData data = connection.createData();
    AccountConnection accountConnection = new AccountConnection();
    accountConnection.setUserId(userId);
    accountConnection.setProviderId(data.getProviderId());
    accountConnection.setProviderUserId(data.getProviderUserId());
    accountConnection.setDisplayName(data.getDisplayName());
    accountConnection.setProfileUrl(data.getProfileUrl());
    accountConnection.setImageUrl(data.getImageUrl());
    accountConnection.setAccessToken(encrypt(data.getAccessToken()));
    accountConnection.setSecret(encrypt(data.getSecret()));
    accountConnection.setRefreshToken(encrypt(data.getRefreshToken()));
    accountConnection.setExpireTime(data.getExpireTime());
    accountConnectionRepository.updateConnection(accountConnection);
  }

  @Override
  @Transactional
  public void removeConnections(String providerId) {
    accountConnectionRepository.deleteRemoveConnections(userId, providerId);
  }

  @Override
  @Transactional
  public void removeConnection(ConnectionKey connectionKey) {
    accountConnectionRepository.deleteRemoveConnection(userId, connectionKey.getProviderId(),
        connectionKey.getProviderUserId());
  }

  // internal helpers

  /**
   * TODO: 説明記載
   * @return
   */
  //  private String selectFromUserConnection() {
  //    return "select userId, providerId, providerUserId, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime from "
  //        + tablePrefix + "UserConnection";
  //  }

  /**
   * TODO: 説明記載
   * @param providerId
   * @return
   */
  private Connection<?> findPrimaryConnection(String providerId) {
    List<AccountConnection> acList = accountConnectionRepository.findPrimaryConnection(userId, providerId);
    List<Connection<?>> connections = connectionMapper.map(acList);
    if (connections.size() > 0) {
      return connections.get(0);
    } else {
      return null;
    }
  }

  //  @SuppressWarnings("all")
  //  Function<Stream<AccountConnection>, List<Connection<?>>> doma2ConnectionMapper = stream -> {
  //    List<Connection<?>> connections = new LinkedList<Connection<?>>();
  //    return stream.map((AccountConnection ac) -> {
  //      //      try {
  //      ConnectionData connectionData = mapConnectionData(ac);
  //      ConnectionFactory<?> connectionFactory = connectionFactoryLocator
  //          .getConnectionFactory(connectionData.getProviderId());
  //      return connectionFactory.createConnection(connectionData);
  //      //      } catch (Exception ex) {
  //      //        ex.printStackTrace();
  //      //      }
  //    }).collect(Collectors.toList());
  //  };
  //
  //  private ConnectionData mapConnectionData(AccountConnection ac) {
  //    return new ConnectionData(ac.getProviderId(), ac.getProviderUserId(), ac.getDisplayName(),
  //        ac.getProfileUrl(), ac.getImageUrl(),
  //        decrypt(ac.getAccessToken()), decrypt(ac.getSecret()), decrypt(ac.getRefreshToken()),
  //        expireTime(ac.getExpireTime()));
  //    //    return new ConnectionData(rs.getString("providerId"), rs.getString("providerUserId"), rs.getString("displayName"),
  //    //        rs.getString("profileUrl"), rs.getString("imageUrl"),
  //    //        decrypt(rs.getString("accessToken")), decrypt(rs.getString("secret")), decrypt(rs.getString("refreshToken")),
  //    //        expireTime(rs.getLong("expireTime")));
  //  }
  //
  //  // Helper
  //
  //  private String decrypt(String encryptedText) {
  //    return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
  //  }
  //
  //  private Long expireTime(long expireTime) {
  //    return expireTime == 0 ? null : expireTime;
  //  }
  //
  //  private <A> String getProviderId(Class<A> apiType) {
  //    return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
  //  }
  //
  //  private String encrypt(String text) {
  //    return text != null ? textEncryptor.encrypt(text) : text;
  //  }

  // Native JDBC Connection

  private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

  // TODO: マッパーをDOMA用に用意する必要がある
  private final class ServiceProviderConnectionMapper {
    public List<Connection<?>> map(List<AccountConnection> acList) {
      List<Connection<?>> resultList = new LinkedList<Connection<?>>();
      try {
        for (AccountConnection ac : acList) {
          Connection<?> result = mapRow(ac, 0);
          resultList.add(result);
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      return resultList;
    }

    private Connection<?> mapRow(AccountConnection ac, int rowNum) throws SQLException {
      ConnectionData connectionData = mapConnectionData(ac);
      ConnectionFactory<?> connectionFactory = connectionFactoryLocator
          .getConnectionFactory(connectionData.getProviderId());
      return connectionFactory.createConnection(connectionData);
    }

    private ConnectionData mapConnectionData(AccountConnection ac) throws SQLException {
      return new ConnectionData(ac.getProviderId(), ac.getProviderUserId(), ac.getDisplayName(),
          ac.getProfileUrl(), ac.getImageUrl(),
          decrypt(ac.getAccessToken()), decrypt(ac.getSecret()), decrypt(ac.getRefreshToken()),
          expireTime(ac.getExpireTime()));
    }

    private String decrypt(String encryptedText) {
      return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
    }

    private Long expireTime(long expireTime) {
      return expireTime == 0 ? null : expireTime;
    }

  }

  private <A> String getProviderId(Class<A> apiType) {
    return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
  }

  private String encrypt(String text) {
    return text != null ? textEncryptor.encrypt(text) : text;
  }

}