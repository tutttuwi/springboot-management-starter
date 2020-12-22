package me.tutttuwi.springboot.management.util;

import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.data.domain.Pageable;

/**
 * Doma関連ユーティリティ
 * TODO: 要実装
 */
public class DomaUtils {

  /**
   * SearchOptionsを作成して返します。
   *
   * @return
   */
  public static SelectOptions createSelectOptions() {
    return SelectOptions.get();
  }

  /**
   * SearchOptionsを作成して返します。
   *
   * @param pageable
   * @return
   */
  public static SelectOptions createSelectOptions(Pageable pageable) {
    int page = pageable.getPageNumber();
    int perpage = pageable.getPageSize();
    return createSelectOptions(page, perpage);
  }

  /**
   * SearchOptionsを作成して返します。
   *
   * @param page
   * @param perpage
   * @return
   */
  public static SelectOptions createSelectOptions(int page, int perpage) {
    int offset = (page - 1) * perpage;
    return SelectOptions.get().offset(offset).limit(perpage);
  }
}
