package me.tutttuwi.springboot.management.security;

import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.MsgConst;
import me.tutttuwi.springboot.management.dao.AccountIndivRepository;
import me.tutttuwi.springboot.management.dao.AccountInfoRepository;
import me.tutttuwi.springboot.management.dto.SidebarModel;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.entity.AccountInfo;
import me.tutttuwi.springboot.management.util.MessageUtils;

@Slf4j
@Service
public class AccountUserDetailsService implements UserDetailsService {

  // TODO: 権限関連の処理実装
  @Autowired
  AccountInfoRepository accountRepository;

  @Autowired
  AccountIndivRepository accountIndivDao;

  /**
   * loadUserByUsername.
   *
   * @param id id
   * @param password password
   * @return UserDetails
   * @throws UsernameNotFoundException UsernameNotFoundException
   */
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String id, String password)
      throws UsernameNotFoundException {
    log.info("id + password is ... " + id + " " + password);
    AccountInfo account = Optional.ofNullable(accountRepository.selectByIdAndPw(id, password))
        .orElseThrow(() -> new UsernameNotFoundException("user not found."));
    return new AccountUserDetails(account, getAuthorities(account));
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

  private Collection<GrantedAuthority> getAuthorities(AccountInfo account) {
    // if (account.isAdmin()) {
    if (false) {
      return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
    } else {
      return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
  }

  /**
   * アカウント個人情報取得.
   *
   * @param userDetails AccountUserDetails
   * @return accountIndiv AccountIndiv
   */
  @Transactional
  public AccountIndiv getUserInfo(AccountUserDetails userDetails) throws Throwable {
    AccountIndiv accountIndiv =
        accountIndivDao.selectByAccountId(userDetails.getAccount().getAccountId());
    if (Objects.isNull(accountIndiv)) {
      log.error(MessageUtils.getMsg(MsgConst.ERROR_DATA_NOT_FOUND.KEY));
    }
    return accountIndiv;
  }

  /**
   * サイドバーメニュー取得.
   *
   * @return sidebarModel SidebarModel
   */
  @Transactional
  public SidebarModel getMenuInfo() throws Throwable {

    LineNumberReader br = new LineNumberReader(Files.newBufferedReader(
        Paths.get("src/main/resources/json/sidebar-menu.json"), Charset.forName("UTF-8")));
    JsonReader jsr = new JsonReader(br);
    // List<String> list = Files.readAllLines(Path.of("json/sidebar-menu.json"));
    // StringJoiner sj = new StringJoiner("");
    // list.stream().forEach(e -> sj.add(e));
    // String json = sj.toString();
    Gson gson = new Gson();
    SidebarModel sidebarModel = gson.fromJson(jsr, SidebarModel.class);
    // sidebarModel.setActive("ダッシュボード");
    // sidebarModel.setActive("ダッシュボード", "ダッシュボード１");
    return sidebarModel;
  }

}
