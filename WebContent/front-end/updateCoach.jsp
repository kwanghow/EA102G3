<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.exp.model.*"%>
<%@ page import="com.exptype.model.*"%>
<%@ page import="java.util.*"%>

<%
	MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO)session.getAttribute("coachLogIn");
		
	CoachService coachSvc = new CoachService();
	CoachVO coachVO = coachSvc.getOneByCoach(coachLogIn.getCoach_Id());
	pageContext.setAttribute("coachVO", coachVO);	
	
	ExpService expSvc = new ExpService();
	List<ExpVO> list = expSvc.oneCoachHowManySkill(coachLogIn.getCoach_Id());
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<title>修改教練資訊 - updateCoach</title>

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
		.wrong {
		    position: absolute;
		    right: 10%;
		    z-index: 1;
		    top: 100%;
		    line-height: 3;
		    width: 280px;
		    font-weight: bolder;
		}
		
		img#coach_img_preview {
		    width: 600px;
		    height: 600px;
		}		    	
	</style>
</head>
  
<body class="text-center">

<%@ include file="headerh.file"%>	
	<input type="hidden" id="isOk" value="${isOk}">
	<input type="hidden" id="okVal" value="${okVal}">	
	<!-- ***** Our Classes Start ***** -->	
	<jsp:useBean id="ExptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />
	
    <section class="section" id="our-classes">
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
                <div class="col-lg-6 offset-lg-3">
                    <div class="section-heading">
                    	<!-- ***** Coach_Name ***** -->
                        <h2>Coach <em>${memLogIn.mem_Name}</em></h2>
                        <!-- ***** Coach_Img ***** -->
                        <form method="post" action="<%=request.getContextPath()%>/front-end/coach.do" enctype="multipart/form-data">
	                        <img id="coach_img_preview" src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${coachVO.coach_Id}&type=coachPic"><br>
	                        <input id="coach_img" type="file" name="coach_Img"><br>
	                        <!-- ***** Coach_Experience ***** -->
	                        <img src="assets/images/line-dec.png">                      
	                        <p><b><textarea name="experience" rows="8" cols="100">${coachVO.experience}</textarea></b></p>
	                        
	                        <input type="hidden" name="coach_Id" value="${coachVO.coach_Id}">
	                        <input type="hidden" name="member_Id" value="${coachVO.member_Id}">
	                        <input type="hidden" name="isCoach" value="${coachVO.isCoach}">
	                        <input type="hidden" name="action" value="update_Coach">
	                        <input type="submit" value="送出修改">
                        </form>
                    </div>
                </div>
            </div>     
            
            <div class="row" id="tabs">
              <div class="col-lg-4">
              
                 <ul>               
                   <c:forEach var="ExpVO" items="${list}" varStatus="ExpVOStatus">
	           		 <li><a href='#tabs-${ExpVOStatus.count}'>
	           		 	<img src="assets/images/tabs-first-icon.png" alt=""> ${ExptypeSvc.getOneExptype(ExpVO.exp_Id).exp_Name}</a>
	           		 </li>
            	   </c:forEach>
                  
                   <div class="main-rounded-button">
                  	 <a href="#new_skill">申請新的專長項目</a>
                   </div>         
                 </ul>
                
               </div>
	           <div class="col-lg-8">
	                <section class='tabs-content'>
	                
	                 <c:forEach var="ExpVO" items="${list}" varStatus="ExpVOStatus">	
		                  <article id='tabs-${ExpVOStatus.count}'>
		                    <img src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${ExpVO.exp_Id}&type=exptypePic">
		                    <h4>${ExptypeSvc.getOneExptype(ExpVO.exp_Id).exp_Name}</h4>
		                    <p><b>${ExptypeSvc.getOneExptype(ExpVO.exp_Id).exp_Words}</b></p>                                 
		                  </article>
	                  </c:forEach>
	                </section>
	           </div>
            </div>
        </div>
    </section>
    <!-- ***** Our Classes End ***** -->
    <!-- ***** Our Classes Start ***** -->
    <section class="section" id="new_skill">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <div class="section-heading">
                        <h2>新申請<em>專長項目</em></h2>
                        <img src="assets/images/line-dec.png" alt="">
                        <p><b>在這裡, 你必須提供一張符合此專長的相關證照或照片, 送出後等待我們平台為你做審核</b></p>
                    </div>
                </div>
            </div>
            <div class="row" id="tabs">
              <div class="col-lg-2">
              </div>
              <div class="col-lg-8">
                <section class='tabs-content'>
                  <article id='tabs-1'>
                    <img id="license_preview" src="<%=request.getContextPath()%>/front-end/assets/images/NoImage.jpg"><br>
	                <input id="license" type="file" name="license"><br>
                    <h4>簡短描述專長內容</h4>
                    <p><textarea name="new_skill" rows="2" cols="100"></textarea></p>
                    <div class="main-button">
					<!-- <a id="apply" href="#">送出申請</a> -->
                        <button id="apply">送出申請</button>
                    </div>
                  </article>
                </section>
              </div>
              <div class="col-lg-2">
              </div>
            </div>
        </div>
    </section>
    <!-- ***** Our Classes End ***** -->	
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
	$(document).ready(function(){
	   // 圖片改變
	   $("#coach_img").change(function(){
		   readURLCoach(this);
	     });
	   function readURLCoach(input) {
	      if (input.files && input.files[0]) {
	          var reader = new FileReader();
	
	          reader.onload = function (e) {
	              $('#coach_img_preview').attr('src', e.target.result).fadeIn('slow');
	          }
	          reader.readAsDataURL(input.files[0]);
	      }
	   }
	   
	   // 證照圖片改變
	   $("#license").change(function(){
	         readURL(this);
	     });
	   function readURL(input) {
	      if (input.files && input.files[0]) {
	          var reader = new FileReader();
	
	          reader.onload = function (e) {
	              $('#license_preview').attr('src', e.target.result).fadeIn('slow');
	          }
	          reader.readAsDataURL(input.files[0]);
	      }
	   }
	   
	   // 彈跳視窗
	   let ok = $("#isOk").val();
	   let okVal = $("#okVal").val();
	   if(ok == "No"){
		   Swal.fire(okVal,'是否還有欄位未填寫完成?','error');
	   }
	   if(ok == "Yes"){
		   Swal.fire(okVal,'此次修改完成!','success');
	   }
	   
	   // 申請新專長
	   $("#apply").click(function(){
		   Swal.fire('已送出申請','這需要幾天的時間審核,請耐心等候','success');
	   })
	   
	});
</script>	
</body>
<%
	session.removeAttribute("isOk");
	session.removeAttribute("okVal");
%>
</html>