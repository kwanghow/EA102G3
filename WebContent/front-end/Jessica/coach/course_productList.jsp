<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<jsp:useBean id="courseFavorSvc" scope="page" class="com.course_favor.model.CourseFavorService"/>

<c:if test="${empty courseListByCoachId}">
	<jsp:forward page="/course/course.do">
		<jsp:param name="action" value="getCourselistByCoach_id" />
	</jsp:forward>
</c:if>
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜我的課程賣場</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<style>

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
            <h2>課程<em>賣場</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">首頁</a></li>
                    <li class="breadcrumb-item"><a href="#">教練專區</a></li>
                    <li class="breadcrumb-item active" aria-current="page">私人課程賣場</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<section class="section" id="section-content">
  <div class="container">
    <!-- 小標 -->
    <div class="row mx-5 mb-3">
        <div class="col">
            <a href="${pageContext.request.contextPath}/front-end/Jessica/coach/addCourse.jsp"><button class="btn btn-primary">新增課程</button></a>　
            <a href="${pageContext.request.contextPath}/front-end/Jessica/coach/course_salesList.jsp"><button class="btn btn-secondary">前往銷售列表</button></a><br>
        </div>
    </div>  
  
  	<!-- 課程列表 -->
  	<div class="row mx-4">
	  	<c:if test="${courseListByCoachId == 'noResult'}">
	        <div class="col my-5 tab-content text-center">
	        	<img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/no-order-b.svg">
	        	<span>您目前尚無課程，趕快新增課程吧！</span>
	        </div>  	
	  	</c:if>

	    <c:if test='${courseListByCoachId != "noResult"}'>
		  <c:forEach var="courseVo" items="${courseListByCoachId}">
		  	<!-- 單頁商品開始 -->
		    <div class="col-md-4 px-4 py-2">
		      <div class="card mb-4 shadow-sm">
		        <img class="card-img-top" src="${pageContext.request.contextPath}/jessica/img.do?course_id=${courseVo.course_id}&picIndex=1" alt="Card image cap">
		        <div class="card-body">
		          <div class="card-text">		          	
	          		<span class="badge badge-light">${courseVo.course_id}</span>
	          		<span class="badge badge-warning">${exptypeSvc.getOneExptype(courseVo.exp_id).exp_Name}</span>	
					<c:if test="${courseVo.state == 1}">
						<span class="badge badge-info">上架中</span>
					</c:if>
					<c:if test="${courseVo.state == 0}">
	              		<span class="badge badge-info">下架中</span>
	              	</c:if>   		
	          		<h6 class="mt-3">${courseVo.cname}</h6>
		          </div>
		          <br>
		          <div class="d-flex justify-content-between align-items-center">
		            <div class="btn-group">
			          <a href="${pageContext.request.contextPath}/front-end/Jessica/course/intro.jsp?course_id=${courseVo.course_id}">
			          	<button type="button" class="btn btn-sm btn-outline-secondary">查看</button>
			          </a>
			          <a href="${pageContext.request.contextPath}/course/course.do?action=productListFwUpdateCourse&course_id=${courseVo.course_id}">
			          	<button type="button" class="btn btn-sm btn-outline-secondary">編輯</button>
			          </a>
		            </div>
 		            <!-- <small class="text-muted">已售出0</small> -->
		          </div>
		        </div>
		      </div>
		    </div>
		    <!-- 單頁商品結束 -->    
		  </c:forEach>
		</c:if>
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
<%-- 錯誤表列 --%>
<c:if test="${not empty msgs}"> 
	$('#msgsModal').modal('show');
</c:if>

</script>

</body>
</html>