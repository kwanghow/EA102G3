<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.profav.model.*"%>
<%@ page import="com.profav.controller.*"%>

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


Product_FavoriteService Profavsvc = new Product_FavoriteService();
List<Product_FavoriteVO> list = Profavsvc.getFavBymemno(member_id);
request.setAttribute("list", list);
    	
%>


<jsp:useBean id="prosvc" scope="page" class="com.pro.model.ProductService" />


<!DOCTYPE html>
<html lang="en">
<title>會員個人頁面 - memberPage</title>

<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <script src="https://://code.jquery.com/jquery-2.1.4.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
	<!-- Bootstrap 的 CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css">
	
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
		
		/*圖片*/
   .course-img{
      height: 100px;
      padding-bottom: 200px;
      position: relative;
      overflow: hidden;
        }
    .course-img img{
      position: absolute;
      top: 50%;
      left: 50%;
      display: block;
      transform:translate(-50%,-50%);
          width: 100px;
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
 margin-left:-300px;
 width:900px;
  }
  
  /* 愛心特效 */

.HeartAnimation {
	    position: absolute;
    background-image: url(<%=request.getContextPath()%>/front-end/haoren/images/web_heart_animation_edge.png);
    background-position: left;
    background-repeat: no-repeat;
    height: 100px;
    width: 100px;
    left: 235%;
    -ms-transform: translate(-50%, -47.5%);
    transform: translate(-50%, -47.5%);
}

.like-active {
	animation-timing-function: steps(28);
	animation-name: heart-burst;
	animation-duration: .8s;
	animation-iteration-count: 1;
	display: inline-block;
	animation-fill-mode: forwards;
}

.HeartAnimation :hover {
background-position: 3.571428571428571%
}
 @keyframes heart-burst {
            0% {
                background-position: left
            }

            100% {
                background-position: right
            }
        }
/*    愛心特效  */ 
		
	</style>
</head>
   
<body class="text-center">


	 <!-- header -->
     <%@ include file="../haoren/header.jsp"%>
	<!-- header -->
	
	
	
	<!-- ***** Features Item Start ***** -->
    <section class="section" id="features" style="height:900px">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <div class="section-heading">
                        <h2>會員 <em>商品追蹤頁面</em></h2>
                        <img src="${pageContext.request.contextPath}/front-end/haoren/images/line-dec.png" alt="waves">
                    </div>
                </div>              
                         
                           
<!--                 訂單選單結尾 -->
 
                <div class="col-lg-8 col-md-6">                     
                        <h4  class = "order-t" style="margin-left: 40%;">追蹤商品</h4>                           
                            <div class="right-content order-all col-md-6"> 
                              <table class="table table-sm text-center table table-striped "  style="width:900px" >
                                <thead>
                                <tr>
                                    <th width="100px" class="text-center"></th>
                                    <th width="100px" class="text-center">商品名稱</th>
                                    <th width="100px" class="text-center">價錢</th>
                                    <th width="100px" class="text-center">剩餘數量</th>                                    
                                    <th width="100px" class="text-center">取消追蹤</th>                                    
                                  </tr>                              
                                </thead>                                
                                <tbody> 
                                
                                <c:forEach var="favVO" items="${list}">
                                <tr class="father">
                               			 <td width="80px" class="course-img">
                               			 <img  src="<%=request.getContextPath() %>/ProImg?name=PRODUCT_Lpic&PRODUCT_ID=${favVO.product_id}" class="img" >
                                		 </td> 
										
										<td class="align-middle">${prosvc.getOneProduct(favVO.product_id).product_Name}</td>
                                        
                                        
										<td class="align-middle">${prosvc.getOneProduct(favVO.product_id).product_Price}</td>
										<c:if test="${prosvc.getOneProduct(favVO.product_id).product_Stock < 10}">
										<td class="align-middle" style="color:red;font-size:13px;">最後五件</td>
										</c:if>
										<c:if test="${prosvc.getOneProduct(favVO.product_id).product_Stock > 10}">
										<td class="align-middle" >數量充足</td>
										</c:if>
										<td class="align-middle">
										<div class="HeartAnimation like-active">
										<input type="hidden" class="member_id" name="member_id" value="${member_id }"/>
										<input type="hidden"  name="product_id1" class="product_id" value="${favVO.product_id}"/>
										<input type="hidden" id="fav" class="action" name="action" value="cancel" />
										</div>
										</td>
										
										
										
									   </tr>    	
										</c:forEach>
                            
                                </tbody>
                                 
                            </table>
                            </div>
                            
                     
                </div>
<!--                 訂單詳情結尾 -->          
        </div>
    </section>
    <!-- ***** Features Item End ***** -->
	<!-- ***** Footer Start ***** -->
	<footer>
	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/haoren/images/tick.png">
				<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/haoren/images/message.png">
				<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/haoren/images/mail.png">
				<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/haoren/images/dot.png">
				<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/haoren/images/instagram-b.png">
				<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/haoren/images/facebook-b.png">
				<p>Copyright &copy; 2020 Training Studio
					- Modify by <a rel="nofollow" href="https://reurl.cc/yZ22W2" class="tm-text-link" target="_parent">FatBoy</a></p>
					<!-- You shall support us a little via PayPal to info@templatemo.com -->

				</div>
			</div>
		</div>
	</footer>
	<!-- ***** Footer End ***** -->

<script src="<%=request.getContextPath()%>/front-end/assets/js/jquery-2.1.0.min.js"></script>
<!-- Bootstrap -->
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


<script>
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
	
	
	
$('.HeartAnimation').click(  
		function() {
			var _this = $(this);
			$(this).removeClass('like-active') ;
			
			
			var product_id = $(this).find("input.product_id").val();
        	var action = $(this).find("input.action").val();
        	var member_id = $(this).find("input.member_id").val();
        	
        	console.log("product_id" + product_id);
        	console.log("action" + action);
        	console.log("member_id" +member_id);
			
$.ajax({
	url:"<%=request.getContextPath()%>/product_favorite/favorite.do",
	type: "POST",
	dataType: "json",
	data: {
		"product_id" : product_id,
		"action" : action,
		"member_id" : member_id
	},   	            		
		complete: function(data){
			console.log(data);
// 			console.log($(_this).closest('.father'));
			$(_this).closest('.father').remove();
		}
	
})
		});
		
		
</script>


	
</body>
</html>