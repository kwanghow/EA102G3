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

	<title>修改揪團課程</title>

	<!-- Bootstrap 的 CSS -->

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

<!------------------------------------------------------------- ***** 這裡以上複製貼上 ***** ------------------------------------------------------->
	<!-- ***** Main Banner Area Start ***** -->
	<section class="section" id="contact-us">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-xs-12" style="padding-left: 0px; padding-right: 0px;">
                    <div class="contact-form">
		                <div class="col-lg-6 offset-lg-3">
		                    <div class="section-heading dark-bg" style="margin: auto">
		                        <h2>修改<em>內容</em></h2>
		                        <img src="<%=request.getContextPath()%>/front-end/assets/images/line-dec.png" alt="">
		                    </div>
		                </div>	                  
	                        
<%--其他例外的提醒--%>   <h3><span id="errorMsgs">${errorMsgs.exception}</span></h3>
                        
                        <FORM id="contact" METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/KaiPing/gpc/gpc.do" enctype="multipart/form-data">
 <%--隱藏的gpc_Id--%>       <input type="hidden" name="gpc_Id" value="${gpcVO.gpc_Id}">
                          <div class="row city-selector-set">
                          	<div class="col-md-12 col-sm-12"><label>課程名稱: </label><span id="errorMsgs">${errorMsgs.gpc_Name}</span>
                              <fieldset>
                                <input type="TEXT" name="gpc_Name" size="30" placeholder="請輸入課程名稱" value="${gpcVO.gpc_Name}" />
                              </fieldset>
                            </div>
                            <div class="col-md-4 col-sm-12"><label>開課類別: </label>
                              <fieldset>
<% //${exptypeSvc.getOneExptype(gpcSvc.getOneGpc(gpcVO.gpc_Id).exp_Id).exp_Name} %>
								<input type="TEXT" name="exp_Id" size="30" readonly value="${exptypeSvc.getOneExptype(gpcVO.exp_Id).exp_Name}" />

                              </fieldset>     
                            </div>
                            <div class="col-md-4 col-sm-12"><label>人數下限: </label><span id="errorMsgs">${errorMsgs.mem_Min}</span> 
                              <fieldset>
								<input type="TEXT" name="mem_Min" size="7" placeholder="請輸入正整數" value="${gpcVO.mem_Min}" />
                              </fieldset>
                            </div>
                            <div class="col-md-4 col-sm-12"><label>人數上限: </label><span id="errorMsgs">${errorMsgs.mem_Max}</span> 
                              <fieldset>
								<input type="TEXT" name="mem_Max" size="7" placeholder="請輸入正整數" value="${gpcVO.mem_Max}" />
                              </fieldset>
                            </div>
                            <%--費用不可修改 --%>
                            <input type="hidden" name="price" size="7" value="${gpcVO.price}" />
                            <%--繳費開始不可修改 --%>
                            <input type="hidden" name="pay_Start" id="f_date1" value="${gpcVO.pay_Start}" />
                            
                            <%--配合controller先塞值首次上課日開始不可修改 --%>
                            <input type="hidden" name="gpc_Start" id="f_date1" value="${gpcVO.gpc_Start}" />
                        
                        	<%--地址不可修改 --%>
                        	<input type="hidden" name="address" size="30"  value="${gpcVO.address}" />
                            
                            
                            <div class="col-md-4 col-sm-12 pic"><label>第一張圖片(封面): </label><span id="errorMsgs">${errorMsgs.rePick}</span>
                            	<div id="select1">
	                             	<input type='file' name="pic1" class="imgInp" id="pic1Up" /> <!--小心這裡的input下個元素要是img-->
									<img class="pics" src="<%=request.getContextPath()%>/back-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic1" />
									<br>
								</div>	
							</div>
							<br>
							<div class="col-md-4 col-sm-12 pic" ><label>第二張圖片: </label><span id="errorMsgs">${errorMsgs.rePick}</span> 
								<div id="select2">
	                             	<input type='file' name="pic2" class="imgInp" id="pic2Up" /> <!--小心這裡的input下個元素要是img-->
									<img class="pics" src="<%=request.getContextPath()%>/back-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic2" />
									<br>
								</div>		
							</div>
							<br>							
							<div class="col-md-4 col-sm-12 pic" ><label>第三張圖片: </label><span id="errorMsgs">${errorMsgs.rePick}</span> 
								<div id="select3">
	                             	<input type='file' name="pic3" class="imgInp" id="pic3Up" /> <!--小心這裡的input下個元素要是img-->
									<img class="pics" src="<%=request.getContextPath()%>/back-end/KaiPing/gpc/ShowPic.do?gpc_Id=${gpcVO.gpc_Id}&picNo=pic3" />
									<br>
								</div>		
							</div>
							
							<div class="col-lg-12"><label>課程內容簡述: </label><span id="errorMsgs">${errorMsgs.intro}</span> 
                              <fieldset>
                                <textarea name="intro" rows="6" id="message" placeholder="請輸入您的課程內容文字簡述，於500字內">${gpcVO.intro}</textarea>
                              </fieldset>
                            </div>
																		
							<br>
							<br>
							<br>
                            <div class="col-lg-12">
                              <fieldset>
<!--                               	<button id="returnBtn" class="main-button">回上一頁</button> -->
<%--修改不用這個按鈕 --%>    <!--<button id="resetBtn" class="main-button">清空重填</button> -->
                              	<input type="hidden" name="action" value="update_front">
                              	<input name="coach_Id" value="${coachLogIn.coach_Id}" type="hidden">
                                <button type="submit" id="form-submit" class="main-button">送出修改</button>
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
			
		
		<!-- 預覽圖片用&datetimepicker用 -->
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
// 				// 抓取DOM元素
// 				var contact = document.getElementById('contact');
// 				var returnBtn = document.getElementById('returnBtn');
// 				var resetBtn = document.getElementById('resetBtn');

			
// 				// 返回上一頁
// 				returnBtn.addEventListener('click', function(e){
// 					// 注意: button在form表單裡，本身會帶有預設行為將其送出表單
// 					e.preventDefault();
// 					window.location.href="www.google.com"
// 				}, false);

// 				// 表單重設
// 				resetBtn.addEventListener('click', function(e){
// 					// 注意: button在form表單裡，本身會帶有預設行為將其送出表單
// 					e.preventDefault();
					
// 					//圖片變回預設圖
<%-- 					$(".pics").attr('src', "<%=request.getContextPath()%>/front-end/KaiPing/images/example.png"); --%>

// 					// 表單重設 提示：reset()
// 					contact.reset();
// 				}, false);				
			}	        		
	        window.onload = init;	
	        
  		</script>       
	</body>
</html>
