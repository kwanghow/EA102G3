<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>


<!DOCTYPE html>
<html lang="en">
<title>登入畫面 - logIn</title>

<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
	<!-- Bootstrap 的 CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css">
	<style>
		* {
			font-family: 微軟正黑體;
		}
		.main-banner .caption {
			width: 400px;
			margin: auto;
			color: #ed563b;
			padding: 5px;
		}	
		.form-signin .btn {
			padding: .5rem 1rem;
		    font-size: 1.25rem;
		    line-height: 1.5;
		    border-radius: .3rem;
		    color: #fff;
		    background-color: #ed563b;
		}
		img.mb-4 {
		    opacity: 0.7;
		    width: 150px;
    		height: 150px;
		}
		.wrong {
    position: absolute;
    right: 18%;
    z-index: 1;
    top: 44%;
    line-height: 3;
    width: 280px;
    font-weight: bolder;
		}
		p.mt-5.mb-3.text-muted {
		    margin-top: 5px;
		    margin-bottom: 5px;
		}
		button.btn.btn-primary {
		    margin-top: 10px;
		    margin-bottom: 10px;
		}
	</style>
</head>
  
  
<body class="text-center">
	<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav">
                    <!-- logo -->
                    <a href="<%=request.getContextPath()%>/front-end/kevin/index.jsp" class="logo">我就<em> 健</em></a>
                    <!-- menu -->
                    <ul class="nav">
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/news/News.jsp">最新消息</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/Jessica/course/explore.jsp">課程</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/listAllGpc.jsp">揪團</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/haoren/list-all-product.jsp">商城</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/article/articleList.jsp">健身文章</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/kevin/stream/Stream.jsp">直播區</a></li>                        
                        <!-- 會員下拉 -->                        
                        <li class="scroll-to-section dropdown">
                            <a href="#" id="navbarDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="ture">會員/教練專區</a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="navbarDropdown">
                            	<li><a class="dropdown-item m1" href="<%=request.getContextPath()%>/front-end/memberPage.jsp">會員專區</a></li>
                                <li><a class="dropdown-item c1" href="<%=request.getContextPath()%>/front-end/coachPage.jsp">教練專區</a></li>
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp">飲食/健身紀錄</a></li>
                                <li style="display: none;"><a class="dropdown-item" href="#">none</a></li>
                            </ul>
                        </li>        

                        <!-- 會員登入訊息 -->
                        <c:if test="${empty sessionScope.memLogIn}">
                        	<li class="main-button">
                        		<a href="<%=request.getContextPath()%>/front-end/mem.do?action=logIn">
                        			Log In
                        		</a>
                        	</li>
                        </c:if>
                        <c:if test="${not empty sessionScope.memLogIn}">
                        	<li class="main-button">
                        		<a href="<%=request.getContextPath()%>/front-end/mem.do?action=logOut">
                        			${sessionScope.memLogIn.member_Id}${sessionScope.memLogIn.mem_Name}
                        		</a>
                        	</li>
                        </c:if>
                    </ul>
                    <a class='menu-trigger'><span>Menu</span></a>                    
                </nav>
            </div>
        </div>
    </div>
</header>
	<!-- ***** Main Banner Area Start ***** -->
	<input type="hidden" id="isOk" value="${isOk}">
	<input type="hidden" id="okVal" value="${okVal}">
	
	<div class="main-banner" id="top">
		<video autoplay muted loop id="bg-video">
			<source src="<%=request.getContextPath()%>/front-end/assets/radio/GYM.mp4" type="video/mp4" />
		</video>

		<div class="video-overlay header-text">
			<div class="wrong">		
				<%-- 錯誤表列  --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color:red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
			</div>
			<div class="caption">
					<form class="form-signin" method="post" action="<%=request.getContextPath()%>/front-end/mem.do">			   
				  		<img class="mb-4" src="<%=request.getContextPath()%>/front-end/assets/images/13.jpg">
				  		<h1 class="h3 mb-3 font-weight-normal">LOG IN</h1>
				  		
				  		<label for="inputMem_Account" class="sr-only">Member Account:</label>
				  		<input type="text" name="mem_Account" class="form-control" 
				  		 maxlength="16" size="20" placeholder="Account">
				  		<input type="hidden" name="action" value="getMember_Login"><br>
				  		
				  		<label for="inputMem_Psw" class="sr-only">Member Password:</label>
				  		<input type="password" name="mem_Psw" class="form-control" 
				  		 maxlength="16" size="20" placeholder="Password">
				  		<input type="hidden" name="action" value="getMember_Login"><br>
  
				  		<button class="btn" type="submit">LOG IN</button>		  		
					</form>
					
				<!-- Button trigger modal -->
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#exampleModal">Forgot your password?</button>	
					
				<div class="main-button scroll-to-section">
					<a href="<%=request.getContextPath()%>/front-end/becomeMember.jsp">Become a member</a>
				</div>			
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Please input your Email</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post" action="<%=request.getContextPath()%>/front-end/mem.do">
					<div class="modal-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Email address</label> 
							<input type="email" class="form-control" name="mem_Email" id="exampleInputEmail1"
								aria-describedby="emailHelp" placeholder="Enter email">
							<small id="emailHelp" class="form-text text-muted">
							We'll never share your email with anyone else.</small>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Comfirm</button>
					</div>
					<input type="hidden" name="action" value="forgot_Password">
				</form>
			</div>
		</div>
	</div>
	<!-- ***** Main Banner Area End ***** -->		
	<%@ include file="footerf.file"%>

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
<!-- SweetAlert2 -->
<%@ include file="sweet.file"%>
<script>
	//彈跳視窗
	$(document).ready(function(){		
		let ok = $("#isOk").val();
		   let okVal = $("#okVal").val();
		   if(ok == "No"){
			   Swal.fire(okVal,'信箱不可以是空白','error');
		   }
		   if(ok == "Yes"){
			   Swal.fire(okVal,'請到您的信箱查看!','success');
		   }
		   
		});
	
		let account = "${memLogIn.member_Id}";
		$('.main-nav .nav .m1').click(function(){
			if(!account){
				event.preventDefault();
				// 跳彈窗	 
				Swal.fire({
				  title: '沒有權限!',
				  text: "會員請先登入!",
				  icon: 'error',
				}).then((result) => {
				  if (result.value) {
						$(location).attr('href', '<%=request.getContextPath()%>/front-end/logIn2.jsp');
				  }
				})
			}
		});
	
		let account2 = "${coachLogIn.coach_Id}";
		$('.main-nav .nav .c1').click(function(){
			if(!account2){
				event.preventDefault();
				// 跳彈窗	 
				Swal.fire({
				  title: '沒有權限!',
				  text: "不是教練不能進入操作! 趕快申請加入吧!",
				  icon: 'error',
				}).then((result) => {
				  if (result.value) {
						$(location).attr('href', '<%=request.getContextPath()%>/front-end/logIn2.jsp');
				  }
				})
			}
		});
</script>	
</body>
<%
	session.removeAttribute("isOk");
	session.removeAttribute("okVal");
%> 
</html>