//'use strict';

/**
 * common.js.<br/> Commonオブジェクト内に共通クラス定義を記述します。<br/> すべてのクラスはnewされることを期待しますので、<code>this.initialize.apply(this,arguments);</code>でコンストラクタを
 * 記述したあとに、具体的なコンストラクタ処理は<code>prototype</code>に実装します。 reference:
 * http://blog.livedoor.jp/aki_mana/archives/2383135.html
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
	 * ユーティリティクラス.
	 *
	 * @type {function}
	 */
	Common.Util = function() {
		this.initialize.apply(this, arguments);
	}
	Common.Util.prototype = {
		/**
		 * コンストラクタ.
		 *
		 * @param args
		 *          {object} 引数
		 */
		initialize : function(args) {
		},

		/**
		 * ゼロパディング関数.
		 *
		 * @param NUM
		 *          {Number} ゼロパディングしたい数値
		 * @param LEN
		 *          {Number} ゼロパディングの桁数
		 */
		zeroPadding : function(NUM, LEN) {
			return (Array(LEN).join('0') + NUM).slice(-LEN);
		}
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

		showLoading : function() {
			let el = document.querySelector("#"+this.loadingId) || document.querySelector("body");
			let divEl = document.createElement("div");
			let iEl = document.createElement("i");
			divEl.id = 'apiloading';
			divEl.classList.add("d-flex", "align-items-center", "justify-content-center", "w-100", "h-100", "position-absolute");
			divEl.style.display = 'none';
			divEl.style.top = '0px';
			divEl.style.left = '0px';
			divEl.style.background = 'rgb(200,200,200,0.5)';
			iEl.classList.add("fas","fa-sync","fa-spin","fa-5x");
			divEl.appendChild(iEl);
			el.appendChild(divEl);
			$('#apiloading').fadeIn(500);
		},
		hideLoading : function() {
			$('#apiloading').fadeOut(500,function(){
				$(this).remove();
			});
		},

		/**
		 * 【GET】APIリクエスト関数(同期処理).
		 *
		 * @param req
		 *          {Object} リクエスト設定オブジェクト
		 */
		doGet : function(reqUserSetting) {
			// LOADING START
			this.showLoading();
			let ret = {};
			let reqDefault = {
					url: undefined,
					type: 'GET',
					dataType: 'json',
					data: undefined,
					timeout: 10000,
					async: false
			}
			let req = Object.assign(reqDefault, reqUserSetting);
			$.ajax(req).done(function(data, textStatus, jqXHR) {
				ret.data = data;
				ret.textStatus = textStatus;
				if (jqXHR.status === 200) {
					ret.isOk = true;
				} else {
					ret.isOk = false;
				}
			}).fail(function(jqXHR, textStatus, errorThrown){
				ret.data = data;
				ret.textStatus = textStatus;
				ret.isOk = false;
				ret.errorThrown = errorThrown;
			}).always(function(){
				// LOADING END
				this.hideLoading();
			});
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
