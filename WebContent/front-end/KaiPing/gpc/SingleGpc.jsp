<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.gpc_List.model.*"%>
<%@ page import="com.gpc.model.*"%>

<jsp:useBean id="gpcSvc" scope="page" class="com.gpc.model.GpcService" />
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService" />
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<jsp:useBean id="gpc_DiscSvc" scope="page" class="com.gpc_Disc.model.Gpc_DiscService" />
<jsp:useBean id="gpc_RepSvc" scope="page" class="com.gpc_Rep.model.Gpc_RepService" />
<jsp:useBean id="expSvc" scope="page" class="com.exp.model.ExpService" />
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />



<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");

%>



<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">   

	<title>${gpcSvc.getOneGpc(param.gpc_Id).gpc_Name}</title>
<!-- 	<title>TTTTTT</title> -->
	<!-- Bootstrap 的 CSS -->

	<link rel="stylesheet" type="text/css" 
		href="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.css">
	
	<link rel="stylesheet" type="text/css" 
		href="<%=request.getContextPath()%>/front-end/KaiPing/css/fonts/font-awesome.css">
	
	<link rel="stylesheet" 
		href="<%=request.getContextPath()%>/front-end/KaiPing/css/G3header.css">
	
	<!--KaiPing-->

	<!-- ElegantFonts CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/elegant-fonts.css">
    <!-- themify-icons CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/themify-icons.css">
     <!-- Swiper CSS -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/swiper.min.css">
    <!-- Styles -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/gpc_style.css">
	
	<!--KaiPing-->
	<style>
	#overflowTable {
	  width:100%;
	  height: 200px;
	  overflow-y: scroll;
	}
	.gpc_State{
	  margin-top: 18px;
	  color: #ed563b;
	}
	
	</style>
	
</head>

<body class="courses-page single-courses-page">
	<%@ include file="js/Header.html"%>

<!------------------------------------------------------------- ***** 這裡以上複製貼上 ***** ------------------------------------------------------->

	<!-- ***** Main Banner Area Start ***** -->
	<div class="main-banner" id="top">
		<div class="page-header">
	        <div class="page-header-overlay">
	            <div class="container">
	                <div class="row">
	                    <div class="col-12">	                        
                            <h1 class="entry-title">${gpcSvc.getOneGpc(param.gpc_Id).gpc_Name}</h1>

	                    </div><!-- .col -->
	                </div><!-- .row -->
	            </div><!-- .container -->
	        </div><!-- .page-header-overlay -->
	    </div><!-- .page-header -->
	
	    <div class="container">
	        <div class="row">
	            <div class="col-12 offset-lg-1 col-lg-10">
	                <div class="featured-image">
	                    <img src="<%=request.getContextPath()%>/front-end/KaiPing/gpc/ShowPic.do?gpc_Id=${param.gpc_Id}&picNo=pic1" alt="">
	
	                    <div class="course-cost"><span>$</span><span>${gpcSvc.getOneGpc(param.gpc_Id).price}</span></div>
	                </div>
	            </div><!-- .col -->
	        </div><!-- .row -->
	
	        <div class="row">
	            <div class="col-12 offset-lg-1 col-lg-1">
	                <div class="post-share">
	                    <h3>share</h3>
	
	                    <ul class="flex flex-wrap align-items-center p-0 m-0">
	                        <li><a href="#"><i class="fa fa-facebook"></i></a></li>
	                        <li><a href="#"><i class="fa fa-twitter"></i></a></li>
	                        <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
	                        <li><a href="#"><i class="fa fa-instagram"></i></a></li>
	                        <li><a href="#"><i class="fa fa-thumb-tack"></i></a></li>
	                    </ul>
	                </div><!-- .post-share -->
	            </div><!-- .col -->
	
	            <div class="col-12 col-lg-8">
	                <div class="single-course-wrap">
	                    <div class="course-info flex flex-wrap align-items-center">
	                        <div class="course-author flex flex-wrap align-items-center mt-3">
	                         
	
	                            <div class="author-wrap">
	                                <label class="m-0">教練</label>
	                                <div class="author-name">
	            						<a href="<%=request.getContextPath()%>/front-end/coachInfo.jsp?coach_Id=${gpcSvc.getOneGpc(param.gpc_Id).coach_Id}">
	            							${memSvc.getOneMem(coachSvc.getOneByCoach(gpcSvc.getOneGpc(param.gpc_Id).coach_Id).member_Id).mem_Name}
	                                	</a>
	                                </div>
	                            </div><!-- .author-wrap -->
	                        </div><!-- .course-author -->
	
	                        <div class="course-cats mt-3">
	                            <label class="m-0">課程類別</label>
	                            <div class="author-name">
	                            	<a href="#">${exptypeSvc.getOneExptype(gpcSvc.getOneGpc(param.gpc_Id).exp_Id).exp_Name} </a>
	                            </div>
	                        </div><!-- .course-cats -->
	                        
	                        <div class="course-students mt-3">
	                            <label class="m-0">剩餘名額：</label>
	                            <div class="author-name"><a href="#">${gpcRemain}</a></div>
	                        </div><!-- .course-students -->
	                        
<!-- ****************************************************** -->
							<c:if test="${gpcFull==0}">
								<c:set var="state" value="${gpcSvc.getOneGpc(param.gpc_Id).gpc_State}"/>			              	
								<c:choose>
									<c:when test="${state == 0}">
								       	<h5 class="gpc_State">&nbsp審核中</h5>
								    </c:when>
									<c:when test="${state == 1}">
										<div class="buy-course mt-3">
										
											<form id="joinGpc" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
												<input name="action" value="joinGpc" type="hidden">
												<input name="gpc_Id" value="${param.gpc_Id}" type="hidden">
												<input name="member_Id" value="${memLogIn.member_Id}" type="hidden">
												<button id="sideTwo">報名揪團</button>
											</form>
<!-- 				                            <a class="btn" href="#">報名揪團</a> -->
				                        </div><!-- .buy-course -->

								    </c:when>
								    <c:when test="${state == 2}">
								       	<h5 class="gpc_State">&nbsp未通過審核</h5>
								    </c:when>
								    <c:otherwise>
								    	<h5 class="gpc_State">&nbsp該課程已取消</h5>
								    </c:otherwise>
								</c:choose>
							</c:if>	
							<c:if test="${gpcFull==1}">
								<c:set var="state" value="${gpcSvc.getOneGpc(param.gpc_Id).gpc_State}"/>
								<c:choose>
									<c:when test="${state >= 0}">
								       	<h5 class="gpc_State">&nbsp課程已額滿</h5>
								    </c:when>
								    <c:otherwise>
								    	<h5 class="gpc_State">&nbsp該課程已取消</h5>
								    </c:otherwise>
								</c:choose>								
							</c:if>
							<c:if test="${gpcPass==1}">
								<h5 class="gpc_State">&nbsp已成團</h5>
                       	    </c:if>                 
<!-- ****************************************************** -->	 	                        
	                    
	                    
	                    </div><!-- .course-info -->
	
	                    <div class="single-course-cont-section">
	
	
	                        <h2>課程內容</h2>
	
	                        <p>${gpcSvc.getOneGpc(param.gpc_Id).intro}</p>
	                        
	                       	<h2>上課地點</h2>
	
	                        <p>${gpcSvc.getOneGpc(param.gpc_Id).address}</p>
	                        
	                        <h2>上課時間</h2>
							<div id="overflowTable">
								<table  id="timeTable"class="table table-bordered table-striped table-responsive-xl" >
									<tr class="weekSch" id="row_1st">
									<th class="schCell">日期</th>
									<th class="schCell">時間</th>
									</tr>
								</table>
							</div>

	                    </div>
		
	                    <div class="related-courses">
	                        <header class="entry-heading flex flex-wrap justify-content-between align-items-center">
	                            <h2 class="entry-title">Related Courses</h2>
	
	                            <a href="#">View all</a>
	                        </header><!-- .entry-heading -->
	
	                        <div class="row mx-m-25">
	                            <div class="col-12 col-lg-6 px-25">
	                                <div class="course-content">
	                                    <figure class="course-thumbnail">
	                                        <a href="#"><img src="<%=request.getContextPath()%>/front-end/KaiPing/gpc/ShowPic.do?gpc_Id=${param.gpc_Id}&picNo=pic2" alt=""></a>
	                                    </figure><!-- .course-thumbnail -->	                                    
	                                </div><!-- .course-content -->
	                            </div><!-- .col -->
	
	                            <div class="col-12 col-lg-6 px-25">
	                                <div class="course-content">
	                                    <figure class="course-thumbnail">
	                                        <a href="#"><img src="<%=request.getContextPath()%>/front-end/KaiPing/gpc/ShowPic.do?gpc_Id=${param.gpc_Id}&picNo=pic3" alt=""></a>
	                                    </figure><!-- .course-thumbnail -->
	                                </div><!-- .course-content -->
	                            </div><!-- .col -->
	                        </div><!-- .row -->
	                    </div><!-- .related-course -->
	                </div><!-- .single-course-wrap -->
	                
	                <div class="post-comments-wrap">
						<div class="post-comments">
							<h3 class="comments-title">
								<span class="comments-number">留言板</span>
							</h3>

							<ol class="comment-list">
								<li class="comment">
									<article class="comment-body">
										<figure class="comment-author-avatar">
											<img
												src="<%=request.getContextPath()%>/front-end/KaiPing/images/c-1.png"
												alt="">
										</figure>
										<!-- .comment-author-avatar -->

										<div class="comment-wrap">
											<div class="comment-author">
												<span class="comment-meta d-block"> <a href="#">16
														Sep 2020</a>
												</span>
												<!-- .comment-meta -->

												<span class="fn"> <a href="#">Chris Hadfield</a>
												</span>
												<!-- .fn -->
											</div>
											<!-- .comment-author -->

											<p>我想當館長</p>

											<div class="reply">
												<a>reply</a>
												<form class="reply-form">
													<textarea rows="4" placeholder="Messages" style="display:none;"></textarea>
													<input type="button" value="send" style="display:none;">
												</form>
											</div>
											<!-- .reply -->
										</div>
										<!-- .comment-wrap -->

										<div class="clearfix"></div>
									</article> <!-- .comment-body -->
								</li>
								<!-- .comment -->

								<li class="comment">
									<!-- .comment-body -->
								</li>
								<!-- .comment -->
							</ol>
							<!-- .comment-list -->
						</div>
						<!-- .post-comments -->
                        <%if(memLogIn!=null){%>
						<div class="comments-form">
							<div class="comment-respond">
								<h3 class="comment-reply-title">留言</h3>

								<form class="comment-form">
									<input type="text" value="${memLogIn.mem_Name}">
									<!-- 	                                <input type="email" placeholder="Email"> -->
									<!-- 	                                <input type="url" placeholder="Website"> -->
									<textarea rows="4" placeholder="Messages"></textarea>
									<input type="button" value="send message" id="leave">
								</form>
								<!-- .comment-form -->
							</div>
							<!-- .comment-respond -->
						</div>
						<%}else{%>
						<div class="comments-form">
							<div class="comment-respond">
								<h3 class="comment-reply-title">留言</h3>

								<form class="comment-form">
									<input type="text" placeholder="Name">
									<!-- 	                                <input type="email" placeholder="Email"> -->
									<!-- 	                                <input type="url" placeholder="Website"> -->
									<textarea rows="4" placeholder="Messages"></textarea>
									<input type="button" value="send message" id="leave">
								</form>
								<!-- .comment-form -->
							</div>
							<!-- .comment-respond -->
						</div>
						<%}%>
						<!-- .comments-form -->
					</div>
					<!-- .post-comments-wrap -->
	                
	            </div><!-- .col -->
	        </div><!-- .row -->
	    </div><!-- .container -->
	</div><!-- .main-banner -->
	<br>
	<br>
	<!-- ***** Main Banner Area End ***** -->
	<!------------------------------------------------------------- ***** 這裡以下複製貼上 ***** ------------------------------------------------------->
	<%@ include file="js/footer.html"%>

		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/jquery-2.1.0.min.js"></script>
		<!-- Bootstrap -->
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/popper.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.js"></script>
		<!-- Plugins -->
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/scrollreveal.min.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/waypoints.min.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/jquery.counterup.min.js"></script>
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/imgfix.min.js"></script> 
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/mixitup.js"></script> 
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/accordions.js"></script>
		<!-- Global Init -->
		<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/custom.js"></script>
		
		<!--KaiPing-->
		<script type='text/javascript' src='<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/swiper.min.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/masonry.pkgd.min.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/jquery.collapsible.min.js'></script>
		
		<!--Gray-->
		<script>
			var mydate = new Date();
		    var t=mydate.toLocaleString();
				$("#leave").click(function(){
					$(".comment-list").append(`<li class="comment">
			                <article class="comment-body">
			                <figure class="comment-author-avatar">
			                    <img src="<%=request.getContextPath()%>/front-end/KaiPing/images/c-3.png">
			                </figure><!-- .comment-author-avatar -->
		
			                <div class="comment-wrap">
			                    <div class="comment-author">
			                        <span class="comment-meta d-block">
			                            <a>`+t+`</a>
			                        </span><!-- .comment-meta -->
		
			                        <span class="fn">
			                            <a href="#">`+$(".comment-form>input").val()+`</a>
			                        </span><!-- .comment-autho -->
			                    </div><!-- .comment-author -->
		
			                    <p>`+$('.comment-form>textarea').val()+`</p>
		
			                    <div class="reply">
			<!-- 	                                            <a href="#">like</a> -->
			                        <a>reply</a>
			                        <textarea rows="3" placeholder="Messages" style="display: none;"></textarea>
									<input type="button" value="send" style="display: none;">
			                    </div><!-- .reply -->
			                </div><!-- .comment-wrap -->
		
			                <div class="clearfix"></div>
			            </article><!-- .comment-body -->
			        </li>`);
					$(this).siblings('textarea').val('');
					$(this).siblings("input[type='text']").val('');
				});
				// 點擊 reply
				$(document).on('click',".reply > a",function(){
					$(this).siblings('textarea').toggle();
					$(this).siblings("input[type='button']").toggle();
					$(this).hide();
				});
				
				// 發送 send
				$(document).on('click',".reply > input[type='button']",function(){
					$(this).parent().parent().append(
					`<ol class="children">
		                    <li class="comment">
		                    <article class="comment-body">
		                        <figure class="comment-author-avatar">
		                            <img src="<%=request.getContextPath()%>/front-end/KaiPing/images/c-2.png" alt="">
		                        </figure><!-- .comment-author-avatar -->
		
		                        <div class="comment-wrap">
		                            <div class="comment-author">
		                                <span class="comment-meta d-block">
		                                    <a>`+t+`</a>
		                                </span><!-- .comment-meta -->
		
		                                <span class="fn">
		                                    <a>教練姓名</a>
		                                </span><!-- .fn -->
		                            </div><!-- .comment-author -->
		
		                            <p>`+$('.reply>textarea').val()+`</p>
		
		                        </div><!-- .comment-wrap -->
		
		                        <div class="clearfix"></div>
		                    </article><!-- .comment-body -->
		                </li><!-- .comment -->
		            </ol>`
		            );
					
					$(this).siblings('textarea').toggle();
					$(this).toggle();
				});
					
		</script>
		
		<!--KaiPing-->
		<script>
			//資料
	        const timeTable = document.getElementById("timeTable");
	
	        
	        //模擬從後端拿到的值
	        const timeObj = <%=request.getAttribute("jsonStrGpcSch")%>;
	        function init(){
	
	        	for(var i = 0; i < Object.keys(timeObj).length; i++){
	        		var dateStr = Object.keys(timeObj)[i];
	        		var timeStr = timeObj[dateStr];
	        		var tempDay = new Date(dateStr).getDay();
	        		var dayStr = '';
		            switch (tempDay) {
		            	case 1:
		            		dayStr = ' (一) ';
		            		break;
		            	case 2:
		            		dayStr = ' (二) ';
		            		break;
		            	case 3:
		            		dayStr = ' (三) ';
		            		break;
		            	case 4:
		            		dayStr = ' (四) ';
		            		break;
		            	case 5:
		            		dayStr = ' (五) ';
		            		break;
		            	case 6:
		            		dayStr = ' (六) ';
		            		break;
		            	case 0:
		            		dayStr = ' (日) ';
		       		}
		       		console.log(dayStr);
	        		for(var j = 0; j < timeStr.length; j++){
	        			//這裡先寫>0，之後再修正
	        			if(timeStr.charAt(j) >0){
		        			var trElem = document.createElement('tr');
		        			var tdDate = document.createElement('td');
		        			var tdTime = document.createElement('td');
	
		        			tdDate.innerText = dateStr + dayStr;
		        			trElem.appendChild(tdDate);
	
		        			var prefix = j>9? "": "0";
				            var startStr = prefix + j + ":00";
				            var nextNum = j + 1;
				            var nextPrefix = nextNum>9? "": "0";
				            var endStr = nextPrefix + nextNum + ":00";
				            var resultStr = startStr + " - " + endStr;
				            tdTime.innerText = resultStr;
				            trElem.appendChild(tdTime);
				            timeTable.appendChild(trElem);
	
	        			}
	        		}
	        	}
	        }

			init();
	</script>
		
		
	</body>
	</html>
