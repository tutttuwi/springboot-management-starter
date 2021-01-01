const UserList = function() {
	this.initialize.apply(this, arguments);
}
UserList.prototype = {
	initialize : function(args) {
	},
	init : function() {
	},
	search : function() {
		const req = new Common.Request();
		const json = req.doGet({
			url : "/api/user/list",
			data : {
				"lstNm" : $("#lstNm").val(),
				"fstNm" : $("#fstNm").val(),
				"userId" : $("#userId").val(),
				"email" : $("#emailaddr").val(),
			}
		});
		console.log(json);
		if (json && json.data && json.data.length > 0) {
			this._drawDataTable(json);
		} else if (json && json.isOk) {
			Common.Util.toast.fire({
				icon : 'warning',
				title : '該当するデータが見つかりませんでした。'
			});
		} else {
			Common.Util.toast.fire({
				icon : 'error',
				title : 'データ取得中にエラーが発生しました。'
			});
		}
	},
	_drawDataTable : function(json) {
		if (!$("#searchResult").hasClass("d-none")) {
			$("#userListTable").DataTable().state.clear();
			$("#userListTable").DataTable().destroy();
		}

		$("#theadRender").html($("#theadTemplate").render({
			thList : [ {
				label : ""
			}, {
				label : "名前(姓)"
			}, {
				label : "名前(名)"
			}, {
				label : "メール"
			}, {
				label : "作成日時"
			}, {
				label : "更新日時"
			}, ]
		}));
		let dataRenderJson = {};
		let trList = [];
		json.data
				.forEach(function(user) {
					let tr = {};
					tr.tdList = [];
					let editBtn = {};
					let lstName = {};
					let fstName = {};
					let email = {};
					let createDt = {};
					let updateDt = {};
					editBtn.tdValue = "<button class='btn'><i class='fas fa-edit'></i></button>";
					lstName.tdValue = user.lstName;
					fstName.tdValue = user.fstName;
					email.tdValue = user.emailAddr;
					createDt.tdValue = user.createDt;
					updateDt.tdValue = user.updateDt;

					tr.tdList.push(editBtn);
					tr.tdList.push(lstName);
					tr.tdList.push(fstName);
					tr.tdList.push(email);
					tr.tdList.push(createDt);
					tr.tdList.push(updateDt);

					trList.push(tr);
				});
		dataRenderJson.trList = trList;
		$("#tbodyRender").html($("#tbodyTemplate").render(dataRenderJson));

		userList.initDataTable();
		$("#searchResult").removeClass("d-none");
	},
	initDataTable : function() {
		$("#userListTable").DataTable({
			"responsive" : true,
			"lengthChange" : false,
			"autoWidth" : false,
			"buttons" : [ {
				extend : 'copy',
				text : 'コピー'
			}, {
				extend : 'csv',
				text : 'CSV出力'
			}, {
				extend : 'print',
				text : '印刷'
			}, {
				extend : 'colvis',
				text : '表示カラム'
			} ],
			"language" : {
				"processing" : "処理中・・・",
				"info" : " _TOTAL_ 件中 _START_ 件から _END_ 件まで表示",
				"emptyTable" : "該当するデータがありませんでした。",
				"zeroRecords" : "絞り込んだ結果該当するデータがありませんでした。",
				"search" : "検索",
				"paginate" : {
					"first" : "先頭",
					"previous" : "前",
					"next" : "次",
					"last" : "最後"
				}
			// 日本語化対応 ここで取得したJSON通りに設定が上書きされる。個別設定したければ別途記述していく
			// "url" : "//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Japanese.json"
			},
		// dom: 'B<"clear">lfrtip',
		// buttons: [ 'copy', 'csv', 'excel' ]
		// pdf,excelは日本語文字がもじバケる,CSVもBom付きでダウンロードできない
		// "buttons" : [ "copy", "csv", "excel", "pdf", "print", "colvis" ]
		}).buttons().container().appendTo('#userListTable_wrapper .col-md-6:eq(0)');
	},
}
const userList = new UserList();
userList.init();

$(function() {
});
$("#searchForm").submit(function(event) {
	event.preventDefault();
	userList.search();
});
