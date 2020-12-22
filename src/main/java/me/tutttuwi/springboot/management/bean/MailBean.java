package me.tutttuwi.springboot.management.bean;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private String from;
  private String to;
  private String subject;
  private String body;

}
