<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%
	MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
  	MemVO memVO = (MemVO) request.getAttribute("memVO");
  	String mem_Sex = (memVO==null)? "1" : memVO.getMem_Sex();
%>

<!DOCTYPE html>
<html lang="en">
<title>成為會員 - becomeMember</title>

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
	<!-- 	datetimepicker -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />	

	<style>
		* {
			font-family: 微軟正黑體;
		}
		.InOut {
			color: #ed563b;
		}
		.InOut .btn{
			color: #fff;
		    background-color: #ed563b;
		}
		.main-banner .caption {
			width: 800px;
			margin: auto;
			color: #ed563b;
		}	
		.form-signin .btn {
			padding: .5rem 1rem;
		    font-size: 1.25rem;
		    line-height: 1.5;
		    border-radius: .3rem;
		    color: #fff;
		    background-color: #ed563b;
		}		
		.area {
			text-align: left;
		    border: 2px solid;
		    border-color: white;
		    padding: 10px;
		}		
		.big1 {
		    background-color: rgb(255 255 255 / 0.2);
		    box-shadow: 0px -6px 1px white;
		    margin-top: 20px;
		}		
		.btn1{
			padding: .5rem 1rem;
		    font-size: 1.25rem;
		    line-height: 1.5;
		    border-radius: .3rem;
		    color: #fff;
		    background-color: #ed563b;
			position: relative;
    		left: 300px;
		}
		img#mem_pic_preview {
		    width: 150px;
		    height: 150px;
		}
		form label {
		    width: 30%;
		    display: inline-block;
		    font-family: 微軟正黑體;
		    font-weight: 700;
		    font-size: large;
		}
		form div {
		    margin-bottom: 5px;
		}
		input[type="TEXT"] {
		    width: 65%;
		}
		.wrong {
		position: absolute;
    	left: 100%;
    	z-index: 1;
    	top: 1%;
    	line-height: 3;
    	width: 300px;
    	font-weight: bolder;
		    
		    
		}
		#magic {
		    padding: 5px;
		    font-size: 0.8rem;
		    line-height: 1.5;
		    border-radius: 0.3rem;
		    color: yellow;
		    background-color: blueviolet;
		    letter-spacing: 2px;
		    font-weight: 600;
		    margin-top: 5px;
		    position: absolute;
		    left: 50%;
		    position: absolute;
    		bottom: 24px;
    		left: 58%;
		}

	</style>	
	<!-- 	datetimepicker -->
	<style>
	.xdsoft_datetimepicker .xdsoft_datepicker {
	           width:  300px;   /* width:  300px; */
	  }
	  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	           height: 151px;   /* height:  151px; */
	  }
	  #bg-video{
	      min-height: 148vh !important;
	  }
	</style>
</head>
   
<body class="text-center">
<%@ include file="headerh.file"%>
	<!-- ***** Main Banner Area Start ***** -->
	<input type="hidden" id="isOk" value="${isOk}">
	<input type="hidden" id="okVal" value="${okVal}">
	
	<div class="main-banner" id="top">
		<video autoplay muted loop id="bg-video">
			<source src="<%=request.getContextPath()%>/front-end/assets/radio/GYM.mp4" type="video/mp4" />
		</video>
		<div class="video-overlay header-text">
			<div class="caption">
				<div class="wrong">
					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
				<h3><b>新增會員填寫下列欄位:</b></h3>
				<form class="big1" method="post" action="<%=request.getContextPath()%>/front-end/mem.do" enctype="multipart/form-data">
					<div class="area">							
						<div>
							<label>會員姓名:</label>
							<input type="TEXT" id="name" name="mem_Name" size="20" value="<%= (memVO==null)? "" : memVO.getMem_Name()%>" />
						</div>			 			
						<div>
							<label>會員性別:</label>
							<input type="radio" name="mem_Sex" value="男" <%= ("男".equals(mem_Sex))? "checked" : "" %>>男
							<input type="radio" name="mem_Sex" value="女" <%= ("女".equals(mem_Sex))? "checked" : "" %>>女
						</div>		
						<div>
							<label>會員帳號:</label>
							<input type="TEXT" id="acc" name="mem_Account" size="20" 
							value="<%= (memVO==null)? "" : memVO.getMem_Account()%>" />
						</div>					
						<div>
							<label>會員密碼:</label>
							<input type="TEXT" id="psw" name="mem_Psw" size="20" 
					 		value="<%= (memVO==null)? "" : memVO.getMem_Psw()%>" />
						</div>			
						<div>
							<label>會員信箱:</label>
							<input type="TEXT" id="email" name="mem_Email" size="20" 
					 		value="<%= (memVO==null)? "" : memVO.getMem_Email()%>" />
						</div>					
						<div>
							<label>會員手機:</label>
							<input type="TEXT" id="phone" name="mem_Phone" size="20" 
					 		value="<%= (memVO==null)? "" : memVO.getMem_Phone()%>" />
						</div>												
						<div>
							<label>會員生日:</label>
							<input name="mem_Birth" id="f_date1" type="text">
						</div>						
						<div>
							<label>會員地址:</label>
							<input type="TEXT" id="add" name="mem_Addr" size="20" 
					 		value="<%= (memVO==null)? "" : memVO.getMem_Addr()%>" />
						</div>						
						<div>
							<label>會員信用卡資訊:</label>
							<input type="TEXT" id="card" name="mem_Card" size="20" 
					 		value="<%= (memVO==null)? "" : memVO.getMem_Card()%>" />
						</div>						
						<div>
							<label>會員照片:</label>
							<img id="mem_pic_preview" src="<%=request.getContextPath()%>/front-end/assets/images/NoImage.jpg">
							<input id="mem_pic" type="file" name="mem_Img">
						</div>						
						<br>
						<input type="hidden" name="action" value="insert">
						<button class="btn1" type="submit">送出申請</button>
					</div>
				</form>
				<div>
					<button id="magic">magic</button>
				</div>
			</div>
		</div>
	</div>
	<!-- ***** Main Banner Area End ***** -->	
	<%@ include file="footerf.file"%>
</body>

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
<!--  datetimepicker -->
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<!-- SweetAlert2 -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<% 
	java.sql.Date mem_Birth = null;
	try {
		    mem_Birth = memVO.getMem_Birth();
	 } catch (Exception e) {
		   mem_Birth = new java.sql.Date(System.currentTimeMillis());
	 }
%>

<script>
	$(document).ready(function(){
	   $("#mem_pic").change(function(){
	         readURL(this);
	     });
	   function readURL(input) {
	      if (input.files && input.files[0]) {
	          var reader = new FileReader();
	
	          reader.onload = function (e) {
	              $('#mem_pic_preview').attr('src', e.target.result).fadeIn('slow');
	          }
	          reader.readAsDataURL(input.files[0]);
	      }
	   }
	   
	   let ok = $("#isOk").val();
	   let okVal = $("#okVal").val();
	   if(ok == "No"){
		   Swal.fire(okVal,'是否還有欄位未填寫完成?','error');
	   }
	   if(ok == "Yes"){
		   Swal.fire(okVal,'太棒了','success');
	   }
	   
	});
	
	
	$.datetimepicker.setLocale('zh');
	$('#f_date1').datetimepicker({
	   theme: '',              //theme: 'dark',
	   timepicker:false,       //timepicker:true,
	   step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	   format:'Y-m-d',         //format:'Y-m-d H:i:s',
	   value: '<%=mem_Birth%>', // value:   new Date(),
	   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	   //startDate:	            '2017/07/10',  // 起始日
	   //minDate:               '-1970-01-01', // 去除今日(不含)之前
	   maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
	
	// 一鑑搞定
	$(document).ready(function(){
		$("#magic").click(function(){
			$("#name").val("胖虎");
			$("#acc").val("ggg");
			$("#psw").val("yyy");
			$("#email").val("lupopo86@gmail.com");
			$("#phone").val("0988222111");
			$("#add").val("台北市中山區");
			$("#card").val("4444222233331111");
		})
	});
</script>
<%@ include file="sweet.file"%>
<%
 	session.removeAttribute("isOk");
 	session.removeAttribute("okVal");
%> 	
</html>