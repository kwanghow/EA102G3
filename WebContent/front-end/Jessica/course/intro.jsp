<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<jsp:useBean id="courseFavorSvc" scope="page" class="com.course_favor.model.CourseFavorService"/>

<c:if test="${empty param.course_id || empty courseSvc.getOneCourseById(param.course_id)}">
	查無該課程! 3秒後自動跳轉回搜尋頁面
	<% 
	String url = request.getContextPath() + "/front-end/Jessica/course/explore.jsp";
	response.setHeader("refresh","3;URL="+url); 
	%>
</c:if>
<c:if test="${not empty courseSvc.getOneCourseById(param.course_id)}">
<c:set var="courseVo" value="${courseSvc.getOneCourseById(param.course_id)}" scope="page" />

<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜課程詳情</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAm0PnJOlagjyo4b6waNYMXuUd5RnjDKf4&libraries=places&callback=initMap" async defer></script>
<style>
    /*課程分類的膠囊樣式*/
    .badge-theme{
        background-color: transparent;
        color: #ed563b;
        border: 1px solid #ed563b;
        font-size: 18px;
    }
    #course-img-controls{
        margin: 20px 0;
    }
    #card-link a{ /* 把頁籤標題的藍色取消 */
        color: black;
    }
    .nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active{ /* 把活動中的頁籤標題的設為主題色 */
        color: #ed563b;
    }
    /*裁切倫播相片*/
    .course-img{
        width: 100%;
        height: 0;
        padding-bottom: 400px;
        position: relative;
        overflow: hidden;
    }
    .course-img img{
        position: absolute;
        top: 50%;
        left: 50%;
        display: block;
        min-width: 100%;
        min-height: 100%;
        transform:translate(-50%,-50%);          
    }
    .course-set-header{ /*課程方案標題大小*/
        font-size: 20px;
    }
    /*調整方案大小*/
    #tabs ul li{
        margin-bottom: 20px;
    }
    #tabs ul li a{
        padding: 30px;
    }
    .div-right{
        /*position: absolute;*/
    }
    #contact_row i {
    	font-size: 22px;
    }
    .addHeart .fa-heart{
    	color: #ed563b;
    }
	
	#tabs #cartRow #goCart{
		padding: 10px 30px;
		box-shadow: none;
	}	
	#tabs #cartRow #addCartItem{
		padding: 10px 30px; background-color:white; color:#ed563b; border:1px solid #ed563b;
		box-shadow: none;
	}	
	#tabs #cartRow #goCart2{
		padding: 10px 30px;
		box-shadow: none;
	}	
	#map {
		width: 100%;
		height: 500px;
		margin: 10px auto;
  	}
    .info_title {
    	font-size: 20px;
    	font-weight: 800;
    }
    .info_head {
    	font-weight: 800;
    }
    .info_body {
    	height: 50px;
    }
  	pre {
  		white-space: pre-wrap;
  		word-wrap: break-word;
  	}	
</style>

</head>
<body>
<%-- Preloader & Header --%>
<%@ include file="/front-end/Jessica/common/header.jsp"%>
<!--------------------------------------------------------------------------------------------------->

<!----- Main Banner Area ----->
<div class="main-banner" id="top">
    <!-- Banner Img -->
    <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/course-banner.png" id="bg-video">
    <div class="video-overlay header-text">
	    <!-- 麵包屑 -->
	    <div class="caption">
	        <h2>COURSE <em>DETAILS</em></h2>
	        <nav aria-label="breadcrumb">
		        <ol class="breadcrumb justify-content-center">
		            <li class="breadcrumb-item"><a href="#">Home</a></li>
		            <li class="breadcrumb-item"><a href="#">COURSE</a></li>
		            <li class="breadcrumb-item active" aria-current="page">COURSE DETAILS</li>
		        </ol>
	        </nav>
	    </div>
    </div>
</div>

<!--------------------------------------------------------------------------------------------------->

<!----- 課程內容 開始↓ ----->
<section class="section" id="section-content">
    <div class="container">
        <div class="row justify-content-between">

        <!-- Post Content Column -->
        <div class="col-lg-8">

            <!-- Title -->
            <div class="section-heading">
                <h2 class="mt-4">${courseVo.cname}
                    <span class="badge badge-pill badge-theme"><em>${exptypeSvc.getOneExptype(courseVo.exp_id).exp_Name}</em></span>
                </h2>
            </div>

            <!-- 圖片聯播 -->
            <div id="course-img-controls" class="carousel slide" data-ride="carousel" style="box-shadow: 0px 0px 15px rgba(0,0,0,0.1)">
                <!-- 圖片src -->
                <div class="carousel-inner">
                    <div class="carousel-item course-img active">
                        <img src="${pageContext.request.contextPath}/jessica/img.do?course_id=${courseVo.course_id}&picIndex=1" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item course-img">
                        <img src="${pageContext.request.contextPath}/jessica/img.do?course_id=${courseVo.course_id}&picIndex=2" class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item course-img">
                        <img src="${pageContext.request.contextPath}/jessica/img.do?course_id=${courseVo.course_id}&picIndex=3" class="d-block w-100" alt="...">
                    </div>
                </div>
                <!-- 圖片控制 -->
                <a class="carousel-control-prev" href="#course-img-controls" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                </a>
                <a class="carousel-control-next" href="#course-img-controls" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                </a>
            </div>

            <!-- 課程 -->
            <div class="card course-card" style="box-shadow: 0px 0px 15px rgba(0,0,0,0.1)">
                <!-- 分頁header -->
                <div class="card-header border-bottom"  id="card-link">
                    <ul class="nav nav-tabs card-header-tabs" role="tablist">
                        <li class="nav-item"><a class="nav-link active" data-toggle="tab" role="tab" id="myTab1" href="#myTab1Val" aria-controls="myTab1Val" aria-selected="true">課程簡介</a></li>
                        <li class="nav-item"><a class="nav-link" data-toggle="tab" role="tab" id="myTab2" href="#myTab2Val" aria-controls="myTab2Val" aria-selected="false">課程評價</a></li>
                        <li class="nav-item"><a class="nav-link" data-toggle="tab" role="tab" id="myTab4" href="#myTab4Val" aria-controls="myTab4Val" aria-selected="false">課程地點</a></li>
                        <li class="nav-item"><a class="nav-link" data-toggle="tab" role="tab" id="myTab5" href="#myTab5Val" aria-controls="myTab5Val" aria-selected="false">授課師資</a></li>
                    </ul>
                </div>
                
                <!-- 課程內容 -->
                <div class="card-body">
                    <div class="tab-content">
                        <!-- 1課程簡介 -->
                        <div class="tab-pane fade show active" role="tabpanel" id="myTab1Val" aria-labelledby="myTab1">
                            <h5 class="card-title text-danger">課程簡介</h5>
                            <pre class="intro">${courseVo.intro}</pre>
                        </div>

                        <!-- 2課程評論 -->
                        <div class="tab-pane fade" role="tabpanel" id="myTab2Val" aria-labelledby="myTab2">
                            <span class="card-title text-danger">課程評價　</span>
							<span id="starArea"></span>                            
                            <span id="com_star_show"></span> (<span id="com_count_show"></span>則評論)
                            <hr>
                            <c:set var="com_count" value="0" />
                            <c:set var="com_star_sum" value="0" />
                            <c:forEach var="courseOrderVo" items="${courseSvc.getCOrderFromCourse(courseVo.course_id)}">
	                            <c:if test="${not empty courseOrderVo.com_star && courseOrderVo.com_star != 0}">	                            
		                            <div class="card-text">	                               
		                                <div class="media mb-4">		                                    
		                                    <div class="media-body">
		                                        <span class="mt-0">${memberSvc.getOneMem(courseOrderVo.member_id).mem_Name}</span>
		                                        <i class="fa fa-star text-warning" aria-hidden="true"></i> ${courseOrderVo.com_star}
		                                        <p class="text-justify">${courseOrderVo.com_content}</p>
		                                        <small class="text-muted　text-right">Posted by ${courseOrderVo.com_date}</small>
		                                    </div>
		                                </div>
		                            </div>
		                            <c:set var="com_count" value="${com_count+1}" />
		                            <c:set var="com_star_sum" value="${com_star_sum + courseOrderVo.com_star}" />
	                            </c:if>
                            </c:forEach>
                            <input type="hidden" id="com_count" name="com_count" value="${com_count}">
                            <input type="hidden" id="com_star_avg" name="com_star_avg" value="${com_star_sum/com_count}">
                        </div>

                        <!-- 4上課地點 -->
                        <div class="tab-pane fade" role="tabpanel" id="myTab4Val" aria-labelledby="myTab4">
                            <span class="card-title text-danger">上課地點</span>　
                            <span id="loc"></span>
                            <hr>
                            <!-- google map -->
                            <div id="map"></div>
                        </div>

                        <!-- 5教練資訊 -->
                        <div class="tab-pane fade" role="tabpanel" id="myTab5Val" aria-labelledby="myTab5">
                            <div class="card-text p-3">
                                <h5 class="card-title" style="display:inline;">
                                	<a class="text-danger" href="${pageContext.request.contextPath}/front-end/coachInfo.jsp?coach_Id=${courseVo.coach_id}">
                                		${memberSvc.getOneMem(coachSvc.getOneByCoach(courseVo.coach_id).member_Id).mem_Name}
                                	</a>
                                </h5>　
                                <span><a class="text-info" href="${pageContext.request.contextPath}/front-end/MyCalendar.jsp?coach_Id=${courseVo.coach_id}">(查看教練時刻表)</a></span>
                                <hr>
                                <span class="card-title text-danger">教練經歷</span><br>
                                <img class="py-3" src="${pageContext.request.contextPath}/front-end/Jessica/static/img/line-dec.png">
                                <pre class="text-justify">${coachSvc.getOneByCoach(courseVo.coach_id).experience}</pre>

                                <div class="media my-4 col" style="width:50%;">
                                	<img class="d-flex mr-3 img-fluid" src="${pageContext.request.contextPath}/front-end/ShowPhotos?type=coachPic&img_no=${courseVo.coach_id}" alt="">
                                </div>

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <!-- Sidebar Widgets Column -->
        <div class="col-lg-4 div-right">
            <div class="section-heading"><h4 class="mt-4 course-set-header font-weight-bold">課程方案</h4></div>
            <div class="text-center mb-3"><img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/line-dec.png" alt=""></div>

            <div id="tabs" class="p-4 bg-light">
                <ul class="list-group mb-3">
                	<c:forEach var="courseSetVo" items="${courseSetSvc.getAllCourseSetByCourse_id(courseVo.course_id)}">
	                    <li class="list-group-item d-flex justify-content-between lh-condensed" id="${courseSetVo.set_id}">
	                        <div>
	                            <h6 class="my-0">${courseSetVo.lesson}堂</h6>
	                            <small class="text-muted">
	                            	<fmt:formatNumber value="${courseSetVo.price}" type="currency" maxFractionDigits="0"/>
	                            </small>
	                        </div>

	                        <span>
	                            <span class="small">平均 </span>
	                            <span class="theme-colors">
	                            	<fmt:formatNumber value="${courseSetVo.price/courseSetVo.lesson}" type="currency" maxFractionDigits="0" />
	                            </span>
	                            <span class="small"> /堂</span>
	                        </span>
	                    </li>
                    </c:forEach>
                </ul>
                <div class="row align-items-center" id="cartRow">
                	<c:if test="${not sessionScope.courseCart.isCartItem(param.course_id)}">                	
                    	<div class="col-12 main-rounded-button my-2"><a id="goCart" href="#">直接購買</a></div>                    
                    	<div class="col-12 main-rounded-button my-2"><a id="addCartItem" href="#">加入購物車</a></div>
                    </c:if>
                	<c:if test="${sessionScope.courseCart.isCartItem(param.course_id)}">
                    	<div class="col-12 main-rounded-button my-2"><a id="goCart2" href="${pageContext.request.contextPath}//front-end/Jessica/course/basket.jsp">已加入購物車，結帳去!</a></div>
                    </c:if>
                </div>
				
				<hr>
				<div class="row align-items-center px-3" id="contact_row">
					<div class="col-3">
						<img class="rounded-circle img-fluid float-left" src="${pageContext.request.contextPath}/front-end/ShowPhotos?type=coachPic&img_no=${courseVo.coach_id}">
					</div>
					<div class="col-5 theme-colors">
						<a href="${pageContext.request.contextPath}/front-end/coachInfo.jsp?coach_Id=${courseVo.coach_id}">
							${memberSvc.getOneMem(coachSvc.getOneByCoach(courseVo.coach_id).member_Id).mem_Name}
						</a>
					</div>
					<div class="col-2 contactCoach">
					<form id="coachchat" action="<%=request.getContextPath() %>/chat.do" method="POST">
						<i class="fa fa-comment-o" aria-hidden="true" onclick="coachchatClick()">
						<input id="memId" name="memId" class="text-field" type="hidden" value="${memLogIn.member_Id}"/>
			            <input id="reciver" name="reciver" class="text-field" type="hidden" value="${courseVo.coach_id}"/>
			            </i>
					</form>
					</div>
					<div class="col-2 addHeart">
						<input type="hidden" name="course_id" value="${courseVo.course_id}" />
						<input type="hidden" name="member_id" value="${sessionScope.memLogIn.member_Id}" />
						<c:if test="${ empty courseFavorSvc.getOneCourseFavor(sessionScope.memLogIn.member_Id, courseVo.course_id) }">
							<input type="hidden" name="isFavor" value="0" />
							<i class="fa fa-heart-o" aria-hidden="true"></i>
						</c:if>
						<c:if test="${ not empty courseFavorSvc.getOneCourseFavor(sessionScope.memLogIn.member_Id, courseVo.course_id) }">
							<input type="hidden" name="isFavor" value="1" />
							<i class="fa fa-heart" aria-hidden="true"></i>
						</c:if>	                                
					</div>
				</div>           
            </div>     
      
        </div>
        
    </div>
  </div>
</section>

<!--------------------------------------------------------------------------------------------------->

<%-- 沒登入跳窗 --%>
<%@ include file="/front-end/Jessica/common/noLogInModal.jsp"%>
<%-- footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>
<%-- socket --%>
<%@ include file="/front-end/Jessica/common/socket.jsp"%>

<!--------------------------------------------------------------------------------------------------->
<script type="text/javascript">
window.onload = function() {
	connect();
	init();
};

var locArr = '${courseVo.loc}'.split(',');
var initLat = Number(locArr[0]);
var initLng = Number(locArr[1]);
var address = locArr[2];

$(document).ready(function(){
	$('#loc').text(address);
});

//-------
//聊天室
function coachchatClick(){
	$('#coachchat').submit();
}
//-------
$('#addCartItem').click(function(){
	var course_id = '${param.course_id}';	
	console.log(course_id);
	
	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseCart.do?action=ajaxEditCart',
		 data: {'course_id':course_id},
		 success: function (data){
			if(data === 'addCartItemSucess'){
				$('#cartRow').html('<div class="col-12 main-rounded-button my-2"><a id="goCart2" href="#">已加入購物車，結帳去!</a></div>');
				swal('成功', '成功加入課程購物車！', 'success');
				
				$('#goCart2').click(function(){
					location.href = "${pageContext.request.contextPath}//front-end/Jessica/course/basket.jsp"; 
				});
			}else{
				console.log('不可能');
			}
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	}); 
})

$('#goCart').click(function(){
	var course_id = '${param.course_id}';	
	console.log(course_id);
	
	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseCart.do?action=ajaxEditCart',
		 data: {'course_id':course_id},
		 success: function (data){
			if(data === 'addCartItemSucess' || 'updateCartItemSetSucess'){
				location.href = "${pageContext.request.contextPath}/front-end/Jessica/course/basket.jsp";
			}else{
				console.log('不可能');
			}
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	}); 
});

//-----------------------------------------
// 評論區星星
$(document).ready(function() {
	var com_star_avg = $('#com_star_avg').val();
	var starRound = Math.round(com_star_avg * 10) / 10;
	var starInt = Math.round(com_star_avg);
	var com_count = $('#com_count').val();
	
	if(com_count > 0){
		$('#com_star_show').text(starRound);
	}else{
		$('#com_star_show').text('尚無評價');
	}
	$('#com_count_show').text(com_count);

	
	for(var i=0; i<starInt; i++){
		$('#starArea').append('<i class="fa fa-star text-warning" aria-hidden="true"></i> ');
	}
	for(var i=starInt; i<5; i++){
		$('#starArea').append('<i class="fa fa-star-o text-warning" aria-hidden="true"></i> ');
	}
});

//-----------------------------------------
// 假的檢舉	
$('.reportCourse').click(function(){
    var val = $(this).val();
    if(val % 2 === 0){
        $(this).html('<i class="fa fa-thumbs-down" aria-hidden="true"></i>');
        $(this).css("color","#ed563b");
        $(this).val('1');

        swal({
          title: "檢舉課程",
          text: "請輸入檢舉原因：",
          type: "input",
          showCancelButton: false,
          closeOnConfirm: false,
          allowOutsideClick: true,
          animation: "slide-from-top",
          inputPlaceholder: "輸入一些話"
        },
        function(inputValue){
          if (inputValue === false) returnfalse;
          
          if (inputValue === "") {
            swal.showInputError("你需要輸入檢舉原因！");
            return false
          }
          
          swal("成功！", "你成功檢舉了課程代碼：${param.course_id}", "success");
        });

    }else{
        swal('錯誤！', '您已檢舉過', 'error');
    };                    
});

//-----------------------------------------
//愛心特效變化

$('.addHeart').click(function(){
	var heart = $(this).children('i');
	
 var course_id = $(this).children('input[name="course_id"]');
 var member_id = $(this).children('input[name="member_id"]');
 var isFavor = $(this).children('input[name="isFavor"]');
 var args = {'course_id' : $(course_id).val(),
 			'member_id' : $(member_id).val() };
 console.log(args);

	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseFavor.do?action=ajaxEditFavor&location='+location,
		 data: args,
		 success: function (data){
			if(data === 'NoLogIn'){
				$('#NoLogInModal').modal('show');		
			}
			if(data === 'addSuccess'){
				$(heart).attr('class','fa fa-heart');
			    $(isFavor).val<('1');
				swal('成功', '成功加入課程最愛！', 'success');
			}else if(data === 'deleteSuccess'){
				$(heart).attr('class','fa fa-heart-o');
				$(isFavor).val('0');
				swal('成功', '成功刪除課程最愛！', 'info');
			}
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	});          
});

//-----------------------------------------
// 去登入
$('#goToLogIn').click(function(){	
	window.location.href = "${pageContext.request.contextPath}/front-end/logIn2.jsp";	
});

//-----------------------------------------
// googleMap
var map;
function init() {
   	var latlng = new google.maps.LatLng(initLat, initLng); 
   	map.setCenter(latlng);
   	
    var marker = new google.maps.Marker({ // 紅點
        position: { lat: initLat, lng: initLng },
        map: map,
        draggable: false // true、false可否拖拉
    });
     
    var localContent = '<div class="info_title">' + address + '</div>'
    				 + '<div class="info_body">'
    				 + '<div><span class="info_head">地址: </span>' + address + '</div>'
    				 + '<div><span class="info_head">經緯度: </span>(' + initLat + ', ' + initLng + ')</div>'
    				 + '</div>';
    
    var infowindow = new google.maps.InfoWindow({
        content: localContent
    });
    infowindow.open(map, marker);
}

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: 24.9576852, lng: 121.2250143 },
        zoom: 14,
    });     
}

</script>

</body>
</html>
</c:if>