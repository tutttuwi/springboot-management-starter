const loginFn = function() {
  function init() {
    remember();
  }
	function submit() {
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
	function remember() {
    let id = localStorage.getItem("id");
    let isRemember = localStorage.getItem("isRemember");
    console.log("isRemember : " + isRemember);
    document.querySelector(".js-remember").checked = isRemember;
    if (id) {
      document.querySelector(".js-id").value = id;
    }
	}
	return { init,submit }
}
const login = loginFn();
login.init();
