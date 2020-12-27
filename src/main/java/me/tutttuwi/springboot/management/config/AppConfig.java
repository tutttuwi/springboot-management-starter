package me.tutttuwi.springboot.management.config;

import java.util.Locale;
import javax.inject.Inject;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordValidator;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.google.config.boot.GoogleAutoConfiguration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import me.tutttuwi.springboot.management.interceptor.CustomCallableProcessingInterceptor;
import me.tutttuwi.springboot.management.interceptor.LoggingFunctionNameInterceptor;
import me.tutttuwi.springboot.management.interceptor.LoggingInterceptor;
import me.tutttuwi.springboot.management.interceptor.RequestTrackingInterceptor;
import me.tutttuwi.springboot.management.thymleaf.ThymeleafConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import({ThymeleafConfig.class, // Thymeleafを使用することを明示
    GoogleAutoConfiguration.class})
@EnableSwagger2
public class AppConfig implements WebMvcConfigurer {

  @Value("${setting.cache:false}")
  public boolean CACHE;

  private @Inject UsersConnectionRepository usersConnectionRepository;

  private static final String[] EXCLUDE_SOCIAL_INTERCEPTOR =
      {"/favicon.ico", "/css/**", "/js/**", "/img/**", "/lib/**", "/font/**", "/auth/**"};

  // TODO: 多言語対応
  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("i18n/messages"); // クラスパス上に格納されているプロパティファイル（拡張子は除く）を指定する
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  /**
   * passwordValidator.
   *
   * @return
   */
  @Bean
  public PasswordValidator passwordValidator() {
    // PasswordValidator passwordValidator = new PasswordValidator(new LengthRule(8, 30),
    // new CharacterRule(EnglishCharacterData.UpperCase, 1),
    // new CharacterRule(EnglishCharacterData.LowerCase, 1),
    // new CharacterRule(EnglishCharacterData.Digit),
    // new CharacterRule(EnglishCharacterData.Special));
    PasswordValidator passwordValidator = new PasswordValidator(new LengthRule(8, 30),
        new CharacterRule(EnglishCharacterData.Alphabetical, 1),
        new CharacterRule(EnglishCharacterData.Digit, 1),
        // new IllegalCharacterRule("$".toCharArray()), // 指定文字を禁止するルール
        // new CharacterRule(EnglishCharacterData.Special), // Specialを含めなくてもOK 記号文字許容する
        new WhitespaceRule());
    return passwordValidator;
  }

  // @Bean
  // public MailSender mailSender() {
  // return new JavaMailSenderImpl();
  // }

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    configurer.registerCallableInterceptors(new CustomCallableProcessingInterceptor());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/resources/**");
    registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/resources/**").excludePathPatterns("/**/*.html");
    registry.addInterceptor(new LoggingFunctionNameInterceptor()).addPathPatterns("/**");
    registry.addInterceptor(new RequestTrackingInterceptor()).addPathPatterns("/**");
    // TODO: SESSION_IDをログ出力させたい
    // Spring Social
    // registry.addInterceptor(new SocialUserInterceptor(usersConnectionRepository))
    // .addPathPatterns("/**")
    // .excludePathPatterns(EXCLUDE_SOCIAL_INTERCEPTOR);
  }

  @Bean
  public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
    return new ResourceUrlEncodingFilter();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
        .resourceChain(CACHE)
        .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
  }

  /**
   * Dispatcherサーブレットをデフォルトサーブレットへ転送する機能を有効化.
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * デフォルトのロケール定義.
   *
   * @return resolver
   */
  public LocaleResolver localeResolver() {
    CookieLocaleResolver resolver = new CookieLocaleResolver();
    resolver.setCookieName("locale");
    resolver.setDefaultLocale(Locale.JAPANESE);
    return resolver;
  }
  // MEMO: ViewControllerの利用
  // WebMvcConfigurerのaddViewControllersを実装してルーティング設定
  // Viewを返却するだけであればこれでOK
  // @Override
  // public void addViewControllers(ViewControllerRegistry registry) {
  // registry.addViewController("/").setViewName("index");
  // }
}
