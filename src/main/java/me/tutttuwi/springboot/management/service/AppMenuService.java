package me.tutttuwi.springboot.management.service;

import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.dao.AccountIndivRepository;
import me.tutttuwi.springboot.management.dto.SidebarModel;
import me.tutttuwi.springboot.management.entity.AccountIndiv;
import me.tutttuwi.springboot.management.security.AccountUserDetails;
import me.tutttuwi.springboot.management.util.MessageUtils;

@Slf4j
@Transactional
@Service
public class AppMenuService {

  @Autowired
  AccountIndivRepository accountIndivDao;

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
      log.error(MessageUtils.getMsg("ERROR.USER.NOT_FOUND"));
    }
    return accountIndiv;
  }

  /**
   * アカウント個人情報取得.
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
    sidebarModel.setActive("ダッシュボード", "ダッシュボード１");
    return sidebarModel;
  }
}
