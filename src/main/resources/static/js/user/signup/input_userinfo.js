const input_userinfoFn = function() {
  function init() {
    checkTerms();
    event();
  }
  function checkTerms() {
    if(document.querySelector(".js-terms").checked){
      document.querySelector(".js-submit").classList.remove("disabled");
      document.querySelector(".js-submit").disabled = false;
    }
  }
  function event() {
    document.querySelector(".js-terms")
      .addEventListener("change",function(e){
        // console.log(e.target.checked);
        if(e.target.checked) {
          document.querySelector(".js-submit").classList.remove("disabled");
          document.querySelector(".js-submit").disabled = false;
        } else {
          document.querySelector(".js-submit").classList.add("disabled");
          document.querySelector(".js-submit").disabled = true;
        }
    });
  }
  return { init }
}

const input_userinfo = input_userinfoFn();
input_userinfo.init();
