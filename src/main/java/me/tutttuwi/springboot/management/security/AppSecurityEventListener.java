package me.tutttuwi.springboot.management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent;
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.session.CommonSession;

/**
 * <p>
 * 認証イベントの通知の流れ.
 * </p>
 * <ol>
 * <li>SpringSecurityの認証機能は、認証結果をAuthenticationEventPublisherに渡して認証イベントの通知依頼を行う</li>
 * <li>AuthenticationEventPublisherインターフェースのデフォルトの実装クラスは、認証結果に対応する
 * 認証イベントクラスのインスタンスを生成し、ApplicationEventPublisherに渡してイベントの通知依頼を行う</li>
 * <li>ApplicationEventPublisherインターフェースの実装クラスは、ApplicationListenerインターフェースの実装クラスにイベントを通知する</li>
 * <li>ApplicationListenerの実装クラスの１つであるApplicationListenerMethodAdaptorは、
 * `@org.springframework.context.event.EventLintener`が付与されているメソッドを呼び出してイベントを通知する</li>
 * </ol>
 * <span> `@EventLitener`を付与したメソッドを実装するだけで認証成功/失敗時の処理を実装できる仕組み </span>
 *
 * @author Tomo
 *
 */
@Slf4j
@Component
@SessionAttributes("scopedTarget.commonSession")

public class AppSecurityEventListener {

  @Autowired
  AccountUserDetailsService service;

  @Autowired
  CommonSession commonSession;

  @ModelAttribute
  CommonSession createSession() {
    return commonSession;
  }

  // ==============================
  // SUCCESS EVENT HANDLERS
  // ==============================

  /**
   * AuthenticationProviderによる認証処理が成功したことを通知する。 このイベントをハンドリングすると、クライアントが正しい認証情報を指定したことを検知することができるが、
   * 後続の認証処理でエラーになる可能性がある.
   *
   * @param event AuthenticationSuccessEvent
   */
  @EventListener
  public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
    //
  }

  /**
   * セッション固定攻撃対策の処理（セッションIDの変更処理）が成功したことを通知する。 このイベントをハンドリングすると、変更後のセッションIDを検知することができる.
   *
   * @param event SessionFixationProtectionEvent
   */
  @EventListener
  public void handleSessionFixationProtection(SessionFixationProtectionEvent event) {
    //
  }

  /**
   * 認証処理がすべて成功したことを通知する。 このイベントをハンドリングすると、画面遷移を除くすべての認証処理が成功したことを検知することができる.
   *
   * @param event InteractiveAuthenticationSuccessEvent
   */
  @EventListener
  public void handleInteractiveAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event) {
    // 認証が成功した場合、ログイン後のページで使用するための認証ユーザ情報をセッションに格納
    try {
      AccountUserDetails userDetails =
          (AccountUserDetails) event.getAuthentication().getPrincipal();
      AccountIndiv accountIndiv = service.getUserInfo(userDetails);
      commonSession.setUsername(accountIndiv.getLstName() + "　" + accountIndiv.getFstName());
      commonSession.setSidebarModel(service.getMenuInfo());
    } catch (Throwable tw) {
      tw.printStackTrace();
    }
  }

  // ==============================
  // FAILURE EVENT HANDLERS
  // ==============================

  @EventListener
  public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event) {
    log.info("BAD Credentials is detected. username : {}", event.getAuthentication().getName());
  }

  @EventListener
  public void handleDisabled(AuthenticationFailureDisabledEvent event) {
    log.info("Disabled user is detected. username : {}", event.getAuthentication().getName());
  }

  @EventListener
  public void handleLocked(AuthenticationFailureLockedEvent event) {
    log.info("Locked user is detected. username : {}", event.getAuthentication().getName());
  }

  @EventListener
  public void handleExpired(AuthenticationFailureExpiredEvent event) {
    log.info("Expired user is detected. username : {}", event.getAuthentication().getName());
  }

  @EventListener
  public void handleCredentialsExpired(AuthenticationFailureCredentialsExpiredEvent event) {
    log.info("CredentialsExpired is detected. username : {}", event.getAuthentication().getName());
  }

  @EventListener
  public void handleServiceException(AuthenticationFailureServiceExceptionEvent event) {
    log.info("ServiceException is detected. username : {}", event.getAuthentication().getName());
  }
}
