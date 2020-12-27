package me.tutttuwi.springboot.management.util;

import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class AppMailer {

  @Autowired
  MailSender sender;

  SimpleMailMessage msg;

  public AppMailer from(final String address) {
    msg.setFrom(address);
    return this;
  }

  public AppMailer to(final String address) {
    msg.setTo(address);
    return this;
  }

  public AppMailer subject(final String subject) {
    msg.setSubject(subject);
    return this;
  }

  public AppMailer body(final String message) {
    msg.setText(message);
    return this;
  }

  private SimpleMailMessage getMessage() {
    return this.msg;
  }

  /**
   * send.
   *
   * @param block {@code Consumer<AppMailer>}
   */
  public void send(final Consumer<AppMailer> block) {
    final AppMailer mailer = new AppMailer();
    mailer.setMsg(new SimpleMailMessage());
    block.accept(mailer);
    System.out.println("sending...");
    sender.send(mailer.getMessage());
  }
}
