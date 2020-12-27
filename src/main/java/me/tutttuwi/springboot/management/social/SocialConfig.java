package me.tutttuwi.springboot.management.social;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;
import me.tutttuwi.springboot.management.dao.AccountConnectionRepository;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

  private final DataSource dataSource;
  private final SocialSignupService signupService;
  @Autowired
  AccountConnectionRepository accountConnectionRepository;

  public SocialConfig(DataSource dataSource, SocialSignupService signupService) {
    this.dataSource = dataSource;
    this.signupService = signupService;
  }

  @Override
  public UsersConnectionRepository getUsersConnectionRepository(
      ConnectionFactoryLocator connectionFactoryLocator) {
    Doma2UsersConnectionRepository repository = new Doma2UsersConnectionRepository(dataSource,
        connectionFactoryLocator, Encryptors.noOpText(), accountConnectionRepository);
    repository.setConnectionSignUp(new SocialUserConnectionSignUp(signupService));
    return repository;
  }

  // MEMO: このメソッドオーバーライドしたら動いた...
  @Override
  public UserIdSource getUserIdSource() {
    return new UserIdSource() {
      @Override
      public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
          return null;
        }
        return authentication.getName();
      }
    };
  }

  @Bean
  @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
  public Facebook facebook(ConnectionRepository repository) {
    Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
    return connection != null ? connection.getApi() : null;
  }

  @Override
  public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
    cfConfig.addConnectionFactory(new TwitterConnectionFactory("clientId", "clientSecret"));
    cfConfig.addConnectionFactory(
        new FacebookConnectionFactory("259477695116176", "5953fafbec10f70e209b5301398c5bae"));
    cfConfig.addConnectionFactory(new GoogleConnectionFactory("clientId", "clientSecret"));
    // cfConfig.addConnectionFactory(new TwitterConnectionFactory("consumerKey", "consumerSecret"));
    // cfConfig.addConnectionFactory(new LinkedInConnectionFactory("consumerKey",
    // "consumerSecret"));
  }

  @Bean
  public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,
      ConnectionRepository connectionRepository) {
    return new ConnectController(connectionFactoryLocator, connectionRepository);
  }

  @Bean
  public ProviderSignInController providerSignInController(
      ConnectionFactoryLocator connectionFactoryLocator,
      UsersConnectionRepository usersConnectionRepository) {
    return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository,
        new SocialSignInAdapter());
  }

}
