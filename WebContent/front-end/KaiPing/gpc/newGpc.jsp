<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.gpc.model.*"%>
<%@ page import="com.coach.model.*"%>
<%@ page import="com.exp.model.*"%>
<%@ page import="com.exptype.model.*"%>
<%@ page import="com.mem.model.*"%>


<%
	MemVO memLogIn = (MemVO) session.getAttribute("memLogIn");
	CoachVO coachLogIn = (CoachVO) session.getAttribute("coachLogIn");
%>
<%
	GpcVO gpcVO = (GpcVO) request.getAttribute("gpcVO");
	
	if(coachLogIn != null){
		System.out.println(coachLogIn.getCoach_Id() + "�ثe�i�J�FnewGpc.jsp(newGpc.jsp_12��L�X)");
	}
%>


<%--datetimepicker�� --%>
<%
	java.sql.Date pay_Start = null;
	
	try {
		pay_Start = gpcVO.getPay_Start();
	} catch (Exception e) {
		pay_Start = new java.sql.Date(System.currentTimeMillis());
	}

%>


<jsp:useBean id="expSvc" scope="page" class="com.exp.model.ExpService" />
<jsp:useBean id="exptypeSvc" scope="page"
	class="com.exptype.model.ExptypeService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">

<title>�ӽд��νҵ{</title>

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
	#displayZone {
	  height: 445px;
	  overflow-y: scroll;
	}
	#overflowTable {
	  height: 730px;
	  overflow-y: scroll;
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
				<div class="col-lg-12 col-md-12 col-xs-12"
					style="padding-left: 0px; padding-right: 0px;">
					<div class="contact-form">
					
						<div class="col-lg-6 offset-lg-3">
		                    <div class="section-heading dark-bg" style="margin: auto">
		                        <h2>�ӽ�<em>����</em></h2>
		                        <img src="<%=request.getContextPath()%>/front-end/assets/images/line-dec.png" alt="">
		                    </div>
		                </div>

						<%--��L�ҥ~������--%>
						<h3>
							<span id="errorMsgs">${errorMsgs.exception}</span>
						</h3>

						<FORM id="contact" METHOD="post"
							ACTION="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do"
							enctype="multipart/form-data">


							<!-- coach_Id ���æb�o�� -->
							<input type="hidden" name="coach_Id" value="${coachLogIn.coach_Id}">
							<div class="row city-selector-set">
								<div class="col-md-12 col-sm-12">
									<label>�ҵ{�W��: </label><span id="errorMsgs">${errorMsgs.gpc_Name}</span>
									<fieldset>
										<input type="TEXT" id="gpc_Name" name="gpc_Name" size="30"
											placeholder="�п�J�ҵ{�W��" value="${gpcVO.gpc_Name}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>�}�����O:</label>
									<fieldset>
										<select class="custom-select" name="exp_Id">
											<c:forEach var="expVO" items="${expSvc.all}">
												<c:if test="${coachLogIn.coach_Id==expVO.coach_Id}">
													<c:forEach var="exptypeVO" items="${exptypeSvc.all}">
														<c:if test="${expVO.exp_Id==exptypeVO.exp_Id}">
															<option value="${expVO.exp_Id}"
																${(gpcVO.exp_Id==expVO.exp_Id)? 'selected':'' }>${exptypeVO.exp_Name}
														</c:if>
													</c:forEach>
												</c:if>
											</c:forEach>
										</select>
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>�H�ƤU��: </label><span id="errorMsgs">${errorMsgs.mem_Min}</span>
									<fieldset>
										<input type="TEXT" id="mem_Min" name="mem_Min" size="7"
											placeholder="�п�J�����" value="${gpcVO.mem_Min}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>�H�ƤW��: </label><span id="errorMsgs">${errorMsgs.mem_Max}</span>
									<fieldset>
										<input type="TEXT" id="mem_Max" name="mem_Max" size="7"
											placeholder="�п�J�����" value="${gpcVO.mem_Max}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>�O��: </label><span id="errorMsgs">${errorMsgs.price}</span>
									<fieldset>
										<input type="TEXT" id="price" name="price" size="7"
											placeholder="�п�J�C�H���O(�`���B�A��쬰�s�x��)" value="${gpcVO.price}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>���W�I����: </label><span id="errorMsgs">${errorMsgs.pay_Start}</span>
									<fieldset>
										<input name="pay_Start" id="pay_Start" type="text">
									</fieldset>
								</div>
<%--*****����W�Үɶ��A�n�q��Ʈw����***************** --%>
								<div class="col-md-4 col-sm-12">
									<label>�W�Үɶ�(�T�Ӥ뤺):</label><span id="errorMsgs">${errorMsgs.gpc_Schedule}</span>
									<fieldset class="chooseTime">
										<button type="button" class="btn btn-primary chooseTime" style="margin-top: 0px"data-toggle="modal" data-target="#exampleModal" id="chooseTimeBtn">
 			 								��ܤW�Үɶ�
										</button>
									</fieldset>
								</div>
<%--*****����W�Үɶ������A�������}�l*********************** --%>
								<div class="col-md-4 col-sm-12">
									<label>����</label><span id="errorMsgs">${errorMsgs.county}</span>
									<fieldset>
										<!-- ������� -->
										<select class="county custom-select"></select>
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>�ϰ�</label><span id="errorMsgs">${errorMsgs.district}</span>
									<fieldset>
										<!-- �ϰ��� -->
										<select class="district custom-select"></select>
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>�l���ϸ�</label><span id="errorMsgs"></span>
									<fieldset>
										<!-- �l���ϸ���� (��ĳ�[�J readonly �ݩʡA����ק�) -->
										<input name="zipcode" class="zipcode" type="text" size="3" readonly
											placeholder="�l���ϸ�">
									</fieldset>
								</div>
<%--************������浲��*********************** --%>
								<div class="col-md-12 col-sm-12">
									<label>�a�}:</label><span id="errorMsgs">${errorMsgs.address}</span>
									<fieldset>
										<input type="TEXT" id="address" name="address" size="30"
											placeholder="�п�J�W�Ҧa�I" value="${gpcVO.address}" />
									</fieldset>
								</div>

								<div class="col-md-4 col-sm-12 pic">
									<label>�п�ܲĤ@�i�Ϥ�(�ʭ�): </label><span id="errorMsgs">${errorMsgs.rePick}</span>
									<div id="select1">
										<input type='file' name="pic1" class="imgInp" id="pic1Up" /> 
										<!--�p�߳o�̪�input�U�Ӥ����n�Oimg-->
										<img class="pics"
											src="<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"
											alt="your image" /> <br>
									</div>
								</div>
								<br>
								<div class="col-md-4 col-sm-12 pic">
									<label>�п�ܲĤG�i�Ϥ�: </label><span id="errorMsgs">${errorMsgs.rePick}</span>
									<div id="select2">
										<input type='file' name="pic2" class="imgInp" id="pic2Up" />
										<!--�p�߳o�̪�input�U�Ӥ����n�Oimg-->
										<img class="pics"
											src="<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"
											alt="your image" /> <br>
									</div>
								</div>
								<br>
								<div class="col-md-4 col-sm-12 pic">
									<label>�п�ܲĤT�i�Ϥ�: </label><span id="errorMsgs">${errorMsgs.rePick}</span>
									<div id="select3">
										<input type='file' name="pic3" class="imgInp" id="pic3Up" />
										<!--�p�߳o�̪�input�U�Ӥ����n�Oimg-->
										<img class="pics"
											src="<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"
											alt="your image" /> <br>
									</div>
								</div>

								<div class="col-lg-12">
									<label>�ҵ{���e²�z: </label><span id="errorMsgs">${errorMsgs.intro}</span>
									<fieldset>
										<textarea id="intro" name="intro" rows="6" id="message"
											placeholder="�п�J�z���ҵ{���e��r²�z�A��500�r��">${gpcVO.intro}</textarea>
									</fieldset>
								</div>
<!--modal�\��϶}�l*****************************************************************-->
						<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-xl" role="document">
								<div class="modal-content">
									<div class="modal-header">
									<!--���D-->
										<h5 class="modal-title" id="exampleModalLabel">�п���W�Үɶ�</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
	<!--modal�����}�l *****************************************************************-->
											<div class="container-fluid">
												<div class="row">      	
													<div class="col-md-4 col-sm-12">
														<div style="height: 230px">
															<input name="" id="inlinePicker" type="text" value="" /><br>
														</div>
														<button class="preMonth" >�W��</button>
														<button class="preWeek" >�W�g</button>
														<button class="resetSch" >�M��</button>
														<button class="nextWeek" >�U�g</button>
														<button class="nextMonth" >�U��</button><br><br>
														<span id="totalTimes">�z�|������ɶ�</span>
														<div id="displayZone"></div>
														<div id="hiddenZone" style="display: none"></div>
													</div>
													<div class="col-md-8 col-sm-12 ">	
													<div class="row">
													<div id="overflowTable"> 
														<table class="weekSch table table-bordered table-dark table-striped table-responsive-xl" >
															<tr class="weekSch" id="row_1st">
																<th class="schCell"><button id="tglBtn1">���/����08:00�e</button></th>
																<th class="schCell">�g��</th>
																<th class="schCell">�g�@</th>
																<th class="schCell">�g�G</th>
																<th class="schCell">�g�T</th>
																<th class="schCell">�g�|</th>
																<th class="schCell">�g��</th>
																<th class="schCell">�g��</th>
															</tr>
															<tr id="row_2nd">
																<th class="row_2nd_child schCell">
																	<button id="tglBtn2">���/����20:00��</button>
																</th>
															</tr>
	
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch toggle1"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch"></tr>
															<tr class="timeSch toggle2"></tr>
															<tr class="timeSch toggle2"></tr>
															<tr class="timeSch toggle2"></tr>
															<tr class="timeSch toggle2"></tr>
														</table>
													</div>
													</div>
														<div class="row"> 
															<div class="col-md-12 col-sm-12 ">
																<button class="preMonth" style="width: 20%" >�W��</button>
																<button class="preWeek" style="width: 19%" >�W�g</button>
																<button class="resetSch" style="width: 19%" >�M��</button>
																<button class="nextWeek" style="width: 19%" >�U�g</button>
																<button class="nextMonth" style="width: 20%" >�U��</button>
															</div>
														</div>
													</div>
	
												</div>
											</div>
	<!--modal�������� *****************************************************************-->
										</div>
										<div class="modal-footer">
											<!--������^���s-->
											<button type="button" class="btn btn-secondary" data-dismiss="modal">��^</button>
										</div>
									</div>
								</div>
							</div>
	<!--modal�\��ϵ���*****************************************************************-->
							<div class="col-lg-12">
								<fieldset>
									<button id="returnBtn" class="main-button">�ֳt�q��</button>
									<button id="resetBtn" class="main-button">�M�ŭ���</button>
									<input type="hidden" name="action" value="insert">
									<button type="submit" id="form-submit" class="main-button">�e�X�ӽ�</button>
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

	<!-- datetimepicker�� -->
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/datetimepicker/jquery.datetimepicker.full.js"></script>

	<!-- �������� -->
	<script
		src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.0/dist/tw-city-selector.min.js"></script>
	<script>
 			new TwCitySelector({
 				el: '.city-selector-set',
 			    elCounty: '.county', // �b el �̬d�� element
 			    elDistrict: '.district', // �b el �̬d�� element
 			    elZipcode: '.zipcode' // �b el �̬d�� element
 			});
 	</script>

	<!-- �w���Ϥ��Bdatetimepicker�B����ɶ� -->
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

	    //��I����(pay_start)��picker�]�w
	    $.datetimepicker.setLocale('zh');
	    $('#pay_Start').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (�o�Otimepicker���w�]���j60����)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=pay_Start%>', // value:   new Date(),
		   minDate:'new Date()'			   
		});

	    /******************Toggle***********************/
	    $(document).ready(function() {
	    	$(".toggle1").hide();
	    	$(".toggle2").hide();
	    	$("#tglBtn1").click(function(e) {
	    		e.preventDefault();
	    		$(".toggle1").toggle(500);
	    	});
	    	$("#tglBtn2").click(function(e) {
	    		e.preventDefault();
	    		$(".toggle2").toggle(500);
	    	});
	    });

	    /*****************�g��ͦ�****************/
        // ���
        let state = null;
        let input = null;
        const now = new Date();
        const startY = now.getFullYear();
        const startM = now.getMonth();
        const startD = now.getDate() + 7;
        let tempArr= [];
        let sortedArr= [];
        const displayZone = document.getElementById("displayZone");
        const hiddenZone = document.getElementById("hiddenZone");
        
        //��ݮ��쪺��
        const fromBack = <%=session.getAttribute("jsonStrCoachView")%>;

        //���ݮ��쪺��Ʃ�JSS
        sessionStorage.setItem("fromBack", JSON.stringify(fromBack));

        //���ݸ�Ʈ��쪺���X
        const fromSS = JSON.parse(sessionStorage.getItem('fromBack'));
        
        //��ͦ��᪺checkboxs���U�ƥ�
        function getCBs(){

          // ���checkbox��
          let cBoxs = document.getElementsByName("gpc_sch_temp");

          // ���C�@��checkbox���U�ƥ�A���������·�
          for(var i = 0; i < cBoxs.length; i++ ){
          	cBoxs[i].addEventListener('change', function(e){

          		if(tempArr.indexOf(this.value) !== -1){
                tempArr.splice(tempArr.indexOf(this.value),1); // ���Ȯ���
            }else{
                tempArr.push(this.value); // �S�Ȱe�J
            }

            renderDisHid(tempArr);

        }, false);
          }

      }

        //ø�s��ܰϸ����ê���
        function renderDisHid(tempArr){
          //��ܰϼ��D
          totalTimes.innerHTML=""; // �M�����e�����e
          var spanStr;
          if(tempArr.length > 0){
          	spanStr = "�z�ҿ�����ɶ��X�p��"+ tempArr.length +"�p��";
          } else{
          	spanStr = "�z�|������ɶ�";
          }
          
          totalTimes.innerHTML=spanStr;
          
          //��ܰϤ��e
          displayZone.innerHTML="";  // �M�����e���l�N

          const ol = document.createElement("ol");
          ol.className ="list-group";
          displayZone.appendChild(ol);

          //���ð�
          hiddenZone.innerHTML=""; // �M�����e���l�N

          //�Ƨ�Arr
          sortedArr = tempArr.sort();
          for(var i = 0; i < sortedArr.length; i++ ){
            //��ܰ�
            let li = document.createElement('li');
            li.className ="list-group-item";
            var dateStr = sortedArr[i].substring(0,10);
            var timeCode = sortedArr[i].charCodeAt(11);
            var timeNum = timeCode - 65;
            var prefix = timeNum>9? "": "0";
            var startStr = prefix + timeNum + ":00";
            var nextNum = timeNum + 1;
            var nextPrefix = nextNum>9? "": "0";
            var endStr = nextPrefix + nextNum + ":00";
            //���F��ܬP���X�A�n�B�~�B�z
            var dayStr = '';
            var tempDay = new Date(dateStr).getDay();
            switch (tempDay) {
            	case 1:
            		dayStr = ' (�@) ';
            		break;
            	case 2:
            		dayStr = ' (�G) ';
            		break;
            	case 3:
            		dayStr = ' (�T) ';
            		break;
            	case 4:
            		dayStr = ' (�|) ';
            		break;
            	case 5:
            		dayStr = ' (��) ';
            		break;
            	case 6:
            		dayStr = ' (��) ';
            		break;
            	case 0:
            		dayStr = ' (��) ';
       		}

            var resultStr = dateStr +dayStr+ startStr + " - " + endStr;
            li.innerText = resultStr;
            ol.appendChild(li);

            //���ð�
            let inputElem = document.createElement("input");
            inputElem.type = "checkbox";
            inputElem.name = "gpc_sch_front";
            inputElem.value = sortedArr[i];
            inputElem.checked = true

            //�������q���ð���ܤ���r
            let label = document.createElement("label");
            label.innerHTML = sortedArr[i];
            hiddenZone.appendChild(inputElem);
            hiddenZone.appendChild(label);

        }

    }

        //��date�����ഫ��yyyy-mm-dd���榡
        function formatDate(date) {
        	var d = new Date(date),
        	month = '' + (d.getMonth() + 1),
        	day = '' + d.getDate(),
        	year = d.getFullYear();

        	if (month.length < 2) month = '0' + month;
        	if (day.length < 2) day = '0' + day;

        	return [year, month, day].join('-');
        }

        //��s������
        function refresh(input) {
        	initSch(input);
        }

        //�ھڸ�Ʋ��͵e��
        function render(callback) {
        	let row_2nd = document.querySelector('#row_2nd');
        	let timeSch = document.getElementsByClassName('timeSch');

          	//�M�ŵe���A�R���ĤG��(�t)�H�᪺�l�N
          	let row_2nd_children = document.getElementsByClassName('row_2nd_child');

          	// �Y�j��@�A��ܦ��ĤG�H�W���l�N
	        if(row_2nd_children.length > 1){ 
	            // ���F���n�R��Ĥ@�Ӥl�N i�n�j��s
	            for(var i = 7; i > 0; i--){ 
	            	row_2nd.removeChild(row_2nd_children[i]);
	            }
	        }

	        //ø�s�g����ܮɬq
	        for(var i = 0; i < 24; i++){
	            timeSch[i].innerHTML = ''; //���M�ŵe��
	            var hr =  i<10? "0" + i: i;
	            var nextHr = (i+1<10)? "0" + (i+1): (i+1);
	            var startHr = hr + ':00';
	            var endHr = nextHr + ':00';
	            var periodStr = startHr +' - '+ endHr;
	            timeSch[i].innerHTML = "<td>"+periodStr+"</td>";
	        }

          	//��쥻�g�Ĥ@�� (�g��)
          	let date = new Date(
	          	state.target.getFullYear(),
	          	state.target.getMonth(),
	          	state.target.getDate() - state.target.getDay()
          	);

          	//��ܶg��~�g��������ήɶ�
	        for (var i = 0; i < 7; i++) {
	        	renderRow_2nd(date, row_2nd);
	          	renderTimeSch(date, timeSch);
	          	date.setDate(date.getDate() + 1);
	        }

	        //datetimepicker�ͦ�
	        $.datetimepicker.setLocale('zh'); // kr ko ja en
	        $('#inlinePicker').datetimepicker({
	            theme: 'dark', //theme: 'dark',
	            timepicker: false, //timepicker: false,
	            value: state.target, // �W�����ŧi�n�F
	            format: 'Y-m-d H:i',
	            inline: true, 
	            minDate: '-1969-12-25', // �h������,�é���C��
	            timepickerScrollbar: false,
	            scrollMonth: false,
	            scrollInput: false,
	            onChangeMonth: function (input) {
	            	refresh(input);
	            },
	            onSelectDate: function (input) {
	            	refresh(input);
	            },
	        });

          	callback();
      	}


        //�e�X�ĤGrow(��ܤ��)
        function renderRow_2nd(date, row_2nd) {
        	let thElem = document.createElement('th');
        	thElem.className = 'row_2nd_child schCell';
        	let monthNum = date.getMonth() + 1;
        	let monthStr = monthNum < 10? '0' +  monthNum: monthNum;
        	let dateNum = date.getDate();
        	let dateStr = dateNum < 10? '0' + dateNum: dateNum;
        	thElem.textContent = monthStr + ' / ' + dateStr;
        	row_2nd.appendChild(thElem);
        }

        //�e�XtimeSch(��ܦU�p�ɤ��e=���i��Υi��)
        function renderTimeSch(date, timeSch) {
        	const dateStr = formatDate(date);
        	const eventStr = fromSS[dateStr];


        	for(var i = 0; i < 24; i++){
        		let tdElem = document.createElement('td');
            //className���ت��A���F�f�tCSS
            tdElem.className = 'schCell';

            //eventStr �Y��undefined��ܥثe��ݨS�Ӥ���
            //eventStr.charAt(i) �Y���s��ܦ���
            if(eventStr === undefined || eventStr.charAt(i) === "0"){
              let startDate = new Date(startY, startM, startD); // �w�]�O����@�P
              //�p�G�ǤJ����� >= startDate
              if(date.valueOf() >= startDate.valueOf()){

              	let inputElem = document.createElement("input");
              	inputElem.type = "checkbox";
              	inputElem.name = "gpc_sch_temp";
              	inputElem.className = "gpc_sch_temp";
              	timeStr = String.fromCharCode(65 + i);
              	inputElem.value = dateStr + '_' + timeStr;


              	if(tempArr.indexOf(inputElem.value) !== -1 ){
              		inputElem.checked = true
              	}
              	tdElem.appendChild(inputElem);

              }else{
                //���L�h�ɶ����i��
                let spanElem = document.createElement('span');
                spanElem.className = "gpc_sch_temp"
                spanElem.textContent = 'X';
                tdElem.appendChild(spanElem);
            }

        } else {
              //�����Ʊ����ɬq���i��
              let spanElem = document.createElement('span');
              spanElem.className = "gpc_sch_temp"
              spanElem.textContent = 'X';
              tdElem.appendChild(spanElem);
          }
          (timeSch[i]).appendChild(tdElem);
      }

  }        

	    //��l��GPC����    
	    function init(){
			// ���GPC���DOM����
			const contact = document.getElementById('contact'); 
			const returnBtn = document.getElementById('returnBtn');
			const resetBtn = document.getElementById('resetBtn');

			// ����ɨ��DOM����
			const preMonth = document.getElementsByClassName('preMonth');
			const preWeek = document.getElementsByClassName('preWeek');
			const nextWeek = document.getElementsByClassName('nextWeek');
			const nextMonth = document.getElementsByClassName('nextMonth');
			const resetSch = document.getElementsByClassName('resetSch');
			const totalTimes = document.getElementById('totalTimes');


			//�令�ֳt�q���\��F
			returnBtn.addEventListener('click', function(e){
				// �`�N: button�bform���̡A�����|�a���w�]�欰�N��e�X���
				e.preventDefault();
				
				$("#gpc_Name").val("�L�ĭ�����");
				$("#mem_Min").val("3");
				$("#mem_Max").val("5");
				$("#price").val("5566");
				$("#pay_Start").val("2020-09-24");
				$("#address").val("�_����46��9��");
				$("#intro").val("�Ǥ��������M���O�b������ҤW�A�z�L�b�����ӱ�����O�A������~�樮���Y���q�A�U�Y���q�A�R���E�o���骺�B�ʲӭM��A�b���ӯ�q���P�ɹF���ת��ت��A�z�L���ְV�m�A�J��j�L�٭@�O�Τߪͥ\�઺�[�j�A�åB�Ϻ�O��[�����A�N�t�����n��������������X�ӡA�����������A�O�@���`���ʪ��B�ʡA�i�ӹF���סB�쨭���ĪG�C");
				
				
			}, false);

			// GPC��歫�]
			resetBtn.addEventListener('click', function(e){
				e.preventDefault();
				
				//�Ϥ��ܦ^�w�]��
				$(".pics").attr('src', "<%=request.getContextPath()%>/front-end/KaiPing/images/example.png");

				// ��歫�] ���ܡGreset()
				contact.reset();
			}, false);

			//���U�ɨ��̪����s�̨ƥ�,�]������թҥH�ΰj��]�⦸�A�u�a�M
			for(var i = 0; i < 2; i++ ){
				//�W�Ӥ���s
				preMonth[i].addEventListener('click', function(e){
					e.preventDefault();
					if(((state.target.getMonth() - 1) >= now.getMonth()) || (state.target.getFullYear() > now.getFullYear())){
						state.target.setMonth(state.target.getMonth() - 1);
						render(getCBs);
					}
				}, false);
				//�W�@�g���s
				preWeek[i].addEventListener('click', function(e){
					e.preventDefault();
			        let startDate = new Date(startY, startM, startD); // �w�]�O����@�P
			        if((state.target.getDate()-7) >= (startDate.getDate()-startDate.getDay()) ||
			        	state.target.getMonth() > startDate.getMonth() ||
			        	state.target.getFullYear() > startDate.getFullYear()
			        	){
			        	state.target.setDate(state.target.getDate() - 7);
			        render(getCBs);
				    }
				}, false);
				//�U�@�g���s
				nextWeek[i].addEventListener('click', function(e){
					e.preventDefault();
					state.target.setDate(state.target.getDate() + 7);
					render(getCBs);
				}, false);
				//�U�Ӥ���s
				nextMonth[i].addEventListener('click', function(e){
					e.preventDefault();
					state.target.setMonth(state.target.getMonth() + 1);
					render(getCBs);
				}, false);
				//�M�Ůɨ�������s
				resetSch[i].addEventListener('click', function(e){
					e.preventDefault();
					input = null;
					tempArr = [];
					sortedArr= [];
					totalTimes.innerHTML="�z�|������ɶ�"; // ���s��ܼ��D
			        displayZone.innerHTML="";  // �M�����e���l�N
			        hiddenZone.innerHTML=""; // �M�����e���l�N
			        initSch(input);
			    }, false);

			}

		}

	    //��l�ƶg��
	    function initSch(input) {
	    	if (input !== null) {
	    		state = {
	    			target: input
	    		};
	    	} else {
	        let startDate = new Date(startY, startM, startD); // �w�]�O����@�P
	        state = {
	          target: startDate // �w�]�O����@�P
	      };
	  }
	  render(getCBs);
	}

		//�B�z�y�{
		init();
		initSch(input);
	</script>

</body>
</html>
