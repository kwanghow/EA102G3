<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.gpc.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.exp.model.*"%>
<%@ page import="com.exptype.model.*"%>

<%
  GpcVO gpcVO = (GpcVO) request.getAttribute("gpcVO");

%>

<jsp:useBean id="expSvc" scope="page" class="com.exp.model.ExpService" />
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">

	<title>�קﴪ�νҵ{</title>

	<!-- Bootstrap �� CSS -->

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/KaiPing/css/js/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/KaiPing/css/fonts/font-awesome.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/css/G3header.css">
	
	<!--KaiPing-->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/css/Kai/newGPC.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/KaiPing/datetimepicker/jquery.datetimepicker.css" />
    
    
    <style>
    	.xdsoft_datetimepicker .xdsoft_datepicker {
    		width:  300px;   /* width:  300px; */
  		}
  		.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           	height: 151px;   /* height:  151px; */
 		 }
	</style>

			
</head>

<body class="courses-page">
	<%@ include file="js/Header.html"%>

<!------------------------------------------------------------- ***** �o�̥H�W�ƻs�K�W ***** ------------------------------------------------------->
	<!-- ***** Main Banner Area Start ***** -->
	<section class="section" id="contact-us">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-xs-12" style="padding-left: 0px; padding-right: 0px;">
                    <div class="contact-form">
		                <div class="col-lg-6 offset-lg-3">
		                    <div class="section-heading dark-bg" style="margin: auto">
		                        <h2>�ק�<em>���e</em></h2>
		                        <img src="<%=request.getContextPath()%>/front-end/assets/images/line-dec.png" alt="">
		                    </div>
		                </div>	                  
	                        
<%--��L�ҥ~������--%>   <h3><span id="errorMsgs">${errorMsgs.exception}</span></h3>
                        
                        <FORM id="contact" METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" enctype="multipart/form-data">
 <%--���ê�gpc_Id--%>       <input type="hidden" name="gpc_Id" value="${gpcVO.gpc_Id}">
                          <div class="row city-selector-set">
                          	<div class="col-md-12 col-sm-12"><label>�ҵ{�W��: </label><span id="errorMsgs">${errorMsgs.gpc_Name}</span>
                              <fieldset>
                                <input type="TEXT" name="gpc_Name" size="30" placeholder="�п�J�ҵ{�W��" value="${gpcVO.gpc_Name}" />
                              </fieldset>
                            </div>
                            <div class="col-md-4 col-sm-12"><label>�}�����O: </label>
                              <fieldset>
<% //${exptypeSvc.getOneExptype(gpcSvc.getOneGpc(gpcVO.gpc_Id).exp_Id).exp_Name} %>
								<input type="TEXT" name="exp_Id" size="30" readonly value="${exptypeSvc.getOneExptype(gpcVO.exp_Id).exp_Name}" />

                              </fieldset>     
                            </div>
                            <div class="col-md-4 col-sm-12"><label>�H�ƤU��: </label><span id="errorMsgs">${errorMsgs.mem_Min}</span> 
                              <fieldset>
								<input type="TEXT" name="mem_Min" size="7" placeholder="�п�J�����" value="${gpcVO.mem_Min}" />
                              </fieldset>
                            </div>
                            <div class="col-md-4 col-sm-12"><label>�H�ƤW��: </label><span id="errorMsgs">${errorMsgs.mem_Max}</span> 
                              <fieldset>
								<input type="TEXT" name="mem_Max" size="7" placeholder="�п�J�����" value="${gpcVO.mem_Max}" />
                              </fieldset>
                            </div>
                            <%--�O�Τ��i�ק� --%>
                            <input type="hidden" name="price" size="7" value="${gpcVO.price}" />
                            <%--ú�O�}�l���i�ק� --%>
                            <input type="hidden" name="pay_Start" id="f_date1" value="${gpcVO.pay_Start}" />
                            
                            <%--�t�Xcontroller����ȭ����W�Ҥ�}�l���i�ק� --%>
                            <input type="hidden" name="gpc_Start" id="f_date1" value="${gpcVO.gpc_Start}" />
                        
                        	<%--�a�}���i�ק� --%>
                        	<input type="hidden" name="address" size="30"  value="${gpcVO.address}" />
                            
                            
                            <div class="col-md-4 col-sm-12 pic"><label>�Ĥ@�i�Ϥ�(�ʭ�): </label><span id="errorMsgs">${errorMsgs.rePick}</span>
                            	<div id="select1">
	                             	<input type='file' name="pic1" class="imgInp" id="pic1Up" /> <!--�p�߳o�̪�input�U�Ӥ����n�Oimg-->
									<img class="pics" src="<%=request.getContextPath()%>/back-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic1" />
									<br>
								</div>	
							</div>
							<br>
							<div class="col-md-4 col-sm-12 pic" ><label>�ĤG�i�Ϥ�: </label><span id="errorMsgs">${errorMsgs.rePick}</span> 
								<div id="select2">
	                             	<input type='file' name="pic2" class="imgInp" id="pic2Up" /> <!--�p�߳o�̪�input�U�Ӥ����n�Oimg-->
									<img class="pics" src="<%=request.getContextPath()%>/back-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic2" />
									<br>
								</div>		
							</div>
							<br>							
							<div class="col-md-4 col-sm-12 pic" ><label>�ĤT�i�Ϥ�: </label><span id="errorMsgs">${errorMsgs.rePick}</span> 
								<div id="select3">
	                             	<input type='file' name="pic3" class="imgInp" id="pic3Up" /> <!--�p�߳o�̪�input�U�Ӥ����n�Oimg-->
									<img class="pics" src="<%=request.getContextPath()%>/back-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic3" />
									<br>
								</div>		
							</div>
							
							<div class="col-lg-12"><label>�ҵ{���e²�z: </label><span id="errorMsgs">${errorMsgs.intro}</span> 
                              <fieldset>
                                <textarea name="intro" rows="6" id="message" placeholder="�п�J�z���ҵ{���e��r²�z�A��500�r��">${gpcVO.intro}</textarea>
                              </fieldset>
                            </div>
																		
							<br>
							<br>
							<br>
                            <div class="col-lg-12">
                              <fieldset>
<!--                               	<button id="returnBtn" class="main-button">�^�W�@��</button> -->
<%--�ק藍�γo�ӫ��s --%>    <!--<button id="resetBtn" class="main-button">�M�ŭ���</button> -->
                              	<input type="hidden" name="action" value="update_front">
                              	<input name="coach_Id" value="${coachLogIn.coach_Id}" type="hidden">
                                <button type="submit" id="form-submit" class="main-button">�e�X�ק�</button>
                              </fieldset>
                            </div>
                          </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
	<!-- ***** Main Banner Area End ***** -->
	<!------------------------------------------------------------- ***** �o�̥H�U�ƻs�K�W ***** ------------------------------------------------------->
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
			
		
		<!-- �w���Ϥ���&datetimepicker�� -->
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
// 				// ���DOM����
// 				var contact = document.getElementById('contact');
// 				var returnBtn = document.getElementById('returnBtn');
// 				var resetBtn = document.getElementById('resetBtn');

			
// 				// ��^�W�@��
// 				returnBtn.addEventListener('click', function(e){
// 					// �`�N: button�bform���̡A�����|�a���w�]�欰�N��e�X���
// 					e.preventDefault();
// 					window.location.href="www.google.com"
// 				}, false);

// 				// ��歫�]
// 				resetBtn.addEventListener('click', function(e){
// 					// �`�N: button�bform���̡A�����|�a���w�]�欰�N��e�X���
// 					e.preventDefault();
					
// 					//�Ϥ��ܦ^�w�]��
<%-- 					$(".pics").attr('src', "<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"); --%>

// 					// ��歫�] ���ܡGreset()
// 					contact.reset();
// 				}, false);				
			}	        		
	        window.onload = init;	
	        
  		</script>       
	</body>
</html>
