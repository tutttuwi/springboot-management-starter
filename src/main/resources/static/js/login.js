const Login = function() {
	this.initialize.apply(this, arguments);
}
Login.prototype = {
	initialize : function(args) {
	},
	_remember : function() {
		let id = localStorage.getItem("id");
		let isRemember = localStorage.getItem("isRemember");
		console.log("isRemember : " + isRemember);
		document.querySelector(".js-remember").checked = isRemember;
		if (id) {
			document.querySelector(".js-id").value = id;
		}
	},
	init : function() {
		this._remember();
	},
	submit : function() {
		let isRemember = document.querySelector(".js-remember").checked;
		let id = document.querySelector(".js-id").value;
		if (isRemember && id) {
			localStorage.setItem("id", id);
			localStorage.setItem("isRemember", true);
		} else {
			localStorage.removeItem("id");
			localStorage.removeItem("isRemember");
		}
	}
}
const login = new Login();
login.init();
