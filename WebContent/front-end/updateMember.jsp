<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<%
	MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
  	String mem_Sex = (memLogIn==null)? "1" : memLogIn.getMem_Sex();
%>

<!DOCTYPE html>
<html lang="en">
<title>�ק�|����T - updateMember</title>

<head>
	<meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
	<!-- Bootstrap �� CSS -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/assets/css/font-awesome.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/assets/css/templatemo-training-studio.css">
	<!-- 	datetimepicker -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />	

	<style>
		* {
			font-family: �L�n������;
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
		img#mem_pic_preview {
		    width: 150px;
		    height: 150px;
		}
		.id {
			width: 30%;
		    display: inline-block;
		    font-family: �L�n������;
		    font-weight: 700;
		    font-size: large;
		}
		form label {
		    width: 30%;
		    display: inline-block;
		    font-family: �L�n������;
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
    	right: 4%;
    	z-index: 1;
    	top: 18%;
    	line-height: 3;
    	width: 300px;
    	font-weight: bolder;
		    
		    
		}		
		#btn1 {
			padding: .5rem 1rem;
		    font-size: 1.25rem;
		    line-height: 1.5;
		    border-radius: .3rem;
		    color: #fff;
		    background-color: #ed563b;
		    position: relative;
		    left: 40%;
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
		<div class="wrong">
			<%-- ���~��C --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color:red">�Эץ��H�U���~:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>	
		<video autoplay muted loop id="bg-video">
			<source src="<%=request.getContextPath()%>/front-end/assets/radio/GYM.mp4" type="video/mp4" />
		</video>
		<div class="video-overlay header-text">
			<div class="caption">
				<h3><b>�ק�|����ƽж�g�U�C���:</b></h3>
				<form class="big1" method="post" action="<%=request.getContextPath()%>/front-end/mem.do" enctype="multipart/form-data">
					<div class="area">
						<div>
							<label>�|���s��:<font color=red><b>*</b></font></label>
							<div class="id"><%=memLogIn.getMember_Id()%></div>
						</div>							
						<div>
							<label>�|���m�W:</label>
							<input type="TEXT" name="mem_Name" size="20" value="<%=memLogIn.getMem_Name()%>" />
						</div>			 			
						<div>
							<label>�|���ʧO:</label>
							<input type="radio" name="mem_Sex" value="�k" <%= ("�k".equals(mem_Sex))? "checked" : "" %>>�k
							<input type="radio" name="mem_Sex" value="�k" <%= ("�k".equals(mem_Sex))? "checked" : "" %>>�k
						</div>		
						<div>
							<label>�|���b��:</label>
							<input type="TEXT" name="mem_Account" size="20" 
							value="<%=memLogIn.getMem_Account()%>" />
						</div>					
						<div>
							<label>�|���K�X:</label>
							<input type="TEXT" name="mem_Psw" size="20" 
					 		value="<%=memLogIn.getMem_Psw()%>" />
						</div>			
						<div>
							<label>�|���H�c:</label>
							<input type="TEXT" name="mem_Email" size="20" 
					 		value="<%=memLogIn.getMem_Email()%>" />
						</div>					
						<div>
							<label>�|�����:</label>
							<input type="TEXT" name="mem_Phone" size="20" 
					 		value="<%=memLogIn.getMem_Phone()%>" />
						</div>												
						<div>
							<label>�|���ͤ�:</label>
							<input name="mem_Birth" id="f_date1" type="text">
						</div>						
						<div>
							<label>�|���a�}:</label>
							<input type="TEXT" name="mem_Addr" size="20" 
					 		value="<%=memLogIn.getMem_Addr()%>" />
						</div>						
						<div>
							<label>�|���H�Υd��T:</label>
							<input type="TEXT" name="mem_Card" size="20" 
					 		value="<%=memLogIn.getMem_Card()==null?"":memLogIn.getMem_Card()%>" />
						</div>						
						<div>
							<label>�|���Ӥ�:</label>
							<img id="mem_pic_preview" src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${memLogIn.member_Id}&type=memberPic">
							<input id="mem_pic" type="file" name="mem_Img">
						</div>						
						<br>
						<input type="hidden" name="action" value="update_Front">
						<input type="hidden" name="member_Id" value="<%=memLogIn.getMember_Id()%>">
						<input type="hidden" name="mem_Close" value="<%=memLogIn.getMem_Close()%>">
						<button id="btn1" type="submit">�e�X�ק�</button>
					</div>
				</form>
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
<%@ include file="sweet.file"%>
<% 
	java.sql.Date mem_Birth = null;
	try {
		    mem_Birth = memLogIn.getMem_Birth();
	 } catch (Exception e) {
		   mem_Birth = new java.sql.Date(System.currentTimeMillis());
	 }
%>

<script>
$(document).ready(function(){
	   // �Ϥ�����
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
	   // �u������
	   let ok = $("#isOk").val();
	   let okVal = $("#okVal").val();
	   if(ok == "No"){
		   Swal.fire(okVal,'�O�_�٦���쥼��g����?','error');
	   }
	   if(ok == "Yes"){
		   Swal.fire(okVal,'�����ק粒��!','success');
	   }
	   
	});
	
	
	$.datetimepicker.setLocale('zh');
	$('#f_date1').datetimepicker({
	   theme: '',              //theme: 'dark',
	   timepicker:false,       //timepicker:true,
	   step: 1,                //step: 60 (�o�Otimepicker���w�]���j60����)
	   format:'Y-m-d',         //format:'Y-m-d H:i:s',
	   value: '<%=mem_Birth%>', // value:   new Date(),
	   //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // �h���S�w���t
	   //startDate:	            '2017/07/10',  // �_�l��
	   //minDate:               '-1970-01-01', // �h������(���t)���e
	   maxDate:               '+1970-01-01'  // �h������(���t)����
	});
</script>
 <%
 	session.removeAttribute("isOk");
 	session.removeAttribute("okVal");
 %> 	
</html>