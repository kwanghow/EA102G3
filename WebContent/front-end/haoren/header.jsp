<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<!----- Preloader Start ----->
<style>
#bg-video {
	min-height: 9vh;
	max-height: 9vh;
}

.main-banner .caption {
	top: 60%;
}

.main-banner .caption h2 {
	font-size: 40px;
}

.video-overlay {
	bottom: 0;
}
/*�վ�϶���������*/
.section-heading {
	margin-top: 20px;
	margin-bottom: 10px;
}
/*�ƼЪ��ѥ]�h*/
.breadcrumb {
	background-color: transparent;
	padding: 0;
	margin-bottom: 0;
}
</style>
<!----- Header Area ----->
<!----- Header Area ----->
<header class="header-area header-sticky">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <nav class="main-nav">
                    <!-- logo -->
                    <a href="<%=request.getContextPath()%>/front-end/kevin/index.jsp" class="logo">�ڴN<em> ��</em></a>
                    <!-- menu -->
                    <ul class="nav">
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/news/News.jsp">�̷s����</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/Jessica/course/explore.jsp">�ҵ{</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/listAllGpc.jsp">����</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/haoren/list-all-product.jsp">�ӫ�</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/article/articleList.jsp">�����峹</a></li>
                        <li class="scroll-to-section"><a href="<%=request.getContextPath()%>/front-end/kevin/stream/Stream.jsp">������</a></li>                        
                        <!-- �|���U�� -->                        
                        <li class="scroll-to-section dropdown">
                            <a href="#" id="navbarDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="ture">�|��/�нm�M��</a>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="navbarDropdown">
                            	<li><a class="dropdown-item m1" href="<%=request.getContextPath()%>/front-end/memberPage.jsp">�|���M��</a></li>
                                <li><a class="dropdown-item c1" href="<%=request.getContextPath()%>/front-end/coachPage.jsp">�нm�M��</a></li>
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/guochi/testCal2.jsp">����/��������</a></li>
                                <li style="display: none;"><a class="dropdown-item" href="#">none</a></li>
                            </ul>
                        </li>        

                        <!-- �|���n�J�T�� -->
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
<div class="main-banner" id="top">
	<!-- Banner Img -->
	<img
		src="${pageContext.request.contextPath}/front-end/Jessica/static/img/course-banner.png"
		id="bg-video">
	<div class="video-overlay header-text">
		<!-- �ѥ]�h -->
		<div class="caption">
		</div>
	</div>
</div>