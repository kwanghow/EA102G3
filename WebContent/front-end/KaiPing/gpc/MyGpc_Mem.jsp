<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.gpc.model.*"%>
<%@ page import="com.gpc_List.model.*"%>

<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
	if (memLogIn != null) {
		System.out.println(memLogIn.getMember_Id() + "目前進入了MyGpc_Mem.jsp(MyGpc_Mem.jsp_13行印出)");
	}

	if (coachLogIn != null) {
		System.out.println(coachLogIn.getCoach_Id() + "目前進入了MyGpc_Mem.jsp(MyGpc_Mem.jsp_17行印出)");
	}
	String identity = memLogIn == null ? "Member" : "Coach";
	request.setAttribute("identity", identity);
	System.out.println();
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

<title>我的揪團課程</title>

<!-- Bootstrap 的 CSS -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/fonts/font-awesome.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/G3header.css">

<!--KaiPing-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/newGPC.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/KaiPing/datetimepicker/jquery.datetimepicker.css" />


<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>


</head>

<body class="courses-page">
	<%@ include file="js/Header.html"%>

	<!------------------------------------------------------------- ***** 這裡以上複製貼上 ***** ------------------------------------------------------->
	<!-- ***** Main Banner Area Start ***** -->
	<section class="section" id="schedule">
		<div class="container">
			<div class="row">
				<div class="col-lg-6 offset-lg-3">
					<div class="section-heading dark-bg">
						<h2>
							我的 <em>揪團</em>
						</h2>
						<img
							src="<%=request.getContextPath()%>/front-end/assets/images/line-dec.png"
							alt="">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-10 offset-lg-1">
					<div class="schedule-table filtering">
						<table>
							<tbody>
								<tr>
                                    <td style="color:darkgray">課程名稱</td>
                                    <td style="color:darkgray">課程詳情</td>
                                    <td style="color:darkgray">課程狀態</td>
                                    <td style="color:darkgray">付款狀態</td>
                                    <td style="color:darkgray">取消課程</td>
								</tr>
								<jsp:useBean id="gpcSvc" scope="page" class="com.gpc.model.GpcService" />                                
								<jsp:useBean id="gpc_ListSvc" scope="page" class="com.gpc_List.model.Gpc_ListService" />
   
									<c:forEach var="gpc_ListVO" items="${gpc_ListSvc.all}">
	  								<c:set var="gpc_State" value="${gpcSvc.getOneGpc(gpc_ListVO.gpc_Id).gpc_State}"/>
										<c:if test="${(memLogIn.member_Id==gpc_ListVO.member_Id)&&(gpc_State>=1)}">
								          <tr>
							<!--課程名稱  -->	  <td>  ${gpcSvc.getOneGpc(gpc_ListVO.gpc_Id).gpc_Name}   </td>
							<!--課程詳情  -->	  <td>  
												<form id="getGpcTime" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
													<input name="action" value="getGpcTime" type="hidden">
													<input name="gpc_Id" value="${gpc_ListVO.gpc_Id}" type="hidden">
													<input name="member_Id" value="${memLogIn.member_Id}" type="hidden">
													<button id="sideTwo">前往查看</button>
												</form>

							 				</td>
							<!--課程狀態  -->	  <td>	${myGpcStates.get(gpc_ListVO.gpc_Id)} </td>
							<!--付款狀態  -->	  <td> 
								              	<c:set var="state" value="${gpc_ListVO.mem_State}"/>
								              	

													<c:choose>
														<c:when test="${state == 0}">
													       	未具繳費資格
													    </c:when>
														<c:when test="${state == 1}">
															<form id="payGpc" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
																<input name="action" value="payGpc" type="hidden">
																<input name="gpc_Id" value="${gpc_ListVO.gpc_Id}" type="hidden">
																<input name="member_Id" value="${gpc_ListVO.member_Id}" type="hidden">
																<button id="payBtn">前往付款</button>
															</form>
													    </c:when>
													    <c:when test="${state == 2}">
													       	<p style="color:#ed563b">繳費完成</p>
													    </c:when>
													    <c:otherwise>
													                       已取消報名
													    </c:otherwise>
													</c:choose>
										        
										              
								              </td>
							<!--取消課程  -->	  <td>
								              	<c:choose>
									              <c:when test="${state >= 0}">
									              	<form id="cancelThisGpc_Mem" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
														<input name="action" value="cancelThisGpc_Mem" type="hidden">
														<input name="gpc_Id_Canceled" value="${gpc_ListVO.gpc_Id}" type="hidden">
														<input name="member_Id" value="${gpc_ListVO.member_Id}" type="hidden">
														<button id="cancelBtn">我要取消</button>
													</form>
												  </c:when>
												  <c:otherwise>
												                       已取消報名
												  </c:otherwise>
												 </c:choose>
								              </td>
								          </tr>
										</c:if>
										<c:if test="${(memLogIn.member_Id==gpc_ListVO.member_Id)&&(gpc_State<1)}">
								          <tr>
								              <td style="color:#808080">  ${gpcSvc.getOneGpc(gpc_ListVO.gpc_Id).gpc_Name}   </td>
								              <td >
								              	<form id="getGpcTime" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
													<input name="action" value="getGpcTime" type="hidden">
													<input name="gpc_Id" value="${gpc_ListVO.gpc_Id}" type="hidden">
													<input name="member_Id" value="${memLogIn.member_Id}" type="hidden">
													<button id="sideTwo">前往查看</button>
												</form>
								              </td>
								              <td style="color:#808080">教練取消</td>
								              <td style="color:#808080">教練取消</td>
								              <td style="color:#808080">教練取消</td>
								          </tr>
										</c:if>
										
										
										
										
										
									</c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </section>
	<!-- ***** Main Banner Area End ***** -->
	<!------------------------------------------------------------- ***** 這裡以下複製貼上 ***** ------------------------------------------------------->
	<%@ include file="js/footer.html"%>

	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/jquery-2.1.0.min.js"></script>

	<!-- Bootstrap -->
	<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/popper.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.js"></script>

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
	<script>
			//圖片用
			function readURL(input) {
				if (input.files && input.files[0]) {
					if(input.files[0].type.indexOf('image') > -1) {
				    	var reader = new FileReader();			    
				    	reader.onload = function(e) {
				    		$(input).next().attr('src', e.target.result);
				    	}			    
				    	reader.readAsDataURL(input.files[0]); // convert to base64 string
					} else {
						alert('請上傳圖片檔(jpg/jpeg/png/gif/bmp之格式)');
					}
			  	}
			}

			$(".imgInp").change(function() {
				readURL(this);
			});
			
				        
	        //網頁底部按鈕功能
			function init(){
				// 抓取DOM元素
				var contact = document.getElementById('contact');
				var returnBtn = document.getElementById('returnBtn');
				var resetBtn = document.getElementById('resetBtn');

			
				// 返回上一頁
				returnBtn.addEventListener('click', function(e){
					// 注意: button在form表單裡，本身會帶有預設行為將其送出表單
					e.preventDefault();
					window.location.href="www.google.com"
				}, false);

				// 表單重設
				resetBtn.addEventListener('click', function(e){
					// 注意: button在form表單裡，本身會帶有預設行為將其送出表單
					e.preventDefault();
					
					//圖片變回預設圖
					$(".pics").attr('src', "<%=request.getContextPath()%>
		/front-end/KaiPing/images/example.png");

								// 表單重設 提示：reset()
								contact.reset();
							}, false);
		}
		window.onload = init;
	</script>
</body>
</html>
