<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*, com.course_order.model.*"%>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>

<c:if test="${empty orderlistByMember_id}">
	<jsp:forward page="/course/courseOrder.do">
		<jsp:param name="action" value="getOrderlistByMember_id" />
	</jsp:forward>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜私人課程訂單列表</title>
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
            <h2>訂單<em>紀錄</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">首頁</a></li>
                    <li class="breadcrumb-item"><a href="#">會員專區</a></li>
                    <li class="breadcrumb-item"><a href="#">訂單</a></li>
                    <li class="breadcrumb-item active" aria-current="page">私人課程訂單</li>
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
                    <i class="fa fa-clock-o" aria-hidden="true"></i><br>
                    <span>等待教練確認</span>
                </a>
            </li>
            <li class="col-md nav-item">
                <a class="nav-link ${(param.order_state == '2') ?'active':'' }" id="2">
                    <i class="fa fa-credit-card" aria-hidden="true"></i><br>
                    <span>等待付款</span>
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
    <c:if test='${orderlistByMember_id == "noResult"}'>
	    <div class="row my-5 order-content text-center">
	        <div class="col my-5 tab-content">
	        	<img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/no-order-b.svg">
	        	<span>您目前尚無訂單，<a href="${pageContext.request.contextPath}/front-end/Jessica/course/explore.jsp">前往逛課</a></span>
	        </div>
	    </div>
    </c:if>
    
    <c:if test='${orderlistByMember_id != "noResult"}'>
    <% List<COrderViewVo> list = (List<COrderViewVo>)request.getAttribute("orderlistByMember_id"); %>
    <%@ include file="pages/page1.file" %>
    
	<c:forEach var="orderVo" items="${orderlistByMember_id}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
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
                        	<span>等待教練確認</span>
                        </c:if>
                        <c:if test="${orderVo.state == 2}">
                        	<span>等待付款</span>
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
                            <span class="small">by ${memberSvc.getOneMem(coachSvc.getOneByCoach(orderVo.coach_id).member_Id).mem_Name}</span> 
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
					<div class="row align-self-center justify-content-end" id="${coachSvc.getOneByCoach(orderVo.coach_id).member_Id}">	
						<%-- 0:等待教練確認 --%>				
						<c:if test="${orderVo.state == 0}"> 		
							<div class="col-2 contact-form">
								<button class="btn btn-primary btn-lg btn-block whiteBtn backBtn">取消訂單</button>
							</div>						
						</c:if>
						<%-- 2:等待付款 --%>
						<c:if test="${orderVo.state == 2}"> 
							<div class="col-2 contact-form">
								<button class="btn btn-primary btn-lg btn-block whiteBtn backBtn">取消訂單</button>
							</div>
							<div class="col-2 contact-form">
								<a href="${pageContext.request.contextPath}/course/courseOrder.do?action=ordersFwCheckout&order_id=${orderVo.order_id}">
									<button class="btn btn-primary btn-lg btn-block checkOutBtn">前往付款</button>
								</a>
							</div>
						</c:if>
						<%-- 3:訂單完成(未完課) --%>
					 	<c:if test="${orderVo.state == 3}"> 
						 	<div class="col-2 contact-form">
						 		<a href="${pageContext.request.contextPath}/front-end/Jessica/member/myCalendar.jsp">
						 			<button class="btn btn-primary btn-lg btn-block">前往約課</button>
						 		</a>
						 	</div>
						</c:if>
						<%-- 4:訂單完成(已完課) --%>  
						<c:if test="${orderVo.state == 4}"> 
							<div class="col-2 contact-form"><button class="btn btn-primary btn-lg btn-block whiteBtn comBtn" data-toggle="modal">評價</button></div>    
						</c:if>
						<%-- 都有聯絡教練 --%>  
						<div class="col-2 contact-form">
							<form action="<%=request.getContextPath() %>/chat.do" method="POST">
						<input  name="memId" class="text-field" type="hidden" value="${memLogIn.member_Id}"/>
			            <input  name="reciver" class="text-field" type="hidden" value="${orderVo.coach_id}"/>
								<button  type="submit" class="btn btn-primary btn-lg btn-block checkOutBtn">聯絡教練</button>
						</form>
						</div>		
					</div>
	            </div>
            </c:if>
        </div>
    </div>
    </div>
    <!-- 訂單內容 結束↑ -->
    </c:forEach>
    <%@ include file="pages/page2.file" %>
    </c:if>

	<form id="comForm">
	<!-- Modal -->
	<div class="modal fade" id="comArea" tabindex="-1" role="dialog" aria-labelledby="commentAreaTitle" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="commentAreaTitle">評價此課程</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	            </div>
	            <div class="modal-body">
	                <label for="star-area">評分</label>
	                <input type="hidden" name="com_star" value="0" class="com_star_value">
	                <div class="star-area"><ul><li>☆</li><li>☆</li><li>☆</li><li>☆</li><li>☆</li></ul></div>
	            </div>
	            <div class="modal-body">
	                <div class="form-group text-left">
	                    <label for="comment-area">請寫下您的評論</label>
	                    <textarea class="form-control" rows="3" name="com_content"></textarea>
	                </div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
	                <button type="button" class="btn btn-primary"  data-dismiss="modal" id="comSubmitBtn">評價</button>
	            </div>
	        </div>
	    </div>
	</div>
	</form>

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
		window.location.href = "${pageContext.request.contextPath}/course/courseOrder.do?action=getOrderlistByMember_id";
	}else{
		window.location.href = "${pageContext.request.contextPath}/course/courseOrder.do?action=getOrderlistByMember_id&order_state="+orderState;
	}
});

//----------------------------------------------
// 取消訂單
$('.backBtn').click(function(e){
	e.preventDefault();
	var order_id = $(this).parent().parent().parent().parent().attr('id');
	var coach_memberId = $(this).parent().parent().attr('id');
	
    swal({
        title: "確定取消訂單嗎？",
        html: "按下確定後無法恢復訂單狀態",
        type: "question", // type can be "success", "error", "warning", "info", "question"
        showCancelButton: true,
        showCloseButton: true,
    }).then(function(result) {
        if (result) {
        	var obj = {				 						
 					"sender" : self,
 					"receiver" : coach_memberId,
 					"message" : "你的訂單["+order_id+"]被教練取消了", 					
 					"url" : "${pageContext.request.contextPath}/front-end/Jessica/member/course_orders.jsp"
 				};
	 		webSocket.send(JSON.stringify(obj));
        	window.location.href = '${pageContext.request.contextPath}/course/courseOrder.do?action=cancelOrder&order_id='+order_id;
        }
    }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
        swal("維持訂單狀態！", "Good Choice!","info");
    }).catch(swal.noop);
});

// ----------------------------------------------
// 評論
var course_id = '';

$('.comBtn').click(function(){
	$("#comArea").modal('show');
	course_id = $(this).parent().parent().parent().parent().attr('id');
	console.log($(course_id));
});

$('#comSubmitBtn').click(function(e){
    e.preventDefault();
    $.ajax({
        url:'${pageContext.request.contextPath}/course/courseOrder.do?action=ajaxUpdateCom',
        type : "POST",
        data : formData,
        contentType: false,
        cache: false,
        processData: false,
        success : function(data) {
        	alert('test');
        },
	   	error: function(xhr, ajaxOptions, thrownError){
		    console.log(xhr.status);
		    console.log(thrownError);
		}
    });
})

$(function(){
    $(".star-area > i").click(function(){
        var inputVal = $(".com_star_value").val();
        console.log("inputVal="+inputVal);
        var clickStar = $(this).attr("class");
        console.log("clickStar="+ typeof clickStar);
        var index = parseInt(clickStar.substring(clickStar.length-1, clickStar.length));
        console.log("index="+ index);
        var starArea = $(this).parent('span');
        console.log("媽媽="+$(starArea).attr('class'));

        var str = " ";
        for(var i=1; i<=index; i++){
            str+='<i class="fa fa-star star'+i+'" aria-hidden="true"></i>';
        }        
        for(var j=(index+1); j<=5; j++){
            str+='<i class="fa fa-star-o star'+j+'" aria-hidden="true"></i>';
        }
        console.log("str="+str);

        $(starArea).html(str);
        // $(starArea).css("color","yellow");
        $(inputVal).val(index);
    });    
});

var stark = "☆";
var stars = "★"
$(function(){
    //未點擊之前 li標籤本身及前面全部添加樣式
    $(".star-area li").on("mouseenter", function () {
        $(this).text(stars).prevAll().text(stars);
        $(this).nextAll().text(stark);
    });
    //鼠標離開事件，讓所有的星星為空心；
    $(".star-area li").on("mouseleave", function () {
        $(".star-area li").text(stark);
        //讓class 為current的 前面所有的星星為實體，後面的星星為空
        $(".star-area li.current").text(stars).prevAll().text(stars);
    })
    //註冊點擊事件，讓當前點擊的前面的星星全為實體；
    $(".star-area li").on("click",function () {
        $(this).addClass("current").siblings().removeClass("current");
    })
})

</script>

</body>
</html>