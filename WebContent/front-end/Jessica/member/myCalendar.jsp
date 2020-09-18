<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="cOrderViewSvc" scope="page" class="com.course_order.model.COrderViewService"/>
<jsp:useBean id="courseDateSvc" scope="page" class="com.course_date.model.CourseDateService"/>
<jsp:useBean id="gpcListSvc" scope="page" class="com.gpc_List.model.Gpc_ListService"/>
<jsp:useBean id="gpcSvc" scope="page" class="com.gpc.model.GpcService"/>
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜約課</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>
<%-- 單頁所需css樣式、js文件 --%>
<link rel='stylesheet' type='text/css' href='<%=request.getContextPath()%>/front-end/Jessica/static/css/fullcal-main.min.css' />
<script type='text/javascript' src='<%=request.getContextPath()%>/front-end/Jessica/static/js/fullcal-main.min.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/front-end/Jessica/static/js/fullcal-locales-all.min.js'></script>
<style>
    /*調整標題*/
    #bg-video{
        min-height: 80px;
        max-height: 80px;
    }
    .main-banner .caption{
        top: 60%;
    }
    #section-content{
        margin-top: 25px;
    }
    .card, .form-control{
    	font-size: 13px;
    }
    table td{
    	margin: 5px 5px;
    	padding: 10px 10px;
    }
    .course .fc-daygrid-event-dot{
    	border: 4px solid red;
    }    
    .gpc .fc-daygrid-event-dot{
    	border: 4px solid green;
    }
    a{
    	color: gray;
    }
</style>
</head>

<body>
<%-- Preloader & Header --%>
<%@ include file="/front-end/Jessica/common/header.jsp"%>
<!----- Main Banner Area ----->
<div class="main-banner" id="top">
	<img src="" id="bg-video">
    <div class="video-overlay header-text"></div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<!----- Content Area ----->
<section class="section" id="section-content">
    <div class="container">
        <div class="row">

            <aside class="col-sm-3">
                <div class="card">
                
                    <!-- 選課 -->
                    <article class="card-group-item">
                        <div class="card-header"><h6 class="title">開始選課</h6></div>
                        <div class="card-body">
                        	<ul class="my-2">
	                        	<li>Step1. 可選擇課程，查詢教練時段</li>
	                        	<li>Step2. 或直接點選月曆選取上課時段</li>		
								<li id="btn">Step3. 約課完成!</li>
                        	</ul>
                        	<select class="form-control" name="choseCourse" id="choseCourse">
                        		<option value="">請選擇約課課程</option>
		                        <c:forEach var="cOrderVo" items="${cOrderViewSvc.getAllCOrderViewByMember_id(memLogIn.member_Id)}">
		                        	<c:if test="${cOrderVo.state != 1}">
		                             <option value="${cOrderVo.coach_id}">${cOrderVo.cname}</option>
		                            </c:if>
		                        </c:forEach>
							</select>				
                        </div>
                    </article>
                    <!-- 選課  -->
                
                    <!-- 1對1 -->
                    <article class="card-group-item">
                        <div class="card-header"><h6 class="title">我的1對1課程</h6></div>
                        <div class="card-body">
                            <form>
                            <c:forEach var="cOrderVo" items="${cOrderViewSvc.getAllCOrderViewByMember_id(memLogIn.member_Id)}">
                            	<c:if test="${cOrderVo.state != 1}">
	                                <label class="form-check">
	                                    <input class="form-check-input" class="cCheck" type="checkbox" value="${cOrderVo.order_id}" checked>
	                                    <span class="form-check-label">${cOrderVo.cname}(${cOrderVo.book_lesson}/${cOrderVo.lesson})</span>
	                                </label>
                                </c:if>
                            </c:forEach>
                            </form>
                        </div>                        
                    </article>
                    <!-- 1對1 -->
                    
                    <!-- 揪團 -->
                    <article class="card-group-item">
                        <div class="card-header"><h6 class="title">我的團體課程</h6></div>
                        <div class="card-body">
                            <form>
                            <c:forEach var="gpcListVo" items="${gpcListSvc.getGpc_Lists(memLogIn.member_Id)}">                            
                            	<c:if test="${cOrderVo.state != 1}">
	                                <label class="form-check">
	                                    <input class="form-check-input" class="cCheck" type="checkbox" value="${gpcListVo.gpc_Id}" checked>
	                                    <span class="form-check-label">${gpcSvc.getOneGpc(gpcListVo.gpc_Id).gpc_Name}</span>
	                                </label>
                                </c:if>
                            </c:forEach>
                            </form>
                        </div>                        
                    </article>
                    <!-- 揪團 -->
                    
                </div>
            </aside>

            <div class="col-sm-9"><div id='calendar'></div></div>
        </div>
    </div>
</section>


<!-- Modal -->
<div class="modal fade" id="addCDateModal" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalTitle">新增約課</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            </div>
            <div class="modal-body">
                <form id="addCDateForm">
                    <table>
                        <tr>
                            <td>課程名稱</td>
                            <td>
                                <select class="custom-select mr-sm-2" id="order_id">
                                    <option value="" selected>選擇課程</option>
                                    <c:forEach var="cOrderVo" items="${cOrderViewSvc.getAllCOrderViewByMember_id(memLogIn.member_Id)}">
                            			<c:if test="${cOrderVo.state != 1}">
                                    		<option value="${cOrderVo.order_id}" id="${coachSvc.getOneByCoach(cOrderVo.coach_id).member_Id}">${cOrderVo.cname}</option>                                   		
                                    	</c:if>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>開始時間</td>
                            <td>
                                <select class="custom-select mr-sm-2" id="eStart">
                                    <option value="" selected>選擇時間</option>
                                    <c:forEach var="i" begin="0" end="9">
                                    	<option value="${i}">0${i}:00</option>
                                    </c:forEach>
                                    <c:forEach var="i" begin="10" end="23">
                                    	<option value="${i}">${i}:00</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>結束時間</td>
                            <td><input class="form-control" type="text" id="eEnd" value="" readonly></td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <button type="button" class="btn btn-primary" id="addCDateSubmit">Yes!</button>
            </div>
        </div>
    </div>
</div>


<!--------------------------------------------------------------------------------------------------->

<%-- 沒登入跳窗 & footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>
<%-- socket --%>
<%@ include file="/front-end/Jessica/common/socket.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script>

$(function(){
	$('input:checkbox').click(function() {
		this.blur();
		this.focus();
		var className = $(this).val();
		if($(this).attr('checked')){
			$('.'+className).hide();
			$(this).attr('checked',false);
		}else{
			$('.'+className).show();
			$(this).attr('checked',true);
		}		
	});	
	
	$('#choseCourse').change(function(){		
		var coach_id = $(this).val();		
		$('.coach').hide();
		$('.'+coach_id).show();
	});
});	

$('#eStart').change(function(){
    var val = parseInt($(this).val()) + 1;
    val = (val < 10) ? '0'+val : val;
    $('#eEnd').val(val + ':00');
});

document.addEventListener('DOMContentLoaded', function() {
	var calendarEl = document.getElementById('calendar');

	var calendar = new FullCalendar.Calendar(calendarEl, {

	customButtons: { // 自定義btn
		myAddButton: {
			text: '新增約課',
			click: function() {
				alert('clicked the custom button!');
			}
		}
	},
	headerToolbar: { // header
		left: 'prev,next today',
		center: 'title',
		right: 'dayGridMonth,timeGridWeek,timeGridDay'
	},

	navLinks: true, // 允許天名稱是否可點擊，點了跳到單頁

	selectable: true, // 可不可點來觸發新增事件
	selectMirror: true,
	selectConstraint: { // 限制可以選的時間
		start: '2020-09-18T00:00', 
	  	end: '2020-12-31T24:00'
	},

	select: function(arg) {
        $('#addCDateModal').modal('show');
        $('#addCDateSubmit').unbind('click').click(function() {});

        $('#addCDateSubmit').click(function(){     	
            var order_id = $('#order_id').val();
            var cdate = arg.startStr;
            var ctime = $('#eStart').val();
            var coachId = $('#order_id option:selected').attr('id');
            console.log(coachId);
            
            document.getElementById("addCDateForm").reset();
            $('#addCDateModal').modal('hide');

             $.ajax({
                 type: 'POST',
                 url: '<%=request.getContextPath()%>/course/courseDate.do?action=addCDate',
                 data: { 'order_id':order_id, 'cdate':cdate, 'ctime':ctime },
                 dataType: 'json',
                 success: function (data){
                	 console.log(data);
                	 if("errorMsg" in data){
                		 swal("請修正錯誤", data.errorMsg, "error");
                		 
                	 }else if("msg" in data){
                		 swal("恭喜", data.msg, "success");
                		 var obj = {           
                				 "sender" : self,
                				 "receiver" : coachId,
                				 "message" : "你的訂單["+order_id+"]有1則新的約課",      
                				 "url" : "${pageContext.request.contextPath}/front-end/Jessica/member/myCalendar.jsp"
                				 };
                				 webSocket.send(JSON.stringify(obj));
                		 window.location.reload();
                	 } 
                 },
                 error: function(xhr, ajaxOptions, thrownError){
                     console.log(xhr.status);
                     console.log(thrownError);
                 }
            });
        });
	},

	editable: false, // 可不可改
	dayMaxEvents: false, // more...太多不顯示

	eventClick: function(info) {
    	console.log('title='+info.event.title);
    	console.log('url='+info.event.url);
    	console.log('start='+info.event.start);
    	console.log('end='+info.event.end);

	    // if (confirm('Are you sure you want to delete this event?')) {
	    // 	info.event.remove()
	    // }
	},

	eventTimeFormat: { // 事件24小時制HH:mm
	  	hour: '2-digit',
	  	minute: '2-digit',
	  	meridiem: false,
	  	hour12: false
	},
	// 設定看一天的
	slotDuration: '01:00:00', // 時間間隔
	slotLabelFormat: { // 24小時制HH:mm
	  	hour: '2-digit',
	  	minute: '2-digit',
	  	meridiem: false,
	  	hour12: false
	},
	slotEventOverlap: false, // 事件不可以重疊

//------------------------------
    eventSources: [
     	'<%=request.getContextPath()%>/course/courseDate.do?action=showCal',
     	'<%=request.getContextPath()%>/course/courseDate.do?action=showSelfCoachCal',
     	'<%=request.getContextPath()%>/course/courseDate.do?action=showAllCoachCal'
	]	
	
});	

	setTimeout(function(){
		$('.coach').hide()},500);
	calendar.render();
	
});
window.onload = function() {
	connect();
};


</script>

</body>
</html>