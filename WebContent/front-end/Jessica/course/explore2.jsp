<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService"/>

<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜探索課程</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<link href="${pageContext.request.contextPath}/front-end/Jessica/static/css/hamburgers.css" rel="stylesheet">
<style>
    #our-classes {
        margin-bottom: 0px;
    }
    /*調整課程與課程間的高度*/
    .trainer-item {
        margin-bottom: 30px;
    }
    /*改變愛心跟結帳的大小*/
    .social-icons{
        font-size: 18px;
    }
    .social-icons li{
        cursor: pointer;
    }
    #trainers h4 a{
        color: black;
    }
    #trainers a{
        color: #ed563b;
    }

    /*訂單狀態選項*/
    .option ul li{
        margin-bottom: 20px; display: inline-block; width: 100%;
    }
    .option ul li a{        
        text-transform: capitalize;
        width: 100%;
        padding: 15px 15px;
        display: inline-block;
        background-color: #fff;
        border: none;
        box-shadow: 0px 0px 15px rgba(0,0,0,0.1);
        border-radius: 5px;
        font-size: 16px;
        letter-spacing: 0.5px;
        font-weight: 600;
        transition: all 0.3s;
        cursor: pointer;
    }
    .option ul li .yesActive { color: #ed563b; }
    .option ul li a span{
        margin-left: 15%;
    }
    #loc-state ul li a{
    	border: 1px solid rgba(0,0,0,0.1);
    	box-shadow: 0px 0px 0px rgba(0,0,0,0);
    }
    #loc-state ul li a span{
    	margin-left: 0%;
    }
    #our-classes form{
        padding: 30px 30px;
    }
    .itme a{
        margin-right: 10px;
        background-color: lightgray;
    }
    .FilterBtn{
        text-align: center; color: #fff; background-color: #ed563b; border:none;
    }
    #trainers .trainer-item p{
        margin-bottom: 0px;
        color: black;
    }
    .fa-heart{
    	color: #ed563b;
    }
    #FilterArea {
    	border: 1px solid black;
    }
    
    /*裁切相片*/
    .course-img{
        height: 0;
        padding-bottom: 200px;
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
	#recommend .step{
		display: none;
	}
	#recommend .stepActive{
		display: block;
	}

</style>

</head>
<body>
<%-- Preloader & Header --%>
<%@ include file="/front-end/Jessica/common/header.jsp"%>

<!----- Main Banner Area ----->
<div class="main-banner" id="top">
    <!-- Banner Img -->
    <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/course-banner.png" id="bg-video">
    <div class="video-overlay header-text">
        <!-- 麵包屑 -->
        <div class="caption">
            <h2>EXPLORE PERSONAL <em>COURSE</em></h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">HOME</a></li>
                    <li class="breadcrumb-item active" aria-current="page">COURSE</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!--------------------------------------------------------------------------------------------------->

<!-- Modal -->
<div class="modal fade" id="recommend" tabindex="-1" aria-labelledby="stepModal" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="stepModal">請開始你的表演</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>	
			
<!-- ------------------------------------------------------------- -->
			<div id="step0" class="step stepActive">
				<div class="modal-body my-3 text-center">
					<h3>找教練</h3>
					<h3>不知道如何開始？</h3>
					<div class="text-danger" style="font-size: 5em"><i class="fa fa-question" aria-hidden="true"></i></div>
					<h5 class="mt-3">5步驟，將智能推薦最合適的課程給您</h5>
				</div>
				<div class="modal-footer justify-content-center">　
					<button type="button" class="btn btn-primary next">開始探索課程</button>
				</div>	
			</div>
<!-- ------------------------------------------------------------- -->
			<div id="step1" class="step">
				<div class="modal-body mx-5">
		 			<!-- 進度條 -->
					<div class="progress mb-4">
						<div class="progress-bar" role="progressbar" style="width: 20%;" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">step1</div>
					</div>
					<h5 class="mb-4">您有興趣的課程為？(複選)</h5>
		            <!-- 選項 開始↓ -->
		            <div id="order-state" class="option">
		                <ul class="row">
		                	<c:forEach var="exptypeVo" items="${exptypeSvc.all}">
			                    <li class="col-sm-4">
			                        <a id="${exptypeVo.exp_Id}" class="noActive">
			                            <span>💪　${exptypeVo.exp_Name}</span>
			                        </a>
			                    </li>
		                    </c:forEach>
		                </ul>
		            </div>
		            <!-- 選項 結束↑ -->
				</div>
				<div class="modal-footer justify-content-around my-3">
					<button type="button" class="btn btn-primary prev">上一步</button>
					<button type="button" class="btn btn-primary next">下一步</button>
				</div>
			</div>
<!-- ------------------------------------------------------------- -->
			<div id="step2" class="step">
				<div class="modal-body mx-5">
		 			<!-- 進度條 -->
					<div class="progress mb-4">
						<div class="progress-bar" role="progressbar" style="width: 40%;" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100">step2</div>
					</div>
					<h5 class="mb-4">您需要指定老師的性別嗎？(複選)</h5>
		            <!-- 選項 開始↓ -->
		            <div id="sex-state" class="option">
		                <ul class="row">
		                    <li class="col-sm-4">
		                        <a class="noActive" id="男">
		                            <span>🐶　男性</span>
		                        </a>
		                    </li>
		                    <li class="col-sm-4">
		                        <a class="noActive" id="女">
		                            <span>😼　女性</span>
		                        </a>
		                    </li>

		                </ul>
		            </div>
		            <!-- 選項 結束↑ -->
				</div>
				<div class="modal-footer justify-content-around my-3">
					<button type="button" class="btn btn-primary prev">上一步</button>
					<button type="button" class="btn btn-primary next">下一步</button>
				</div>
			</div>
<!-- ------------------------------------------------------------- -->
			<div id="step3" class="step">
				<div class="modal-body mx-5">
		 			<!-- 進度條 -->
					<div class="progress mb-4">
						<div class="progress-bar" role="progressbar" style="width: 60%;" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100">step3</div>
					</div>
					<h5 class="mb-4">您希望在哪裡上課？(複選)</h5>
		            <!-- 選項 開始↓ -->
		            <div id="loc-state" class="option">
		                <ul class="row text-center">
		                	<li class="col-sm-3"><a class="noActive" id="新北市"><span>新北市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="台北市"><span>台北市</span></a></li>		                    
		                    <li class="col-sm-3"><a class="noActive" id="基隆市"><span>基隆市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="桃園市"><span>桃園市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="新竹縣"><span>新竹縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="新竹市"><span>新竹市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="苗栗縣"><span>苗栗縣</span></a></li>                    
		                    <li class="col-sm-3"><a class="noActive" id="宜蘭縣"><span>宜蘭縣</span></a></li>	                    
		                    
		                    <li class="col-sm-3"><a class="noActive" id="台中市"><span>台中市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="彰化縣"><span>彰化縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="南投縣"><span>南投縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="雲林縣"><span>雲林縣</span></a></li>
		                    
		                    <li class="col-sm-3"><a class="noActive" id="嘉義縣"><span>嘉義縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="嘉義市"><span>嘉義市</span></a></li>		                    
		                    <li class="col-sm-3"><a class="noActive" id="台南市"><span>台南市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="高雄市"><span>高雄市</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="屏東縣"><span>屏東縣</span></a></li>
		                    
		                    <li class="col-sm-3"><a class="noActive" id="台東縣"><span>台東縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="花蓮縣"><span>花蓮縣</span></a></li>
		                    
		                    <li class="col-sm-3"><a class="noActive" id="澎湖縣"><span>澎湖縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="金門縣"><span>金門縣</span></a></li>
		                    <li class="col-sm-3"><a class="noActive" id="連江縣"><span>連江縣</span></a></li>                         
		                </ul>
		            </div>
		            <!-- 選項 結束↑ -->
				</div>
				<div class="modal-footer justify-content-around my-3">
					<button type="button" class="btn btn-primary prev">上一步</button>
					<button type="button" class="btn btn-primary next">下一步</button>
				</div>
			</div>
<!-- ------------------------------------------------------------- -->
			<div id="step3" class="step">
				<div class="modal-body mx-5">
		 			<!-- 進度條 -->
					<div class="progress mb-4">
						<div class="progress-bar" role="progressbar" style="width: 80%;" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100">step4</div>
					</div>
					<h5 class="mb-4">您希望每堂課課費多少呢？(複選)</h5>
		            <!-- 選項 開始↓ -->
		            <div id="cost-state" class="option">
		                <ul class="row">
		                	<li class="col-sm-6"><a class="noActive" id="m0"><span>😣　NT$ 0 ~ 999</span></a></li>
		                    <li class="col-sm-6"><a class="noActive" id="m1000"><span>💰️　NT$ 1000 ~ 1499</span></a></li>		                    
		                    <li class="col-sm-6"><a class="noActive" id="m1500"><span>💰️　NT$ 1500 ~ 1999</span></a></li>
		                    <li class="col-sm-6"><a class="noActive" id="m2000"><span>💰️　NT$ 2000 ~ 2499</span></a></li>
		                    <li class="col-sm-6"><a class="noActive" id="m2500"><span>💰️　NT$ 2500 ~ 2999</span></a></li>
		                    <li class="col-sm-6"><a class="noActive" id="m3000"><span>💰️　NT$ 3000 ~ 3499</span></a></li>
		                    <li class="col-sm-6"><a class="noActive" id="m3500"><span>💰️　NT$ 3500 ~ 4000</span></a></li>
		                    <li class="col-sm-6"><a class="noActive" id="m4000"><span>🤑　NT$ 4000 ~ 無上限</span></a></li>                      
		                </ul>
		            </div>
		            <!-- 選項 結束↑ -->
				</div>
				<div class="modal-footer justify-content-around my-3">
					<button type="button" class="btn btn-primary prev">上一步</button>
					<button type="button" class="btn btn-primary next">下一步</button>
				</div>
			</div>
<!-- ------------------------------------------------------------- -->
			<div id="step5" class="step">
				<div class="modal-body mx-5">
		 			<!-- 進度條 -->
					<div class="progress mb-4">
						<div class="progress-bar" role="progressbar" style="width: 100%;" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">step5</div>
					</div>
					<h5 class="mb-4">還有什麼想補充的地方嗎？</h5>
		            <!-- 選項 開始↓ -->
		            <div id="cname-state" class="option mb-5">
	                    <div class="form-group">
	                        <input type="text" class="form-control" placeholder="請輸入課程關鍵字" id="cname">
	                    </div>
		            </div>
		            <!-- 選項 結束↑ -->
					<div class="row filter mb-2">
					    <div class="col-12 ml-3 p-3 bg-white">           
					        <span class="itme"></span>                    
					    </div>
					</div>	            
				</div>
				<div class="modal-footer justify-content-around my-3">
					<button type="button" class="btn btn-primary prev">上一步</button>
					<button type="button" class="btn btn-primary next">下一步</button>
				</div>
			</div>
<!-- ------------------------------------------------------------- -->
			<div id="step6" class="step">
				<div class="modal-body mx-5">
					<h5 class="mb-4">恭喜，完成所有的步驟！</h5>
					<h5 class="mb-2">請確認所有的條件</h5>
		            <!-- 選項 開始↓ -->
		            <div id="cname-state" class="option mb-5">
	                    <div class="form-group">
	                        <input type="text" class="form-control" placeholder="請輸入課程關鍵字" id="cname">
	                    </div>
		            </div>
		            <!-- 選項 結束↑ -->
					<div class="row filter mb-2">
					    <div class="col-12 ml-3 p-3 bg-white">           
					        <span class="itme"></span>                    
					    </div>
					</div>	            
				</div>
				<div class="modal-footer justify-content-around my-3">
					<button type="button" class="btn btn-primary prev">上一步</button>
					<button type="button" class="btn btn-primary next">下一步</button>
				</div>
			</div>
			
		</div>
	</div>
</div>






<h6 class="my-4">請確認您的選擇條件：</h6>					
<div class="row filter mb-2">
    <div class="col-12 ml-3 p-3 bg-white" id="FilterArea">           
        <span class="itme"></span>                    
    </div>
    <div class="col text-center pt-2">
        <button class="FilterBtn" id="sendFilter">搜尋</button> 　
        <button class="FilterBtn"  id="clearFilter">清除</button> 
    </div>
</div>


<!--------------------------------------------------------------------------------------------------->

<!----- 課程區塊 開始↓ ----->
<section class="section" id="trainers">
    <div class="container">
		<!-- 課程開始 --> 
        <div class="row" id="courseList">
        </div>
        <!-- 課程結束 -->
    </div>
</section>
<!----- 課程區塊 結束↑ ----->

<%-- 沒登入跳窗 --%>
<%@ include file="/front-end/Jessica/common/noLogInModal.jsp"%>
<%-- footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script type="text/javascript">
$(function() {
// 	callAjaxFilter();
	
	$('#recommend').modal('show');
	$('.prev').on('click', function(e){
		e.preventDefault();
		var step = $(this).parent().parent();
		$(step).attr('class', 'step');			
		$(step).prev().attr('class', 'step stepActive');
	});	
	$('.next').on('click', function(e){
		e.preventDefault();
		var step = $(this).parent().parent();
		$(step).attr('class', 'step');			
		$(step).next().attr('class', 'step stepActive');
	});	
});

//搜尋課程條件
var obj = {};
// 搜尋條件增加事件
$("#cname").change(function() {        
    var name = 'cname';
    var val = $(this).val();
    if (val.length !== 0 && val.trim().length !== 0){
    	addDomItem(name, val, val);
    	$(this).val('');
    }
});
$("#cost-state a").click(function(){      
    var name = 'cost';
    var val = $(this).attr('id');
    var value = $(this).children("span").text().substring(3);
    
	if( $(this).attr('class') == 'noActive'){
		$(this).attr('class','yesActive');
		addDomItem(name, val, value);		
	}else{
		$(this).attr('class','noActive');
		deleteDomItem(val);
	}
});
$("#loc-state a").click(function(){      
    var name = 'loc';
    var val = $(this).children("span").text();
    
	if( $(this).attr('class') == 'noActive'){
		$(this).attr('class','yesActive');
		addDomItem(name, val, val);		
	}else{
		$(this).attr('class','noActive');
		deleteDomItem(val);
	}
});
$("#sex-state a").click(function(){      
    var name = 'sex';
    var val = $(this).children("span").text().substring(3,4);
    
	if( $(this).attr('class') == 'noActive'){
		$(this).attr('class','yesActive');
		addDomItem(name, val, val);		
	}else{
		$(this).attr('class','noActive');
		deleteDomItem(val);
	}
});
$("#order-state a").click(function(){
    var name = 'exp_type';
    var val = $(this).attr('id'); // exp_id
    var value = $(this).children("span").text().substring(3); // exp_name
    
	if( $(this).attr('class') == 'noActive'){
		$(this).attr('class','yesActive');
		addDomItem(name, val, value);		
	}else{
		$(this).attr('class','noActive');
		deleteDomItem(val);
	}
});
function addDomItem(name, val, value){
    if(name in obj){ // 判斷obj裡面有沒有這個屬性name
        var a = obj[name];
        for(var i=0; i<a.length; i++){ 
            if( a[i] === val)
            	return; // 如果obj裡面已經有值，直接回傳不加span
        }    
    }
    var domItem = "<a onclick=deleteDomItem('"+val+"') rel='"+val+"' name='"+name+"'>×  <span>"+value+"</span></a>";
    $(".itme").append(domItem); 
    
    // 增加obj屬性name的value
    if(name in obj){
    	obj[name].push(val);
    }else{
    	obj[name] = [val];
    }
    console.log("增加item後的obj:", obj);
}

// 清除單個搜尋條件
function deleteDomItem(val){
	var e = $(".filter").find("a[rel='"+val+"']");
	$(e).remove();

    var name = $(e).attr('name');
    var val =  $(e).attr('rel');
    $('#'+val).attr('class', 'noActive');

    for(var i=0; i<obj[name].length; i++){
    	if(obj[name][i] == val){
    		obj[name].splice(i,1);
    		i--;
    	}
    }
    
    if(obj[name].length == 0){ // 如果obj的屬性name空了的話，刪除該屬性
    	delete obj[name];
    }
    console.log("刪除item後的obj:", obj);
}

// 清除全部搜尋條件事件
$('#clearFilter').click(function(e){
    e.preventDefault();
    clearFilter();
});

function clearFilter(){
    $(".itme").html('');
    
    for(let name in obj){
    	delete obj[name];
    }    
    console.log("清除所有item後的obj:", obj);
}

// 送出搜尋條件
$('#sendFilter').click(function(e){
	e.preventDefault();
	callAjaxFilter();
	$('#searchForm').attr('class','bg-light mb-5 collapse');
	$('html,body').animate({
        scrollTop:$('#trainers').offset().top
      }, 'show');
	return false;
});

//把物件(搜尋條件)回傳server
function callAjaxFilter() {
    $.ajax({
        type: 'POST',
        url: "${pageContext.request.contextPath}/course/course.do?action=exploreFilter",
        data: obj,
        dataType: "json",
        success: function (data){
        	$('#courseList').html('');
        	for(var i=0; i<data.length; i++){
	        	var heart, heartClass, courseUrl, courseImgUrl, coachUrl, coachImgUrl;
	        	
	        	if(!("isFavor" in data[0]) || data[i].isFavor === '0'){
	        		heart = '0';
	        		heartClass = 'fa fa-heart-o';
	        	}else if(data[i].isFavor === '1'){
	        		heart = '1';
	        		heartClass = 'fa fa-heart';
	        	}
	        	courseUrl = '${pageContext.request.contextPath}/front-end/Jessica/course/intro.jsp?course_id=' + data[i].course_id;
	        	courseImgUrl = '${pageContext.request.contextPath}/jessica/img.do?picIndex=1&course_id=' + data[i].course_id;	        	
	        	coachUrl = '${pageContext.request.contextPath}/front-end/coachInfo.jsp?coach_Id=' + data[i].coach_id;
	        	coachImgUrl = '${pageContext.request.contextPath}/front-end/ShowPhotos?type=coachPic&img_no=' + data[i].coach_id;

	        	$('#courseList').append(
	        			'<div class="col-lg-4" id="'+data[i].course_id+'">'
	        			+'<div class="trainer-item">'
	        			+'<div class="image-thumb course-img">'
	        			+'<img src="'+courseImgUrl+'" alt="">'
	        			+'</div>'
	        			+'<div class="down-content">'
	        			+'<div class="course-type">'
	        			+'<span class="border badge-pill">'+data[i].exp_name+'</span>'
	        			+'</div>'
	        			+'<h4>'
	        			+'<a href="'+courseUrl+'">'+data[i].cname+'</a>'
	        			+'</h4>'
	        			+'<p><i class="fa fa-usd" aria-hidden="true"></i>　NT '+data[i].price_min+' ~ '+data[i].price_max+'</p>'
	        			+'<p><i class="fa fa-map-marker" aria-hidden="true"></i>　'+data[i].loc+'</p>'
	        			+'<hr>'
	        			+'<div class="row align-items-center">'
	        			+'<div class="col-3 coach_pic"><img class="rounded-circle img-fluid float-left" src="'+coachImgUrl+'"></div>'
	        			+'<div class="col-6 theme-colors"> '
	        			+'<a href="'+coachUrl+'">'+data[i].coach_name+'</a>'
	        			+'</div>'
	        			+'<div class="col-3 addHeart">'
	        			+'<input type="hidden" name="course_id" value="'+data[i].course_id+'" />'
	        			+'<input type="hidden" name="isFavor" value="'+heart+'" />'
	        			+'<i class="'+heartClass+'" aria-hidden="true"></i>'
	        			+'</div>'
	        			+'</div>'
	        			+'</div>'
	        			+'</div>'
	        			+'</div>'
	        	);
        	}

        	$('.addHeart').click(function(){
        		addHeartFunction($(this));
        	});
        },
        error: function(xhr, ajaxOptions, thrownError){
            console.log(xhr.status);
            console.log(thrownError);
        }
    });
}

//-----------------------------------------
// hamburger icon 的切換
$("button.hamburger").on("click", function(){
    $(this).toggleClass("is-active");
});

//-----------------------------------------
// 愛心特效變化
function addHeartFunction(e){
	var loaction = "$(pageScope.request.servletPath}"; //如果沒登入，可以把地址由Servlet存在session
	var heart = $(e).children('i');
	
    var course_id = $(e).children('input[name="course_id"]');
    var isFavor = $(e).children('input[name="isFavor"]');
    var args = {'course_id' : $(course_id).val(), 'action' : 'ajaxEditFavor', 'location' : loaction};

	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseFavor.do',
		 data: args,
		 success: function (data){
			if(data === 'NoLogIn'){
				$('#NoLogInModal').modal('show');		
			}
			if(data === 'addSuccess'){
				$(heart).attr('class','fa fa-heart');
			    $(isFavor).val('1');
				swal('成功', '成功加入課程最愛！', 'success');
			}else if(data === 'deleteSuccess'){
				$(heart).attr('class','fa fa-heart-o');
				$(isFavor).val('0');
				swal('成功', '成功刪除課程最愛！', 'info');
			}
		 },
		 error: function(xhr, ajaxOptions, thrownError){
		     console.log(xhr.status);
		     console.log(thrownError);
		 }
	});              
}
$('.addHeart').click(function(){
	addHeartFunction($(this));
});

$('#goToLogIn').click(function(){	
	window.location.href = "${pageContext.request.contextPath}/front-end/logIn2.jsp";	
});

</script>

</body>
</html>
