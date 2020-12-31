//'use strict';

/**
 * common.js.<br/> Commonオブジェクト内に共通クラス定義を記述します。<br/> すべてのクラスはnewされることを期待しますので、<code>this.initialize.apply(this,arguments);</code>でコンストラクタを
 * 記述したあとに、具体的なコンストラクタ処理は<code>prototype</code>に実装します。 reference:
 * http://blog.livedoor.jp/aki_mana/archives/2383135.html
 *
 * <p>
 * 実装規約
 * <p>
 * <ul>
 * <li>Common内に実装する状態を持つクラスはNewして使用すること。Utilなど静的な関数のみを保持するものはNewせずに使用させる。</li>
 * <li>クラス内のprivateメソッドは先頭に_(アンダースコア)を付与すること。利用側で制限を強制できないため。</li>
 * <li>第三者に意図が伝わるようなJSDocコメントを付与すること</li>
 * </ul>
 *
 */

(function(window, Common) {
	window.Common = Common;
	/**
	 * グローバル変数領域用オブジェクト.
	 *
	 * @type {Object}
	 */
	let global = {};

	/**
	 * ユーティリティオブジェクト.
	 *
	 * @type {Object}
	 */
	Common.Util = {
		/**
		 * ゼロパディング関数.
		 *
		 * @param NUM
		 *          [{Number}|{string}] ゼロパディングしたい数値・文字列
		 * @param LEN
		 *          {Number} ゼロパディングの桁数
		 */
		zeroPadding : function(NUM, LEN) {
			return (Array(LEN).join('0') + NUM).slice(-LEN);
		},

		/**
		 * トーストオブジェクト.
		 */
		toast : Swal.mixin({
			toast : true,
			position : 'top-end',
			showConfirmButton : false,
			timer : 3000
		})
	}

	/**
	 * リクエストクラス.
	 *
	 * @type {function}
	 */
	Common.Request = function() {
		this.initialize.apply(this, arguments);
	}
	Common.Request.prototype = {

		/**
		 * コンストラクタ.
		 *
		 * @param args
		 *          {object} 引数
		 */
		initialize : function(loadingId) {
			this.loadingId = loadingId;
		},

		_showLoading : function() {
			let el = document.querySelector("#" + this.loadingId)
					|| document.querySelector("body");
			let divEl = document.createElement("div");
			let iEl = document.createElement("i");
			divEl.id = 'apiloading';
			divEl.classList.add("d-flex", "align-items-center",
					"justify-content-center", "w-100", "h-100", "position-absolute");
			divEl.style.display = 'none';
			divEl.style.top = '0px';
			divEl.style.left = '0px';
			divEl.style.background = 'rgb(200,200,200,0.5)';
			iEl.classList.add("fas", "fa-sync", "fa-spin", "fa-5x");
			divEl.appendChild(iEl);
			el.appendChild(divEl);
			$('#apiloading').fadeIn(500);
		},
		_hideLoading : function() {
			$('#apiloading').fadeOut(500, function() {
				$(this).remove();
			});
		},

		/**
		 * 共通パラメータ生成関数.<br/> 共通パラメータをここで定義してリクエストデータ以外を共通で設定する。
		 * 必要に応じてセッション領域などから共通パラメータを取得して設定する。
		 */
		_createParamData : function(data) {
			const header = {
			// deviceType: 'xxx',
			// browserType: 'xxx',
			// system: 'xxx',
			}
			data = Object.assign(header, data);
			return data;
		},

		/**
		 * パラメータチェック処理.<br/> リクエストするパラメータとして許容しない型が設定されていた場合、エラーをスローします。
		 */
		_checkParam : function(data) {
			Object.keys(data).forEach(key=>{
				const acceptTypes = ["string", "number"];
			  if(!~acceptTypes.indexOf(typeof data[key])){
			  	throw new Error("パラメータエラー！" + "プロパティ：" + key + " 値：" + data[key]);
			  }
			});
		},


		/**
		 * 【GET】APIリクエスト関数(同期処理).
		 *
		 * @param req
		 *          {Object} リクエスト設定オブジェクト
		 */
		doGet : function(reqUserSetting) {
			const self = this;
			// LOADING START
			self._showLoading();
			let ret = {};
			try {
				// ユーザ設定データに共通設定適用
				reqUserSetting.url = GLOBAL.API_CONTEXT_PATH + reqUserSetting.url;
				reqUserSetting.data = self._createParamData(reqUserSetting.data);
				const reqDefault = {
					url : undefined,
					type : 'GET',
					dataType : 'json',
					data : undefined,
					timeout : 10000,
					async : false
				}
				let req = Object.assign(reqDefault, reqUserSetting);
				console.log(req);
				// API呼び出し
				$.ajax(req).done(function(data, textStatus, jqXHR) {
					ret.data = data;
					ret.textStatus = textStatus;
					if (jqXHR.status === 200) {
						ret.isOk = true;
					} else {
						ret.isOk = false;
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					ret.textStatus = textStatus;
					ret.isOk = false;
					ret.errorThrown = errorThrown;
				}).always(function() {
					// LOADING END
				});
			} catch(err) {
				console.error(err);
			} finally {
				self._hideLoading();
			}
			return ret;
		},

		/**
		 * 【GET】APIリクエスト関数(非同期処理).
		 *
		 * @param req
		 *          {Object} リクエスト設定オブジェクト
		 */
		doGetAsync : function(req, callback) {

		}
	}

})(window, window.Common || {});
