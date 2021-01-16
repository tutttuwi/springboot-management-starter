/**
 * グローバル定数領域.<br/> このオブジェクト内に処理で使用する定数を定義 {@code Object.freeze()}でオブジェクト内プロパティも変更不可とする。
 */
const GLOBAL = {
	// 定数
	"API_CONTEXT_PATH" : "/springboot-admin-management",
	// セッションキー
	"SS_KEY": {
		"SIDE_MENU": "SIDE_MENU",
	},
	// サイドメニューJSONオブジェクト
	"MENU_JSON" : {
		"sidebarGroup" : [ {
			"key" : "dashboard",
			"name" : "ダッシュボード",
			"icon" : "nav-icon fas fa-tachometer-alt",
			"clazz" : "",
			"link" : "#",
			"active" : true,
			"sidebarItem" : [ {
				"key" : "dashboard1",
				"name" : "ダッシュボード１",
				"icon" : "far fa-circle nav-icon",
				"clazz" : "",
				"link" : "/springboot-admin-management/dashboard/dashboard1",
				"active" : true
			}, {
				"key" : "dashboard2",
				"name" : "ダッシュボード２",
				"icon" : "far fa-circle nav-icon",
				"clazz" : "",
				"link" : "/springboot-admin-management/dashboard/dashboard2",
				"active" : false
			} ]
		}, {
			"key" : "maintenance",
			"name" : "メンテナンス",
			"icon" : "nav-icon fas fa-fan",
			"clazz" : "",
			"link" : "#",
			"active" : false,
			"sidebarItem" : [ {
				"key" : "maintenance_userlist",
				"name" : "ユーザ一覧検索",
				"icon" : "far fa-circle nav-icon",
				"clazz" : "",
				"link" : "/springboot-admin-management/maintenance/userlist",
				"active" : false
			}, {
				"key" : "maintenance_userregedit",
				"name" : "ユーザ登録",
				"icon" : "far fa-circle nav-icon",
				"clazz" : "",
				"link" : "/springboot-admin-management/maintenance/userregedit",
				"active" : false
			} ]
		}, {
			"key" : "settings",
			"name" : "個人設定",
			"icon" : "nav-icon fas fa-user-edit",
			"clazz" : "",
			"link" : "#",
			"active" : false,
			"sidebarItem" : [ {
				"key" : "account_info",
				"name" : "アカウント情報",
				"icon" : "far fa-circle nav-icon",
				"clazz" : "",
				"link" : "/springboot-admin-management/account/edit",
				"active" : false
			}, {
				"key" : "password_change",
				"name" : "パスワード変更",
				"icon" : "far fa-circle nav-icon",
				"clazz" : "",
				"link" : "/springboot-admin-management/account/password",
				"active" : false
			} ]
		} ]
	}

}

window.GLOBAL = Object.freeze(GLOBAL);
