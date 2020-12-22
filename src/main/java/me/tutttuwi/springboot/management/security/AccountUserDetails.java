package me.tutttuwi.springboot.management.security;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import me.tutttuwi.springboot.management.entity.AccountInfo;

//  public class AccountUserDetails implements UserDetails { // インターフェースを実装するパターン
public class AccountUserDetails extends User { // Userクラスを継承するパターン

  private final AccountInfo account;

  public AccountUserDetails(AccountInfo account, Collection<GrantedAuthority> authorities) {
    super(account.getUserId(), account.getPassword(), account.isEnabled(), account.isAccountNonExpired(),
        account.isCredentialsNonExpired(), account.isAccountNonLocked(),
        authorities);
    this.account = account;
  }

  public AccountInfo getAccount() {
    return account;
  }

  public String getUserId() {
    if (Objects.nonNull(account)) {
      return account.getAccountId();
    }
    return null;
  }
}
