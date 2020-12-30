package me.tutttuwi.springboot.management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.WebConst;
import me.tutttuwi.springboot.management.social.SocialUserDetailsServiceImpl;

/**
 * セキュリティコンフィグクラス.<br/>
 * <p>
 * SpringSecurityが提供しているコンフィギュレーションクラスがインポートされ、SpringSecurityを利用するために必要となるコンポーネントのBean定義が自動で行われる仕組み
 * </p>
 * <p>
 * <code>@EnableWebSecurity</code> <br/>
 * メソッドに対して認可処理を行うAOPを有効化SpringSecurityが提供している コンフィギュレーションクラスがインポートされ、SpringSecurityを利用するために必要となる
 * コンポーネントのBean定義が自動で行われる仕組み
 * </p>
 * <p>
 * <code>@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)</code> <br/>
 * prePostEnabled属性にtrueを指定すると、Expressionを使用してアクセスポリシーを定義することができるアノテーションが有効になる
 * </p>
 * <p>
 * <code>WebSecurityConfigurerAdapter</code> <br/>
 * 継承すると、デフォルトで適用されるBean定義を簡単にカスタマイズすることができる
 * </p>
 *
 * @author Tomo
 *
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${settings.password.secret}")
  private String secret;

  @Value("${settings.password.iterations}")
  private int iterations;

  @Value("${settings.password.hashWidth}")
  private int hashWidth;

  private static final String[] STATIC_PERMIT_PATH =
      {"/", "/login", "/user/**", "/auth/**", "/error/**", "^/actuator.*" // Actuatorの定義追加
      };
  private static final String[] STATIC_RESOURCES =
      {"/**/favicon.ico", "/css/**", "/js/**", "/img/**", "/lib/**", "/font/**", "/webjars/**"};

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  /**
   * パスワードエンコード指定.<br/>
   * ここで指定したルールに従ってパスワードエンコード処理が行われる
   *
   * @return
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // return new NoOpPasswordEncoder() // Depricated Now.
    // return new StandardPasswordEncoder(); // Depricated Now.
    log.debug("***** secret : " + secret);
    log.debug("***** iterations : " + iterations);
    log.debug("***** hashWidth : " + hashWidth);
    // 設定可能な反復回数とランダムな8バイトランダムソルト値を使用してPBKDF2を使用するPasswordEncoderの実装
    return new Pbkdf2PasswordEncoder(secret, iterations, hashWidth);
  }

  @Override
  public void configure(WebSecurity web) {
    // セキュリティ対策が不要なリソースがある場合、SpringSecurityの処理を適用しないようにする
    web.ignoring().antMatchers(STATIC_RESOURCES);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    final HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    http.authorizeRequests().antMatchers(STATIC_PERMIT_PATH).permitAll()
        // ロール認証設定
        // .antMatchers("/admin/accounts/***").hasRole("ACCOUNT_MANAGER")
        // .antMatchers("/admin/***").hasRole("ADMIN")
        // .antMatchers("/admin/***").access("hasIpAddress('127.0.0.1') and
        // hasRole('CONFIGURATION_MANAGER')")
        // .antMatchers("/admin/***").hasRole("ADMIN")
        .anyRequest().authenticated().and()
        // SocialLogin処理(SocialAuthenticationFilterを適用するための定義)
        .apply(new SpringSocialConfigurer()).postLoginUrl("/menu")
        .connectionAddedRedirectUrl("/menu").signupUrl("/user/signup").defaultFailureUrl("/error")
        .and()
        // Login処理
        // 認証を必要とするURLに遷移した場合、このURLにリダイレクトしてログインフォームを表示する仕組み
        .formLogin().loginPage(WebConst.LOGIN_URL)
        // .loginProcessingUrl(WebConst.LOGIN_PROCESSING_URL)
        // .successForwardUrl(WebConst.LOGIN_SUCCESS_URL)
        .failureUrl(WebConst.LOGIN_FAILURE_URL) // 認証失敗時ログイン画面に戻す
        .defaultSuccessUrl("/menu") // 認証成功時のデフォルトアクセスはルート。カスタマイズするために記載
        // ログイン画面から渡されるパラメータ名を指定
        .usernameParameter("id").passwordParameter("password").and().exceptionHandling()
        .accessDeniedPage("/error").and()
        // Logout処理 permitAll():すべてのユーザーに対してログインフォームへのアクセス件を付与する
        .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))
        .deleteCookies("JSESSIONID").invalidateHttpSession(false).logoutSuccessUrl("/login")
        .permitAll();
    // CSRF対策
    // Ajaxでcsrfチェックをする場合、Cookieに詰めて、クライアントサイドで取り出して送り返す形にするため、Cookieに詰める処理を定義
    // .and().csrf().csrfTokenRepository(repository).and()
    // .addFilterAfter(new ChgCsrfTokenFilter(repository), CsrfFilter.class);

    // 無効なセッションを検知した際の遷移先を指定
    http.sessionManagement().invalidSessionUrl("/error/401");

    // セキュリティヘッダー出力機能の無効化(Spring4.0以降デフォルトは有効になっている)
    // http.headers().disable();
    http.headers().defaultsDisabled().cacheControl().and().frameOptions().and().contentTypeOptions()
        .and().xssProtection().and().httpStrictTransportSecurity();

  }

  // Security Filter Chain に登録する Filter は Bean 定義しない
  /**
   * 認証処理前フィルター.
   *
   * @return AbstractPreAuthenticatedProcessingFilter
   * @throws Exception exception
   */
  public AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter()
      throws Exception {
    final AppPreAuthenticatedFilter filter = new AppPreAuthenticatedFilter();
    filter.setAuthenticationManager(this.authenticationManager());
    return filter;
  }

  /**
   * SocialUserDetailsServiceのBean定義します.<br/>
   * 上述のSpringSocialConfigurer内Configureメソッド内でDIされます。
   */
  @Bean
  public SocialUserDetailsService socialUsersDetailService() {
    return new SocialUserDetailsServiceImpl(userDetailsService());
  }
}
