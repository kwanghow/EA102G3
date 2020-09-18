<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, com.course_order.model.*"%>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>

<c:if test="${empty orderlistByCoach_id}">
	<jsp:forward page="/course/courseOrder.do">
		<jsp:param name="action" value="getOrderlistByCoach_id" />
	</jsp:forward>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜私人課程教練銷售列表</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<style>
    /*裁切課程相片*/
    .course-img{
        width: 128px;
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

    /*訂單狀態選項*/
    #order-state ul li{
        margin-bottom: 30px; display: inline-block; width: 100%;
    }
    #order-state ul li a{
    text-transform: capitalize;
        width: 100%;
        padding: 10px 10px;
        display: inline-block;
        background-color: #fff;
        border: none;
        box-shadow: 0px 0px 15px rgba(0,0,0,0.1);
        border-radius: 5px;
        font-size: 16px;
        letter-spacing: 0.5px;
        font-weight: 600;
        transition: all 0.3s;
        cursor: pointer;
    }
    #order-state ul li a i{ 
        font-size: 35px; 
        margin: 10px;
    }
    .nav-tabs { border: none; }
    .nav-tabs  .nav-link, .nav-tabs { color: #232d39; }
    .nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active { color: #007bff; }

    /*課程分類的膠囊樣式*/
    .badge-theme{
        background-color: transparent;
        color: #ed563b;
        border: 1px solid #ed563b;
    }
    /*課程右邊的價格與堂數*/
    .order-right{
        border:1px solid orange;
        border-radius: 5px;
    }
     .order-content .col .card .card-footer .whiteBtn{
        background-color: white;
        color: #ed563b;
        border: 1px solid #ed563b;
    }

    .card-footer {
        background-color: transparent; 
        border-top: 0px; 
    }

    .star-area li{
        list-style: none;
        float: left;
        font-size: 36px;
        color: #F4DA40;
        cursor: pointer;
        display: inline-block;
    }
    .star-area ul{
        /*display: inline-block;*/
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
            <h2>銷售<em>紀錄</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">首頁</a></li>
                    <li class="breadcrumb-item"><a href="#">教練專區</a></li>
                    <li class="breadcrumb-item"><a href="#">銷售列表</a></li>
                    <li class="breadcrumb-item active" aria-current="page">私人課程銷售列表</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<section class="section" id="section-content">
  <div class="container">

    <!-- 訂單狀態 開始↓ -->
    <div id="order-state">
        <ul class="row nav nav-tabs text-center" role="tablist">
            <li class="col-md nav-item">
                <a class="nav-link ${(empty param.order_state || param.order_state == '') ?'active':'' }" id="all">
                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i><br>
                    <span>全部</span>
                </a>
            </li>
            <li class="col-md nav-item">
                <a class="nav-link ${(param.order_state == '0') ?'active':'' }" id="0">
                    <i class="fa fa-exclamation-triangle" aria-hidden="true"></i><br>
                    <span>待確認</span>
                </a>
            </li>
            <li class="col-md nav-item">
                <a class="nav-link ${(param.order_state == '2') ?'active':'' }" id="2">                    
                    <i class="fa fa-clock-o" aria-hidden="true"></i><br>
                    <span>等待學員付款</span>
                </a>
            </li>
            <li class="col-md nav-item">
                <a class="nav-link ${(param.order_state == '3') ?'active':'' }" id="3">
                    <i class="fa fa-check-circle-o" aria-hidden="true"></i><br>
                    <span>尚未完課</span>
                </a>
            </li>
            <li class="col-md nav-item">
                <a class="nav-link ${(param.order_state == '4') ?'active':'' }" id="4">
                    <i class="fa fa-check-circle" aria-hidden="true"></i><br>
                    <span>訂單完成</span>
                </a>
            </li>
            <li class="col-md nav-item">
                <a class="nav-link ${(param.order_state == '1') ?'active':'' }" id="1">
                    <i class="fa fa-times-circle" aria-hidden="true"></i><br>
                    <span>訂單取消</span>
                </a>
            </li>
        </ul>
    </div>
    <!-- 訂單狀態 結束↑ -->
    
    <!-- 訂單內容 開始↓ -->
    <c:if test='${orderlistByCoach_id == "noResult"}'>
	    <div class="row my-5 order-content text-center">
	        <div class="col my-5 tab-content">
	        	<img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/no-order-b.svg">
	        	<span>您目前尚無訂單，前往<a href="${pageContext.request.contextPath}/front-end/Jessica/coach/course_productList.jsp">我的商品列表</a></span>
	        </div>
	    </div>
    </c:if>
    
    <c:if test='${orderlistByCoach_id != "noResult"}'>    
    <%-- 頁數 --%>
    <% List<COrderViewVo> list = (List<COrderViewVo>)request.getAttribute("orderlistByCoach_id"); %>
    <%@ include file="pages/page1.file" %>
    
	<c:forEach var="orderVo" items="${orderlistByCoach_id}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
    <div class="row my-4 order-content">
        <div class="col tab-content">
            <div class="card" id="${orderVo.order_id}">
            <!-- header -->
                <div class="card-header">
                    <div class="row mx-0">
			                        訂單編號： <span>${orderVo.order_id}</span> <div class="col-1"></div>
			                        訂單日期： <span><fmt:formatDate value="${orderVo.initstamp}" pattern="yyyy-MM-dd HH:mm:ss"/></span> <div class="col-1"></div>
			                        訂單狀態： 
                        <c:if test="${orderVo.state == 0}">
                        	<span>等待我的確認</span>
                        </c:if>
                        <c:if test="${orderVo.state == 2}">
                        	<span>等待學員付款</span>
                        </c:if>
                        <c:if test="${orderVo.state == 3}">
                        	<span>訂單完成(未完課)</span>
                        </c:if>
                        <c:if test="${orderVo.state == 4}">
                        	<span>訂單完成(已完課)</span>
                        </c:if>
                        <c:if test="${orderVo.state == 1}">
                        	<span>訂單取消</span>
                        </c:if>
                    </div>
                </div>
			<!-- body -->
                <div class="card-body">
                    <div class="row mr-0 px-3">
                        <div class="col-2 align-self-center course-img">
                            <img class="img-fluid" src="${pageContext.request.contextPath}/jessica/img.do?course_id=${orderVo.course_id}&picIndex=1">
                        </div>
                        <div class="col-6 align-self-center">                        	 
                            <h5 class="card-title">
                            	<a href="${pageContext.request.contextPath}/front-end/Jessica/course/intro.jsp?course_id=${orderVo.course_id}" class="theme-colors font-weight-bold">
                            		${orderVo.cname} 
                            	</a>                            	
                            </h5>                            
                            <span class="card-text badge badge-pill badge-theme">${orderVo.exp_name}</span>　
                            <span class="small">from ${memberSvc.getOneMem(orderVo.member_id).mem_Name}</span>           
                        </div>
                        <div class="card-text col-4 align-self-center p-2 order-right">
                            <div class="mb-1">
                               	 訂單金額：<span><fmt:formatNumber value="${orderVo.total_price}" type="currency" maxFractionDigits="0"/></span>
                                <small class="text-muted">(查看明細)</small>
                            </div>
                            <div class="mt-1">堂　　數：已約 <span>${orderVo.book_lesson}</span>堂，共 <span>${orderVo.lesson}</span>堂</div>                            
                        </div>
                    </div>
                </div>
             <!-- footer -->
             <c:if test="${orderVo.state != 1}">
	             <div class="card-footer mr-3 mb-3">								            
					<div class="row align-self-center justify-content-end"  id="${orderVo.member_id}">	
						<%-- 0:等待教練確認 --%>				
						<c:if test="${orderVo.state == 0}"> 		
							<div class="col-2 contact-form">
								<button class="btn btn-primary btn-lg btn-block whiteBtn backBtn">拒絕訂單</button>
							</div>
							<div class="col-2 contact-form">
								<button class="btn btn-primary btn-lg btn-block acceptBtn">接受訂單</button>
							</div>					
						</c:if>
						<%-- 4:訂單完成(已完課) --%>  
						<c:if test="${orderVo.state == 4}"> 							  
							<div class="col-2 contact-form">
								<button class="btn btn-primary btn-lg btn-block whiteBtn comBtn" data-toggle="modal" data-target="#com${orderVo.order_id}">查看評價</button>
							</div>							
						</c:if>
						<%-- 都有聯絡教練 --%>  
						<div class="col-2 contact-form">
							<form id="coachchat" action="<%=request.getContextPath() %>/chat.do" method="POST">
								<button class="btn btn-primary btn-lg btn-block checkOutBtn" type="submit">聯絡學員</button>
								<input name="memId" class="text-field" type="hidden" value="${orderVo.coach_id}"/>
					            <input name="reciver" class="text-field" type="hidden" value="${orderVo.member_id}"/>
							</form>		
						</div>			
					</div>
	            </div>
            </c:if>
        </div>
    </div>
    </div>  
    <!-- 訂單內容 結束↑ -->
    
    <!-- Modal -->    
	<div class="modal fade" id="com${orderVo.order_id}" tabindex="-1" role="dialog" aria-labelledby="commentAreaTitle" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="commentAreaTitle">訂單${orderVo.order_id}的評價</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	            </div>
	            
	            <c:if test="${empty orderVo.com_star || orderVo.com_star == 0}">
	            	<div class="modal-body">
	            		尚無評論
	            	</div>
	            </c:if>
	                        
		        <c:if test="${not empty orderVo.com_star && orderVo.com_star != 0}">    
		            <div class="modal-body">
		                <label for="star-time">評分日期</label>
		                <div class="star-time">${orderVo.com_date}</div>
		            </div>
		            <div class="modal-body">
		                <label for="star-area">評分</label>
		                <div class="star-area"><ul>
			                <c:forEach begin="1" end="${orderVo.com_star}">
			                	<li>★</li>
			                </c:forEach>
			                <c:forEach begin="${orderVo.com_star+1}" end="5">
			                	<li>☆</li>
			                </c:forEach>
						</ul></div>
		            </div>
		            <div class="modal-body">
		                <div class="form-group text-left">
		                    <label for="comment-area">評論內容</label>
		                    <textarea class="form-control" rows="3" name="com_content" readonly>${orderVo.com_content}</textarea>
		                </div>
		            </div>
		        </c:if>
		        
	            <div class="modal-footer">
	                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	            </div>
	        </div>
	    </div>
	</div>
	<!-- Modal -->
    
    </c:forEach>
    <%@ include file="pages/page2.file" %> 
    </c:if>
      

  </div>
</section>

<!--------------------------------------------------------------------------------------------------->

<%-- footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>
<%-- socket --%>
<%@ include file="/front-end/Jessica/common/socket.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script type="text/javascript">
window.onload = function() {
	connect();
};

<%-- 錯誤表列 --%>
<c:if test="${not empty msgs}"> 
	$('#msgsModal').modal('show');
</c:if>

//----------------------------------------------
// 選訂單狀態
$('.nav-link').click(function(){
	var orderState = $(this).attr('id');
	if(orderState == 'all'){
		window.location.href = "${pageContext.request.contextPath}/course/courseOrder.do?action=getOrderlistByCoach_id";
	}else{
		window.location.href = "${pageContext.request.contextPath}/course/courseOrder.do?action=getOrderlistByCoach_id&order_state="+orderState;
	}
});

//----------------------------------------------
// 取消訂單
$('.backBtn').click(function(e){
	e.preventDefault();
	var order_id = $(this).parent().parent().parent().parent().attr('id');
	var member_id = $(this).parent().parent().attr('id');
	
    swal({
        title: "確定拒絕此訂單嗎？",
        html: "按下確定後無法恢復訂單狀態",
        type: "warning", // type can be "success", "error", "warning", "info", "question"
        showCancelButton: true,
        showCloseButton: true,
    }).then(function(result) {
        if (result) {
        	var obj = {				 						
 					"sender" : self,
 					"receiver" : member_id,
 					"message" : "你的訂單["+order_id+"]被教練取消了",
 					"url" : "${pageContext.request.contextPath}/front-end/Jessica/member/course_orders.jsp"
 				};
	 		webSocket.send(JSON.stringify(obj));
        	window.location.href = '${pageContext.request.contextPath}/course/courseOrder.do?action=cancelOrder2&order_id='+order_id;
        }
    }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
        swal("再考慮一下！", "","info");
    }).catch(swal.noop);
});

//----------------------------------------------
//接受訂單
$('.acceptBtn').click(function(e){
	e.preventDefault();
	var order_id = $(this).parent().parent().parent().parent().attr('id');
	var member_id = $(this).parent().parent().attr('id');
	
 swal({
     title: "確定接受此訂單嗎？",
     html: "按下確定後無法恢復訂單狀態",
     type: "question", // type can be "success", "error", "warning", "info", "question"
     showCancelButton: true,
     showCloseButton: true,
 }).then(function(result) {
     if (result) {
     	var obj = {				 						
					"sender" : self,
					"receiver" : member_id,
					"message" : "你的訂單["+order_id+"]被教練接受了，可以開始約課囉",
					"url" : "${pageContext.request.contextPath}/front-end/Jessica/member/course_orders.jsp"
				};
 		webSocket.send(JSON.stringify(obj));
     	window.location.href = '${pageContext.request.contextPath}/course/courseOrder.do?action=acceptOrder&order_id='+order_id;
     }
 }, function(cancel) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
     swal("再考慮一下！", "時間不等人喔","info");
 }).catch(swal.noop);
});

</script>

</body>
</html>