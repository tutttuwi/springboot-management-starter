package me.tutttuwi.springboot.management.controller.api.resource;

import java.util.List;

import me.tutttuwi.springboot.management.entity.Dto;

public interface Resource {

  List<? extends Dto> getData();

  void setData(List<? extends Dto> data);

  String getMessage();

  void setMessage(String message);
}
