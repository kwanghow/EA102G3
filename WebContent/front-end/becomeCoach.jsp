<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.exp.model.*"%>
<%@ page import="com.exptype.model.*"%>
<%@ page import="java.util.*"%>

<%
	MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
	CoachVO coachVO = (CoachVO)session.getAttribute("coachVO");	
%>

<!DOCTYPE html>
<html lang="en">
<title>成為教練 - becomeCoach</title>

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
	label {
	    padding-right: 5px;
	}
	.main-button1 {
		text-align: center;
	    padding-top: 20px; 
	}	
	#schedule table tbody tr td {
	    padding-right: 10px;
	    padding-left: 10px;
	}
	td {
	    padding-top: 5px;
	    padding-bottom: 0px;
	}
	input[type="submit"] {
	    padding: .5rem 1rem;
	    font-size: 1.25rem;
	    line-height: 1.5;
	    border-radius: .3rem;
	    color: #fff;
	    background-color: #ed563b;
	}
	img#mem_pic_preview {
	    width: 250px;
	    height: 250px;
	}
	.wrong {
	    position: absolute;
    right: 5%;
    z-index: 1;
    top: 25%;
    line-height: 3;
    width: 280px;
    font-weight: bolder;
	}
	section#schedule {
	    padding-top: 5%;
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
   		bottom: 9px;
    	left: 62%;
	}
</style>

 <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&display=swap" rel="stylesheet">
    <title>becomeCoach.jsp</title>
    <!-- Additional CSS Files -->
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-training-studio.css">
</head>
    
<body>  
  <!-- ***** Preloader Start ***** -->
  <!-- ***** Preloader End ***** -->     
    <!-- ***** Header Area Start ***** -->
    
    <%@ include file="headerh.file"%>
    
    
    <!-- ***** Header Area End ***** -->
	<input type="hidden" id="isOk" value="${isOk}">
	<input type="hidden" id="okVal" value="${okVal}">
    <section class="section" id="schedule">
        <div class="container">
        <div class="wrong">
           	<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color:red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
		</div>
        	<div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="cta-content">
                        <h2>Don’t <em>think</em>, begin <em>today</em>!</h2>
                        <p>申請成為教練 , 完成以下資訊</p>
                    </div>
                </div>
            </div>            
            <div class="row">
                <div class="col-lg-10 offset-lg-1">
                    <div class="schedule-table filtering">
                      <form method="post" action="<%=request.getContextPath()%>/front-end/coach.do" enctype="multipart/form-data">
                        <table>
                            <tbody>
                                <tr>
                                    <td class="day-time">會員編號</td>
                                    <td>${memLogIn.member_Id}</td>
                                </tr>
                                <tr>
                                    <td class="day-time">會員姓名</td>
                                    <td>${memLogIn.mem_Name}</td>
                                </tr>
                                <tr>
                                    <td class="day-time">相關經驗</td>                         
                                    <td><textarea id="exp" name="experience" rows="8" cols="100"></textarea></td>
                                </tr>
                                <tr>
                                    <td class="day-time">照片</td>                         
                                    <td>
	                                   <img id="mem_pic_preview" src="<%=request.getContextPath()%>/front-end/assets/images/NoImage.jpg">
									   <input id="mem_pic" type="file" name="coach_Img">
									</td>
                                </tr>
                                	<jsp:useBean id="exptypeSvc" scope="session" class="com.exptype.model.ExptypeService" />
                                <tr>
                                   <td class="day-time">專長</td>
                                   <td>
                                      <c:forEach var="exptypeVO" items="${exptypeSvc.all}">
                                    	<label><input type="checkbox" name="exp_Id" value="${exptypeVO.exp_Id}">${exptypeVO.exp_Name}</label>
                                      </c:forEach>
                                   </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="main-button1">
                        	<input type="hidden" name="action" value="become_coach">
                            <input id="b1" type="submit" value="Become a Coach">
                        </div>
                        <input type="hidden" name="member_Id" value="${memLogIn.member_Id}">
                        <input type="hidden" name="member_Name" value="${memLogIn.mem_Name}">         
                      </form>
                    <div>
						<button id="magic">magic</button>
					</div>
                    </div>
                </div>
            </div>
        </div>
    </section>    
    <!-- ***** Footer Start ***** -->
	
<%@ include file="footerf.file"%>
    <!-- jQuery -->
    <script src="assets/js/jquery-2.1.0.min.js"></script>
    <!-- Bootstrap -->
    <script src="assets/js/popper.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <!-- Plugins -->
    <script src="assets/js/scrollreveal.min.js"></script>
    <script src="assets/js/waypoints.min.js"></script>
    <script src="assets/js/jquery.counterup.min.js"></script>
    <script src="assets/js/imgfix.min.js"></script> 
    <script src="assets/js/mixitup.js"></script> 
    <script src="assets/js/accordions.js"></script>   
    <!-- Global Init -->
    <script src="assets/js/custom.js"></script>
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    <%@ include file="sweet.file"%>
    
    <script>
	   $(document).ready(function(){
		   // 彈跳視窗
		   let ok = $("#isOk").val();
		   let okVal = $("#okVal").val();
		   if(ok == "No"){
			   Swal.fire(okVal,'是否還有欄位未填寫完成?','error');
		   }
		   if(ok == "Yes"){
			   Swal.fire(okVal,'太棒了','success');
		   }
		   
		   // 圖片改變
		   $("#mem_pic").change(function(){
		         readURL(this);
		         console.log(111);
		     });
		   function readURL(input) {
		      if (input.files && input.files[0]) {
		    	  console.log(123);
		          var reader = new FileReader();
		
		          reader.onload = function (e) {
		              $('#mem_pic_preview').attr('src', e.target.result).fadeIn('slow');
		          }
		          reader.readAsDataURL(input.files[0]);
		      }
		  }
		});
	   
	   $(document).ready(function(){
		   $("#magic").click(function(){
			   $("#exp").val("AFAA 重量訓練證照, TRX STC懸吊訓練師, MMA4Ffifcoach證照, 綜合格鬥適能教練認證");
		   })
	   });
	   
	</script>
  </body>
<%
	session.removeAttribute("isOk");
	session.removeAttribute("okVal");
%> 
</html>