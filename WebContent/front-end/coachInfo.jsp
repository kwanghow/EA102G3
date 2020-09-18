<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.exp.model.*"%>
<%@ page import="com.exptype.model.*"%>
<%@ page import="java.util.*"%>

<%
	MemVO memLogIn = (MemVO)session.getAttribute("memLogIn");	
	
	String coach_Id = request.getParameter("coach_Id");
	CoachService coachSvc = new CoachService();
	CoachVO coachVO = coachSvc.getOneByCoach(coach_Id);
	pageContext.setAttribute("coachVO", coachVO);	

	MemService memSvc = new MemService();
	MemVO memVO = memSvc.getOneMem(coachVO.getMember_Id());
	pageContext.setAttribute("memVO", memVO);
	
	ExpService expSvc = new ExpService();
	List<ExpVO> list = expSvc.oneCoachHowManySkill(coachVO.getCoach_Id());
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<title>教練簡介 - coachInfo</title>

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
		    
	
	</style>

</head>
  
  
<body class="text-center">

<%@ include file="headerh.file"%>	
	<!-- ***** Our Classes Start ***** -->
	
	<jsp:useBean id="ExptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />
	
    <section class="section" id="our-classes">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 offset-lg-3">
                    <div class="section-heading">
                        <h2>Coach <em>${memVO.mem_Name}</em></h2>
                        <img src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${coachVO.coach_Id}&type=coachPic"><br>
                        <img src="assets/images/line-dec.png">
                        
                        <p><b>${coachVO.experience}</b></p>
                    </div>
                </div>
            </div>
            
        
            
            <div class="row" id="tabs">
              <div class="col-lg-4">
                <ul>
                 <c:forEach var="ExpVO" items="${list}" varStatus="ExpVOStatus">
            <li><a href='#tabs-${ExpVOStatus.count}'><img src="assets/images/tabs-first-icon.png" alt=""> ${ExptypeSvc.getOneExptype(ExpVO.exp_Id).exp_Name}</a></li>
            	</c:forEach>
                  <div class="main-rounded-button"><a href="MyCalendar.jsp?coach_Id=${coachVO.coach_Id}">View Schedules</a></div>
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
	<%@ include file="sweet.file"%>
</body>
</html>