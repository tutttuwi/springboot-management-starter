package me.tutttuwi.springboot.management.dto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SidebarGroup {

  @SerializedName("name")
  @Expose
  public String name;
  @SerializedName("icon")
  @Expose
  public String icon;
  @SerializedName("clazz")
  @Expose
  public String clazz;
  @SerializedName("link")
  @Expose
  public String link;
  @SerializedName("active")
  @Expose
  public Boolean active;
  @SerializedName("sidebar-item")
  @Expose
  public List<SidebarItem> sidebarItem = null;

}
