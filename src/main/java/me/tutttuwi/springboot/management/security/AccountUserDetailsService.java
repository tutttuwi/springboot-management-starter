package me.tutttuwi.springboot.management.security;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;
import me.tutttuwi.springboot.management.entity.AccountInfo;

@Slf4j
@Service
public class AccountUserDetailsService implements UserDetailsService {

  // TODO: 権限関連の処理実装
  @Autowired
  AccountInfoRepository accountRepository;

  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String id, String password) throws UsernameNotFoundException {
    log.info("id + password is ... " + id + " " + password);
    AccountInfo account = Optional.ofNullable(accountRepository.selectByIdAndPw(id, password))
        .orElseThrow(() -> new UsernameNotFoundException("user not found."));
    return new AccountUserDetails(account, getAuthorities(account));
  };

  private Collection<GrantedAuthority> getAuthorities(AccountInfo account) {
    //    if (account.isAdmin()) {
    if (false) {
      return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
    } else {
      return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    log.info("input id is ... : " + id);
    Optional.of(id).orElseThrow(() -> new UsernameNotFoundException("id is empty"));
    AccountInfo ac = Optional.ofNullable(accountRepository.selectById(id))
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    if (!ac.isEnabled()) {
      throw new UsernameNotFoundException("User not found: " + id);
    }
    return new AccountUserDetails(ac, getAuthorities(ac));
  }
}
