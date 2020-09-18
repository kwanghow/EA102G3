<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<jsp:useBean id="courseFavorSvc" scope="page" class="com.course_favor.model.CourseFavorService"/>
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜課程最愛</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<style>
    /*裁切課程相片*/
    .course-img{
        width: 162px;
        height: 0;
        padding-bottom: 120px;
        position: relative;
        overflow: hidden;
    }
    .course-img img{
        position: absolute;
        top: 50%;
        left: 50%;
        display: block;
        min-width: 100%;
        min-height: 100%;
        transform:translate(-50%,-50%);          
    }
    button a{
        color: white;
    }
    #backBtn{
        background-color: white;
        color: #ed563b;
        border: 1px solid #ed563b;
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
            <h2>課程 <em>最愛</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">首頁</a></li>
                    <li class="breadcrumb-item"><a href="#">會員專區</a></li>
                    <li class="breadcrumb-item active" aria-current="page">我的課程最愛</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<!----- Content Area ----->
<section class="section" id="section-content">
  <div class="container">

    <!-- 最愛課程 -->
    <div class="row mx-4 mt-2">
        <table class="table bg-white py-4 mt-3">
            <thead>
                <tr>
                    <th colspan="2">課程</th>
                    <th>課程方案</th>
                    <th>加入購物</th>
                    <th>刪除最愛</th>
                </tr>
            </thead>
            <tbody>
            	<c:if test="${empty courseFavorSvc.getListByMember_id(sessionScope.memLogIn.member_Id)}">
            		<tr class="my-5 py-5 text-center">
            			<td class="my-5 py-5" colspan="5">
            				<img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/no-collect.svg">
	        				<span>您目前尚無收藏，<a href="${pageContext.request.contextPath}/front-end/Jessica/course/explore.jsp">開始探索</a></span>
            			</td>
            		</tr>
            	</c:if>
            	<c:forEach var="favorVo" items="${courseFavorSvc.getListByMember_id(sessionScope.memLogIn.member_Id)}">            	
	                <tr id="${favorVo.course_id}">
	                    <!-- 課程圖片 -->
	                    <td> 
	                        <div class="course-img">
	                            <img class="img-fluid" src="${pageContext.request.contextPath}/jessica/img.do?course_id=${favorVo.course_id}&picIndex=1" alt="">
	                        </div>                                
	                    </td>
	                    <!-- 課程名稱 -->
	                    <td class="theme-colors pl-0">
	                    	<a href="${pageContext.request.contextPath}/front-end/Jessica/course/intro.jsp?course_id=${favorVo.course_id}" class="theme-colors font-weight-bold">
	                        	<h5>${courseSvc.getOneCourseById(favorVo.course_id).cname}</h5> 
	                        </a>
	                    </td>                   
	                    <!-- 課程方案 -->
	                    <td>
	                        <div class="form-group">
	                            <select class="form-control">
	                            	<c:forEach var="courseSetVo" items="${courseSetSvc.getAllCourseSetByCourse_id(favorVo.course_id)}">  
		                                <option value="${courseSetVo.set_id}">${courseSetVo.lesson} 堂，NT$ ${courseSetVo.price}</option>
	                                </c:forEach>	                                
	                            </select>
	                        </div>
	                    </td>
	                    <!-- 加入購物車 -->
	                    <td class="cart text-center"><i class="fa fa-shopping-cart" aria-hidden="true"></i></td>
	                    <!-- 垃圾桶 -->
	                    <td class="trash text-center"><i class="fa fa-trash-o" aria-hidden="true"></i></td>
	                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- RPG選項 -->
    <div class="row mx-4 mt-1 mb-5 py-3">
        <div class="col-3"></div>
        <div class="col-3 contact-form">
            <a href="${pageContext.request.contextPath}/front-end/Jessica/course/explore.jsp">
            	<button class="btn btn-primary btn-lg btn-block" id="backBtn">繼續逛課</button>
            </a> 
        </div>
        <div class="col-3 mb-5 contact-form">
            <a href="${pageContext.request.contextPath}/front-end/Jessica/course/basket.jsp">
            	<button class="btn btn-primary btn-lg btn-block">前往課程購物車</button>
            </a>
        </div>
        <div class="col-3 mb-5 contact-form">
            <a href="${pageContext.request.contextPath}/front-end/memberPage.jsp">
            	<button class="btn btn-primary btn-lg btn-block">返回會員專區</button>
            </a>
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

$('.trash').click(function(){
	var tr = $(this).parent();
	var course_id = $(tr).attr('id');
	
	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseFavor.do?action=ajaxEditFavor',
		 data: {'course_id':course_id},
		 success: function (data){
			if(data === 'addSuccess'){
				console.log('不可能');
			}else if(data === 'deleteSuccess'){
				$(tr).remove();
				swal('成功', '成功刪除課程最愛！', 'info');
			}
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	});  
});
$('.cart').click(function(){
	var tr = $(this).parent();
	var course_id = $(tr).attr('id');
	
	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseCart.do?action=ajaxEditCart',
		 data: {'course_id':course_id},
		 success: function (data){
			if(data === 'addCartItemSucess'){
				swal('成功', '成功加入課程購物車！', 'success');
				$(tr).css('color','#ed563b');
			}else{
				swal('不要再按了小孩', '課程已在購物車', 'info');
			}
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	});   
});
</script>

</body>
</html>