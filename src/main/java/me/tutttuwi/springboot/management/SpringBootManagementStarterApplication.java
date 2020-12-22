package me.tutttuwi.springboot.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootApplication
public class SpringBootManagementStarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootManagementStarterApplication.class, args);
        System.out.println("TEST BCryptPasswordEncoder:" + new BCryptPasswordEncoder().encode("password"));
        System.out
                .println("TEST Pbkdf2PasswordEncoder:"
                        + new Pbkdf2PasswordEncoder("your-app-secret", 100, 256).encode("password"));
    }
}
