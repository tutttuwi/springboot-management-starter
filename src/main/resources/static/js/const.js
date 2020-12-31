/**
 * グローバル定数領域.<br/>
 * このオブジェクト内に処理で使用する定数を定義
 * {@code Object.freeze()}でオブジェクト内プロパティも変更不可とする。
 */
const GLOBAL = {
	"API_CONTEXT_PATH" : "/springboot-admin-management"
}

window.GLOBAL = Object.freeze(GLOBAL);
