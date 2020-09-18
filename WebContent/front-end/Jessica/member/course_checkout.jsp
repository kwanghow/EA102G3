<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<c:if test="${empty requestScope.orderVo}">
請從訂單列表進入! 3秒後自動跳轉回訂單列表
<% 
	String url = request.getContextPath() + "/front-end/Jessica/member/course_orders.jsp";
	response.setHeader("refresh","3;URL="+url); 
%>
</c:if>
<c:if test="${not empty requestScope.orderVo}">

<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜私人課程結帳</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>

<style>
    /*裁切課程相片*/
    .course-img{
        width: 162px;
        height: 0;
        padding-bottom: 120px;
        position: relative;
        overflow: hidden;
    }
    .course-img img{
        position: absolute;
        top: 50%;
        left: 50%;
        display: block;
        min-width: 100%;
        min-height: 100%;
        transform:translate(-50%,-50%);          
    }
    button a{
        color: white;
    }
    .row .contact-form .backBtn{
        background-color: white;
        color: #ed563b;
        border: 1px solid #ed563b;
    }
    #step{
        margin-top: 60px;
    }
</style>
</head>
<body>
<%-- Preloader & Header --%>
<%@ include file="/front-end/Jessica/common/header.jsp"%>
<!--------------------------------------------------------------------------------------------------->

<!----- Main Banner Area ----->
<div class="main-banner" id="top">
    <!-- Banner Img -->
    <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/course-banner.png" id="bg-video">
    <div class="video-overlay header-text">
        <!-- 麵包屑 -->
        <div class="caption">
            <h2>COURSE <em>CHECKOUT</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                    <li class="breadcrumb-item"><a href="#">MEMBER</a></li>
                    <li class="breadcrumb-item active" aria-current="page">COURSE CHECKOUT</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<!----- 付款step ----->
<section class="section" id="step">
  <div class="container">
    <div class="row justify-content-center text-center align-self-center">
        <div class="col-8">
            <div class="row" id="stepItem">
                <div class="col font-weight-bold">
                    <span class="badge badge-primary">1</span>
                    <span>　確認訂單項目</span>
                </div>
                <div class="col-1">
                    <i class="fa fa-long-arrow-right" aria-hidden="true"></i>
                </div>
                <div class="col text-muted">
                    <span class="badge badge-light">2</span>
                    <span>　金流付款</span>            
                </div>
                <div class="col-1">
                    <i class="fa fa-long-arrow-right" aria-hidden="true"></i>
                </div>
                <div class="col text-muted">
                    <span class="badge badge-light">3</span>
                    <span>　完成</span>            
                </div>
            </div>
        </div>
    </div>
  </div>
</section>

<!----- Content Area ----->
<section class="section" id="section-content">
  <div class="container bg-light">
    <div class="row my-3"></div> <!-- 撐高度用 -->

    <!----- 購物車 ----->

    <!-- 購物車小標 -->
    <div class="row mt-4">
        <div class="col-lg-6 offset-lg-3"><div class="section-heading mb-0">
            <h2>Order<em> item</em></h2>
            <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/line-dec.png" alt="">
        </div></div>
    </div>
    <!-- 購物車課程 -->
    <div class="row mx-4 mt-1">
        <table class="table bg-white py-4 mt-3">
                <thead>
                    <tr>
                        <th colspan="2">訂單編號：<span id="order_id">${orderVo.order_id}</span></th>
                        <th>堂數</th>
                        <th class="text-right">單堂價格</th>
                        <th class="text-right">小計</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <!-- 課程圖片 -->
                        <td> 
                            <div class="course-img">
                                <img class="img-fluid" src="${pageContext.request.contextPath}/jessica/img.do?course_id=${courseSetSvc.getOneCourseSetById(orderVo.set_id).course_id}&picIndex=1">
                            </div>                                
                        </td>
                        <!-- 課程名稱 -->
                        <td class="pl-0">
                            <strong>${courseSvc.getOneCourseById(courseSetSvc.getOneCourseSetById(orderVo.set_id).course_id).cname}</strong>
                        </td>
                        <!-- 課程方案 -->
                        <td>${orderVo.lesson}堂</td>
                        <!-- 單堂價格 -->
                        <td>
                            <div class="text-right"><fmt:formatNumber value="${orderVo.order_price div orderVo.lesson}" type="currency" maxFractionDigits="0"/>/堂</div>
                        </td>
                        <!-- 課程總價 -->
                        <td class="text-right">NT$ <span id="order_price">${orderVo.order_price}</span></td>
                    </tr>
                </tbody>
                <tfoot id="tfoot" style="display:none">
                    <tr class="text-right">
                        <td colspan="3">
                        	折價 <span class="text-success">- NT$ <span id="disc">0</span></span>
                        </td>
                        <td colspan="2">                        
                            <strong>結帳金額　NT$ <span id="total_price">${orderVo.order_price}</span></strong>      
                        </td>
                    </tr>
                </tfoot>
        </table>
    </div>
    
    <!-- 優惠券 -->
    <div class="row mx-4 mt-1 mb-4 py-3 bg-white" id="couponRow">
        <div class="col-3">
            <input type="text" class="form-control" placeholder="我有優惠券" name="coupon_id" id="coupon_id">
            <small class="text-danger" id="couponVerifyRes"></small>
        </div>
        <div class="col-5"></div>
        <div class="col-4">
            <div class="text-right">
            	折價 <span class="text-success">- NT$ <span id="disc1">0</span></span>
            </div>
            <div class="text-right"><strong>結帳金額　NT$ <span id="total_price1">${orderVo.order_price}</span></strong></div>
        </div>
    </div>

    <!-- RPG選項，去付信用卡 -->
    <div class="row mx-4 mt-3 pt-3 justify-content-end">
        <div class="col-3 contact-form">
            <button class="btn btn-primary btn-lg btn-block backBtn">取消訂單</button>
        </div>
        <div class="col-3 mb-5 contact-form">
            <a href="#top"><button class="btn btn-primary btn-lg btn-block" id="goCheckoutBtn">前往付款</button></a>
        </div>
    </div>
        
<!----------------------------------------------------------->

	<div id="payment" style="display:none">
	    <!----- 購買明細 ----->        
	    <div class="row mt-6">
	        <div class="col-lg-6 offset-lg-3 mt-3"><div class="section-heading mb-0">
	            <h2>Payment</h2>
	            <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/line-dec.png" alt="">
	        </div></div>
	    </div>
	    <!--- from開始 ---> 
	    <div class="needs-validation">	
	        <!-- 輸入信用卡 -->
	        <div class="row mx-4 mt-1 mb-3 py-3">                            
	            <div class="col-8 bg-white">
	                <div class="mt-4"><span class="text-danger"><em>Credit Card</em></span></div>
	                <br>
	                <div class="row">
	                    <div class="col-md-6 mb-3">
	                        <label for="cc-name">Name on card</label>
	                        <input type="text" class="form-control" id="cc-name" name="cc-name" required>
	                        <small class="text-muted">Full name as displayed on card</small>
	                        <div class="invalid-feedback">Name on card is required</div>
	                    </div>
	                    <div class="col-md-6 mb-3">
	                        <label for="cc-number">Credit card number</label>
	                        <input type="text" class="form-control" id="cc-number" required>
	                        <div class="invalid-feedback">Credit card number is required</div>
	                    </div>
	                    <div class="col-md-3 mb-3">
	                        <label for="cc-expiration">Expiration</label>
	                        <input type="text" class="form-control" maxlength="4" size="4" id="cc-expiration" required>
	                        <div class="invalid-feedback">Expiration date required</div>
	                    </div>
	                    <div class="col-md-3 mb-3">
	                        <label for="cc-cvv">CVV</label>
	                        <input type="text" class="form-control" maxlength="3" size="3" id="cc-cvv" required>
	                        <div class="invalid-feedback">Security code required</div>
	                    </div>
	                </div>
	            </div>
	            <div class="col-4 text-center align-self-center">
	                <div style="font-size: 100px"><i class="fa fa-credit-card" aria-hidden="true"></i></div>                         
	            </div>
	        </div>
	    	 <div class="row mt-5"></div>
	    <!--- form結束 --->
		</div>
	</div>
	
    <!-- 0送出 -->
    <div class="row mx-4 mb-5 pb-5" id="finalBtnRow" style="display:none">
        <div class="col-3 contact-form">
        	<button class="btn btn-primary btn-lg btn-block backBtn">取消訂單</button>
    	</div>
        <div class="col-3 contact-form">
            <button class="btn btn-primary btn-lg btn-block" id="confirmCheck">確認結帳</button>  
        </div>
    </div>  
    <div class="col-3 contact-form">
      	<button class="btn btn-primary btn-lg btn-block" id="fastbtn">一鍵輸入</button>
  	</div>

  </div>
</section>

<!--------------------------------------------------------------------------------------------------->

<%-- footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>
<%-- 通知小盒子 --%>
<%@ include file="/front-end/Jessica/common/msgsModal.jsp"%>
<%-- socket --%>
<%@ include file="/front-end/Jessica/common/socket.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script type="text/javascript">
window.onload = function() {
	connect();
};

var order_price = $('#order_price').text();
$('#coupon_id').change(function(){
	var coupon_id = $(this).val();
	if(coupon_id != ''){
		 $.ajax({
			 type: 'POST',
			 url: '${pageContext.request.contextPath}/coupon.do?action=ajaxVerifyCoupon',
			 data: {'coupon_id':coupon_id, 'order_price':order_price},
			 success: function (data){
				 if(data === 'cantUse'){
					 $('#couponVerifyRes').text('折價券不存在或已使用');
					 $('#disc').text(0);
					 $('#total_price').text(order_price);
					 $('#disc1').text(0);
					 $('#total_price1').text(order_price);					 
				 } else{
					 var jsonObj = JSON.parse(data);
					 $('#couponVerifyRes').text(jsonObj.msg);
					 $('#disc').text(jsonObj.coupon_disc);
					 $('#total_price').text(jsonObj.total_price);
					 $('#disc1').text(jsonObj.coupon_disc);
					 $('#total_price1').text(jsonObj.total_price);
				 }				 
			 },
			 error: function(xhr, ajaxOptions, thrownError){
			     console.log(xhr.status);
			     console.log(thrownError);
			 }
		});
	}else{
		$('#couponVerifyRes').text('');
		$('#disc').text(0);
		$('#total_price').text(order_price);
		$('#disc1').text(0);
		$('#total_price1').text(order_price);
	}
})

$('.backBtn').click(function(e){
	e.preventDefault();
	
    swal({
        title: "確定取消訂單嗎？",
        html: "按下確定後無法恢復訂單狀態",
        type: "question", // type can be "success", "error", "warning", "info", "question"
        showCancelButton: true,
        showCloseButton: true,
    }).then(function(result) {
        if (result) {
        	window.location.href = '${pageContext.request.contextPath}/course/courseOrder.do?action=cancelOrder&order_id=${orderVo.order_id}';
        }
    }, function(dismiss) { // dismiss can be "cancel" | "overlay" | "esc" | "cancel" | "timer"
        swal("繼續結帳！", "Good Choice!","info");
    }).catch(swal.noop);
});

$('#goCheckoutBtn').click(function(e){	
	$('#stepItem').html('<div class="col text-muted"><span class="badge badge-light">1</span><span>　課程結帳</span></div><div class="col-1"><i class="fa fa-long-arrow-right" aria-hidden="true"></i></div><div class="col font-weight-bold"><span class="badge badge-primary">2</span><span>　金流支付</span></div><div class="col-1"><i class="fa fa-long-arrow-right" aria-hidden="true"></i></div><div class="col text-muted"><span class="badge badge-light">3</span><span>　完成</span></div>');
	$('#tfoot').show();
	$('#couponRow').css('display','none');
	$('#payment').show();
	$('#finalBtnRow').show();
	$(this).parent().parent().parent().css('display','none');
});

$('#confirmCheck').click(function(e){
	var order_id = $('#order_id').text();
	var coupon_id = $('#coupon_id').val();
	
	sendCredit(order_id, coupon_id);
});

function sendCredit(order_id, coupon_id){ 
	 var ccname = $('#cc-name').val();
	 var ccnumber = $('#cc-number').val();
	 var ccexpiration = $('#cc-expiration').val();
	 var cccvv = $('#cc-cvv').val();
	 if(ccname=='' || ccnumber=='' || ccexpiration=='' || cccvv==''){
	  swal("錯誤！", "請填入信用卡資料！","error");
	 }else{ 
	  swal({
	     title: '送出信用卡訊息',
	     text:'信用卡進行驗證中',
	     confirmButtonText: 'OK',
	     showLoaderOnConfirm: true,
	     preConfirm: function() {
	       return new Promise(function(resolve) {
	         setTimeout(function() { resolve();
	         window.location.href = '${pageContext.request.contextPath}/course/courseOrder.do?action=payOrder&order_id='+order_id+'&coupon_id='+coupon_id;}, 1500);
	       });
	     },
	     allowOutsideClick: false
	   }).then(function() {
	     swal({
	      type: 'success',
	      title: '信用卡驗證完成！'
	     });
	   });
	 }
	}
	
$('#fastbtn').click(function(e){
	e.preventDefault();
	$('#cc-name').val('小夫');
	$('#cc-number').val('4311-9522-2222-2222');
	$('#cc-expiration').val('09/29');
	$('#cc-cvv').val('222');
});
</script>


</body>
</html>
</c:if>