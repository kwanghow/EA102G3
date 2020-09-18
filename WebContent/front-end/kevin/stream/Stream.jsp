<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.stream.model.*"%>
<jsp:useBean id="coachSvc" scope="page"
	class="com.coach.model.CoachService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />


<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
%>
<%
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0);
%>
<%
	String stream_id = (String)session.getAttribute("stream_id");
	pageContext.setAttribute("stream_id", stream_id);
	CoachVO coachVO = new CoachVO();
	String coach_id = null;
	StreamService streamSvc = new StreamService();
	if (memLogIn != null) {
		String mem_name = memLogIn.getMem_Name();
		pageContext.setAttribute("mem_name", mem_name);
		if (coachSvc.getOneByMem(memLogIn.getMember_Id()) != null) {
			coachVO = coachSvc.getOneByMem(memLogIn.getMember_Id());
			coach_id = coachVO.getCoach_Id();
			String coachName = memLogIn.getMem_Name();
			application.setAttribute("coachName", coachName);
			session.setAttribute("coach_id", coach_id);
			pageContext.setAttribute("coach_id", coach_id);
		}
	}
%>
<%!int count = 0;%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>

<%@ include file="js2.file"%>
<!-- Bootstrap 的 CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/kevin/css/js/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/kevin/css/fonts/font-awesome.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/kevin/G3header.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/kevin/stream/mervick-emojionearea-99129f7/dist/emojionearea.min.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/front-end/kevin/stream/mervick-emojionearea-99129f7/dist/emojionearea.js"></script>
<!-- This Library is used to detect WebRTC features -->
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/DetectRTC.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/socket.io.js">
	
</script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/adapter-latest.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/IceServersHandler.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/CodecsHandler.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/RTCPeerConnection-v1.5.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/broadcast.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/kevin/stream/webrtc/getHTMLMediaElement.js"></script>


<style>
* {
	font-family: 微軟正黑體;
}
/*header奸細*/
.streambody {
    padding-top: 70px;
    min-height: 950px;
}

.bg {
	background: -webkit-linear-gradient(top, rgb(255 255 255/ 0.3),
		rgb(255 255 255/ 0.3)),
		url('<%=request.getContextPath()%>/front-end/kevin/images/bg1.jpg');
	background-attachment: fixed;
	background-size: cover;
	background-repeat: repeat;
	background-position: top center;
}

html, body, container {
	height: 100%;
}
footer {
    position: fixed;
    bottom: 0;
	text-align: center;
	padding: 30px 0px;
	background: #343a40;
	width: 100%;
}

footer p {
	color: white;
}

/* 輸入標題窗 */
input#streaminput {
    width: calc(80% - 15px);
    height: calc(85px - 30px);
    margin-top: 15px;
    border-radius: 9px 0 0 9px;
    margin-left: 15px;
    padding-left: 8px;
}

input#streaminput+button {
    width: calc(20% - 15px);
    height: calc(85px - 30px);
    border-radius: 0 9px 9px 0;
    margin-right: 15px;
    padding-left: 8px;
}

article section > section section button {
    background: rgba(7, 7, 7, 0.5);
    color: white;
    border-radius: 9px;
    padding: 8px 25px;
    margin-top: 15px;
    transition: 0.3s;
}

article section > section section button:hover {
    background: rgba(7, 7, 7, 1);
}

#streamblock {
    background: rgba(0, 0, 0, 0.5);
}

.emojionearea {
    width: 65%;
    float: left;
}
input#sendMessage {
    width: 35%;
    float: left;
}
/* 測試區 */
.CustomCard {
	padding-top: 20px;
	margin: 10px 0 20px 0;
	background-color: rgba(214, 224, 226, 0.2);
	border-top-width: 0;
	border-bottom-width: 2px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 15px;
	-webkit-box-shadow: none;
	-moz-box-shadow: none;
	box-shadow: none;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

.CustomCard.hoverCustomCard {
	position: relative;
	padding-top: 0;
	overflow: hidden;
	text-align: center;
}

.CustomCard.hoverCustomCard .CustomCardheader {
	background-size: cover;
	height: 85px;
}

.CustomCard.hoverCustomCard .avatar {
	position: relative;
	top: -50px;
	margin-bottom: -50px;
}

.CustomCard.hoverCustomCard .avatar img {
	width: 100px;
	height: 100px;
	max-width: 100px;
	max-height: 100px;
	-webkit-border-radius: 50%;
	-moz-border-radius: 50%;
	border-radius: 50%;
	border: 5px solid rgba(255, 255, 255, 0.5);
}

.CustomCard.hoverCustomCard .info {
	padding: 4px 8px 10px;
}

.CustomCard.hoverCustomCard .info .desc {
	overflow: hidden;
	font-size: 12px;
	line-height: 20px;
	color: #737373;
	text-overflow: ellipsis;
}

.CustomCard.hoverCustomCard .bottom {
	/*padding: 20px 5px;*/
	margin-bottom: -6px;
	text-align: center;
}

.btn {
	width: 100px;
	height: 30px;
	line-height: 0px;
}

html {
	background: #f4f9f4;
}

.message-area {
    height: 72.2%;
	resize: none;
	box-sizing: border-box;
	overflow: auto;
	width: 100%;
	background-color: #e9ecefa6;
}

#webSocket-submit {
	background-color: #e9ecefa6;
	border-radius: 4px;
}

.media-box {
	overflow: hidden;
	height: 460px;
	z-index: 0;
}

.emojioneemoji {
	width: 20px;
	height: auto;
}
</style>
</head>
<body onunload="disconnect();">
	<%@ include file="headerh.file"%>




	<div class="bg">
	<div class="container streambody">
		<div class="row">
			<div class="col col-sm-12">
				<div class="CustomCard hoverCustomCard">
					<div id="CustomCardheader"
						class="CustomCardheader text-info bg-dark">
						<input type="hidden" id="coach_id" value="${coach_id}" /> <input
							type="hidden" id="lsViewNum" value="0" />
						<%
							if (coach_id != null) {
						%>
						<h5 id="coachName">
<%-- 							<a>${memSvc.getOneMem(coachSvc.getOneByCoach(streamVO.coach_id).member_Id).mem_Name}</a> --%>
						</h5>
						<h1 id="streamtitle">
							<input type="text" placeholder="請輸入直播標題" id="streaminput"/><button class="btn-dark" id="header">確認送出</button>
						</h1>
						<%
							} else {
						%>
						<h5 id="coachName">
							<a></a>
						</h5>
						<h1>
							<span id="todayheader"></span>
						</h1>
						<%
							}
						%>
						<i id="WebSocket-count" class="far pt-2 pr-3 float-right"
							style="position: absolute; right: 0; top: 0px"></i> <i
							id="WebRTC-count" class="far pt-2 pr-3 float-right"
							style="position: absolute; right: 0; top: 20px"></i></i>
					</div>


				</div>
			</div>

			<script>
			$('#todayheader').html('等待教練開啟新直播');
		</script>
			<!-- 	---------------------------------------------------------------------- -->




			<div class="col col-sm-9">
				<article>
					<section id="session1" class="experiment">

						<!-- list of all available broadcasting rooms -->
						<br>

						<!-- local/remote videos container -->
						<div id="videos-container" style="width: 100%;">
							<div class="media-controls" id="media-controls"></div>
							<div class="volume-control" id="volume-control">
								<div class="control stop-recoeding-video selected"
									id="recoed-video"></div>
							</div>
							<div class="wrap flex-column">
								<div class="media-box" id="media-box">
									<video autoplay playsinline controls id="streamblock"
										style="height: 460px; width: 811px; z-index: -1; border-radius: 20px; overflow: hidden;"></video>
								</div>
							</div>
						</div>
						<table style="width: 100%;" id="rooms-list"></table>


						<div class="visible">
							<div style="text-align: center;">
								<div class="center"></div>
							</div>
						</div>
						<section>
							<section
								<%=(coach_id == null) ? "style='visibility: hidden;'" : ""%>>
								<input type="hidden" id="broadcasting-option"
									class="broadcasting-option" value="Audio + Video"> <input
									type="hidden" id="broadcast-name" class="broadcast-name"
									value="${coachName}">
								<button id="setup-new-broadcast">啟動新直播</button>
								<button class="btn-3d-can" id="record">開始錄影</button>
								<button class="btn-3d-can" id="download">儲存錄影</button>
								<button id="play" class="btn btn-success" style="display: none">播放</button>
								<div style="display: none">
									<div id="errorMsg"></div>
								</div>
							</section>
						</section>
					</section>
				</article>
			</div>


			<script>
				var visibleElements = document
						.getElementsByClassName('visible'), length = visibleElements.length;
				for (var i = 0; i < length; i++) {
					visibleElements[i].style.display = 'none';
				}
			</script>
			<!-- 	---------------------------------------------------------------------- -->
			<div class="col col-sm-3" style="height: 590px">
				<br>
				<div id="messagesArea" class="message-area"></div>
				<div class="panel input-area">
					<div id="webSocket-submit" class="g1">
						<input id="emojionearea1" style="width: 50px;" class="text-field"
							type="text" placeholder="訊息"
							onkeydown="if(event.keyCode == 13) sendMessage();" />
						<input type="submit" id="sendMessage"
							class="btn btn-danger" value="送出訊息" onclick="sendMessage();" />
						<input
							id="userName" class="panel input-default" type="hidden"
							placeholder="暱稱" value="${(coach_id!=null)? mem_name :mem_name}"
							readonly="readonly" />
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-1"></div>
		<form method="POST"
			ACTION="<%=request.getContextPath()%>/front-end/kevin/stream/stream.do">
			<input type="hidden" name="coach_id" value="${coach_id}" /> <input
				type="hidden" name="action" value="FindByCoach" />
			<button class="btn-dark" id="history" type="submit" style="margin-left: 15px;">歷史直播影片</button>
		</form>

	</div>
	<input id="socketid" type="hidden" value="${stream_id}" />
	<footer class="home">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<p>
						Copyright &copy; 2020 Training Studio - Modify by <a
							rel="nofollow" href="https://reurl.cc/yZ22W2"
							class="tm-text-link" target="_parent">FatBoy</a>
					</p>
					<!-- You shall support us a little via PayPal to info@templatemo.com -->
				</div>
			</div>
		</div>
	</footer>


	<!-- 	---------------------------------------------------------------------- -->

	<script src="jsmain.js"></script>
</body>


<script>
var obj;
var RoomNum;
	var config = {
		openSocket : function(config) {
			var SIGNALING_SERVER = 'https://socketio-over-nodejs2.herokuapp.com:443/';
// 			            var SIGNALING_SERVER = 'https://54.148.206.111:9001/';

			config.channel = config.channel
					|| location.href.replace(/\/|:|#|%|\.|\[|\]/g, '');
			var sender = Math.round(Math.random() * 999999999) + 999999999;
			io.connect(SIGNALING_SERVER).emit('new-channel', {
				channel : config.channel,
				sender : sender
			});
            
// 			var socket = io.connect(SIGNALING_SERVER + config.channel);
            var socket = io.connect(SIGNALING_SERVER + config.channel);
			socket.channel = config.channel;
			socket.on('connect', function() {
				if (config.callback)
					config.callback(socket);
			});
			socket.send = function(message) {
				socket.emit('message', {
					sender : sender,
					data : message
				});
			};
			socket.on('message', config.onmessage);
		},
		onRemoteStream : function(htmlElement) {
			document.getElementById('streamblock').srcObject = htmlElement.srcObject;
			
			//$(".join").parent().parent().show();	
			//obj.disabled = true;
		},
		onRoomFound : function(room) {
			document.getElementById("coachName").innerHTML = "<strong>"+ room.roomName + "正在直播</strong>";
			$('#todayheader').html('請選擇加入教練直播');
			var alreadyExist = document.querySelector('button[data-broadcaster="'+ room.broadcaster + '"]');
			if (alreadyExist)
				return;

			if (typeof roomsList === 'undefined')
				roomsList = document.body;
			var tr = document.createElement('tr');
			tr.innerHTML = '<td><strong>' + room.roomName
					+ '</strong> 正在直播中</td>'
					+ '<td><button class="join">加入直播</button></td>';
			roomsList.appendChild(tr);

			var joinRoomButton = tr.querySelector('.join');
			joinRoomButton.setAttribute('data-broadcaster', room.broadcaster);

			joinRoomButton.setAttribute('data-roomToken', room.broadcaster);
			joinRoomButton.onclick = function() {
				
				
// 				$('#streamblock')[0].pause();
// 				$('#streamblock').removeAttr('src');
			
				
				obj =this ;
				$('#todayheader').html('正在觀看'+room.roomName+'教練的直播');
				//點擊後加入直播	
				document.getElementById("CustomCardheader").className = "CustomCardheader text-white btn-success";
				document.getElementById("webSocket-submit").className = "g2";
				document.getElementById("coachName").innerHTML = "<strong2>您正在觀看</strong2>";
				document.getElementById('broadcast-name').value = room.roomName;
				videosContainer.className = "videosContainer";
				document.getElementById("WebRTC-count").innerHTML = "";
				
				
				//$(".join").attr('disabled', 'disabled');
				  $(".join").parent().parent().hide();
						
				
	
				var broadcaster = this.getAttribute('data-broadcaster');
				var roomToken = this.getAttribute('data-roomToken');
				broadcastUI.joinRoom({
					roomToken : broadcaster,
					joinUser : roomToken
				});
				
				endPointURL= "wss://" + host + webCtx +"/MyStream/"+$(this).attr('data-broadcaster');
				connect();
				
			};
		},
		onNewParticipant : function(numberOfViewers) {
			document.title = '觀看人數: ' + numberOfViewers;
			document.getElementById("WebRTC-count").innerHTML = "直播累計觀看人數"
					+ " " + numberOfViewers;
			document.getElementById("lsViewNum").value = numberOfViewers;
		},
		onReady : function() {
			console.log('now you can open or join rooms');
		}
	};
	//開始直播 觸發後跳出畫面
	function setupNewBroadcastButtonClickHandler() {
	
		document.getElementById("streamblock").innerHTML = "<strong>${coachName}: 您正在直播</strong>"
		DetectRTC.load(function() {
					captureUserMedia(function() {
						var shared = 'video';
						if (window.option == 'Only Audio') {
							shared = 'audio';
						}
						if (window.option == 'Screen') {
							shared = 'screen';
						}

						broadcastUI.createRoom({roomName : (document.getElementById('broadcast-name') || {}).value|| 'Anonymous',isAudio : shared === 'audio'});
				
						//console.log(RoomNum);
						endPointURL= "wss://" + host + webCtx +"/MyStream/"+RoomNum;
						hideUnnecessaryStuff();
					})
						
						
				
						
					let coach_id='${coach_id}';
				    let newBroadcast={};
				    stream_header=$('#streaminput').val();
				    $('#streamtitle').html(`<span>`+stream_header+`</span>`);
				    if(stream_header==''){
				    	stream_header='無直播標題';
				    	$('#streamtitle').html(`<span>無直播標題</span>`);	
				    }
				    newBroadcast= {"action":"insertAJAX", "coach_id":coach_id,"stream_header":stream_header};
					 $.ajax({
						 type: "POST",
						 url: "<%=request.getContextPath()%>/front-end/kevin/stream/stream.do",
						 data: newBroadcast,
						 dataType: "json",
						 success: function (data){
					     },
				        error: function(){
				       	 
				        }
				    });
					
				});
		
	}

	function captureUserMedia(callback) {
		var constraints = null;
		window.option = broadcastingOption ? broadcastingOption.value : '';
		if (option === 'Only Audio') {
			
			constraints = {
					
				audio : true,
				video : false
			};

			if (DetectRTC.hasMicrophone !== true) {
				alert('DetectRTC library is unable to find microphone; maybe you denied microphone access once and it is still denied or maybe microphone device is not attached to your system or another app is using same microphone.');
			}
		}
		if (option === 'Screen') {
			var video_constraints = {
				mandatory : {
					chromeMediaSource : 'screen',
					zIndex : -1
				},
				optional : []
			};
			constraints = {
				audio : false,
				video : video_constraints
			};

			if (DetectRTC.isScreenCapturingSupported !== true) {
				alert('DetectRTC library is unable to find screen capturing support. You MUST run chrome with command line flag "chrome --enable-usermedia-screen-capturing"');
			}
		}

		if (option != 'Only Audio' && option != 'Screen'
				&& DetectRTC.hasWebcam !== true) {
			alert('DetectRTC library is unable to find webcam; maybe you denied webcam access once and it is still denied or maybe webcam device is not attached to your system or another app is using same webcam.');
		}

		var htmlElement = document.getElementById('streamblock');
		htmlElement.muted = true;
		htmlElement.volume = 0;

		var mediaElement = getHTMLMediaElement(htmlElement, {
			buttons : [ 'record-video' ]
		});
		var mediaConfig = {
			video : htmlElement,
			onsuccess : function(stream) {
				config.attachStream = stream;

				callback && callback();
			},
			onerror : function() {
				if (option === 'Only Audio')
					alert('unable to get access to your microphone');
				else if (option === 'Screen') {
					if (location.protocol === 'http:')
						alert('Please test this WebRTC experiment on HTTPS.');
					else
						alert('Screen capturing is either denied or not supported. Are you enabled flag: "Enable screen capture support in getUserMedia"?');
				} else
					alert('unable to get access to your webcam');
			}
		};
		if (constraints)
			mediaConfig.constraints = constraints;
		getUserMedia(mediaConfig);
		
	}

	var broadcastUI = broadcast(config);

	/* UI specific */
	var videosContainer = document.getElementById('videos-container')
			|| document.body;
	var mediaBox = document.getElementById('media-box');
	var setupNewBroadcast = document.getElementById('setup-new-broadcast');
	var roomsList = document.getElementById('rooms-list');

	var broadcastingOption = document.getElementById('broadcasting-option');
	//點擊後開始直播
	if (setupNewBroadcast)
		setupNewBroadcast.onclick = setupNewBroadcastButtonClickHandler;
		
	function hideUnnecessaryStuff() {
		connect();

		var visibleElements = document.getElementsByClassName('visible'), length = visibleElements.length;
		for (var i = 0; i < length; i++) {
			visibleElements[i].style.display = 'inline';
		}
	}

	//旋轉
	<%--
	function rotateInCircle(video) {
		video.style[navigator.mozGetUserMedia ? 'transform'
				: '-webkit-transform'] = 'rotate(0deg)';
		setTimeout(function() {
			video.style[navigator.mozGetUserMedia ? 'transform'
					: '-webkit-transform'] = 'rotate(360deg)';
		}, 1000);
		document.querySelector('button#record').disabled = false;
		document.getElementById("session1").className = "experiment2";
	}
    --%>
    
    
    var MyPoint;
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "wss://" + host + webCtx + MyPoint;
<%--http上線請使用https , webSocket請使用wss--%>
	var webSocket;
   
	function connect() {
		
		
		var rtcroomName = document.getElementById('broadcast-name').value;
		alert('已開啟直播');
		// 建立 websocket 物件
		console.log('房間號碼:'+endPointURL);
		console.log('教練:'+rtcroomName);
		webSocket = new WebSocket(endPointURL<%-- + "/" + rtcroomName--%>);
		document.getElementById('broadcasting-option').style.display = 'none';
		document.getElementById('broadcast-name').style.display = 'none';
		document.getElementById('setup-new-broadcast').style.display = 'none';
		webSocket.onopen = function(event) {
			document.getElementById('sendMessage').disabled = false;
		};

		webSocket.onmessage = function(event) {
			if (event.data.indexOf('count=') == 0) {
				document.getElementById("WebSocket-count").innerHTML = "目前在線人數"
						+ " " + event.data.substring(6);
			} else {

				var messagesArea = document.getElementById("messagesArea");
				var jsonObj = JSON.parse(event.data);
				var message = jsonObj.userName + ": " + jsonObj.message
						+ "<br>";
				var showCount = jsonObj.showCount;
				messagesArea.innerHTML = messagesArea.innerHTML + message;
				messagesArea.scrollTop = messagesArea.scrollHeight;
			}
		}

		webSocket.onclose = function(event) {
			var coach_no = document.getElementById("messagesArea");
			var jsonObj = {
				"coach_id" : userName,
				"message" : message
			};
			webSocket.send(JSON.stringify(jsonObj));
		};
		
	
	}

	function sendMessage() {
		var inputMessage = document
				.getElementsByClassName("emojionearea-editor")[0];
		var message = inputMessage.innerHTML.trim();
		var inputUserName = document.getElementById("userName");
		var userName = inputUserName.value.trim();
		if (message === "") {
			alert("訊息請勿空白!");
			inputMessage.focus();
		} else {
			var jsonObj = {
				"message" : message,
				"userName" : userName
			};
			webSocket.send(JSON.stringify(jsonObj));
			document.getElementsByClassName("emojionearea-editor")[0].innerHTML = "";
			document.getElementById("emojionearea1").innerText = "";
			inputMessage.focus();
		}
	}
	
	
	//測試按鈕區
	$('#header').click(function(){
		let stream_header=$('#streaminput').val();
		if(stream_header!==''){
		$('#streamtitle').html(`<span>`+stream_header+`</span>`);
		}else{
		$('#streamtitle').html(`<span>無直播標題</span>`);	
		}
	});
	
	
	
	
	
	
	
	
	
	
	
</script>

<script>
        'use strict';
        const mediaSource = new MediaSource();
        mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
        let mediaRecorder;
        let recordedBlobs;  //錄製成功的Blob
        let sourceBuffer;

        const errorMsgElement = document.querySelector('span#errorMsg'); 
        const recordedVideo = document.querySelector('video#recorded');
        const recordButton = document.querySelector('button#record');
        recordButton.addEventListener('click', () => {
          if (recordButton.textContent === '開始錄影') {
adjustControls();
volumeControl.style.opacity = 1;
recordVideo.className = 'control record-video';
            startRecording();
          } else {
recordVideo.className = 'stop-recording-video selected';
            stopRecording();
	         let closestatus= {"action":"statusAJAX"};
            $.ajax({
	    		 type: "POST",
	    		 url: "<%=request.getContextPath()%>/front-end/kevin/stream/stream.do",
	    		 data: closestatus,
	    		 dataType: "json",
	    		 success: function (data){
	    	     },
	             error: function(){
	            	 
	             }
	         })
	         alert('錄影已結束，記得儲存錄影');
            recordButton.textContent = '開始錄影';
volumeControl.style.opacity = 1;
            playButton.disabled = false;
            //downloadButton.disabled = false;
          }
        });
 
        const playButton = document.querySelector('button#play');
        playButton.addEventListener('click', () => {
          const superBuffer = new Blob(recordedBlobs, {type: 'video/webm'});
          recordedVideo.src = null;
          recordedVideo.srcObject = null;
          recordedVideo.src = window.URL.createObjectURL(superBuffer);
          recordedVideo.controls = true;
          recordedVideo.play();
        });

        const downloadButton = document.querySelector('button#download');
        downloadButton.addEventListener('click', () => {
              document.querySelector('button#record').disabled = false;
              document.querySelector('button#download').disabled = true;
              const blob = new Blob(recordedBlobs, {type: 'video/webm'});	 
        	  var xhr = new XMLHttpRequest();
         	  xhr.open('POST', '<%=request.getContextPath()%>/front-end/stream/upload.do', true);
        	  xhr.onload = function(e) { console.log("loaded"); };
        	  xhr.onreadystatechange = function(){
        	      console.log("state: " + xhr.readyState);
        	  };
        	  // Listen to the upload progress.
        	  xhr.upload.onprogress = function(e) { console.log("uploading..."); };
        	  xhr.setRequestHeader("Content-Type", "video/webm");
        	  xhr.send(blob);
volumeControl.style.opacity = 0;
        });

        function handleSourceOpen(event) {
          console.log('MediaSource opened');
          sourceBuffer = mediaSource.addSourceBuffer('video/webm; codecs="vp8"');
          console.log('Source buffer: ', sourceBuffer);
        }

        function handleDataAvailable(event) {
          if (event.data && event.data.size > 0) {
            recordedBlobs.push(event.data);
          }
        }

        function startRecording() {
          recordedBlobs = [];
          let options = {mimeType: 'video/webm;codecs=vp9'};
          if (!MediaRecorder.isTypeSupported(options.mimeType)) {
            console.error(`${options.mimeType} is not Supported`);
            errorMsgElement.innerHTML = `${options.mimeType} is not Supported`;
            options = {mimeType: 'video/webm;codecs=vp8'};
            if (!MediaRecorder.isTypeSupported(options.mimeType)) {
              console.error(`${options.mimeType} is not Supported`);
              errorMsgElement.innerHTML = `${options.mimeType} is not Supported`;
              options = {mimeType: 'video/webm'};
              if (!MediaRecorder.isTypeSupported(options.mimeType)) {
                console.error(`${options.mimeType} is not Supported`);
                errorMsgElement.innerHTML = `${options.mimeType} is not Supported`;
                options = {mimeType: ''};
              }
            }
          }

          try {
            mediaRecorder = new MediaRecorder(window.stream, options);
          } catch (e) {
            console.error('Exception while creating MediaRecorder:', e);
            errorMsgElement.innerHTML = `Exception while creating MediaRecorder: ${JSON.stringify(e)}`;
            return;
          }

//           console.log('Created MediaRecorder', mediaRecorder, 'with options', options);
          recordButton.textContent = '結束錄影';
          playButton.disabled = true;
          downloadButton.disabled = true;
          mediaRecorder.onstop = (event) => {
//             console.log('Recorder stopped: ', event);
          };
          mediaRecorder.ondataavailable = handleDataAvailable;
          mediaRecorder.start(10); // collect 10ms of data
//           console.log('MediaRecorder started', mediaRecorder);
        }
        function stopRecording() {
          mediaRecorder.stop();
//           console.log('Recorded Blobs: ', recordedBlobs);
          downloadButton.disabled = false;
        }

        function handleSuccess(stream) {
          recordButton.disabled = false;
          downloadButton.disabled = true;
//           console.log('getUserMedia() got stream:', stream);
          window.stream = stream;
//           console.log(window.stream);

        }

        async function init(constraints) {
            const stream = await navigator.mediaDevices.getUserMedia(constraints);
            handleSuccess(stream);
        }
        document.querySelector('#setup-new-broadcast').addEventListener('click', async () => {
          const hasEchoCancellation = true;
          const constraints = {
            audio: {
              echoCancellation: {exact: hasEchoCancellation}
            },
            video: {
              width: 1280, height: 720
            }
          };
//           console.log('Using media constraints:', constraints);
          await init(constraints);
        });
</script>
<script>
	$("#emojionearea1").emojioneArea({
		pickerPosition : "top",
		tonesStyle : "bullet"
	});
</script>

</html>