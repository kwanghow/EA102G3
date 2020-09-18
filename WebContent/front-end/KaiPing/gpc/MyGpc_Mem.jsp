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
		System.out.println(memLogIn.getMember_Id() + "�ثe�i�J�FMyGpc_Mem.jsp(MyGpc_Mem.jsp_13��L�X)");
	}

	if (coachLogIn != null) {
		System.out.println(coachLogIn.getCoach_Id() + "�ثe�i�J�FMyGpc_Mem.jsp(MyGpc_Mem.jsp_17��L�X)");
	}
	String identity = memLogIn == null ? "Member" : "Coach";
	request.setAttribute("identity", identity);
	System.out.println();
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

<title>�ڪ����νҵ{</title>

<!-- Bootstrap �� CSS -->

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

	<!------------------------------------------------------------- ***** �o�̥H�W�ƻs�K�W ***** ------------------------------------------------------->
	<!-- ***** Main Banner Area Start ***** -->
	<section class="section" id="schedule">
		<div class="container">
			<div class="row">
				<div class="col-lg-6 offset-lg-3">
					<div class="section-heading dark-bg">
						<h2>
							�ڪ� <em>����</em>
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
                                    <td style="color:darkgray">�ҵ{�W��</td>
                                    <td style="color:darkgray">�ҵ{�Ա�</td>
                                    <td style="color:darkgray">�ҵ{���A</td>
                                    <td style="color:darkgray">�I�ڪ��A</td>
                                    <td style="color:darkgray">�����ҵ{</td>
								</tr>
								<jsp:useBean id="gpcSvc" scope="page" class="com.gpc.model.GpcService" />                                
								<jsp:useBean id="gpc_ListSvc" scope="page" class="com.gpc_List.model.Gpc_ListService" />
   
									<c:forEach var="gpc_ListVO" items="${gpc_ListSvc.all}">
	  								<c:set var="gpc_State" value="${gpcSvc.getOneGpc(gpc_ListVO.gpc_Id).gpc_State}"/>
										<c:if test="${(memLogIn.member_Id==gpc_ListVO.member_Id)&&(gpc_State>=1)}">
								          <tr>
							<!--�ҵ{�W��  -->	  <td>  ${gpcSvc.getOneGpc(gpc_ListVO.gpc_Id).gpc_Name}   </td>
							<!--�ҵ{�Ա�  -->	  <td>  
												<form id="getGpcTime" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
													<input name="action" value="getGpcTime" type="hidden">
													<input name="gpc_Id" value="${gpc_ListVO.gpc_Id}" type="hidden">
													<input name="member_Id" value="${memLogIn.member_Id}" type="hidden">
													<button id="sideTwo">�e���d��</button>
												</form>

							 				</td>
							<!--�ҵ{���A  -->	  <td>	${myGpcStates.get(gpc_ListVO.gpc_Id)} </td>
							<!--�I�ڪ��A  -->	  <td> 
								              	<c:set var="state" value="${gpc_ListVO.mem_State}"/>
								              	

													<c:choose>
														<c:when test="${state == 0}">
													       	����ú�O���
													    </c:when>
														<c:when test="${state == 1}">
															<form id="payGpc" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
																<input name="action" value="payGpc" type="hidden">
																<input name="gpc_Id" value="${gpc_ListVO.gpc_Id}" type="hidden">
																<input name="member_Id" value="${gpc_ListVO.member_Id}" type="hidden">
																<button id="payBtn">�e���I��</button>
															</form>
													    </c:when>
													    <c:when test="${state == 2}">
													       	<p style="color:#ed563b">ú�O����</p>
													    </c:when>
													    <c:otherwise>
													                       �w�������W
													    </c:otherwise>
													</c:choose>
										        
										              
								              </td>
							<!--�����ҵ{  -->	  <td>
								              	<c:choose>
									              <c:when test="${state >= 0}">
									              	<form id="cancelThisGpc_Mem" action="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" method="post">
														<input name="action" value="cancelThisGpc_Mem" type="hidden">
														<input name="gpc_Id_Canceled" value="${gpc_ListVO.gpc_Id}" type="hidden">
														<input name="member_Id" value="${gpc_ListVO.member_Id}" type="hidden">
														<button id="cancelBtn">�ڭn����</button>
													</form>
												  </c:when>
												  <c:otherwise>
												                       �w�������W
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
													<button id="sideTwo">�e���d��</button>
												</form>
								              </td>
								              <td style="color:#808080">�нm����</td>
								              <td style="color:#808080">�нm����</td>
								              <td style="color:#808080">�нm����</td>
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
	<!------------------------------------------------------------- ***** �o�̥H�U�ƻs�K�W ***** ------------------------------------------------------->
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
			//�Ϥ���
			function readURL(input) {
				if (input.files && input.files[0]) {
					if(input.files[0].type.indexOf('image') > -1) {
				    	var reader = new FileReader();			    
				    	reader.onload = function(e) {
				    		$(input).next().attr('src', e.target.result);
				    	}			    
				    	reader.readAsDataURL(input.files[0]); // convert to base64 string
					} else {
						alert('�ФW�ǹϤ���(jpg/jpeg/png/gif/bmp���榡)');
					}
			  	}
			}

			$(".imgInp").change(function() {
				readURL(this);
			});
			
				        
	        //�����������s�\��
			function init(){
				// ���DOM����
				var contact = document.getElementById('contact');
				var returnBtn = document.getElementById('returnBtn');
				var resetBtn = document.getElementById('resetBtn');

			
				// ��^�W�@��
				returnBtn.addEventListener('click', function(e){
					// �`�N: button�bform���̡A�����|�a���w�]�欰�N��e�X���
					e.preventDefault();
					window.location.href="www.google.com"
				}, false);

				// ��歫�]
				resetBtn.addEventListener('click', function(e){
					// �`�N: button�bform���̡A�����|�a���w�]�欰�N��e�X���
					e.preventDefault();
					
					//�Ϥ��ܦ^�w�]��
					$(".pics").attr('src', "<%=request.getContextPath()%>
		/front-end/KaiPing/images/example.png");

								// ��歫�] ���ܡGreset()
								contact.reset();
							}, false);
		}
		window.onload = init;
	</script>
</body>
</html>
