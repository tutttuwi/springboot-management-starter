package me.tutttuwi.springboot.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootManagementStarterApplication {

  /**
   * SpringBoot Main Method.
   *
   * @param args starter argumants
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringBootManagementStarterApplication.class, args);
    // System.out.println("TEST BCryptPasswordEncoder:" + new
    // BCryptPasswordEncoder().encode("password"));
    // System.out
    // .println("TEST Pbkdf2PasswordEncoder:"
    // + new Pbkdf2PasswordEncoder("your-app-secret", 100, 256).encode("password"));
  }
}
