<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<% 
	MemVO memVO = (MemVO) session.getAttribute("memVO");

%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title></title>

	<!-- Bootstrap 的 CSS -->

	<link rel="stylesheet" type="text/css" href="css/js/bootstrap.min.css">

	<link rel="stylesheet" type="text/css" href="css/fonts/font-awesome.css">

	<link rel="stylesheet" href="css/G3header.css">
	
<style>
	div.gggg{
		height:900px;
		background-color:#000;
		color:#ed563b;
	}
	
	p {
		color:#ed563b;
	}



</style>
</head>


<body>
	<header class="header-area header-sticky">
		<div class="container">
			<div class="row">
				<div class="col-12">
					<nav class="main-nav">
						<!-- ***** Logo Start ***** -->
						<a href="index.html" class="logo">我就<em> 健</em></a>
						<!-- ***** Logo End ***** -->
						<!-- ***** Menu Start ***** -->
						<ul class="nav">
							<li class="scroll-to-section"><a href="#top" class="active">Home</a></li>


							<li class="scroll-to-section"><a href="news.html">News</a></li>


							<li class="scroll-to-section"><a href="#features">About</a></li>


							<li class="scroll-to-section"><a href="#our-classes">Classes</a></li>
							

							<li class="scroll-to-section"><a href="#schedule">Schedules</a></li>

							<li class="scroll-to-section"><a href="#schedule">Schedules</a></li>

							<li class="scroll-to-section"><a href="#schedule">Schedules</a></li>


							<li class="scroll-to-section"><a href="#contact-us">Contact</a></li> 
							<li class="main-button"><a href="#">Sign Up</a></li>
						</ul>        
						<a class='menu-trigger'>
							<span>Menu</span>
						</a>
						<!-- ***** Menu End ***** -->
					</nav>
				</div>
			</div>
		</div>
	</header>

<!------------------------------------------------------------- ***** 這裡以上複製貼上 ***** ------------------------------------------------------->
<div class="gggg">
<h1>你好,歡迎回來</h1>

<p>${memVO.getMem_Account()}</p>
</div>
<!------------------------------------------------------------- ***** 這裡以下複製貼上 ***** ------------------------------------------------------->
	<footer>
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					
					
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
					<div></div>
						
					
					<img  class="footerimg" src="images/tick.png">
					<img  class="footerimg" src="images/message.png">
					<img  class="footerimg" src="images/mail.png">
					<img  class="footerimg" src="images/dot.png">
					<img  class="footerimg" src="images/instagram-b.png">
					<img  class="footerimg" src="images/facebook-b.png">

					<p>Copyright &copy; 2020 Training Studio
						- Modify by <a rel="nofollow" href="https://reurl.cc/yZ22W2" class="tm-text-link" target="_parent">FatBoy</a></p>
						<!-- You shall support us a little via PayPal to info@templatemo.com -->

					</div>
				</div>
			</div>
		</footer>




		<script src="css/js/jquery-2.1.0.min.js"></script>

		<!-- Bootstrap -->
		<script src="css/js/popper.js"></script>
		<script src="css/js/bootstrap.min.js"></script>

		<!-- Plugins -->
		<script src="css/js/scrollreveal.min.js"></script>
		<script src="css/js/waypoints.min.js"></script>
		<script src="css/js/jquery.counterup.min.js"></script>
		<script src="css/js/imgfix.min.js"></script> 
		<script src="css/js/mixitup.js"></script> 
		<script src="css/js/accordions.js"></script>

		<!-- Global Init -->
		<script src="css/js/custom.js"></script>



	</body>
	</html>
