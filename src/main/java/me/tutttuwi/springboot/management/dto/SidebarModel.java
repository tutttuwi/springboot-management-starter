package me.tutttuwi.springboot.management.dto;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SidebarModel {

  @SerializedName("sidebar-group")
  @Expose
  public List<SidebarGroup> sidebarGroup = null;

  /**
   * setActive.
   *
   * @param groupName String
   */
  public void setActive(String groupName) {
    sidebarGroup.stream().forEach(sidebarGroup -> {
      if (groupName.equals(sidebarGroup.name)) {
        sidebarGroup.setActive(true);
      } else {
        sidebarGroup.setActive(false);
      }
    });
  }

  /**
   * setActive.
   *
   * @param groupName String
   * @param itemName String
   */
  public void setActive(String groupName, String itemName) {
    sidebarGroup.stream().forEach(sidebarGroup -> {
      if (groupName.equals(sidebarGroup.name)) {
        sidebarGroup.setActive(true);
        sidebarGroup.getSidebarItem().forEach(sidebarItem -> {
          if (itemName.equals(sidebarItem.name)) {
            sidebarItem.setActive(true);
          } else {
            sidebarItem.setActive(false);
          }
        });
      } else {
        sidebarGroup.setActive(false);
      }
    });
  }

}
