package me.tutttuwi.springboot.management.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

public class SocialUserConnectionSignUp implements ConnectionSignUp {

  private final SocialSignupService signupService;

  public SocialUserConnectionSignUp(SocialSignupService signupService) {
    this.signupService = signupService;
  }

  @Override
  public String execute(Connection<?> connection) {
    // プロバイダからユーザー情報取得
    UserProfile profile = connection.fetchUserProfile();

    // プロバイダから取得した情報を元にローカルユーザー情報作成
    //    SocialUser user = signupService.createUser(profile);

    // 自動生成されたローカルユーザーID返却
    // このユーザーIDがUserConnectionテーブルPKとして使用される
    //    return user.getUserId();
    return "user";
  }
}
