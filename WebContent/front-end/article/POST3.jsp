<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ArtMes.model.*"%>
<%@ page import="java.lang.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.artilove.model.*"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <!--http://localhost:8080/EA102G3/ArtServlet?action=getOne_For_Display&article_no=000123-->
 		
<% 
boolean exist = Boolean.parseBoolean(String.valueOf(request.getAttribute("exist")));
CoachVO coachVO = (CoachVO)session.getAttribute("coachLogIn");
MemVO memVO = (MemVO)session.getAttribute("memLogIn");


%>
 
 
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">   <!-- SET RWD  --> 
  <meta name="description" content="">
  <meta name="author" content="">

  <title>${artVO.getArticleTitle()}</title>

<!-- Bootstrap core CSS -->
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/article/T3index/css/js/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/article/T3index/css/fonts/font-awesome.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/article/T3index/css/G3header.css">
  <!-- Custom fonts for this template -->
  <link href="<%=request.getContextPath()%>/front-end/article/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  
   <!-- Custom styles for this template -->
  <link href="<%=request.getContextPath()%>/front-end/article/css/clean-blog.min.css" rel="stylesheet">
  
  
<style>

html, body { height:100%; }

.flexbox {
	display: flex;
	height: 100%;
	justify-content: center;
	align-items: center;
}

.fav-btn {
	display:flex;
	height: 100%;
	justify-content: center;
	align-items: center;
}


@keyframes favme-anime {
  0%   { 
		opacity: 1;
		font-size: ms(0);
		-webkit-text-stroke-color: transparent;
	}
	25%	 { 
		opacity:.6;
		color: #FFF;
		font-size: ms(-2);
		-webkit-text-stroke-width: 1px;
   	-webkit-text-stroke-color: #DC3232;
	}
	75%	 { 
		opacity:.6;
		color: #FFF;
		font-size: ms(3);
		-webkit-text-stroke-width: 1px;
   	-webkit-text-stroke-color: #DC3232;
	}
  100% { 
		opacity: 1;
		font-size: ms(2);
		-webkit-text-stroke-color: transparent;
	}
}

@keyframes favme-hover {
	from {
		font-size: ms(3);
	}
	80% {
		font-size: ms(2);
	}
}

.favme {
	display:block;
	font-size: ms(2);
	width: auto;
	height: auto;
	cursor: pointer;
	box-shadow: none;
	transition: all .2s ease;
	color: #CBCDCE;
	margin: 0;
	
	&.active {
		color: #DC3232;
	}
	&:hover {
		animation: favme-hover .3s infinite alternate;
	}
	&.is_animating {
		animation: favme-anime .3s;
	}

}
.btn-primary {
    background-color: #f76201;
    border-color: #f76201;
}
.rep{
    position: relative;
    top: 83px;
}
.canfav {
    position: relative;
    left: 140px;
}
.reptitle{
    line-height: 1.5;
    margin: 5px 0 !important;
}

</style>

</head>



<body>

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
  <header class="masthead" style="background-image: url('<%=request.getContextPath()%>/front-end/article/img/pc2.jpeg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">     
          <div class="site-heading">
            <h1>Article</h1>
            <span class="subheading">Life is wonderful</span>
          </div>
        </div>
      </div>
  
  </header>
  <!-- add my heart -->
 
  <!-- Page Content -->
  <div class="container" id="mainContainer" style=margin-top:35px>
    <div class="row">
      <!-- Post Content Column -->
      <div class="col-lg-8">
        <!-- Title -->
        <h1 class="mt-5">${artVO.getArticleTitle()}</h1>
		
        <!-- Author -->
        <p class="lead">
          by
          <a href="#">${artVO.getArticleAuthor()}</a>
        <c:if test="${ artVO.memId==memVO.member_Id }">
       
          <a type="button" class="btn btn-primary" src="<%=request.getContextPath()%>/article/addArticle.jsp" onclick="gotoEdit()" style="color:white" >修改文章</a>
          <a type="button" class="btn btn-primary" src="<%=request.getContextPath()%>/ArtServervlet?action=delete&articleNo=${artVO.articleNo}" onclick="gotoDetele()" style="color:white" >刪除文章</a>

		</c:if>
		<!-- TODO for develop i put the button out -->
        </p>

		

        <hr>

        <!-- Date/Time -->
        <p>Posted on 			
        <fmt:formatDate pattern="yyyy-MM-dd" value="${artVO.getPostTime()}" />
        </p>

        <hr>

        <!-- Preview Image -->
        <img class="img-fluid rounded" src="<%=request.getContextPath()%>/front-end/article/img/pic1.jpeg" alt="" width="900" hight="300">

        <hr>

        <!-- Post Content -->
          <p class="lead">
          ${artVO.getArticleContent()}
        <blockquote class="blockquote">
          <p class="mb-0">人生真美好.</p>
          <footer class="blockquote-footer">Someone famous in
            <cite title="Source Title">Source Title</cite>
          </footer>
        </blockquote>

		<!-- Report button -->
		<button class="btn btn-primary rep" onclick="myFunction()" >檢舉</button>
		
		<p id="demo reptitle"></p>
		
		<c:if test="<%=exist != true%>">
			<button class="addToFav btn btn-primary canfav" title="收藏">
			<i class="icon_heart"></i><span>加入收藏</span>
			</button>
			<input type="hidden" id ="iloveAction" value="insert">

		</c:if>
		<c:if test="<%=exist == true%>">
			<button class="addToFav btn btn-primary canfav" title="取消收藏">
				<i class="icon_heart"></i><span>取消收藏</span>
			</button>
		<input type="hidden" id ="iloveAction" value="delete">

		</c:if>
	<!--  
<div>
<div class="flexbox">
	<div class="fav-btn">
		<span href="" class="favme dashicons dashicons-heart"></span>
	</div>
</div>		
</div>		
-->
	
       
        <hr>

        <!-- Comments Form -->
        <div class="card my-4">
          <h5 class="card-header">Leave a Comment:</h5>
          <div class="card-body">
            <form>
              <div class="form-group">
                <textarea id="addComment" class="form-control" rows="3"></textarea>
              </div>
              <button type="button" class="btn btn-primary" onclick="addCommentToServer();">Submit</button>
            </form>
          </div>
        </div>

        <!-- Single Comment -->
<c:forEach var="articleMesVo"   items="${articleMesList}"   varStatus="status" >
		<div class="media mb-4">
          <img class="d-flex mr-3 rounded-circle" src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${articleMesVo.memNo}&type=memberPic" alt="" style="max-height:50px;max-width:50px">
          <div class="media-body">
            <h5 class="mt-0"> ${articleMesVo.memberName}</h5>
            ${articleMesVo.getMsgCont()}
          </div>
        </div>
</c:forEach>
        

          </div>
        </div>

      </div>
	



  <!-- /.container -->
<input type="hidden" id ="memNo" value="${memVO.member_Id}">
<input type="hidden" id ="memName" value="${memVO.getMem_Name()}">

<form id="gotoForm" method="POST" action="<%=request.getContextPath()%>/ArtServlet">
<input type="hidden" id="gotoFormAct" name="action" value="getOne_For_front_Update">
<input type="hidden" name="article_no" value="${artVO.articleNo}">
<input type="hidden" name="fromPage" value="front">
</form>

  <!-- Footer -->
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
						
					
					<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/article/T3index/images/tick.png">
					<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/article/T3index/images/message.png">
					<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/article/T3index/images/mail.png">
					<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/article/T3index/images/dot.png">
					<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/article/T3index/images/instagram-b.png">
					<img  class="footerimg" src="<%=request.getContextPath()%>/front-end/article/T3index/images/facebook-b.png">

					<p>Copyright &copy; 2020 Training Studio
						- Modify by <a rel="nofollow" href="https://reurl.cc/yZ22W2" class="tm-text-link" target="_parent">FatBoy</a></p>
						<!-- You shall support us a little via PayPal to info@templatemo.com -->

					</div>
				</div>
			</div>
		</footer>
  <!-- Bootstrap core JavaScript -->
  <script src="<%=request.getContextPath()%>/front-end/article/vendor/jquery/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/front-end/article/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
  
  function addCommentToServer(){
	  	 var comment = $("#addComment").val(); //.html()
	  	 var articleNo = '${artVO.articleNo}';
	  	 var commentor = '${memVO.getMem_Name()}';
	  	 
	  	 if(commentor==''){
	  		 alert('訪客請先登入才可以留言喔！');
	  		 window.location = "<%=request.getContextPath()%>/front-end/logIn2.jsp";
	  		 return false;
	  	 }
	  	 
	  	 //check content
		  $.ajax({
			    url: "${pageContext.request.contextPath}/ArtMesServlet?action=insert",
			    type: "POST",
			    data: {
			    	"msgCont":comment,
			    	"articleNo":articleNo
			    },
			    success: function(data) {
			    	console.log(data);
			    //var dataJson = JSON.parse(data);
			    
			     if(data.message.indexOf("success")!=-1){
			    	 alert('留言成功!');
				      $('#mainContainer').append(
							     '<div class="media mb-4">'+
						          '<img class="d-flex mr-3 rounded-circle" src="<%=request.getContextPath()%>/front-end/ShowPhotos?img_no=${memVO.getMember_Id()}&type=memberPic" style="max-height:50px;max-width:50px" >'+
						          '<div class="media-body">'+
						          '<h5 class="mt-0"> '+commentor+'</h5>'+
						           comment+'</div>'+
						        '</div>'
					   );
				      /*  http://localhost:8080/EA102G3/front-end/ShowPhotos?img_no=M001&type=memberPic  */
				      $("#addComment").val("");
			      }else{
			    	  alert(data.message);
			      }


			      
			    },
			    error:function(data) {
				  alert("add comement fail!");
			    }
		  });
	  	 
	  	 /*
	  	 $.ajax({
			    url: "${pageContext.request.contextPath}/ArtMesServlet?action=insert",
			    type: "POST",
			    data: {"msgCont":content},

		  })
	  	 */
	  }
  
  

  
	
	function myFunction() {
	  var report = prompt("請輸入檢舉理由", "檢舉理由");
	  if (report != null) {
		  
		  formReport(report);
		  
	    document.getElementById("demo").innerHTML =" 您檢舉的理由: " + report + "! 您的檢舉我們己收到，我們會盡快為您處理";
	    
	  }
	}
	
	function formReport(report){
	  	 //report
		  $.ajax({
			    url: "${pageContext.request.contextPath}/ArtRepServlet?action=insert",
			    type: "POST",
			    data: {
			    	"Report":report,
			    	"articleNo":"${artVO.articleNo}"
			    },

			    success: function(data) {
			      alert(data);
			      
			    },
			    fail:function(data) {
				  alert("add comement fail!");
			    }
		  });
	  	 
	}
	 $().ready(function () {
		  	 /* 收藏文章 */
		  $(".addToFav").click(function(){
				var action = $("#iloveAction").val();
				var articleNo = '${artVO.articleNo}';
				var memNo = $("#memNo").val();
			 	//alert(action+","+articleNo+","+memNo);
				
			    $.ajax({
			    	url: "<%=request.getContextPath()%>/ArtILoveServlet",
			    	type: "POST",
			     	data:{
			    	 "action":action,
					 "articleNo":articleNo,
					 "memNo":$("#memNo").val(),
			   		},
			   		success:function(event){
			   			alert(event);

			   			if(event.indexOf("success")==-1){
			   		  	 window.location = "<%=request.getContextPath()%>/front-end/logIn2.jsp";
							return false;
			   			}
			   		},
			 	});
			   
			    if(action==="insert"){
			    	$("#iloveAction").val("delete");
			    	$(this).attr("title","取消收藏");
			    	$(this).html("<i class='icon_heart' ></i><span>取消收藏</span>");
			    }else{
			    	$("#iloveAction").val("insert");
			    	$(this).attr("title","收藏");
			    	$(this).html("<i class='icon_heart_alt' ></i><span>加入收藏</span>");
			    }
			    
			    
			 }); 
		  	 
			// Favorite Button - Heart
			$('.favme').click(function() {
				$(this).toggleClass('active');
			});

			/* when a user clicks, toggle the 'is-animating' class */
			$(".favme").on('click touchstart', function(){
			  $(this).toggleClass('is_animating');
			});

			/*when the animation is over, remove the class*/
			$(".favme").on('animationend', function(){
			  $(this).toggleClass('is_animating');
			});

	  });
	 
	 function gotoEdit(){
		 $('form#gotoForm').submit();
	 }
	 
	 function gotoDetele(){
		 var goform = document.getElementById("gotoForm");
		 var goAct = document.getElementById("gotoFormAct");
		 goform.action="<%=request.getContextPath()%>/ArtServlet?action=delete&articleNo=${artVO.articleNo}";
		 goAct.value="detele";
		 $('form#gotoForm').submit();
	 }
	 
	 
	 


	
	
   </script>

</body>

</html> 