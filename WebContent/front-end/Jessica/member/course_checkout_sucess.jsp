<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<jsp:useBean id="courseFavorSvc" scope="page" class="com.course_favor.model.CourseFavorService"/>
<jsp:useBean id="courseOrderSvc" scope="page" class="com.course_order.model.CourseOrderService"/>

<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜私人課程結帳成功</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>
<%-- 單頁所需css樣式、js文件 --%>
<style>
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

<!----- 付款step ----->
<section class="section" id="step">
  <div class="container">
    <div class="row justify-content-center text-center align-self-center">
        <div class="col-8">
            <div class="row">
                <div class="col text-muted">
                    <span class="badge badge-light">1</span>
                    <span>　課程結帳</span>
                </div>
                <div class="col-1">
                    <i class="fa fa-long-arrow-right" aria-hidden="true"></i>
                </div>
                <div class="col text-muted">
                    <span class="badge badge-light">2</span>
                    <span>　金流支付</span>            
                </div>
                <div class="col-1">
                    <i class="fa fa-long-arrow-right" aria-hidden="true"></i>
                </div>
                <div class="col font-weight-bold">
                    <span class="badge badge-primary">3</span>
                    <span>　完成</span>            
                </div>
            </div>
        </div>
    </div>
  </div>
</section>

<!----- Content Area ----->
<section class="section" id="section-content">
  <div class="container">
    <div class="row">
        <div class="col">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">親愛的 <span class="text-danger">${sessionScope.memLogIn.mem_Name}</span> 您好</h5>
                    <p class="card-text">您已經成功訂購課程，訂單編號：<span>${param.order_id}</span>
<!--                         <a href="">(查看訂單)</a> -->
                    </p>
                    <div class="card-text">
                        <!----- 購買明細 ----->        
                        <div class="row my-5" id="payment">
                            <div class="col-lg-6 offset-lg-3 mt-3"><div class="section-heading mb-0">
                                <h2>恭喜成功訂購!</h2>
                                <h2 class="text-success"><i class="fa fa-check" aria-hidden="true"></i></h2>
                            </div></div>
                        </div>
                    </div>
                </div>
                <div class="card-footer mt-5">
                    <!-- RPG選項 -->
                    <div class="row my-3">
                        <div class="col-6"></div>
                        <div class="col-3 contact-form">
                        <a href="${pageContext.request.contextPath}/front-end/Jessica/course/explore.jsp">
                        	<button class="btn btn-primary btn-lg btn-block" id="backBtn">繼續逛課</button>  
                        </a>                            
                        </div>
                        <div class="col-3 contact-form">
                            <a href="${pageContext.request.contextPath}/front-end/Jessica/member/course_orders.jsp">
                            	<button class="btn btn-primary btn-lg btn-block" id="goCheckoutBtn">我的訂單</button>
                            </a>
                        </div>
                    </div>
                </div>
            </div>            
        </div>
    </div>

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
</script>
</body>
</html>