<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.diet.model.*"%>
<%@ page import="com.dietCon.model.*"%>
<%@ page import="com.trainingLog.model.*"%>

<%-- <%@ page import ="java.sql.Timestamp" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	String memno = memLogIn.getMember_Id();
	DietService dietSvc = new DietService();
	Set<dietVO> set = dietSvc.getDietByMemno(memLogIn.getMember_Id());
	session.setAttribute("listDiet_ByMemberID", set);
	trainingLogService trainingLogSvc = new trainingLogService();
	Set<trainingLogVO> set2 = trainingLogSvc.getLogByMemno(memLogIn.getMember_Id());
	session.setAttribute("listLogByMemno", set2);
%>

<jsp:useBean id="listLogByMemno" scope="session"
	type="java.util.Set<trainingLogVO>" />
<jsp:useBean id="trainCat" scope="page"
	class="com.trainingCat.model.trainingCatService" />

<jsp:useBean id="listDiet_ByMemberID" scope="session"
	type="java.util.Set<dietVO>" />



<%-- <%dietVO dietVO = (dietVO) request.getAttribute("dietVO");%> --%>

<%
	List<dietVO> list = dietSvc.getAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>會員飲食健身紀錄</title>
<!-- 					JS 								-->
<script
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
	integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
	crossorigin="anonymous"></script>
<script
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/moment.js"></script>
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;display=swap"
	rel="stylesheet">
<!-- 					fullCalendar 					-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/main.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/main.css">
<!-- 					不知道是什麼的包					    -->

<!-- 					Modal 							-->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
	crossorigin="anonymous">


<!-- Additional CSS Files -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/font-awesome.css">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-end/guochi/assets/css/templatemo-training-studio.css">
<!-- Chartjs -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/Chart.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/front-end/guochi/assets/js/analytics.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/front-end/guochi/assets/js/date.format.js"></script>
<style>
}
html, body {
	overflow: hidden; /* don't do scrollbars */
	font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
	font-size: 14px;
}

.fc-header-toolbar {
	/*
  the calendar will be butting up against the edges,
  but let's scoot in the header's buttons
  */
	padding-top: 1em;
	padding-left: 1em;
	padding-right: 1em;
}

div#cal {
	height: 80%;
	width: 80%;
	/* 	margin-top:5%; */
	margin-left: 10%
}

div#calendar {
	margin-top: 5%;
}

.fc .fc-timegrid-axis-cushion {
	color: azure;
}
/* 時間 */
.fc-direction-ltr .fc-daygrid-event .fc-event-time {
	color: aliceblue;
}
/* WEEK左邊的時間 */
.fc .fc-timegrid-axis-cushion, .fc .fc-timegrid-slot-label-cushion {
	color: azure;
	padding: 0 6px;
}

div.fc-scrollgrid-sync-inner>a.fc-col-header-cell-cushion {
	color: white;
}

/* 事件顏色 */
a.fc-daygrid-dot-event>div.fc-event-title {
	color: azure;
}

.fc .fc-daygrid-day-number {
	color: azure;
}

h2.fc-toolbar-title {
	color: azure;
}
/* LIST的事件與時間顏色 */
.fc .fc-list-table td, .fc .fc-list-day-cushion {
	color: darkturquoise;
}
</style>
</head>

<body>

	<!----- Preloader Start ----->

	<!----- Header Area ----->
	<!----- Header Area ----->
	<!----- Header Area ----->
	<header class="header-area header-sticky">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav">
						<!-- logo -->
						<a href="<%=request.getContextPath()%>/front-end/kevin/index.jsp"
							class="logo">我就<em> 健</em></a>
						<!-- menu -->
						<ul class="nav">
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/news/News.jsp">最新消息</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/Jessica/course/explore.jsp">課程</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/listAllGpc.jsp">揪團</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/haoren/list-all-product.jsp">商城</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/article/articleList.jsp">健身文章</a></li>
							<li class="scroll-to-section"><a
								href="<%=request.getContextPath()%>/front-end/kevin/stream/Stream.jsp">直播區</a></li>
							<!-- 會員下拉 -->
							<li class="scroll-to-section dropdown"><a href="#"
								id="navbarDropdown" data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="ture">會員/教練專區</a>
								<ul class="dropdown-menu" role="menu"
									aria-labelledby="navbarDropdown">
									<li><a class="dropdown-item m1"
										href="<%=request.getContextPath()%>/front-end/memberPage.jsp">會員專區</a></li>
									<li><a class="dropdown-item c1"
										href="<%=request.getContextPath()%>/front-end/coachPage.jsp">教練專區</a></li>
									<li><a class="dropdown-item"
										href="<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp">飲食/健身紀錄</a></li>
									<li style="display: none;"><a class="dropdown-item"
										href="#">none</a></li>
								</ul></li>

							<!-- 會員登入訊息 -->
							<c:if test="${empty sessionScope.memLogIn}">
								<li class="main-button"><a
									href="<%=request.getContextPath()%>/front-end/mem.do?action=logIn">
										Log In </a></li>
							</c:if>
							<c:if test="${not empty sessionScope.memLogIn}">
								<li class="main-button"><a
									href="<%=request.getContextPath()%>/front-end/mem.do?action=logOut">
										${sessionScope.memLogIn.member_Id}${sessionScope.memLogIn.mem_Name}
								</a></li>
							</c:if>
						</ul>
						<a class='menu-trigger'><span>Menu</span></a>
					</nav>
				</div>
			</div>
		</div>
	</header>

	<div class="main-banner" id="top">
		<video autoplay="" muted="" loop="" id="bg-video">
			<source
				src="${pageContext.request.contextPath}/front-end/guochi/assets/images/gym-video.mp4"
				type="video/mp4">
		</video>

		<div class="video-overlay header-text">
			<div class="caption"></div>
			<div>
				<div id="cal">
					<div id="calendar"></div>
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#weight" style="background-color: #fd7e14;">體重紀錄圖</button>
				</div>


			</div>
		</div>
	</div>
	<!-- 體重紀錄圖 -->
	<!-- Modal -->
	<div class="modal fade" id="weight" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">體重紀錄圖</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<canvas id="lineChart"></canvas>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>



	<!-- 				Modal(新增)					 -->
	<table>
		<tr>
			<td>
				<!-- 				<button type="button" class="btn btn-primary" data-toggle="modal" -->
				<!-- 					data-target="#exampleModal" data-whatever="@getbootstrap" -->
				<!-- 					style="background-color: #fd7e14;">Modal新增</button> -->



				<div class="modal fade" id="exampleModal2" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">要增加飲食還是健身紀錄呢?</h5>
								<button type="button" data-dismiss="modal" class="close"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<button class="btn btn-primary" data-toggle="modal"
									data-target="#exampleModal" data-dismiss="modal">新增飲食紀錄</button>
								<button class="btn btn-primary" data-toggle="modal"
									data-target="#addTrainLog" data-dismiss="modal">新增健身紀錄</button>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">關閉</button>
							</div>
						</div>
					</div>
				</div> <!-- 健身紀錄MODAL -->
				<div class="modal fade" id="addTrainLog" tabindex="-1" role="dialog"
					aria-labelledby="exampleModalLabel" aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">新增健身紀錄</h5>
								<button type="button" data-dismiss="modal" class="close"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">

								<FORM
									ACTION="<%=request.getContextPath()%>/trainingLog/trainingLog.do"
									METHOD=post enctype="multipart/form-data">

									<jsp:useBean id="trainingCatSvc" scope="page"
										class="com.trainingCat.model.trainingCatService" />
									<div>
										<div>
											<div>
												<label for="recipient-name" class="col-form-label">訓練項目:</label>
											</div>
											<select name="trainingCat" size=1>
												<c:forEach var="trainingCatVO" items="${trainingCatSvc.all}">
													<option value="${trainingCatVO.trainingCat_no}">${trainingCatVO.trainingCat_name}
												</c:forEach>
											</select>
										</div>
									</div>

									<div>
										<div>
											<div>
												<label for="recipient-name" class="col-form-label">體重:</label>
											</div>
											<input type="text" name="weight" size="5">
										</div>

									</div>

									<div>
										<div>
											<div>
												<label for="recipient-name" class="col-form-label">訓練紀錄:</label>
											</div>
											<textarea name="training_item" cols="20" rows="5"></textarea>
										</div>
									</div>

									<div>
										<div>
											<div>
												<label for="recipient-name" class="col-form-label">照片紀錄:</label>
												<img id="blah" src="#" alt="預覽圖片" style="max-height: 250px;" />
											</div>
											<input id="pic" type="file" name="upfile1">
										</div>

									</div>


									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">關閉</button>
										<button type="submit" class="btn btn-primary">送出</button>
										<input type="hidden" name="memno" value="<%=memno%>">
										<input type="hidden" name="action" value="insert"> <input
											type="hidden" name="timestamp" id="timeanother" value="">
										<input type="hidden" name="nowbool" id="nowbool" value="">
									</div>
								</FORM>
							</div>
						</div>
					</div>

				</div> <!-- 				飲食紀錄MODAL -->
				<div class="modal fade" id="exampleModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">新增飲食紀錄</h5>
								<button type="button" data-dismiss="modal" class="close"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/aaa/diet.do"
									style="margin-bottom: 0px;">

									<div>
										<label for="recipient-name" class="col-form-label">吃了什麼:</label>
									</div>

									<div>
										<jsp:useBean id="dietIngSvc" scope="page"
											class="com.dietIng.model.dietIngService" />
										<select size="1" name="dietIng">
											<c:forEach var="dietIngVO" items="${dietIngSvc.all}">
												<option value="${dietIngVO.dietIng_no}">${dietIngVO.dietIng_name}
											</c:forEach>
										</select>
									</div>

									<div class="form-group">
										<label for="message-text" class="col-form-label">吃的內容:</label>
										<textarea class="form-control" id="message-text"
											name="diet_content"></textarea>
									</div>

									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">關閉</button>
										<button type="submit" class="btn btn-primary"
											id="submitButton">新增</button>
										<!-- <input type="submit" value="送出">  -->
										<input type="hidden" name="action" value="insert"> <input
											type="hidden" name="memno" value="<%=memno%>"> <input
											type="hidden" name="requestURL"
											value="<%=request.getServletPath()%>"> <input
											type="hidden" name="timestamp" id="timeanother" value="">
										<input type="hidden" name="nowbool" id="nowbool" value="">

									</div>

								</FORM>
							</div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	<!-- 				Modal(新增)	end				 -->
	<!-- 					Modal(查看或刪除)				 -->
	<div class="modal fade" id="exampleModal2" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">要做什麼呢</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">關閉</button>
					<button type="button" class="btn btn-primary" id="submitButton">查看</button>
					<button type="button" class="btn btn-primary" id="deleteButton">刪除</button>

				</div>
			</div>
		</div>
	</div>
	<!-- 					Modal(查看或刪除)end				 -->

	<!-- 	移動事件 -->
	<div class="modal fade" id="dragEvent" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">確定要將紀錄移動到這天嗎</h5>
					<button type="button" data-dismiss="modal" class="close"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/aaa/diet.do"
					style="margin-bottom: 0px;">
					<div class="modal-body">
						<input type="hidden" name="action" value="update"> <input
							type="hidden" name="memno" value="<%=memno%>"> <input
							type="hidden" name="dietno" id="dietno" value=""> <input
							type="hidden" name="diet_date" id="diet_date" value="">

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">取消</button>
						<button type="submit" class="btn btn-secondary">確定</button>

					</div>
				</FORM>
			</div>
		</div>
	</div>


	<script type="text/javascript">
	
	//line
	var ctxL = document.getElementById("lineChart").getContext('2d');
	var myLineChart = new Chart(ctxL, {
	type: 'line',
	data: {
	labels: [

		<c:forEach var="trainLogVO" items="${listLogByMemno}">
				"${trainLogVO.trainingLog_date}",
			</c:forEach>
		],
	datasets: [{
	label: "體重",
	data: [
		<c:forEach var="trainLogVO" items="${listLogByMemno}">
			"${trainLogVO.weight}",
		</c:forEach>
		],
	backgroundColor: [
	'rgba(105, 0, 132, .2)',
	],
	borderColor: [
	'rgba(200, 99, 132, .7)',
	],
	borderWidth: 2
	},
// 	{
// 	label: "My Second dataset",
// 	data: [28, 48, 40, 19, 86, 27, 90],
// 	backgroundColor: [
// 	'rgba(0, 137, 132, .2)',
// 	],
// 	borderColor: [
// 	'rgba(0, 10, 130, .7)',
// 	],
// 	borderWidth: 2
// 	}
	]
	},
	options: {
	responsive: true
	}
	});
	
// 	------------------------------------------------------------------------------------	
	document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        
        var myDate = new Date();
        
        function formatdays(myDate){
        	
        	var y = myDate.getFullYear();
        	var m = myDate.getMonth()+1;
        	var d = myDate.getDate();
        	if(m<=9){
        		
        	return y+'-'+'0'+m+'-'+d;
        	}else{
            	return y+'-'+m+'-'+d;
        	}
        };
        var now = myDate.toLocaleDateString();
       
        
        $(document).ready(function(){
        	
        	
        
        var calendar = new FullCalendar.Calendar(calendarEl, {
//         	editable:true,//可拖曳紀錄
          selectable: true,
          initialView: 'dayGridMonth',
          
          droppable: true,
          editable:true,
          
          
          aspectRatio: 2.5,          

		  dayMaxEvents:true,
          headerToolbar:{
        	left:'prev,next,today',
        	center:'title',
        	right:'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
          },
          
          eventDrop: function(info) {
        	    $('#dragEvent').modal('show');
        	  var ggg = info.event.startStr;
        	  console.log(ggg);
			  var zzz = ggg.replace("T"," ");
        	  console.log(zzz);

        	  var DATE = zzz.split('+');
        	  console.log(DATE[0]);
        	  DATE[0] = DATE[0].split('.');
        	  console.log(DATE[0][0]);

        	  var str = info.event.title.split(':');
        	
        	    $('#dragEvent #dietno').val(str[1]);
//         	    $('#dragEvent #diet_date').val(DATE[0]+'.'+'0');
        	    $('#dragEvent #diet_date').val(DATE[0][0]+'.'+'0');
        	    
          },
          
          validRange: { 
        	  start: '2017-05-01',
        	  end: myDate, 
        	  } ,
        
//           eventClick: function(info) {
// //         		    alert('Event: ' + info.event.title);  
// // 				$('#exampleModal2').
//         		$('#exampleModal2').modal('show');
//           },
// 		  dateClick: function(Date) {
// 			  console.log('111:'+Date.dateStr);

// 		  },
		  
          select: function(info) {
        	
        	  var ggg = info.start;
        	  var yyy = info.startStr;
			  var DATE = yyy.split('T');
			  console.log(DATE);
        	  console.log(yyy);
//         	  console.log(yyy.format("Y-m-d"))
        	  console.log(info.dateStr);//不能用

        	  console.log(ggg.format("H:i:s"));
        	  
			  var timer = 	ggg.format("H:i:s");
        	  if(info.startStr==formatdays(myDate)){
					var nowbool = 1;
					$('#exampleModal #nowbool').val(nowbool);
					$('#addTrainLog #nowbool').val(nowbool);
				} else{
					nowbool = 0;
					$('#addTrainLog #nowbool').val(nowbool);
					$('#exampleModal #nowbool').val(nowbool);
				}
// 			  alert('nowDay:'+formatdays(myDate));
// 			  alert('Date: ' + info.startStr);
				
			  $('#addTrainLog #timeanother').val(DATE[0]+' '+timer+'.'+'0');
			  $('#exampleModal #timeanother').val(DATE[0]+' '+timer+'.'+'0');

			
        	$('#exampleModal2').modal('show');
        	
          },
  	  
          events : [ 
//                飲食紀錄的部分
  			<c:forEach var="dietVO" items="${listDiet_ByMemberID}">
  				{
  				
  				id:'diet',
  				title : "飲食紀錄:${dietVO.dietno}",
  				start : "${dietVO.diet_date}",
  				color: '#fd7e14',
				url:"<%=request.getContextPath()%>/dietCon/dietCon.do?action=showDietContent&dietno=${dietVO.dietno}&memno=${dietVO.member_id}"
  				} ,
  			</c:forEach>
  				
//         		   健身紀錄的部分
  			<c:forEach var="trainLogVO" items="${listLogByMemno}">
				{
					
	  				title : "健身紀錄:${trainLogVO.trainingLog_no}",
	  				start : "${trainLogVO.trainingLog_date}",
	  				color: 'green',
	  				url:"<%=request.getContextPath()%>/trainingLog/trainingLog.do?action=get_One_For_Update&trainLogNo=${trainLogVO.trainingLog_no}&trainLogCat=${trainLogVO.trainingCat_no}&weight=${trainLogVO.weight}&trainingItem=${trainLogVO.training_item}&photo=<%=request.getContextPath()%>/front-end/ReadPictures?TRAININGLOG_NO=${trainLogVO.trainingLog_no}"
				},
			</c:forEach>

  				],
  				
  		   });
        

        calendar.render();
      		});
        });
//         傳值至新增時點的日期MODAL
		$('#submitButton').click(function(){
		$(this).submit();
		});
// 		顯示預覽圖
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#blah').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#pic").change(function() {
			readURL(this);
		});
		

		
		</script>




</body>
</html>