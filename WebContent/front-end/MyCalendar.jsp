<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* "%>
<%@ page import="com.mem.model.* "%>
<%@ page import="com.coach.model.* "%>
<%@ page import="com.dayoff.model.* "%>

<%
	MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO)session.getAttribute("coachLogIn");

	String coach_Id = request.getParameter("coach_Id");
	CoachService coachSvc = new CoachService();
	CoachVO coachVO = coachSvc.getOneByCoach(coach_Id);
	pageContext.setAttribute("coachVO", coachVO);	

	MemService memSvc = new MemService();
	MemVO memVO = memSvc.getOneMem(coachVO.getMember_Id());
	pageContext.setAttribute("memVO", memVO);
%>

<head>
<title>MyCalendar</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">

<!-- Bootstrap 的 CSS -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css">
	
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css?family=Josefin+Sans:300,400,600" rel="stylesheet">	
<style>
* {
	font-family: 微軟正黑體;
}
body{
     background: 
     linear-gradient(rgba(255,255,255,0.5),rgba(255,255,255,0.5)),
     url("<%=request.getContextPath()%>/front-end/assets/images/112.jpg") center no-repeat fixed;
     background-size: cover;
}
.form-signin .btn {
	padding: .5rem 1rem;
    font-size: 1.25rem;
    line-height: 1.5;
    border-radius: .3rem;
    color: #fff;
    background-color: #ed563b;
}
.InOut {
	color: #ed563b;
}
.InOut .btn{
	color: #fff;
    background-color: #ed563b;
}
#our-classes {
    margin-bottom: 150px;
    transform: translate(10px, 90px);
}
//////////////////////////////////////////////////////////
.calendar {
	display: inline-block;
	width: 66%;
	padding: 1%;
	position: absolute;
    margin-top: 7%;
    margin-left: 5%;
}
 span.open{
	font-size:20px;	
}
span.close {
    font-size: 20px;
}
.calendar .top {
	display: inline-block;
	width: 100%;
	height: 50px;
	line-height: 40px;
	color: #FEFDFD;
    background-color: #1B19CD;
}
.calendar .top:after {
	display: table;
	content: "";
	line-height: 0;
	clear: both
}
.calendar .top>* {
	float: left;
	display: inline-block
}
.calendar .top .title {
	width: calc(100% - 200px);
	border: 2px solid blue; /* not necessary */
	font-size: 30px;
    text-align: center
}
.calendar .top .title:after {
	content: attr(data-y) "年 " attr(data-m) "月"
}
.calendar .top .btns {
	width: 200px;
	text-align: center;
	border: 2px solid blue; /* not necessary */
}
.calendar .top .btns>button {
	border: 1px solid #c8c8c8;
	background-color: white;
	height: 25px;
	line-height: 25px;
	padding: 0 5px;
	font-size: 16px;
	margin-top: 8px
}
.calendar .month {
	display: table;
	border-spacing: 0;
	border-collapse: collapse;
	table-layout: fixed;
	width: 100%;
}
.calendar .month .weeks, .calendar .month .days {
	display: table-row
}
.calendar .month .weeks>div, .calendar .month .days>div {
	display: table-cell;
	width: calc(100%/ 7);
	border: 2px solid #c8c8c8
}
.calendar .month .weeks>div { /* week day */
	height: 35px;
	line-height: 40px;
	text-align: center;
	background-color: #696969;
	font-weight: bold;
	color: #88c13f;
	font-size: 20px;
}
.calendar .month .days>div {
	position: relative;
	height: 100px;
	-moz-transition: background-color .3s;
	-o-transition: background-color .3s;
	-webkit-transition: background-color .3s;
	transition: background-color .3s
}
.calendar .month .days>div[data-y][data-m][data-d]:after {
	content: attr(data-d);
	/* 	attr(data-y) "/" attr(data-m) "/"  if need to show year, month in calendar */
	position: absolute;
	top: 0;
	right: 0;
	display: inline-block
}
.calendar .month .days>div.next-prev-month {
	background-color: #f0f0f0;
}
.calendar .month .days>div.today { /* 今日的DIV  */
	background-color: #88c13f
}
.calendar .month .days>div:hover {
	background-color: #88c13f
}
@media screen and (max-width: 499px) and (min-width: 0) {
	.calendar .month {
		display: inline-block
	}
	.calendar .month .weeks, .calendar .month .days {
		display: inline-block;
		width: 100%
	}
	.calendar .month .weeks>div, .calendar .month .days>div {
		display: inline-block;
		width: 100%
	}
	.calendar .month .weeks {
		display: none
	}
	.calendar .month .days>div {
		height: auto;
		min-height: 111px;
		border: 0;
		border-top: 2px solid #c8c8c8
	}
	.calendar .month .days>div[data-y][data-m][data-d]:after {
		top: 4px;
		right: 4px
	}
	.calendar .month .days>div.next-prev-month {
		display: none
	}
}
.days {
    text-align: left;
}
.modal-body {
    align-self: center;
}

#theme-landscape {
  display: block;
  padding: 0.5vh 2.5vh;
  color: #FEFDFD;
  border: 3px solid #FEFDFD;
  font-size: 2.4vh;
  border-radius: 2.25rem;
  margin: 5% 0 5% 40%;
  display: inline-block;
}
#theme-landscape:hover {
  background-color: #88c13f;
  opacity: 0.4;
  color: black;
}

.card-body {
    padding: 0;
}
table.table.table-striped.table-dark {
    table-layout: fixed;
    margin-top: 1%;
}

#div1{
    width: 40px;
    height: 20px;
    border-radius: 50px;
    position: relative;
}
#div2{
    width: 25px;
    height: 15px;
    border-radius: 48px;
    position: absolute;
    background: white;
    box-shadow: 0px 2px 4px rgba(0,0,0,0.4);
}
.open1{
    background: rgba(0,184,0,0.8);
}
.open2{
    top: 2px;
    right: 1px;
}
.close1{
    background: rgba(255,255,255,0.4);
    border:3px solid rgba(0,0,0,0.15);
    border-left: transparent;
}
.close2{
    left: 0px;
    top: 0px;
    border:2px solid rgba(0,0,0,0.1);
}
div#div1 {
    display: inline-block;
}
div#div1 + span {
    position: absolute;
    margin-left: 5px;
}
.on{
	color:lightgreen;
}
.off{
	color:red;
}
.name{
	display: inline-block;
	font-size: 28px;
    font-weight: 800;
    color: #232d39;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    margin-top: 0px;
    margin-bottom: 0px;
}
.name b{
	color: #ed563b;
}
</style>

</head>

<body class="text-center">

<%@ include file="headerh.file"%>
	<section class="section" id="our-classes">
		<div class="container">
			<!-- Button trigger modal -->
			<c:if test="${coachLogIn.member_Id == coachVO.member_Id}">
			<div class="name"><b>${memVO.mem_Name}</b> 教練,今天想如何安排行事曆呢?</div>
			<button id="theme-landscape" type="button" class="btn btn-primary"
				data-toggle="modal" data-target="#exampleModal">排班休假
			</button>
			</c:if>
			<div class='calendar'>
				<div class='top'>
					<div class='title'></div>
					<div class='btns'>
						<button class='today'>本月</button>
						<button class='icon-keyboard_arrow_left prev'>上月</button>
						<button class='icon-keyboard_arrow_right next'>下月</button>
					</div>
				</div>

				<div class='month'></div>
				<div class="row">
					<div class="col">
						<div class="collapse multi-collapse" id="hourtime">
							<div class="card card-body">
								<table class="table table-striped table-dark">
									<thead>
										<tr>
											<th id="table-title" colspan="6">選擇時段</th>
											<c:if test="${coachLogIn.member_Id == coachVO.member_Id}">
											<td colspan="2">
												<div id="div1" class="close1">
													<div id="div2" class="close2"></div>
												</div> <span>安排休假</span>
											</td>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>00:00</td>
											<td class="status">狀態</td>
											<td>01:00</td>
											<td class="status">狀態</td>
											<td>02:00</td>
											<td class="status">狀態</td>
											<td>03:00</td>
											<td class="status">狀態</td>
										</tr>
										<tr>
											<td>04:00</td>
											<td class="status">狀態</td>
											<td>05:00</td>
											<td class="status">狀態</td>
											<td>06:00</td>
											<td class="status">狀態</td>
											<td>07:00</td>
											<td class="status">狀態</td>
										</tr>
										<tr>
											<td>08:00</td>
											<td class="status">狀態</td>
											<td>09:00</td>
											<td class="status">狀態</td>
											<td>10:00</td>
											<td class="status">狀態</td>
											<td>11:00</td>
											<td class="status">狀態</td>
										</tr>
										<tr>
											<td>12:00</td>
											<td class="status">狀態</td>
											<td>13:00</td>
											<td class="status">狀態</td>
											<td>14:00</td>
											<td class="status">狀態</td>
											<td>15:00</td>
											<td class="status">狀態</td>
										</tr>
										<tr>
											<td>16:00</td>
											<td class="status">狀態</td>
											<td>17:00</td>
											<td class="status">狀態</td>
											<td>18:00</td>
											<td class="status">狀態</td>
											<td>19:00</td>
											<td class="status">狀態</td>
										</tr>
										<tr>
											<td>20:00</td>
											<td class="status">狀態</td>
											<td>21:00</td>
											<td class="status">狀態</td>
											<td>22:00</td>
											<td class="status">狀態</td>
											<td>23:00</td>
											<td class="status">狀態</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<%@ include file="footerf.file"%>
	
<script src="https://kit.fontawesome.com/e45ac9a14a.js" crossorigin="anonymous"></script>	
<script src="<%=request.getContextPath()%>/front-end/assets/js/jquery-2.1.0.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/assets/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/front-end/assets/js/bootstrap.min.js"></script>
<!-- Plugins -->
<script src="<%=request.getContextPath()%>/front-end/assets/js/scrollreveal.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/assets/js/waypoints.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/assets/js/jquery.counterup.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/assets/js/imgfix.min.js"></script> 
<script src="<%=request.getContextPath()%>/front-end/assets/js/mixitup.js"></script> 
<script src="<%=request.getContextPath()%>/front-end/assets/js/accordions.js"></script>

<!-- Global Init -->
<script src="<%=request.getContextPath()%>/front-end/assets/js/custom.js"></script>
<%@ include file="sweet.file"%>
<script type="text/javascript">
var sleepDate = {};

$(document).ready(function() {
	var _weeks = [ 'Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat' ];
	// 1 產生出 二維 陣列
	// 2 二維陣列 是月份內的每天 資訊(日期)
	// 3 產生出 html 元素，加到 month
	function monthDayCount(y, m) {
		return (--m == 1) ? ((y % 4) === 0)
				&& ((y % 100) !== 0)
				|| ((y % 400) === 0) ? 29 : 28 : [ 31,
				28, 31, 30, 31, 30, 31, 31, 30, 31, 30,
				31 ][m];
	}
	function prevMonth(y, m) {
		return {
			y : m == 1 ? y - 1 : y,
			m : m == 1 ? 12 : (m - 1)
		};
	}
	function nextMonth(y, m) {
		return {
			y : m == 12 ? y + 1 : y,
			m : m == 12 ? 1 : (m + 1)
		};
	}
	function createMonthArr(y, m) {
		// 1 號 在星期幾
		// 本月 有幾天
		// 本月 有幾週
		var firstDayWeek = new Date(y, m - 1, 1)
				.getDay();
		var monthCount = monthDayCount(y, m);
		var weekCount = parseInt(
				(firstDayWeek + monthCount) / 7, 10)
				+ (((firstDayWeek + monthCount) % 7) ? 1
						: 0);

		var p = prevMonth(y, m);
		var prevMonthCount = monthDayCount(p.y, p.m);
		var n = nextMonth(y, m);

		var date = new Date();

		return Array.apply(null, Array(weekCount)).map(function(_, i) {
			return Array.apply(null,Array(7)).map(function(_,j) {
				var d = i* 7+ j- firstDayWeek+ 1;
				var m2 = m;
				var y2 = y;
				var l = true; // 是否為本月份

				if (d > monthCount) {
						d = d- monthCount;
						m2 = n.m;
						y2 = n.y;
						l = false;
					}

				if (d <= 0) {
						d = d+ prevMonthCount;
						m2 = p.m;
						y2 = p.y;
						l = false;
					}
				
				var t = y2 == date.getFullYear()&& m2 == date.getMonth() + 1 && d == date.getDate();
					return {
						y : y2,
						m : m2,
						d : d,
						l : l,
						t : t
							};
						});
				});
	}

	var $month = $('.month');
	var $title = $('.calendar .top .title');

	function renderMonth(y, m) {
		var monthArr = createMonthArr(y, m);

		var w = $('<div />').addClass('weeks').append(
				_weeks.map(function(t) {
					return $('<div />').text(t);
				}));

		var ds = monthArr.map(function(w) {
				return $('<div />').addClass('days').append(w.map(function(d) {
					return $('<div />').addClass(d.l ? 'dats': 'next-prev-month dats')
										.addClass(d.t ? 'today': null)
										.attr('data-y',d.y)
										.attr('data-m',d.m)
										.attr('data-d',d.d)
										.attr('data-date', new Date(d.y,d.m-1,d.d))
							}));
				});

		$month.empty().append(w).append(ds);
		$title.attr('data-y', y).attr('data-m', m);
	}

	// 今月
	$('.top .btns .today').click(
			function() {
				var date = new Date();
				renderMonth(date.getFullYear(), date.getMonth() + 1);
			$('#cur-year').empty();
			$('#cur-month').empty();
			$('#cur-date').empty();
			$('#cur-day').empty();
		$('#cur-year').prepend(date.getFullYear()+'年');
		$('#cur-month').prepend(date.getMonth()+1+'月');
		$('#cur-date').prepend(date.getDate()+'日');
		$('#cur-day').append('星期' + date.getDay());
		dayClick();
		getEvents();
		
	}).click();

	// 上月
	$('.top .btns .prev').click(function() {
		var y = parseInt($title.attr('data-y'), 10);
		var m = parseInt($title.attr('data-m'), 10);
		var p = prevMonth(y, m);
		renderMonth(p.y, p.m);
		getEvents();
		
	});

	// 下月
	$('.top .btns .next').click(function() {
		var y = parseInt($title.attr('data-y'), 10);
		var m = parseInt($title.attr('data-m'), 10);
		var n = nextMonth(y, m);
		renderMonth(n.y, n.m);
		getEvents();

	});
	
	// 排班休假
   $("#theme-landscape").click(function(){
	   console.log("sleepDate.close", sleepDate.close)
	   if(sleepDate.close) {
		   $('[data-y="'+sleepDate.y+'"][data-m="'+sleepDate.m+'"][data-d="'+sleepDate.d+'"]').css('background', 'red');
		   $('[data-y="'+sleepDate.y+'"][data-m="'+sleepDate.m+'"][data-d="'+sleepDate.d+'"]')
		   		.append('<div style="text-align:center;">休息</div>');
		   
			var yy = sleepDate.y
		       var dataM = sleepDate.m
		       var dataD = sleepDate.d
		       var mm;
		       var dd;

		       if(dataM<10){
		        mm='0'+dataM;
		       }else if(dataM >9){
		        mm=dataM;
		       }
	       
		       if(dataD<10){
		        dd='0'+dataD;
		       }else if(dataD >9){
		        dd=dataD;
		       }
				var dayoff = yy +'-'+mm+'-'+dd;
		
		   	$.ajax({
				url: '<%=request.getContextPath()%>/front-end/dayoff.do',
				type: 'post',
				data: {
					action: 'insert_dayoff',
					dayoff_Date: dayoff,
				}		
			});	
		   
		   Swal.fire('已送出申請','這天已成功安排休假!','success');
	   }
   });
});
	
	function dayClick(){
		$(".calendar").on('click','.dats',function(){
			$('#div2').removeClass('open2').addClass('close2');
			
				
			$("#hourtime").collapse('show');	
			var dayoff_time = $(this).find('div').data('dayoff_time');
			
			$('.status').text('可預約').removeClass('status off').removeClass('status on').removeClass('status off on').addClass('status');
			
			sleepDate = {};
			sleepDate.close = false;
			sleepDate.y = $(this).data('y'); // 年
			sleepDate.m = $(this).data('m'); // 月
			sleepDate.d = $(this).data('d'); // 日
			
			if(dayoff_time){
				for(var i=0; i<dayoff_time.length; i++) {
					if(dayoff_time.charAt(i)==='1'){
						$('.status').eq(i).addClass('off').text('休息');
					}else{
						$('.status').eq(i).addClass('on').text('可預約');
					}
				}
			}
		});
	}
	
	window.onload=function(){
		var qk='111111111111111111111111';
		var hr='000000000000000000000000';
		$("#div2").click(function(){
			var status = $(this).attr("class");
			var change;
			if(status==='close2'){
				change = qk;
				$(this).removeClass('close2').addClass('open2');
			}else{
				change = hr;
				$(this).removeClass('open2').addClass('close2');
			}
			
			if($('#div2').hasClass('.open2')) { // 休假
				sleepDate.close = false;
			}
			else {
				sleepDate.close = true;
			}
			
			for(var i=0; i<change.length; i++) {
				if(change.charAt(i)==='1'){
					$('.status').eq(i).removeClass('on').addClass('off').text('休息');
				}else{
					$('.status').eq(i).removeClass('off').addClass('on').text('可預約');
				}
			}
		});
    }
//==============================================================================
	
	function getEvents(){
		$.ajax({
			url: '<%=request.getContextPath()%>/front-end/dayoff.do',
			type: 'post',
			dataType: 'json',
			data: {
				action: 'Display_calendar',
				coach_Id: '${coachVO.coach_Id}'
			},
			
			success: function(res) {
				showEvent(res)
			}
		});
	}
	
	function showEvent(res){
		
		  let dateArray = [];
	        for(var i = 0; i<=41; i++){
	         var yy = $('.days div').eq(i).data('y');
	       var dataM = $('.days div').eq(i).data('m');
	       var dataD = $('.days div').eq(i).data('d');
	       var mm;
	       var dd;

	       if(dataM<10){
	        mm='0'+dataM;
	       }else if(dataM >9){
	        mm=dataM;
	       }
	       
	       if(dataD<10){
	        dd='0'+dataD;
	       }else if(dataD >9){
	        dd=dataD;
	       }
	       
	       var dateCheck= yy+'-'+mm+'-'+dd;
	       dateArray.push(dateCheck);
	     }
	    // 比對資料 放入正確日期
		for (var i=0; i<dateArray.length; i++){
			 for (var j=0; j<res.length; j++){
 				if(dateArray[i] === res[j].dayoff_Date){
	 				$('.days>.dats').eq(i).append('<div data-coach_Id="'+res[j].coach_Id
	 						+'" data-dayoff_Id="'+res[j].dayoff_Date
	 						+'" data-dayoff_Date="'+res[j].dayoff_Date
	 						+'" data-dayoff_Time="'+res[j].dayoff_Time
	 						+'">休息</div>');
	 				$('.days>.dats').eq(i).css({'background-color':'red','text-align':'center','font-weight':'blod'});
 				}
 			}
		}		
	}
	
</script>
</body>
</html>
