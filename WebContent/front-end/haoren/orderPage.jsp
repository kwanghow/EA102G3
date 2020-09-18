<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pro.order.model.*"%>
<%@ page import="com.pro.order.controller.*"%>
<%@ page import="com.pro.detail.model.*"%>

<%
MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
CoachVO coachLogIn = (CoachVO)session.getAttribute("coachLogIn");
	 
	 boolean islog = false;
	 String member_id = null;
if((MemVO)session.getAttribute("memLogIn")!=null){
	System.out.println((MemVO)session.getAttribute("memLogIn"));
			islog = true;
			MemVO MemVO =(MemVO)session.getAttribute("memLogIn");
			     MemService memsvc = new MemService();
			     MemVO memVO = memsvc.getOneMem(MemVO.getMember_Id());
			     session.setAttribute("memVO", memVO);
			    member_id = memVO.getMember_Id();
}
session.setAttribute("member_id", member_id);
session.setAttribute("islog", islog);


//ORDER
OrderService OrderSvc = new OrderService();
	List<OrderVO> List_All = OrderSvc.getAll(member_id);	
    List<OrderVO> List_Pay = OrderSvc.getAll_Pay(member_id);
    List<OrderVO> List_Com = OrderSvc.getAll_Com(member_id);
    List<OrderVO> List_Can = OrderSvc.getAll_Can(member_id);
    	request.setAttribute("List_All", List_All);
    	request.setAttribute("List_Pay", List_Pay);
    	request.setAttribute("List_Com", List_Com);
    	request.setAttribute("List_Can", List_Can);
    	
// DetailService Detailsvc = new DetailService();
// List<DetailVO> List_Detail = Detailsvc.getAllOrder(order_id);
%>




<!DOCTYPE html>
<html lang="en">
<title>會員個人頁面 - memberPage</title>

<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/jquery-2.1.0.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
	<!-- Bootstrap 的 CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/haoren/assets/css/templatemo-training-studio.css">

<!-- 	 sweetalert  -->	
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<!-- sweetalert -->
<script src="${pageContext.request.contextPath}/front-end/Jessica/static/js/sweetalert2.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/front-end/Jessica/static/css/sweetalert2.css"> <!-- v6.10.3 -->
	<style>
		body{
	        background: 
	        linear-gradient(rgba(255,255,255,0.5),rgba(255,255,255,0.5)),
	        url("<%=request.getContextPath()%>/front-end/haoren/images/111.jpeg") center no-repeat fixed;
    		background-size: cover;
		}
		
		* {
			font-family: 微軟正黑體;
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
		.coach_pic {
			width: 300px;
			height: 300px;
			margin-top: 400px;
		}
		p {
			font-weight: 600;
		}
		.feature-item .right-content h4 {
			width: 540px;
		}
		.feature-item .right-content h4:after {
		    content: "";
		    width: 280px;
		    height: 2px;
		    background: #ddd;
		    position: absolute;
		    left: calc(100%/2 - 280px/4);
		    margin-top: 29px;
		}
		#features select {
			margin-top: 15px;
			width: auto;
		}
		.bg {
			position: fixed;
			top:0;
			left:0;
			bottom:0;
			right:0;
			z-index: 0;
		}
		
		.bg img {
			min-height: 100%;
			width: 100%;
			opacity: 0.5;
		}
		.order-all{
		margin-left:80px;
		margin-top:30px;
		}
		.order-pay{
		display:none;
		margin-left:80px;
		margin-top:30px;
		}
		.order-com{
		display:none;
		margin-left:80px;
		margin-top:30px;
		}
		.order-can{
		display:none;
		margin-left:80px;
		margin-top:30px;
		}
		.order-t{
	margin-top: 0px;
    margin-bottom: 7px;
    letter-spacing: 0.25px;
    color: #232d39;
    font-size: 19px;
    font-weight: 600;
    text-transform: capitalize;
		}
		
		/*  商品彈窗        */
  .modal-xl{
 height:800px;
 margin-left:-180px;
 width:900px;
  } 
  .de-header{
      font-size: 15px;
    font-weight: bolder;
  }
		
	</style>
</head>
   
<body class="text-center">


<!-- ***** Header Area Start ***** -->
	 <!-- header -->
     <%@ include file="../haoren/header.jsp"%>
	<!-- header -->
	<!-- ***** Header Area End ***** -->
	
	
	
	<!-- ***** Features Item Start ***** -->
    <section class="section" id="features" style="height:900px">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <div class="section-heading">
                        <h2>會員 <em>個人訂單管理頁面</em></h2>
                        <img src="${pageContext.request.contextPath}/front-end/haoren/images/line-dec.png" alt="waves">
                    </div>
                </div>
                <div class="col-lg-4 col-md-6">
                    <ul class="features-items">                      
                        <li class="feature-item">
                            <div class="left-icon">
                                <img src="${pageContext.request.contextPath}/front-end/haoren/images/features-first-icon.png" alt="third gym training">
                            </div>
                            <div class="right-content">
                                <h4 >查詢訂單</h4>
                                 <select class="custom-select order-sec" >
								 <option value="0" selected>全部</option>
								 <option value="1">已出貨</option>
								 <option value="2">完成</option>
								 <option value="3">取消</option>
								</select>
                            </div>
                        </li>
                    </ul>
                </div>
<!--                 訂單選單結尾 -->
 
                <div class="col-lg-8 col-md-6">                     
                        <h4  class = "order-t" style="margin-left: 40%;">訂單資訊</h4>                           
                            <div class="right-content order-all col-md-6"> 
                               <%@ include file="../haoren/order_all.jsp"%>
                            </div>
                            <div class="right-content order-pay col-md-6"> 
                               <%@ include file="../haoren/order_pay.jsp"%>
                            </div>
                            <div class="right-content order-com col-md-6"> 
                               <%@ include file="../haoren/order_com.jsp"%>
                            </div>
                            <div class="right-content order-can col-md-6"> 
                               <%@ include file="../haoren/order_can.jsp"%>
                            </div>
                            </div>
                     
                </div>
<!--                 訂單詳情結尾 -->          
        </div>
    </section>
    <!-- ***** Features Item End ***** -->
	<!-- ***** Footer Start ***** -->
        <!-- footer -->
    	<%@ include file="../haoren/footer.jsp"%>
		<!-- footer -->
	<!-- ***** Footer End ***** -->

 <%-- socket --%>
<%@ include file="/front-end/haoren/socket.jsp"%>


<!-- Bootstrap -->
<script src="<%=request.getContextPath()%>/front-end/assets/js/popper.js"></script>
<script src="<%=request.getContextPath()%>/front-end/assets/js/bootstrap.min.js"></script>


<script>
window.onload = function() {
	connect();
};
$(document).ready(function(){
	$('.order-sec').change(function(){
	if($(this).val()=="0"){
	$('.order-all').fadeIn();
	$('.order-pay').hide();
	$('.order-com').hide();
	$('.order-can').hide();
	}else if($(this).val()=="1"){
		$('.order-all').hide();
		$('.order-pay').fadeIn();
		$('.order-com').hide();
		$('.order-can').hide();
	}else if($(this).val()=="2"){
		$('.order-all').hide();
		$('.order-pay').hide();
		$('.order-com').fadeIn();
		$('.order-can').hide();
	}else if($(this).val()=="3"){
		$('.order-all').hide();
		$('.order-pay').hide();
		$('.order-com').hide();
		$('.order-can').fadeIn();
	}else if($(this).val()=="#"){
		$('.order-all').hide();
		$('.order-pay').hide();
		$('.order-com').hide();
		$('.order-can').hide();
	}
	});
	});
</script>


	
</body>
</html>