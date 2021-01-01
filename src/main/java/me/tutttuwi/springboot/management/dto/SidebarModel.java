package me.tutttuwi.springboot.management.dto;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class SidebarModel implements Serializable {

  @SerializedName("sidebar-group")
  @Expose
  public List<SidebarGroup> sidebarGroup = null;

  /**
   * サイドバーページアクティブ表示設定.<br/>
   * 指定されたグループキーをもとに、該当するグループとアイテムにアクティブを設定する アクティブ設定された項目がフロントエンドでアクティブ表示されることを期待する。
   *
   * @param groupKey String
   */
  public void setActive(String groupKey) {
    sidebarGroup.stream().forEach(sidebarGroup -> {
      if (groupKey.equals(sidebarGroup.key)) {
        sidebarGroup.setActive(true);
      }
    });

  }

  /**
   * サイドバーページアクティブ表示設定.<br/>
   * 指定されたグループキー、アイテムキーをもとに、該当するグループとアイテムにアクティブを設定する アクティブ設定された項目がフロントエンドでアクティブ表示されることを期待する。
   *
   * @param groupKey String
   * @param itemKey String
   */
  public void setActive(String groupKey, String itemKey) {
    resetActive();
    sidebarGroup.stream().forEach(sidebarGroup -> {
      if (groupKey.equals(sidebarGroup.key)) {
        sidebarGroup.setActive(true);
        sidebarGroup.getSidebarItem().forEach(sidebarItem -> {
          if (itemKey.equals(sidebarItem.key)) {
            sidebarItem.setActive(true);
          }
        });
      }
    });
  }

  /**
   * リセットアクティブ.<br/>
   * すべてのサイドバーアイテムのアクティブをfalseにする
   *
   */
  private void resetActive() {
    sidebarGroup.stream().forEach(sidebarGroup -> {
      sidebarGroup.setActive(false);
      sidebarGroup.getSidebarItem().forEach(sidebarItem -> {
        sidebarItem.setActive(false);
      });
    });
  }

}
