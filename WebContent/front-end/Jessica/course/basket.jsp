<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<jsp:useBean id="courseFavorSvc" scope="page" class="com.course_favor.model.CourseFavorService"/>

<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜課程購物車</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<style>
    /*裁切課程相片*/
    .course-img{
        width: 162px;
        height: 0;
        padding-bottom: 120px;
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
    button a{
        color: white;
    }
    #backBtn{
        background-color: white;
        color: #ed563b;
        border: 1px solid #ed563b;
    }
    #step{
        margin-top: 60px;
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
            <h2>COURSE <em>CHECKOUT</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item"><a href="#">COURSE</a></li>
                    <li class="breadcrumb-item active" aria-current="page">COURSE CHECKOUT</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<!----- Content Area ----->
<section class="section" id="section-content">
  <div class="container bg-light">
    <div class="row my-5"></div> <!-- 撐高度用 -->

    <!----- 購物車 ----->

    <!-- 購物車小標 -->
    <div class="row mt-4">
        <div class="col-lg-6 offset-lg-3"><div class="section-heading mb-0">
            <h2>Your<em> Cart</em></h2>
            <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/line-dec.png" alt="">
        </div></div>
    </div>
    <!-- 購物車課程 -->
    <div class="row mx-4 mt-2">
        <table class="table bg-white py-4 mt-3">
            <thead>
                <tr>
                    <th colspan="2">課程</th>
                    <th>堂數</th>
                    <th>單堂價格</th>
                    <th>小計</th>
                    <th>刪除</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${empty sessionScope.courseCart.items}">
            		<tr class="py-5">
            			<td class="py-5 text-center" colspan="6">
            				<img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/empty-cart.svg">
            				<div>尚無任何課程，您可選擇 <a href="${pageContext.request.contextPath}/front-end/Jessica/course/explore.jsp">繼續逛課</a>，
            				或從<a href="${pageContext.request.contextPath}/front-end/Jessica/member/course_collect.jsp">課程最愛</a>加入喔！</div>
            			</td>
            		</tr>
            	</c:if>
            	<c:forEach var="entry" items="${sessionScope.courseCart.items}">
	                <tr id="${entry.value.course_id}">
	                    <!-- 課程圖片 -->
	                    <td> 
	                        <div class="course-img">
	                            <img class="img-fluid" src="${pageContext.request.contextPath}/jessica/img.do?course_id=${entry.value.course_id}&picIndex=1" alt=""> <% //圖片再說  %>
	                        </div>                                
	                    </td>
	                    <!-- 課程名稱 -->
	                    <td class="theme-colors pl-0">
	                    	<a href="${pageContext.request.contextPath}/front-end/Jessica/course/intro.jsp?course_id=${entry.value.course_id}">
	                    		<strong class="theme-colors">${entry.value.cname}</strong>
	                    	</a>
	                    </td>
	                    <!-- 課程方案 -->
	                    <td>
	                        <div class="form-group">
	                            <select class="form-control setChoose">
	                            	<c:forEach var="courseSetVo" items="${courseSetSvc.getAllCourseSetByCourse_id(entry.value.course_id)}">  
		                                <option value="${courseSetVo.set_id}" ${entry.value.set_id == courseSetVo.set_id ? 'selected="selected"':''}>${courseSetVo.lesson} 堂</option>
	                                </c:forEach>	                                
	                            </select>
	                        </div>
	                    </td>
	                    <!-- 單堂價格 -->
	                    <td>
	                    	<c:set var="firstSet" value="${courseSetSvc.getAllCourseSetByCourse_id(entry.value.course_id).get(0)}"/>
	                        <div class="text-right"><del><fmt:formatNumber value="${firstSet.price div firstSet.lesson}" type="currency" maxFractionDigits="0"/>/堂</del></div>
	                        <div class="text-right"><span class="text-danger oneLessonPrice"><fmt:formatNumber value="${entry.value.order_price div entry.value.lesson}" type="currency" maxFractionDigits="0"/>/堂</span></div>
	                    </td>
	                    <!-- 課程總價 -->
	                    <td class="text-right order_price"><fmt:formatNumber value="${entry.value.order_price}" type="currency" maxFractionDigits="0"/></td>
	                    <!-- 垃圾桶 -->
	                    <td class="trash"><i class="fa fa-trash-o" aria-hidden="true"></i></td>
	                </tr>
                </c:forEach>
            </tbody>
            <tfoot>
            	<tr>
            		<td colspan="5">
            			<div class="text-right"><strong>總金額　<span id="totalprice"><fmt:formatNumber value="${sessionScope.courseCart.totalprice}" type="currency" maxFractionDigits="0"/></span></strong></div>
            		</td>
            	</tr>
            </tfoot>
        </table>
    </div>

    <!-- RPG選項 -->
    <div class="row mx-4 mt-1 mb-5 py-3 justify-content-end">
        <div class="col-3 contact-form">
            <a href="${pageContext.request.contextPath}/front-end/Jessica/course/explore.jsp">
            	<button class="btn btn-primary btn-lg btn-block" id="backBtn">繼續逛課</button>
            </a> 
        </div>
        <c:if test="${not empty sessionScope.courseCart.items}">
	        <div class="col-3 mb-5 contact-form">
	            <a href="#payment"><button class="btn btn-primary btn-lg btn-block" id="goCheckoutBtn">確定送出訂單</button></a>
	        </div>
        </c:if>
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
};

$('.setChoose').change(function(){
	var tr = $(this).parent().parent().parent();	
	var oneLessonPrice = $(tr).children(':nth-child(4)').children(':nth-child(2)').children();	
	var order_price = $(tr).children(':nth-child(5)');
	
	var course_id = $(tr).attr('id');
	var set_id = $(this).val();
	
	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseCart.do?action=ajaxEditCart',
		 data: {'course_id':course_id, 'set_id':set_id},
		 dataType: 'json',
		 success: function (data){
			$(oneLessonPrice).text('NT$'+data.oneLessonPrice+'/堂');
			$(order_price).text('NT$'+data.order_price);
			$('#totalprice').text('NT$'+data.totalprice);
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	});
});

$('.trash').click(function(){
	var tr = $(this).parent();
	var course_id = $(tr).attr('id');
	
	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseCart.do?action=ajaxDeleteCartItem',
		 data: {'course_id':course_id},
		 success: function (data){
			 $(tr).remove();
			 $('#totalprice').text('NT$'+data);
			 swal('成功', '成功刪除！', 'info');			 		 
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	});
});

$('#goCheckoutBtn').click(function(){
	<c:if test="${empty memLogIn}">
		$('#NoLogInModal').modal('show');
		<c:set var="location" value="${pageContext.request.requestURI}" scope="session"/>
	</c:if>
	<c:if test="${not empty memLogIn}">		
		swal({
			  title: "確定送出訂單嗎？",
			  text: "你將等待教練的確認！可於訂單列表查詢訂單進度😘", 
			  type: "info",
			  showCancelButton: true,
			  confirmButtonText: "確定送出！", 
			  cancelButtonText: "再考慮看看..."
			}).then(function() {
			 	 $.ajax({
			 		 type: 'POST',
			 		 url: '${pageContext.request.contextPath}/course/courseOrder.do',
			 		 data: {'action':'ajaxCreateOrder'},
			 		 dataType: 'json',
			 		 success: function (data){
			 			 for(var i=0; i<data.length; i++){
				 			var obj = {				 						
				 					"sender" : self,
				 					"receiver" : data[i],
				 					"message" : "你有一則新訂單，請至銷售列表回覆學員是否接受訂單",
				 					"url" : "${pageContext.request.contextPath}/front-end/Jessica/coach/course_salesList.jsp"
				 				};
					 		webSocket.send(JSON.stringify(obj));
					 		swal('成功!','成功送出訂單','success');
					 		window.location.href = "${pageContext.request.contextPath}/front-end/Jessica/member/course_orders.jsp";
			 			 }
			 		 },
			 		 error: function(xhr, ajaxOptions, thrownError){
			 		     console.log(xhr.status);
			 		     console.log(thrownError);
			 		 }
			 	});
			}, function(dismiss) {
			  if (dismiss === 'cancel') {
			    swal('取消送出！','期待你的送出','info'); 
			  } 
			})
	</c:if>

});

$('#goToLogIn').click(function(){	
	window.location.href = "${pageContext.request.contextPath}/front-end/logIn2.jsp";	
});

</script>

</body>
</html>