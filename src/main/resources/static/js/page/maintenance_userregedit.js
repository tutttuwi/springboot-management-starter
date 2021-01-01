const UserRegEdit = function() {
	this.initialize.apply(this, arguments);
}
UserRegEdit.prototype = {
	initialize : function(args) {
	},
	init : function() {
	},
}
const userRegEdit = new UserRegEdit();
userRegEdit.init();

$(function() {
});
$("#searchForm").submit(function(event) {
	event.preventDefault();
	userList.search();
});
