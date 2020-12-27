package me.tutttuwi.springboot.management.social;

import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Service;

@Service
public class SocialSignupService {

  public SocialUser createUser(UserProfile profile) {
    // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    SocialUser user = new SocialUser(null, null, null);
    return user;
  }
}
