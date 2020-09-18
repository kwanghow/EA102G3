<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@page import= "com.artilove.model.ArtILoveVO"%>
 <%@ page import="com.mem.model.*"%>
 
 <%-- <%@page import= "com.coach.model.CoachVO"%> --%>

 <%
	MemVO memVO = (MemVO)request.getSession().getAttribute("memLogIn");
 	ArtILoveVO artILoveVO = (ArtILoveVO)request.getAttribute("ArtILoveVO");
/* 	/* CoachVO coachVO = (CoachVO)session.getAttribute("coachLogIn"); /* 這行要改 */ 

 	
 %>  
 <%-- <jsp:useBean id="artSvc" scope="page" class="com.article.model.ArtService"/> --%>
 
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Article</title>

  <!-- Bootstrap core CSS --> 
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/article/T3index/css/js/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/article/T3index/css/fonts/font-awesome.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/article/T3index/css/G3header.css">
  <!-- Custom fonts for this template -->
  <link href="<%=request.getContextPath()%>/front-end/article/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  
  <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/front-end/article/css/clean-blog.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.css" rel="stylesheet">
  <link href="https://cdn.datatables.net/1.10.21/css/dataTables.bootstrap4.min.css" rel="stylesheet">




  <!-- Navigation -->
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
  <header class="masthead" style="background-image: url('<%=request.getContextPath()%>/front-end/article/img/gymm.jpeg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="site-heading">
            <h1>最愛文章</h1>
            <span class="subheading">收藏好文章</span>
          </div>
        </div>
      </div>
    </div>
  </header>
  
<%-- <c:if test="${ not empty caochVO}">  <!-- 這行要改 -->
</c:if> --%>



<style>
#customers {
  font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}

#customers tr:hover {background-color: #ddd;}

#customers th {
  padding-top: 12px;
  padding-bottom: 12px;
  text-align: left;
  background-color: #4CAF50;
  color: white;
}
/* .col-sm-12 col-md-6{
    position: relative;
    left: 376px;} */

</style>
</head>
<body>

<table id="customers" class="defaultTable">
<thead>
  <tr>
    <th>文章編號</th>
    <th>類別</th>
    <th>標題</th>
    <th>作者</th>
  </tr>
</thead>
<tbody>
<c:forEach var="artILoveVO" items="${faVOlist}">	<!-- 改成以上對映名稱 -->		
			<tr>
				<td>${artILoveVO.articleNo}</td>
				<td>${ artCatMap[artILoveVO.artVO.articleCatNo] }</td>
				<td><a href="${pageContext.request.contextPath}/ArtServlet?action=getOne_For_Display&article_no=${artVo.articleNo}">${artILoveVO.artVO.articleTitle}</a>
				</td>
				<td>${artILoveVO.artVO.articleAuthor}</td>
			
				</tr>			
				
				 <FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/ArtILoveServlet"
						style="margin-bottom: 0px;">
				<!-- <input type="submit" value="移除最愛">  -->
				<input type="hidden" name="article_no" value="${artVO.articleNo}"> 
				<input type="hidden" name="action" value="getOne_For_Update"> 
				
				</FORM>
				

			
					
			 </c:forEach>
			 </tbody>
		
</table>

<%-- </body>
</html>

	
  
  <table>
		<tr>			
			<th>文章編號</th> 
			<th>類別</th>
			<th>標題</th>
			<th>作者</th>			
				
		</tr>
									
		<c:forEach var="artILoveVO" items="${faVOlist}">	<!-- 改成以上對映名稱 -->		
			<tr>
				<td>${artILoveVO.articleNo}</td>
				<td>${artILoveVO.artVO.articleCatNo}</td>
				<td>${artILoveVO.artVO.articleTitle}</td>
				<td>${artILoveVO.artVO.articleAuthor}</td>
			
							
				<td>
				<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/ArtILoveServlet"
						style="margin-bottom: 0px;">
				<input type="submit" value="檢舉處理"> 
				<input type="hidden" name="article_no" value="${artVO.articleNo}"> 
				<input type="hidden" name="action" value="getOne_For_Update">
				
				</FORM>
				</td>

			</tr>
					
			 </c:forEach>
		
	</table> --%>

  <hr>

  <!-- Footer -->
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




<!-- 		<script src="css/js/jquery-2.1.0.min.js"></script>
 -->    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>   

		<!-- Bootstrap -->
<%-- 		<script src="<%=request.getContextPath()%>/js/popper.js"></script>
		<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> --%>

		<!-- Plugins -->
<%-- 		<script src="<%=request.getContextPath()%>/js/scrollreveal.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/waypoints.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/jquery.counterup.min.js"></script>
		<script src="<%=request.getContextPath()%>/js/imgfix.min.js"></script> 
		<script src="<%=request.getContextPath()%>/js/mixitup.js"></script> 
		<script src="<%=request.getContextPath()%>/js/accordions.js"></script>
 --%>
		<!-- Global Init -->
<!-- 		<script src="css/js/custom.js"></script>
 -->
  <!-- Bootstrap core JavaScript -->
<%--   <script src="<%=request.getContextPath()%>/front-end/article/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/front-end/article/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 --%>
  <!-- Custom scripts for this template -->
  <script src="<%=request.getContextPath()%>/front-end/article/js/clean-blog.min.js"></script>
  
    <script src="<%=request.getContextPath()%>/front-end/article/js/clean-blog.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js"></script>
  
<!-- 
https://code.jquery.com/jquery-3.5.1.js
https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js
https://cdn.datatables.net/1.10.21/js/dataTables.bootstrap4.min.js
 -->
  
<style type="text/css" media="screen">
</style>
  <script>

  
    $(document).ready(function() {
	    $('#customers').DataTable();
	} );
  </script>

</body>

</html>
	