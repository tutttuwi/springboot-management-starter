package me.tutttuwi.springboot.management.constant;

/**
 * 定数定義. <br/>
 * interfaceの定数なので暗黙的にpublic static final宣言
 */
public interface WebConst {

  /** ---- ViewComponents. ---- **/
  String MAV_CONST = "Const";

  String MAV_ERRORS = "errors";

  String MAV_PULLDOWN_OPTION = "PulldownOption";

  String MAV_CODE_CATEGORIES = "CodeCategories";

  // ---- URLs. ----
  String HOME_URL = "/";

  String ERROR_URL_ROOT = "/error";

  String ERROR_URL = "/error/500";

  String NOTFOUND_URL = "/error/404";

  String FORBIDDEN_URL = "/error/403";

  String LOGIN_URL = "/login";

  /** SpringFramework認証処理用. */
  String LOGIN_PROCESSING_URL = "/authenticate";

  /** SpringFramework認証成功処理用. */
  String LOGIN_SUCCESS_URL = "/loginSuccess";

  String PAGE = "page";
  String PAGE_URL = "/page";

  /**
   * ログイン認証失敗時のURL. (GETでerrorパラメータを渡す)
   */
  String LOGIN_FAILURE_URL = "/login?error";

  String LOGIN_TIMEOUT_URL = "/loginTimeout";

  String RESET_PASSWORD_URL = "/resetPassword";

  String CHANGE_PASSWORD_URL = "/changePassword";

  String LOGOUT_URL = "/logout";

  String LOGOUT_SUCCESS_URL = "/logoutSuccess";

  String WEBJARS_URL = "/webjars/**";

  String STATIC_RESOURCES_URL = "/static/**";

  String API_BASE_URL = "/api/**";

  // FUNCTION NAMEs
  String FNC_MENU = "F_MEMU";
  String FNC_DASHBOARD = "F_DASHBORD";
  String FNC_MAINTENANCE = "F_MAINTENANCE";

}
