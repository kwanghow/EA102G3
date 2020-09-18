<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 <%@page import= "com.article.model.*"%>
 <%@page import= "com.coach.model.CoachVO"%>
 <%@ page import="java.util.*"%>
 <%@ page import="com.mem.model.*"%>
 
 <%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");

 	ArtVO artVO = (ArtVO)request.getAttribute("ArtVO");
	CoachVO coachVO = (CoachVO)session.getAttribute("coachLogIn");
	ArtService ArtService = new ArtService();
	List<ArtVO> list = ArtService.getAllArticle();

	pageContext.setAttribute("list", list);

 	
 %>  
 <jsp:useBean id="artSvc" scope="page" class="com.article.model.ArtService"/>
 
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" 
  		content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Article</title>
  <!-- Bootstrap core CSS -->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/article/T3index/css/js/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/article/T3index/css/fonts/font-awesome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/article/T3index/css/G3header.css">
<!-- Custom fonts for this template -->
<link
	href="<%=request.getContextPath()%>/front-end/article/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">

  
  
  <!-- Custom styles for this template -->
  <link 
  	href="<%=request.getContextPath()%>/front-end/article/css/clean-blog.min.css" 
  	rel="stylesheet">

	<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css" />
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/rowreorder/1.2.6/css/rowReorder.dataTables.min.css" />
<%-- TODO 改成引入js file--%>


<style type="text/css" media="screen">
</style>

<body>

	<!-- Navigation -->
<!----- Header Area ----->
<!----- Header Area ----->
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

	<!-- Page Header -->

  <!-- Page Header -->
  <header class="masthead" style="background-image: url('<%=request.getContextPath()%>/front-end/article/img/pc2.jpeg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>GYM</h1>
            <span class="subheading">Share your good stuff</span>
          </div>
        </div>
      </div>
    </div>
  </header>
<c:if test="${ not empty caochVO}">
</c:if>

	<div class="btn btn-primary" style="margin-bottom:10px"><a href="<%=request.getContextPath()%>/front-end/article/addArticle.jsp">新增文章</a></div>


  <!-- Main Content -->
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
      
        <!-- 
        <div class="post-preview">
          <a href="POST3.html">
            <h2 class="post-title">
               重量訓練四大專項簡介：健美、健力、舉重與CrossFit
            </h2>
            <h3 class="post-subtitle">
              談到重量訓練，我們可以將之分為四大專項，分別為健美、健力、舉重與CrossFit。
            </h3>
          </a>
          <p class="post-meta">Posted by
            <a href="#">凱樂</a>
            </p>
        </div>
         -->
       
        <hr>
<table class="defaultTable">
<thead>
<tr>
<td>
</td>
</tr>
</thead>
<tbody>
<c:forEach var="artVo"   items="${list}"   varStatus="status" >
			<tr>
			<td>
			<div lass="post-preview">
			 <a href="${pageContext.request.contextPath}/ArtServlet?action=getOne_For_Display&article_no=${artVo.articleNo}">
			   <h2 class="post-title">
			     ${artVo.articleTitle}
			  </h2>
			  <h3 class="post-subtitle">
			  	 ${artVo.articleTitleSub}
			  </h2>
			    <!-- 談到重量訓練，我們可以將之分為四大專項，分別為健美、健力、舉重與CrossFit。 -->
			  </h3>
			</a>
			<p class="post-meta">Posted by ${artVo.articleAuthor}
			<fmt:formatDate pattern="yyyy-MM-dd" value="${artVo.postTime}" />
			  <%-- 	教練跳轉頁面 TODO								<a
										href="${pageContext.request.contextPath}/ArtServlet?action=getOne_For_Display&article_no=${artVo.articleNo}">
										</a> --%>
<%-- 			 							${artVo.postTime} --%>
			</p>
			</div>
			<hr>
			</td>
			</tr>
</c:forEach>
</tbody>
</table>
        <!-- Pager -->
<!--         <div class="clearfix">
          <a class="btn btn-primary float-right" href="#">Older Posts &rarr;</a>
        </div> -->
      </div>
    </div>
  </div>

  <hr>

	<footer class="home">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<p>
						Copyright &copy; 2020 Training Studio - Modify by <a
							rel="nofollow" href="https://reurl.cc/yZ22W2"
							class="tm-text-link" target="_parent">FatBoy</a>
					</p>
					<!-- You shall support us a little via PayPal to info@templatemo.com -->
				</div>
			</div>
		</div>
	</footer>



           <script src="https://code.jquery.com/jquery-3.5.1.js"></script>   

<%-- 		<script src="css/js/jquery-2.1.0.min.js"></script>

		<!-- Bootstrap -->
		<script src="<%=request.getContextPath()%>/css/js/popper.js"></script>
		<script src="<%=request.getContextPath()%>/css/js/bootstrap.min.js"></script>

		<!-- Plugins -->
		<script src="<%=request.getContextPath()%>/css/js/scrollreveal.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/js/waypoints.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/js/jquery.counterup.min.js"></script>
		<script src="<%=request.getContextPath()%>/css/js/imgfix.min.js"></script> 
		<script src="<%=request.getContextPath()%>/css/js/mixitup.js"></script> 
		<script src="<%=request.getContextPath()%>/css/js/accordions.js"></script>

		<!-- Global Init -->
		<script src="css/js/custom.js"></script> --%>

  <!-- Bootstrap core JavaScript -->
<%--   <script src="<%=request.getContextPath()%>/front-end/article/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/front-end/article/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 --%>
  <!-- Custom scripts for this template -->
  <script src="<%=request.getContextPath()%>/front-end/article/js/clean-blog.min.js"></script>
  
    <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
  
  
  
  <script>
  var dataTableObj;

  $(document).ready(function(){
      // console.log(document);
      dataTableObj = $('.defaultTable').DataTable();

  });
  </script>

</body>

</html>
