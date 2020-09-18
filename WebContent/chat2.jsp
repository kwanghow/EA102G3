<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- <link rel="stylesheet" href="css/friendchat.css" type="text/css" />

 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/article/chatCss/chat.css" type="text/css" />

<link href="https://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">



<style type="text/css">

</style>
<title>聊天室2</title>
</head>
<body onload="connect();" onunload="disconnect();">
	<div id="messagesArea" class="panel message-area" ></div>
	<div class="panel input-area">
	</div>
	
<div class="container">
    <div class="col-md-12 col-lg-6">
        <div class="panel">
        	<!--Heading-->
    		<div class="panel-heading">
    			<div class="panel-control">
    				<div class="btn-group">
    					<button class="btn btn-default" type="button" data-toggle="collapse" data-target="#demo-chat-body"><i class="fa fa-chevron-down"></i></button>
    					<button type="button" id="fbtn" class="btn btn-default" data-toggle="dropdown" <c:if test="${empty memId}">style="display:none"</c:if>><i class="fa fa-gear"></i></button>
    					  						
    					<ul id="fmenu" class="dropdown-menu dropdown-menu-right" >
    						<li class="divider"></li>
    						<li class=""><a id="demo-connect-chat" href="#" class="disabled-link" data-target="#demo-chat-body" onclick="connect();">Connect</a></li>
    						<li class=""><a id="demo-disconnect-chat" href="#" data-target="#demo-chat-body" onclick="disconnect();">Disconect</a></li>
    						<li class="divider"></li>

    						 
    					</ul>
    							
    				</div>
    			</div>
    			<h3 class="panel-title" id="statusOutput">${reciverName}</h3>
    		</div>
    
    		<!--Widget body-->
    		<div id="demo-chat-body" class="collapse in">
    			<div class="nano has-scrollbar" style="height:380px">
    				<div class="nano-content pad-all" tabindex="0" style="right: -17px;">
    					<ul id="chatList" class="list-unstyled media-block">
    						
    					</ul>
    				</div>
    			<div class="nano-pane"><div class="nano-slider" style="height: 141px; transform: translate(0px, 0px);"></div></div></div>
    
    			<!--Widget footer-->
    			<div class="panel-footer">
    				<div class="row">
    					<div class="col-xs-9">
    						<input id="message" type="text" placeholder="Enter your text" onkeydown="if (event.keyCode == 13) sendMessage();" class="form-control chat-input">
    						
    					</div>
    					<div class="col-xs-3">
    						<button id="sendMessage" class="btn btn-primary btn-block" type="button" onclick="sendMessage();">Send</button>
    						
    					</div>
    				</div>
    			</div>
    		</div>
    	</div>
    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="https://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script>
	var MyPoint = "/FriendWS/${userName}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "wss://" + window.location.host + webCtx + MyPoint;

	var statusOutput = document.getElementById("statusOutput");
	var messagesArea = document.getElementById("messagesArea");
	var messagesPane = document.getElementById("messagesPane");
	var self = '${userName}';
	var selfName = '${selfName}';

	var reciver = '${reciver}';
	var reciverName = '${reciverName}';
	var chatList = $("#chatList");
	var nameMap = new Map();
	var typeMap = new Map();

	
	var webSocket;

	(function(){
		nameMap.set(reciver,reciverName);	
		typeMap.set("M","memberPic");
		typeMap.set("C","coachPic");

	})();



	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			document.getElementById('sendMessage').disabled = false;
			
			var jsonObj = {
					"type" : "getlist",
					"sender" : self,
					"reciver": reciverName,
					"message" : "",
					"reciverName" :reciverName
				};
			webSocket.send(JSON.stringify(jsonObj));
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			console.log("jsonObj",jsonObj);

			if ("open" === jsonObj.type) {
 
			}
			else if(jsonObj.type == 'listRe'){
				
					var chatlistone = JSON.parse(jsonObj.receiver);
					var chatreceiver = JSON.parse(jsonObj.receiverName);
					var fmenu = document.getElementById("fmenu");
					for(i = 0;i < chatlistone.length;i++ ){
						if(document.getElementById(chatreceiver[i]) == null){
							fmenu.innerHTML = fmenu.innerHTML +
    						    '<li class=""><a id="'+chatlistone[i]+'" class="friendName" href="#" data-target="#demo-chat-body"  >'+chatreceiver[i]+'</a></li>';
							nameMap.set(chatlistone[i],chatreceiver[i]);	

						}
					}
					addListener();

		   }
			else if ("history" === jsonObj.type) {
			
				messagesArea.innerHTML = '';
				chatList.empty();

				var ul = document.createElement('ul');
				ul.id = "area";
				
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				console.log("messages",messages);

				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var li = document.createElement('li');
					console.log("historyData",historyData);
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					
					var picUrl = 'https://bootdey.com/img/Content/avatar/avatar2.png';
					if(historyData.sender.substring(0,1)=='M' || historyData.sender.substring(0,1)=='C'){
						picUrl = "<%=request.getContextPath()%>/front-end/ShowPhotos?img_no="+historyData.sender+"&type="+typeMap.get(historyData.sender.substring(0,1));

					}

					
					if(historyData.sender === self){
						
						var content_other = '    <li class="mar-btm">' +
						'    <div class="media-right">' +
						'    <img src="'+picUrl+'" class="img-circle img-sm" alt="Profile Picture">' +
						'    </div>' +
						'    <div class="media-body pad-hor speech-right">' +
						'    <div class="speech">' +
						'    <a href="#" class="media-heading">'+historyData.sender+'</a>' +
						'    <p>'+historyData.message +
						'    </p>' +
						'    </div>' +
						'    </div>' +
						'    </li>';
						
						chatList.append(content);
					}else{
						var content = '    <li class="mar-btm">' +
						'    <div class="media-left">' +
						'    <img src="'+picUrl+'" class="img-circle img-sm" alt="Profile Picture">' +
						'    </div>' +
						'    <div class="media-body pad-hor">' +
						'    <div class="speech">' +
						'    <a href="#" class="media-heading">'+nameMap.get(historyData.sender)+'</a>' +
						'    <p>'+historyData.message+
						'    </p>' +
						'    </div>' +
						'    </div>' +
						'    </li>';		
						chatList.append(content);
					}
					
					//li.innerHTML = showMsg;
					//ul.appendChild(li);
				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {

				var picUrl = 'https://bootdey.com/img/Content/avatar/avatar2.png';
				if(jsonObj.sender.substring(0,1)=='M' || jsonObj.sender.substring(0,1)=='C'){
					picUrl = "<%=request.getContextPath()%>/front-end/ShowPhotos?img_no="+jsonObj.sender+"&type="+typeMap.get(jsonObj.sender.substring(0,1));

				}
				
				if(jsonObj.sender === self){
					
					
					
					var content = '    <li class="mar-btm">' +
					'    <div class="media-right">' +
					'    <img src="'+picUrl+'" class="img-circle img-sm" alt="Profile Picture">' +
					'    </div>' +
					'    <div class="media-body pad-hor speech-right">' +
					'    <div class="speech">' +
					'    <a href="#" class="media-heading"> '+selfName+' </a>' +
					'    <p>'+jsonObj.message+
					'    </p>' +
					'    </div>' +
					'    </div>' +
					'    </li>';
					chatList.append(content);

					
				}else{
					
					if(document.getElementById(jsonObj.sender) == null){
					    var menu = $('#fmenu');
						menu.append('<li class=""><a id="'+jsonObj.sender+'" class="friendName" href="#" data-target="#demo-chat-body"  >'+jsonObj.senderName+'</a></li>');
					    nameMap.set(jsonObj.sender,jsonObj.senderName);
						addListener();

					}
					
					if(jsonObj.sender === reciver){
						var picUrl;
						var content = '    <li class="mar-btm">' +
						'    <div class="media-left">' +
						'    <img src="'+picUrl+'" class="img-circle img-sm" alt="Profile Picture">' +
						'    </div>' +
						'    <div class="media-body pad-hor">' +
						'    <div class="speech">' +
						'    <a href="#" class="media-heading">'+nameMap.get(jsonObj.sender)+'</a>' +
						'    <p>'+jsonObj.message+
						'    </p>' +
						'    </div>' +
						'    </div>' +
						'    </li>';	
						chatList.append(content);
					}else{

					}
					
				}
				
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("close" === jsonObj.type) {
				//refreshFriendList(jsonObj);
			}
			
		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}
	
	function sendMessage() {
		var inputMessage = document.getElementById("message");
		var friend = reciver;
		var message = inputMessage.value.trim();

		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else if (friend === "") {
			console.log("Choose a friend");
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : friend,
				"message" : message+'</p>' +'<p class="speech-time">' +'<i class="fa fa-clock-o fa-fw"></i>' +formatAMPM(new Date())
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}
	
	// 有好友上線或離線就更新列表
	function refreshFriendList(jsonObj) {
		var friends = jsonObj.users;
		var menu = $("#fmenu");
		//row.innerHTML = '';
		for (var i = 0; i < friends.length; i++) {
			if (friends[i] === self) { continue; }
			console.log(nameMap.get(friends[i]));
			if (nameMap.get(friends[i]) !=null ){ continue; }
			menu.append('<li class=""><a id="'+friends[i]+'" href="#" class="friendName"  data-target="#demo-chat-body" >'+nameMap.get(friends[i])+'</a></li>');
			console.log(menu);

			row.innerHTML +='<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' ><h2>' + friends[i] + '</h2></div>';
			nameMap.set(friends[i], friends[i]);
		}
		addListener();
	}
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener() {
		var element= document.getElementsByClassName('friendName');
		 for(var i=0;i<element.length;i++){
		      element[i].onclick = function(e){
						var friend = e.srcElement.id;
						updateFriendName(friend);
						var jsonObj = {
								"type" : "history",
								"sender" : self,
								"receiver" : friend,
								"message" : ""
							};
						webSocket.send(JSON.stringify(jsonObj));
						reciver = friend;
					};
		 }
		 /*
		var container = document.getElementById("frow");
		container.addEventListener("click", function(e) {
			var friend = e.srcElement.textContent;
			updateFriendName(friend);
			var jsonObj = {
					"type" : "history",
					"sender" : self,
					"receiver" : friend,
					"message" : ""
				};
			webSocket.send(JSON.stringify(jsonObj));
		});
		*/
	}
	
	function disconnect() {
		webSocket.close();
		document.getElementById('sendMessage').disabled = true;
	}
	
	function updateFriendName(name) {
		statusOutput.innerHTML = nameMap.get(name);
	}
	

	function formatAMPM(date) {
	  var hours = date.getHours();
	  var minutes = date.getMinutes();
	  var ampm = hours >= 12 ? 'pm' : 'am';
	  hours = hours % 12;
	  hours = hours ? hours : 12; // the hour '0' should be '12'
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  var strTime = hours + ':' + minutes + ' ' + ampm;
	  return strTime;
	}

</script>
</html>