// AJAX - NOTIFICATION by yin
// 1. 準備參數：重複工作、執行次數、現在的通知列
var workerTimeOut;
var times = 1;
var now = [];

// 2. 載入頁面：執行doFirst()  如果使用者沒有登入 會抓到空直  如果有登入 會是一個Bean  並且執行notifyWorker這個方法;
//    else部分 是當使用者登出  要把計時器給關閉  不在繼續連資料庫

function doFirst() {
	var x = document.getElementById('checkLogin').innerHTML;
	if (x.trim().length != 0) {
		notifyWorker();
	} else {
		clearTimeout(workerTimeOut);
	}

}

//notifyWorker() 從server端抓取資料 傳回一個陣列  如果陣列是空的(now = []) 不執行updateList

function notifyWorker() {
	$.ajax({
		type : 'GET',
		url : '/CGLOHAS/_11_notification/TopNotify.do',
		success : function(obj) {
			// 第一次執行 與 接收進來的obj與用戶螢幕上的now不一樣，就要更新頁面
			if (times == 1 || obj != now) {
				updateList(obj);
			}
			times++;
			console.log(times);
		},
		complete : function() {
			//當完成後 設定每5秒呼叫notifyWorker這個方法
			workerTimeOut = setTimeout(notifyWorker, 5000);
		}
	});
}

var updateList = function updateList(obj) {

	now = obj;
	var unreadCount = 0;

//	var divider = '<a href="/CGLOHAS/_11_notification/notify.jsp">查詢所有通知</a>';

	// 先清空notify_info裡面的資料
	if (obj.length == 0) {
		$("#notify_info").html("");
//		$("#notify_info").append(divider);
	} else {
		$("#notify_info").html("");
		$
				.each(
						obj,
						function(index, element) {
							//在servlet部分有多增加一個bean 只有放入checkRead  其他都是空的
							//這是拿來檢查使用者有多少通知是未讀取的  加上return 不會繼續往下做
							if (element.receiverID == 0 && element.checkRead >= 1) {
								unreadCount = element.checkRead;
								return;
							}
							if (element.notifyType == 1) {
								$("#notify_info")
										.append(
												'<a style="color:#000; hover:text-decoration:none;" href="/CGLOHAS/_09_community/HolderDetail.jsp?JoinMessage=1&holderID='
														+ element.receiverID
														+ '">'
														+ '<div><img src="/CGLOHAS/_00_init/getImage?id='
														+ element.senderID
														+ '&type=userImg" height="20px" width="20px">'
														+ element.senderName
														+ ' has applied your activity -'
														+ element.realTime
														+ '</div> </a>');
							} else if (element.notifyType == 2) {
								$("#notify_info")
										.append(
												'<a style="color:#000; hover:text-decoration:none;" href="/CGLOHAS/_09_community/HolderDetail.jsp?JoinMessage=1&holderID='
														+ element.receiverID
														+ '">'
														+ '<div><img src="/CGLOHAS/_00_init/getImage?id='
														+ element.senderID
														+ '&type=userImg" height="20px" width="20px">'
														+ element.senderName
														+ ' has evaluated your activity -'
														+ element.realTime
														+ '</div> </a>');
							} else if (element.notifyType == 3) {
								$("#notify_info")
										.append(
												'<a style="color:#000; hover:text-decoration:none;"  href="/CGLOHAS/_09_community/HolderDetail.jsp?JoinMessage=1&holderID='
														+ element.receiverID
														+ '">'
														+ '<div><img src="/CGLOHAS/_00_init/getImage?id='
														+ element.senderID
														+ '&type=userImg" height="20px" width="20px">'
														+ element.senderName
														+ ' has leave message for your activity  -'
														+ element.realTime
														+ '</div> </a>');
							} else if (element.notifyType == 4) {
								$("#notify_info")
										.append(
												'<a style="color:#000; hover:text-decoration:none;"  href="/CGLOHAS/_09_community/HolderDetail.jsp?JoinMessage=1&holderID='
														+ element.receiverID
														+ '">'
														+ '<div><img src="/CGLOHAS/_00_init/getImage?id='
														+ element.senderID
														+ '&type=userImg" height="20px" width="20px">'
														+ element.senderName
														+ ' has replied your message  -'
														+ element.realTime
														+ '</div> </a>');
							}
						});
//		$("#notify_info").append(divider);
	}
	//每次更新 都會檢查未讀數量  也可以及時抓取新的未讀通知  讓使用者第一時間發現有留言(類似FB)
	if (unreadCount > 0) {
		$("#unreadCount").html(unreadCount);
	} else {
		$("#unreadCount").html("");
	}
	// alert(unreadCount);
}

//當使用者點擊信件  會將所有信件都改成已讀  不會讀了之後數字沒改變
//這裡暫時使用hover  必須改為使用點擊事件才會顯示信件  使用者點擊後 信件的部分才會顯示  並且同時呼叫changeToRead這個方法

function changeToRead() {
	$.ajax({
		type : 'GET',
		url : '/CGLOHAS/_11_notification/NotifyReaded.do'
	});
	$("#unreadCount").html("");
}

window.addEventListener('load', doFirst, false);
