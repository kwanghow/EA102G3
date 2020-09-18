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
		System.out.println(coachLogIn.getCoach_Id() + "目前進入了newGpc.jsp(newGpc.jsp_12行印出)");
	}
%>


<%--datetimepicker用 --%>
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

<title>申請揪團課程</title>

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

	<!------------------------------------------------------------- ***** 這裡以上複製貼上 ***** ------------------------------------------------------->
	<!-- ***** Main Banner Area Start ***** -->
	<section class="section" id="contact-us">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-xs-12"
					style="padding-left: 0px; padding-right: 0px;">
					<div class="contact-form">
					
						<div class="col-lg-6 offset-lg-3">
		                    <div class="section-heading dark-bg" style="margin: auto">
		                        <h2>申請<em>揪團</em></h2>
		                        <img src="<%=request.getContextPath()%>/front-end/assets/images/line-dec.png" alt="">
		                    </div>
		                </div>

						<%--其他例外的提醒--%>
						<h3>
							<span id="errorMsgs">${errorMsgs.exception}</span>
						</h3>

						<FORM id="contact" METHOD="post"
							ACTION="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do"
							enctype="multipart/form-data">


							<!-- coach_Id 隱藏在這裡 -->
							<input type="hidden" name="coach_Id" value="${coachLogIn.coach_Id}">
							<div class="row city-selector-set">
								<div class="col-md-12 col-sm-12">
									<label>課程名稱: </label><span id="errorMsgs">${errorMsgs.gpc_Name}</span>
									<fieldset>
										<input type="TEXT" id="gpc_Name" name="gpc_Name" size="30"
											placeholder="請輸入課程名稱" value="${gpcVO.gpc_Name}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>開課類別:</label>
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
									<label>人數下限: </label><span id="errorMsgs">${errorMsgs.mem_Min}</span>
									<fieldset>
										<input type="TEXT" id="mem_Min" name="mem_Min" size="7"
											placeholder="請輸入正整數" value="${gpcVO.mem_Min}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>人數上限: </label><span id="errorMsgs">${errorMsgs.mem_Max}</span>
									<fieldset>
										<input type="TEXT" id="mem_Max" name="mem_Max" size="7"
											placeholder="請輸入正整數" value="${gpcVO.mem_Max}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>費用: </label><span id="errorMsgs">${errorMsgs.price}</span>
									<fieldset>
										<input type="TEXT" id="price" name="price" size="7"
											placeholder="請輸入每人收費(總金額，單位為新台幣)" value="${gpcVO.price}" />
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>報名截止日期: </label><span id="errorMsgs">${errorMsgs.pay_Start}</span>
									<fieldset>
										<input name="pay_Start" id="pay_Start" type="text">
									</fieldset>
								</div>
<%--*****選取上課時間，要從資料庫取值***************** --%>
								<div class="col-md-4 col-sm-12">
									<label>上課時間(三個月內):</label><span id="errorMsgs">${errorMsgs.gpc_Schedule}</span>
									<fieldset class="chooseTime">
										<button type="button" class="btn btn-primary chooseTime" style="margin-top: 0px"data-toggle="modal" data-target="#exampleModal" id="chooseTimeBtn">
 			 								選擇上課時間
										</button>
									</fieldset>
								</div>
<%--*****選取上課時間結束，縣市選單開始*********************** --%>
								<div class="col-md-4 col-sm-12">
									<label>縣市</label><span id="errorMsgs">${errorMsgs.county}</span>
									<fieldset>
										<!-- 縣市選單 -->
										<select class="county custom-select"></select>
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>區域</label><span id="errorMsgs">${errorMsgs.district}</span>
									<fieldset>
										<!-- 區域選單 -->
										<select class="district custom-select"></select>
									</fieldset>
								</div>
								<div class="col-md-4 col-sm-12">
									<label>郵遞區號</label><span id="errorMsgs"></span>
									<fieldset>
										<!-- 郵遞區號欄位 (建議加入 readonly 屬性，防止修改) -->
										<input name="zipcode" class="zipcode" type="text" size="3" readonly
											placeholder="郵遞區號">
									</fieldset>
								</div>
<%--************縣市選單結束*********************** --%>
								<div class="col-md-12 col-sm-12">
									<label>地址:</label><span id="errorMsgs">${errorMsgs.address}</span>
									<fieldset>
										<input type="TEXT" id="address" name="address" size="30"
											placeholder="請輸入上課地點" value="${gpcVO.address}" />
									</fieldset>
								</div>

								<div class="col-md-4 col-sm-12 pic">
									<label>請選擇第一張圖片(封面): </label><span id="errorMsgs">${errorMsgs.rePick}</span>
									<div id="select1">
										<input type='file' name="pic1" class="imgInp" id="pic1Up" /> 
										<!--小心這裡的input下個元素要是img-->
										<img class="pics"
											src="<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"
											alt="your image" /> <br>
									</div>
								</div>
								<br>
								<div class="col-md-4 col-sm-12 pic">
									<label>請選擇第二張圖片: </label><span id="errorMsgs">${errorMsgs.rePick}</span>
									<div id="select2">
										<input type='file' name="pic2" class="imgInp" id="pic2Up" />
										<!--小心這裡的input下個元素要是img-->
										<img class="pics"
											src="<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"
											alt="your image" /> <br>
									</div>
								</div>
								<br>
								<div class="col-md-4 col-sm-12 pic">
									<label>請選擇第三張圖片: </label><span id="errorMsgs">${errorMsgs.rePick}</span>
									<div id="select3">
										<input type='file' name="pic3" class="imgInp" id="pic3Up" />
										<!--小心這裡的input下個元素要是img-->
										<img class="pics"
											src="<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"
											alt="your image" /> <br>
									</div>
								</div>

								<div class="col-lg-12">
									<label>課程內容簡述: </label><span id="errorMsgs">${errorMsgs.intro}</span>
									<fieldset>
										<textarea id="intro" name="intro" rows="6" id="message"
											placeholder="請輸入您的課程內容文字簡述，於500字內">${gpcVO.intro}</textarea>
									</fieldset>
								</div>
<!--modal功能區開始*****************************************************************-->
						<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog modal-xl" role="document">
								<div class="modal-content">
									<div class="modal-header">
									<!--標題-->
										<h5 class="modal-title" id="exampleModalLabel">請選取上課時間</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
	<!--modal內頁開始 *****************************************************************-->
											<div class="container-fluid">
												<div class="row">      	
													<div class="col-md-4 col-sm-12">
														<div style="height: 230px">
															<input name="" id="inlinePicker" type="text" value="" /><br>
														</div>
														<button class="preMonth" >上月</button>
														<button class="preWeek" >上週</button>
														<button class="resetSch" >清空</button>
														<button class="nextWeek" >下週</button>
														<button class="nextMonth" >下月</button><br><br>
														<span id="totalTimes">您尚未選取時間</span>
														<div id="displayZone"></div>
														<div id="hiddenZone" style="display: none"></div>
													</div>
													<div class="col-md-8 col-sm-12 ">	
													<div class="row">
													<div id="overflowTable"> 
														<table class="weekSch table table-bordered table-dark table-striped table-responsive-xl" >
															<tr class="weekSch" id="row_1st">
																<th class="schCell"><button id="tglBtn1">顯示/隱藏08:00前</button></th>
																<th class="schCell">週日</th>
																<th class="schCell">週一</th>
																<th class="schCell">週二</th>
																<th class="schCell">週三</th>
																<th class="schCell">週四</th>
																<th class="schCell">週五</th>
																<th class="schCell">週六</th>
															</tr>
															<tr id="row_2nd">
																<th class="row_2nd_child schCell">
																	<button id="tglBtn2">顯示/隱藏20:00後</button>
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
																<button class="preMonth" style="width: 20%" >上月</button>
																<button class="preWeek" style="width: 19%" >上週</button>
																<button class="resetSch" style="width: 19%" >清空</button>
																<button class="nextWeek" style="width: 19%" >下週</button>
																<button class="nextMonth" style="width: 20%" >下月</button>
															</div>
														</div>
													</div>
	
												</div>
											</div>
	<!--modal內頁結束 *****************************************************************-->
										</div>
										<div class="modal-footer">
											<!--頁尾返回按鈕-->
											<button type="button" class="btn btn-secondary" data-dismiss="modal">返回</button>
										</div>
									</div>
								</div>
							</div>
	<!--modal功能區結束*****************************************************************-->
							<div class="col-lg-12">
								<fieldset>
									<button id="returnBtn" class="main-button">快速通關</button>
									<button id="resetBtn" class="main-button">清空重填</button>
									<input type="hidden" name="action" value="insert">
									<button type="submit" id="form-submit" class="main-button">送出申請</button>
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
	<!------------------------------------------------------------- ***** 這裡以下複製貼上 ***** ------------------------------------------------------->
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

	<!-- datetimepicker用 -->
	<script
		src="<%=request.getContextPath()%>/front-end/KaiPing/datetimepicker/jquery.datetimepicker.full.js"></script>

	<!-- 縣市選單用 -->
	<script
		src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.0/dist/tw-city-selector.min.js"></script>
	<script>
 			new TwCitySelector({
 				el: '.city-selector-set',
 			    elCounty: '.county', // 在 el 裡查找 element
 			    elDistrict: '.district', // 在 el 裡查找 element
 			    elZipcode: '.zipcode' // 在 el 裡查找 element
 			});
 	</script>

	<!-- 預覽圖片、datetimepicker、選取時間 -->
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

	    //選截止日期(pay_start)的picker設定
	    $.datetimepicker.setLocale('zh');
	    $('#pay_Start').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
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

	    /*****************週曆生成****************/
        // 資料
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
        
        //後端拿到的值
        const fromBack = <%=session.getAttribute("jsonStrCoachView")%>;

        //把後端拿到的資料放入SS
        sessionStorage.setItem("fromBack", JSON.stringify(fromBack));

        //把後端資料拿到的取出
        const fromSS = JSON.parse(sessionStorage.getItem('fromBack'));
        
        //對生成後的checkboxs註冊事件
        function getCBs(){

          // 找到checkbox們
          let cBoxs = document.getElementsByName("gpc_sch_temp");

          // 幫每一個checkbox註冊事件，媽的有夠麻煩
          for(var i = 0; i < cBoxs.length; i++ ){
          	cBoxs[i].addEventListener('change', function(e){

          		if(tempArr.indexOf(this.value) !== -1){
                tempArr.splice(tempArr.indexOf(this.value),1); // 有值拿掉
            }else{
                tempArr.push(this.value); // 沒值送入
            }

            renderDisHid(tempArr);

        }, false);
          }

      }

        //繪製顯示區跟隱藏表單區
        function renderDisHid(tempArr){
          //顯示區標題
          totalTimes.innerHTML=""; // 清除之前的內容
          var spanStr;
          if(tempArr.length > 0){
          	spanStr = "您所選取的時間合計為"+ tempArr.length +"小時";
          } else{
          	spanStr = "您尚未選取時間";
          }
          
          totalTimes.innerHTML=spanStr;
          
          //顯示區內容
          displayZone.innerHTML="";  // 清除之前的子代

          const ol = document.createElement("ol");
          ol.className ="list-group";
          displayZone.appendChild(ol);

          //隱藏區
          hiddenZone.innerHTML=""; // 清除之前的子代

          //排序Arr
          sortedArr = tempArr.sort();
          for(var i = 0; i < sortedArr.length; i++ ){
            //顯示區
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
            //為了顯示星期幾，要額外處理
            var dayStr = '';
            var tempDay = new Date(dateStr).getDay();
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

            var resultStr = dateStr +dayStr+ startStr + " - " + endStr;
            li.innerText = resultStr;
            ol.appendChild(li);

            //隱藏區
            let inputElem = document.createElement("input");
            inputElem.type = "checkbox";
            inputElem.name = "gpc_sch_front";
            inputElem.value = sortedArr[i];
            inputElem.checked = true

            //除錯階段隱藏區顯示之文字
            let label = document.createElement("label");
            label.innerHTML = sortedArr[i];
            hiddenZone.appendChild(inputElem);
            hiddenZone.appendChild(label);

        }

    }

        //把date物件轉換為yyyy-mm-dd之格式
        function formatDate(date) {
        	var d = new Date(date),
        	month = '' + (d.getMonth() + 1),
        	day = '' + d.getDate(),
        	year = d.getFullYear();

        	if (month.length < 2) month = '0' + month;
        	if (day.length < 2) day = '0' + day;

        	return [year, month, day].join('-');
        }

        //更新頁面用
        function refresh(input) {
        	initSch(input);
        }

        //根據資料產生畫面
        function render(callback) {
        	let row_2nd = document.querySelector('#row_2nd');
        	let timeSch = document.getElementsByClassName('timeSch');

          	//清空畫面，刪除第二個(含)以後的子代
          	let row_2nd_children = document.getElementsByClassName('row_2nd_child');

          	// 若大於一，表示有第二以上的子代
	        if(row_2nd_children.length > 1){ 
	            // 為了不要刪到第一個子代 i要大於零
	            for(var i = 7; i > 0; i--){ 
	            	row_2nd.removeChild(row_2nd_children[i]);
	            }
	        }

	        //繪製週曆顯示時段
	        for(var i = 0; i < 24; i++){
	            timeSch[i].innerHTML = ''; //先清空畫面
	            var hr =  i<10? "0" + i: i;
	            var nextHr = (i+1<10)? "0" + (i+1): (i+1);
	            var startHr = hr + ':00';
	            var endHr = nextHr + ':00';
	            var periodStr = startHr +' - '+ endHr;
	            timeSch[i].innerHTML = "<td>"+periodStr+"</td>";
	        }

          	//找到本週第一天 (週日)
          	let date = new Date(
	          	state.target.getFullYear(),
	          	state.target.getMonth(),
	          	state.target.getDate() - state.target.getDay()
          	);

          	//顯示週日~週六的日期及時間
	        for (var i = 0; i < 7; i++) {
	        	renderRow_2nd(date, row_2nd);
	          	renderTimeSch(date, timeSch);
	          	date.setDate(date.getDate() + 1);
	        }

	        //datetimepicker生成
	        $.datetimepicker.setLocale('zh'); // kr ko ja en
	        $('#inlinePicker').datetimepicker({
	            theme: 'dark', //theme: 'dark',
	            timepicker: false, //timepicker: false,
	            value: state.target, // 上面先宣告好了
	            format: 'Y-m-d H:i',
	            inline: true, 
	            minDate: '-1969-12-25', // 去除今日,並往後七天
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


        //畫出第二row(顯示日期)
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

        //畫出timeSch(顯示各小時內容=不可選或可選)
        function renderTimeSch(date, timeSch) {
        	const dateStr = formatDate(date);
        	const eventStr = fromSS[dateStr];


        	for(var i = 0; i < 24; i++){
        		let tdElem = document.createElement('td');
            //className有目的，為了搭配CSS
            tdElem.className = 'schCell';

            //eventStr 若為undefined表示目前後端沒該日資料
            //eventStr.charAt(i) 若為零表示有空
            if(eventStr === undefined || eventStr.charAt(i) === "0"){
              let startDate = new Date(startY, startM, startD); // 預設是當日後一周
              //如果傳入的日期 >= startDate
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
                //讓過去時間不可選
                let spanElem = document.createElement('span');
                spanElem.className = "gpc_sch_temp"
                spanElem.textContent = 'X';
                tdElem.appendChild(spanElem);
            }

        } else {
              //讓有事情的時段不可選
              let spanElem = document.createElement('span');
              spanElem.className = "gpc_sch_temp"
              spanElem.textContent = 'X';
              tdElem.appendChild(spanElem);
          }
          (timeSch[i]).appendChild(tdElem);
      }

  }        

	    //初始化GPC頁面    
	    function init(){
			// 抓取GPC表單DOM元素
			const contact = document.getElementById('contact'); 
			const returnBtn = document.getElementById('returnBtn');
			const resetBtn = document.getElementById('resetBtn');

			// 抓取時刻表DOM元素
			const preMonth = document.getElementsByClassName('preMonth');
			const preWeek = document.getElementsByClassName('preWeek');
			const nextWeek = document.getElementsByClassName('nextWeek');
			const nextMonth = document.getElementsByClassName('nextMonth');
			const resetSch = document.getElementsByClassName('resetSch');
			const totalTimes = document.getElementById('totalTimes');


			//改成快速通關功能了
			returnBtn.addEventListener('click', function(e){
				// 注意: button在form表單裡，本身會帶有預設行為將其送出表單
				e.preventDefault();
				
				$("#gpc_Name").val("無敵風火輪");
				$("#mem_Min").val("3");
				$("#mem_Max").val("5");
				$("#price").val("5566");
				$("#pay_Start").val("2020-09-24");
				$("#address").val("復興路46號9樓");
				$("#intro").val("室內飛輪車騎乘是在虛擬實境上，透過剎車片來控制阻力，模擬戶外單車爬坡路段，下坡路段，充分激發身體的運動細胞後，在消耗能量的同時達到減脂的目的，透過音樂訓練，雕塑大腿肌耐力及心肺功能的加強，並且使精力更加旺盛，將負面不好的情緒完全釋放出來，徹底的舒壓，是一項節奏性的運動，進而達到減脂、塑身的效果。");
				
				
			}, false);

			// GPC表單重設
			resetBtn.addEventListener('click', function(e){
				e.preventDefault();
				
				//圖片變回預設圖
				$(".pics").attr('src', "<%=request.getContextPath()%>/front-end/KaiPing/images/example.png");

				// 表單重設 提示：reset()
				contact.reset();
			}, false);

			//註冊時刻表裡的按鈕們事件,因為有兩組所以用迴圈跑兩次，真靠杯
			for(var i = 0; i < 2; i++ ){
				//上個月按鈕
				preMonth[i].addEventListener('click', function(e){
					e.preventDefault();
					if(((state.target.getMonth() - 1) >= now.getMonth()) || (state.target.getFullYear() > now.getFullYear())){
						state.target.setMonth(state.target.getMonth() - 1);
						render(getCBs);
					}
				}, false);
				//上一週按鈕
				preWeek[i].addEventListener('click', function(e){
					e.preventDefault();
			        let startDate = new Date(startY, startM, startD); // 預設是當日後一周
			        if((state.target.getDate()-7) >= (startDate.getDate()-startDate.getDay()) ||
			        	state.target.getMonth() > startDate.getMonth() ||
			        	state.target.getFullYear() > startDate.getFullYear()
			        	){
			        	state.target.setDate(state.target.getDate() - 7);
			        render(getCBs);
				    }
				}, false);
				//下一週按鈕
				nextWeek[i].addEventListener('click', function(e){
					e.preventDefault();
					state.target.setDate(state.target.getDate() + 7);
					render(getCBs);
				}, false);
				//下個月按鈕
				nextMonth[i].addEventListener('click', function(e){
					e.preventDefault();
					state.target.setMonth(state.target.getMonth() + 1);
					render(getCBs);
				}, false);
				//清空時刻表選取按鈕
				resetSch[i].addEventListener('click', function(e){
					e.preventDefault();
					input = null;
					tempArr = [];
					sortedArr= [];
					totalTimes.innerHTML="您尚未選取時間"; // 重製顯示標題
			        displayZone.innerHTML="";  // 清除之前的子代
			        hiddenZone.innerHTML=""; // 清除之前的子代
			        initSch(input);
			    }, false);

			}

		}

	    //初始化週曆
	    function initSch(input) {
	    	if (input !== null) {
	    		state = {
	    			target: input
	    		};
	    	} else {
	        let startDate = new Date(startY, startM, startD); // 預設是當日後一周
	        state = {
	          target: startDate // 預設是當日後一周
	      };
	  }
	  render(getCBs);
	}

		//處理流程
		init();
		initSch(input);
	</script>

</body>
</html>
