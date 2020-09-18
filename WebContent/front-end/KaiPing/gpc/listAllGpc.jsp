<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>

<jsp:useBean id="gpcSvc" scope="page" class="com.gpc.model.GpcService" />
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
	if (coachLogIn != null) {
		System.out.println(coachLogIn.getCoach_Id() + "�ثe�i�J�FlistAllGpc.jsp(listAllGpc.jsp_16��L�X)");
	}
	String identity = memLogIn == null ? "Member" : "Coach";
	request.setAttribute("identity", identity);
	pageContext.setAttribute("memLogIn", memLogIn);
	System.out.println();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

<title>���νҵ{</title>

<!-- Bootstrap �� CSS -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.css">

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/fonts/font-awesome.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/G3header.css">

<!--KaiPing-->

<!-- ElegantFonts CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/elegant-fonts.css">
<!-- themify-icons CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/themify-icons.css">
<!-- Swiper CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/swiper.min.css">
<!-- Styles -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/gpc_style.css">

<!--KaiPing-->


</head>

<body class="courses-page">
	<%@ include file="js/Header.html"%>

	<!------------------------------------------------------------- ***** �o�̥H�W�ƻs�K�W ***** ------------------------------------------------------->
	<!-- ***** Main Banner Area Start ***** -->
	<%@ include file="js/banner.html"%>
	<div class="main-banner" id="top">
		<!-- .page-header -->

		<div class="container">
			<div class="row">
				<div class="col-8">
					<div class="breadcrumbs">
						<ul class="flex flex-wrap align-items-center p-0 m-0">
							<li><a href="#"><i class="fa fa-home"></i> Home</a></li>
							<li>���νҵ{</li>
						</ul>
					</div>
					<!-- .breadcrumbs -->
				</div>
				<!-- .col -->

			</div>
			<!-- .row -->

			<div class="row">
				<div class="col-12 col-lg-8">
					<div class="featured-courses courses-wrap">
						<div class="row mx-m-25">
							<%-- --%>
							<c:forEach var="gpcVO" items="${gpcSvc.all}">
		<%--State == �O�֭㪺�ҵ{--%><c:if test="${gpcVO.gpc_State==1}">
									<div class="col-12 col-md-6 px-25">
										<div class="course-content">
											<figure class="course-thumbnail">
					<!-- �W�s����Ӫ��ҵ{ <a href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/SingleGpc.jsp?gpc_Id=${gpcVO.gpc_Id}"> -->
					<!--��Ӫ��ҵ{�ʭ��� --> <img src="<%=request.getContextPath()%>/front-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic1" alt="">
											</a>
											</figure>
											<!-- .course-thumbnail -->

											<div class="course-content-wrap">
												<div class="entry-div">
													<h2 class="entry-title">
														<span class="toGPC">${gpcVO.gpc_Name}</span>
<%-- 														<a class="toGPC" href="<%=request.getContextPath()%>/front-end/KaiPing/gpc/SingleGpc.jsp?gpc_Id=${gpcVO.gpc_Id}">${gpcVO.gpc_Name}</a> --%>
														<!-- �W�s����Ӫ��ҵ{ -->
													</h2>

													<div class="entry-meta flex flex-wrap align-items-center">
														<div class="course-author">
															<a
																href="<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=${coachSvc.getOneByCoach(gpcVO.coach_Id).coach_Id}">
																<!-- �W�s����Ӫ��ҵ{���нm -->
																${memSvc.getOneMem(coachSvc.getOneByCoach(gpcVO.coach_Id).member_Id).mem_Name}�нm
															</a>
														</div>
														<div class="course-date">�}�l�W�ҡG${gpcVO.gpc_Start}</div>
														<!-- �}�l�W�Үɶ� -->
													</div>
													<!-- .course-date -->
												</div>
												<!-- .entry-div -->

												<footer class="entry-footer flex flex-wrap justify-content-between align-items-center">
													<div class="course-cost">
														$${gpcVO.price}
														<!-- �o�̬O���� -->
														<!-- <span class="price-drop">$68</span> -->
														<!-- ���� -->
													</div>
													<!-- .course-cost -->
													<!-- KaiPing -->
													<div class="">���ΤH�ơG${gpcVO.mem_Min}</div>
													

													<div class="course-ratings flex justify-content-end align-items-center">
														<form id="getGpcTime" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
															<input name="action" value="getGpcTime" type="hidden">
															<input name="gpc_Id" value="${gpcVO.gpc_Id}" type="hidden">
															<input name="member_Id" value="${memLogIn.member_Id}" type="hidden">
															<button id="sideTwo">�e���d��</button>
														</form>
													</div>
													<!-- .course-ratings -->
												</footer>
												<!-- .entry-footer -->
											</div>
											<!-- .course-content-wrap -->
										</div>
										<!-- .course-content -->
									</div>
									<!-- .col -->

								</c:if>
							</c:forEach>


						</div>
						<!-- .row -->
					</div>
					<!-- .featured-courses -->

<!-- 					<div class="pagination flex flex-wrap justify-content-between align-items-center"> -->
<!-- 						<div class="col-12 col-lg-4 order-2 order-lg-1 mt-3 mt-lg-0"> -->
<!-- 							<ul -->
<!-- 								class="flex flex-wrap align-items-center order-2 order-lg-1 p-0 m-0"> -->
<!-- 								<li class="active"><a href="#">1</a></li> -->
<!-- 								<li><a href="#">2</a></li> -->
<!-- 								<li><a href="#">3</a></li> -->
<!-- 								<li><a href="#"><i class="fa fa-angle-right"></i></a></li> -->
<!-- 							</ul> -->
<!-- 						</div> -->

<!-- 						<div -->
<!-- 							class="col-12 flex justify-content-start justify-content-lg-end col-lg-8 order-1 order-lg-2"> -->
<!-- 							<div class="pagination-results flex flex-wrap align-items-center"> -->
<!-- 								<p class="m-0">Showing 1�V3 of 12 results</p> -->

<!-- 								<form> -->
<!-- 									<select> -->
<!-- 										<option>Show: 06</option> -->
<!-- 										<option>Show: 12</option> -->
<!-- 										<option>Show: 18</option> -->
<!-- 										<option>Show: 24</option> -->
<!-- 									</select> -->
<!-- 								</form> -->
<!-- 							</div> -->
<!-- 							.pagination-results -->
<!-- 						</div> -->
<!-- 					</div> -->
					<!-- .pagination -->
				</div>
				<!-- .col -->

				<div class="col-12 col-lg-4">

					<div class="sidebar">
						<div class="search-widget">
						<c:if test="${not empty memLogIn}">
							<form id="getMyGpcs_Mem"
								action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
								<input name="action" value="getMyGpcs_Mem" type="hidden">
								<input name="member_Id" value="${memLogIn.member_Id}" type="hidden">
								<button id="sideTwo">�d�ݰѻP����</button>
							</form>
							<br>
						</c:if>
						<c:if test="${not empty coachLogIn}"> 
							<form id="findCoachTime" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
								<input name="action" value="findCoachTime" type="hidden">
								<button id="sideTwo">�s�W����</button>
							</form>
							<br>
							<form id="getMyGpcs_Coach" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
								<input name="action" value="getMyGpcs_Coach" type="hidden">
								<input name="coach_Id" value="${coachLogIn.coach_Id}" type="hidden">
								<button id="sideTwo">�˵�/�޲z���Ϊ��A</button>
							</form>
							<br>
						</c:if>
							<form class="flex flex-wrap align-items-center">
								<input type="search" placeholder="Search...">
								<button type="submit"
									class="flex justify-content-center align-items-center">
									<i class="fa fa-search"></i>
								</button>
							</form>
							<!-- .flex -->
						</div>
						<!-- .search-widget -->

						<div class="cat-links">
							<h2>�ҵ{���O</h2>

							<ul class="p-0 m-0">
								<li><a href="#">��¦����</a></li>
								<li><a href="#">��a�о�</a></li>
								<li><a href="#">�U�׭���</a></li>
								<li><a href="#">��¦����</a></li>
								<li><a href="#">TRX�a�Q�V�m</a></li>
								<li><a href="#">��L...</a></li>
							</ul>
						</div>
						<!-- .cat-links -->

<!-- 						<div class="latest-courses"> -->
<!-- 							<h2>Latest Courses</h2> -->

<!-- 							<ul class="p-0 m-0"> -->
<!-- 								<li -->
<!-- 									class="flex flex-wrap justify-content-between align-items-center"> -->
<!-- 									<img -->
<%-- 									src="<%=request.getContextPath()%>/front-end/KaiPing/images/t-1.jpg" --%>
<!-- 									alt=""> -->

<!-- 									<div class="content-wrap"> -->
<!-- 										<h3> -->
<!-- 											<a href="#">The Complete Financial Analyst Training</a> -->
<!-- 										</h3> -->

<!-- 										<div class="course-cost free-cost">Free</div> -->
<!-- 									</div> .content-wrap -->
<!-- 								</li> -->

<!-- 								<li -->
<!-- 									class="flex flex-wrap justify-content-between align-items-center"> -->
<!-- 									<img -->
<%-- 									src="<%=request.getContextPath()%>/front-end/KaiPing/images/t-2.jpg" --%>
<!-- 									alt=""> -->

<!-- 									<div class="content-wrap"> -->
<!-- 										<h3> -->
<!-- 											<a href="#">Complete Java Masterclass</a> -->
<!-- 										</h3> -->

<!-- 										<div class="course-cost free-cost">Free</div> -->
<!-- 									</div> .content-wrap -->
<!-- 								</li> -->

<!-- 								<li -->
<!-- 									class="flex flex-wrap justify-content-between align-items-center"> -->
<!-- 									<img -->
<%-- 									src="<%=request.getContextPath()%>/front-end/KaiPing/images/t-3.jpg" --%>
<!-- 									alt=""> -->

<!-- 									<div class="content-wrap"> -->
<!-- 										<h3> -->
<!-- 											<a href="#">The Complete Digital Marketing Course</a> -->
<!-- 										</h3> -->

<!-- 										<div class="course-cost">$24</div> -->
<!-- 									</div> .content-wrap -->
<!-- 								</li> -->

<!-- 								<li -->
<!-- 									class="flex flex-wrap justify-content-between align-items-center"> -->
<!-- 									<img -->
<%-- 									src="<%=request.getContextPath()%>/front-end/KaiPing/images/t-4.jpg" --%>
<!-- 									alt=""> -->

<!-- 									<div class="content-wrap"> -->
<!-- 										<h3> -->
<!-- 											<a href="#">Photoshop CC 2018 MasterClass</a> -->
<!-- 										</h3> -->

<!-- 										<div class="course-cost">$18</div> -->
<!-- 									</div> .content-wrap -->
<!-- 								</li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 						.latest-courses -->

<!-- 						<div class="ads"> -->
<!-- 							<img -->
<%-- 								src="<%=request.getContextPath()%>/front-end/KaiPing/images/ads.jpg" --%>
<!-- 								alt=""> -->
<!-- 						</div> -->
<!-- 						.ads -->



<!-- 						<div class="popular-tags"> -->
<!-- 							<h2>Popular Tags</h2> -->

<!-- 							<ul class="flex flex-wrap align-items-center p-0 m-0"> -->
<!-- 								<li><a href="#">Creative</a></li> -->
<!-- 								<li><a href="#">Unique</a></li> -->
<!-- 								<li><a href="#">Photography</a></li> -->
<!-- 								<li><a href="#">ideas</a></li> -->
<!-- 								<li><a href="#">Wordpress Template</a></li> -->
<!-- 								<li><a href="#">startup</a></li> -->
<!-- 							</ul> -->
<!-- 						</div> -->
						<!-- .popular-tags -->
					</div>
					<!-- .sidebar -->
				</div>
				<!-- .col -->
			</div>
			<!-- .row -->
		</div>
		<!-- .container -->
	</div>
	<br>
	<br>
	<!-- ***** Main Banner Area End ***** -->
	<!------------------------------------------------------------- ***** �o�̥H�U�ƻs�K�W ***** ------------------------------------------------------->
	<%@ include file="js/footer.html"%>

	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/jquery-2.1.0.min.js"></script>
	<!-- Bootstrap -->
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/popper.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.js"></script>
	<!-- Plugins -->
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/scrollreveal.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/waypoints.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/jquery.counterup.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/imgfix.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/mixitup.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/accordions.js"></script>
	<!-- Global Init -->
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/custom.js"></script>

	<!--KaiPing-->
	<script type='text/javascript'
		src='<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/swiper.min.js'></script>
	<script type='text/javascript'
		src='<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/masonry.pkgd.min.js'></script>
	<script type='text/javascript'
		src='<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/jquery.collapsible.min.js'></script>
</body>
</html>
