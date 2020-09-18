<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:if test="${empty allCourseOnShelf}"> --%>
<%-- 	<jsp:forward page="/course/course.do"> --%>
<%-- 		<jsp:param name="action" value="list" /> --%>
<%-- 		<jsp:param name="reqUrl" value="${pageContext.request.servletPath}" /> --%>
<%-- 	</jsp:forward> --%>
<%-- </c:if> --%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="coachSvc" scope="page" class="com.coach.model.CoachService"/>
<jsp:useBean id="memberSvc" scope="page" class="com.mem.model.MemService"/>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService"/>
<jsp:useBean id="courseSetSvc" scope="page" class="com.course_set.model.CourseSetService"/>
<jsp:useBean id="courseFavorSvc" scope="page" class="com.course_favor.model.CourseFavorService"/>

<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜探索課程</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<script type="text/javascript">

</script>

<%-- 單頁所需css樣式、js文件 --%>
<link href="${pageContext.request.contextPath}/front-end/Jessica/static/css/hamburgers.css" rel="stylesheet">
<!-- sweetalert -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>

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
    #order-state ul li{
        margin-bottom: 20px; display: inline-block; width: 100%;
    }
    #order-state ul li a{        
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
    }
    #order-state ul li a span{
        margin-left: 15%;
    }
    .nav-tabs { border: none; }
    .nav-tabs  .nav-link, .nav-tabs { color: #232d39; }
    .nav-tabs .nav-item.show .nav-link, .nav-tabs .nav-link.active { color: #ed563b; }

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

<!----- 篩選課程 開始↓ ----->
<section class="section" id="our-classes">  <!-- collapse -->
    <div class="container">
        <div class="row my-4 align-items-center">
            <div class="col-lg-6 offset-lg-3">
                <div class="section-heading">
                    <h2>EXPLORE <em>PERSONAL COURSE</em></h2>
                </div>
            </div>
            <div class="col-lg-1">
                <button class="hamburger hamburger--spring" type="button" data-toggle="collapse" data-target="#searchForm">
                    <span class="hamburger-box">
                        <span class="hamburger-inner"></span>
                    </span>
                </button>            
            </div>
        </div>

         <form class="bg-light mb-5 collapse" id="searchForm"> <!-- collapse show -->
            <div class="row filter mb-2">
                <div class="col-10 ml-3 p-3 bg-white" id="FilterArea">
                    <span>已選條件：</span>                
                    <span class="itme"></span>                    
                </div>
                <div class="col text-center pt-2">
                    <button class="FilterBtn" id="sendFilter">搜尋</button> 　
                    <button class="FilterBtn"  id="clearFilter">清除</button> 
                </div>
            </div>

            <div class="row mb-2 mt-4">
                <div class="col-md-6">                
                    <div class="form-group">
                        <label for="cname">課程名稱</label>
                        <input type="text" class="form-control" placeholder="請輸入課程名" id="cname">
                    </div>
                </div>
                <div class="col-md-3">                
                    <div class="form-group">
                        <label for="loc">上課地點</label>
                        <fieldset>
                            <!-- 縣市選單 -->
                            <select class="county custom-select" id="loc" name="county">
                            	<option value="">選擇縣市</option><option value="台北市" data-index="0">台北市</option><option value="基隆市" data-index="1">基隆市</option><option value="新北市" data-index="2">新北市</option><option value="宜蘭縣" data-index="3">宜蘭縣</option><option value="桃園市" data-index="4">桃園市</option><option value="新竹市" data-index="5">新竹市</option><option value="新竹縣" data-index="6">新竹縣</option><option value="苗栗縣" data-index="7">苗栗縣</option><option value="台中市" data-index="8">台中市</option><option value="彰化縣" data-index="9">彰化縣</option><option value="南投縣" data-index="10">南投縣</option><option value="嘉義市" data-index="11">嘉義市</option><option value="嘉義縣" data-index="12">嘉義縣</option><option value="雲林縣" data-index="13">雲林縣</option><option value="台南市" data-index="14">台南市</option><option value="高雄市" data-index="15">高雄市</option><option value="澎湖縣" data-index="16">澎湖縣</option><option value="金門縣" data-index="17">金門縣</option><option value="屏東縣" data-index="18">屏東縣</option><option value="台東縣" data-index="19">台東縣</option><option value="花蓮縣" data-index="20">花蓮縣</option><option value="連江縣" data-index="21">連江縣</option>
                            </select>
                        </fieldset>    
                    </div>
                </div>
                <div class="col-md-3">                
                    <div class="form-group">
                        <label for="sex">教練性別</label>
                        <select class="form-control" id="sex">
                            <option>選擇性別</option>
                            <option value="男">男</option>
                            <option value="女">女</option>
                        </select>
                    </div>
                </div>

            </div>

            <label for="order-state">課程種類</label>
            <!-- 選項 開始↓ -->
            <div id="order-state">
                <ul class="row nav nav-tabs" role="tablist"  id="tabs">
                	<c:forEach var="exptypeVo" items="${exptypeSvc.all}">
	                    <li class="col-sm-3 nav-item">
	                        <a class="nav-link" data-toggle="tab" role="tab" id="${exptypeVo.exp_Id}">
	                            <span>💪　${exptypeVo.exp_Name}</span>
	                        </a>
	                    </li>
                    </c:forEach>
                </ul>
            </div>
            <!-- 選項 結束↑ -->
        </form>
    </div>
</section>
<!----- 篩選課程 結束↑ ----->

<!--------------------------------------------------------------------------------------------------->

<!----- 課程區塊 開始↓ ----->
<section class="section" id="trainers">
    <div class="container">
		<!-- 課程開始 --> 
        <div class="row" id="courseList">  
        
<%--                     <c:forEach var="courseVo" items="${courseSvc.allCourseOnShelf}"> --%>
<!-- 	            <div class="col-lg-4"> -->
<!-- 	                <div class="trainer-item"> -->
<!-- 	                    <div class="image-thumb course-img"> -->
<%--  	                        <img src="${pageContext.request.contextPath}/jessica/img.do?course_id=${courseVo.course_id}&picIndex=1" alt=""> --%>
<!-- 	                    </div> -->
<!-- 	                    <div class="down-content"> -->
<!-- 	                        <div class="course-type"> -->
<%-- 	                            <span class="border badge-pill">${exptypeSvc.getOneExptype(courseVo.exp_id).exp_Name}</span> --%>
<!-- 	                        </div> -->
<!-- 	                        <h4> -->
<%-- 	                            <a href="${pageContext.request.contextPath}/front-end/Jessica/course/intro.jsp?course_id=${courseVo.course_id}">${courseVo.cname}</a> --%>
<!-- 	                        </h4> -->
<!-- 	                        <p> -->
<!-- 		                        <i class="fa fa-usd" aria-hidden="true"></i>　 -->
<%-- 								<c:forEach var="price" items="${courseSetSvc.getAllCourseSetPrice(courseVo.course_id)}" varStatus="status" > --%>
<%-- 									<c:if test="${status.first}">NT ${price} ~ </c:if> --%>
<%-- 									<c:if test="${status.last}">${price}</c:if> --%>
<%-- 								</c:forEach>								 --%>
<!-- 							</p> -->
                   
<%-- 	                        <p><i class="fa fa-map-marker" aria-hidden="true"></i>　${courseVo.loc}</p> --%>
<!-- 	                        <hr> -->
<!-- 	                        <div class="row align-items-center"> -->
<%-- 	                            <div class="col-3 coach_pic"><img class="rounded-circle img-fluid float-left" src="${pageContext.request.contextPath}/front-end/ShowPhotos?type=coachPic&img_no=${courseVo.coach_id}"></div> --%>
<!-- 	                            <div class="col-6 theme-colors">  -->
<%-- 		                            <a href="${pageContext.request.contextPath}/front-end/coachInfo.jsp?coach_Id=${courseVo.coach_id}"> --%>
<%-- 		                            	${memberSvc.getOneMem(coachSvc.getOneByCoach(courseVo.coach_id).member_Id).mem_Name} --%>
<!-- 		                            </a> -->
<!-- 	                            </div> -->
<!-- 	                            <div class="col-3 addHeart"> -->
<%-- 	                            	<input type="hidden" name="course_id" value="${courseVo.course_id}" /> --%>
<%-- 	                            	<c:if test="${ empty courseFavorSvc.getOneCourseFavor(sessionScope.memLogIn.member_Id, courseVo.course_id) }"> --%>
<!-- 	                            		<input type="hidden" name="isFavor" value="0" /> -->
<!-- 	                                	<i class="fa fa-heart-o" aria-hidden="true"></i> -->
<%-- 	                                </c:if> --%>
<%-- 	                                <c:if test="${ not empty courseFavorSvc.getOneCourseFavor(sessionScope.memLogIn.member_Id, courseVo.course_id) }"> --%>
<!-- 	                            		<input type="hidden" name="isFavor" value="1" /> -->
<!-- 	                                	<i class="fa fa-heart" aria-hidden="true"></i> -->
<%-- 	                                </c:if>	                                 --%>
<!-- 	                            </div> -->
<!-- 	                        </div> -->
<!-- 	                    </div> -->
<!-- 	                </div> -->
<!-- 	            </div> -->
<%--             </c:forEach> --%>
        
        </div>
        <!-- 課程結束 -->
    </div>
</section>
<!----- 課程區塊 結束↑ ----->

<%-- 沒登入跳窗 & footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script type="text/javascript">
$(function() {
	callAjaxFilter();
});
	
var obj = {};
// 搜尋條件增加事件
$("#cname").change(function() {        
    var name = 'cname';
    var val = $(this).val();
    if (val.length !== 0)
    	addDomItem(name, val, val);
});
$("#loc").change(function(){
    var name = 'loc';
    var val = $(this).val();
    addDomItem(name, val, val);
});
$("#sex").change(function(){      
    var name = 'sex';
    var val = $(this).val();
    addDomItem(name, val, val);
});
$("#order-state a").click(function(){        
    var name = 'exp_type';
    var val = $(this).attr('id'); // exp_id
    var value = $(this).children("span").html().substring(3); // exp_name
    addDomItem(name, val, value);
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

    for(var i=0;i<obj[name].length;i++){
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
});

//把物件obj(搜尋條件回傳server)
function callAjaxFilter() {
    $.ajax({
        type: 'POST',
        url: "${pageContext.request.contextPath}/course/course.do?action=ajaxFilter",
        data: obj, // JSON.stringify(obj)
        dataType: "json",
        success: function (data){
        	$('#courseList').html('');
        	for(var i=0; i<data.length; i++){
        		var heartClass;
        		if(data[i].isFavor === '1'){
        			heartClass = 'fa fa-heart';
        		}else if(data[i].isFavor === '0'){
        			heartClass = 'fa fa-heart-o';
        		}
        			
	        	$('#courseList').append(
	        			'<div class="col-lg-4" id="'+data[i].course_id+'">'
	        			+'<div class="trainer-item">'
	        			+'<div class="image-thumb course-img">'
	        			+'<img src="'+data[i].pic1url+'" alt="">'
	        			+'</div>'
	        			+'<div class="down-content">'
	        			+'<div class="course-type">'
	        			+'<span class="border badge-pill">'+data[i].exp_name+'</span>'
	        			+'</div>'
	        			+'<h4>'
	        			+'<a href="'+data[i].courseUrl+'">'+data[i].cname+'</a>'
	        			+'</h4>'
	        			+'<p><i class="fa fa-usd" aria-hidden="true"></i>　NT '+data[i].lowPrice+' ~ '+data[i].HighPrice+'</p>'
	        			+'<p><i class="fa fa-map-marker" aria-hidden="true"></i>　'+data[i].loc+'</p>'
	        			+'<hr>'
	        			+'<div class="row align-items-center">'
	        			+'<div class="col-3 coach_pic"><img class="rounded-circle img-fluid float-left" src="'+data[i].coach_img+'"></div>'
	        			+'<div class="col-6 theme-colors"> '
	        			+'<a href="'+data[i].coachUrl+'">'+data[i].coach_name+'</a>'
	        			+'</div>'
	        			+'<div class="col-3 addHeart">'
	        			+'<input type="hidden" name="course_id" value="'+data[i].course_id+'" />'
	        			+'<input type="hidden" name="isFavor" value="'+data[i].isFavor+'" />'
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
	console.log(e);
	var loaction = "$(pageScope.request.servletPath}";
	var heart = $(e).children('i');
	
    var course_id = $(e).children('input[name="course_id"]');
    var isFavor = $(e).children('input[name="isFavor"]');
    var args = {'course_id' : $(course_id).val() };
    console.log(args);

	 $.ajax({
		 type: 'POST',
		 url: '${pageContext.request.contextPath}/course/courseFavor.do?action=ajaxEditFavor&location='+location,
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
