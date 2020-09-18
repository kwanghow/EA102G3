<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
var MyPoint = "/WebSocket/${memLogIn.member_Id}";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "wss://" + window.location.host + webCtx + MyPoint;

var self = '${memLogIn.member_Id}';
var webSocket;
console.log(self);
window.onunload = disconnect;

function connect() {
	webSocket = new WebSocket(endPointURL);

	webSocket.onopen = function(event) {
		console.log(self + "連接成功!");
	};

	webSocket.onmessage = function(event) {
		var jsonObj = JSON.parse(event.data);
		console.log(jsonObj);
		if("url" in jsonObj){
			swal({
				  title: '出貨通知',
				  type: 'info',				  
				  html:jsonObj.message + '，<a href="'+jsonObj.url+'">前往查看</a>',
				  showCloseButton: true,
			})
		}else{
			swal({
				  title: '出貨通知',
				  type: 'info',
				  html:jsonObj.message + '，<a href="'+jsonObj.url+'">前往查看</a>',
				  showCloseButton: true,
			})
		}
	};

	webSocket.onclose = function(event) {
		console.log("Disconnected!");
	};
}

function disconnect() {
	webSocket.close();
}
</script>