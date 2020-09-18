<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="courseSvc" scope="page" class="com.course.model.CourseService"/>
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />
<jsp:useBean id="expSvc" scope="page" class="com.exp.model.ExpService" />
<c:if test="${empty courseVo}">
	請從課程賣場進入! 3秒後自動跳轉回課程賣場頁面
	<% 
	String url = request.getContextPath() + "/front-end/Jessica/coach/course_productList.jsp";
	response.setHeader("refresh","3;URL="+url); 
	%>
</c:if>
<c:if test="${not empty courseVo}">
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜編輯課程</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<!-- google map -->
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAm0PnJOlagjyo4b6waNYMXuUd5RnjDKf4&libraries=places&callback=initMap" async defer></script>
<style>
	.errorMsg {
	    width: 100%;
	    margin-top: .25rem;
	    font-size: 80%;
	    color: #dc3545;
	}
	table tr th, td{
		padding: 8px 30px;
		vertical-align: bottom;
	}
	.fa-minus-circle{
		padding-bottom: 15px;
	}
	
	.display {
		text-align: center;
	}
	/* 	map */
	#map {
		width: 100%;
		height: 300px;
		margin: 10px auto;
    }
    .info_title {
    	font-size: 20px;
    	font-weight: 800;
    }
    .info_head {
    	font-weight: 800;
    }
    .info_body {
    	height: 50px;
    }
    
	.sendBtn .btn {
	    display: inline-block;
	    font-size: 13px;
	    padding: 11px 17px;
	    background-color: #ed563b;
	    color: #fff;
	    text-align: center;
	    font-weight: 400;
	    text-transform: uppercase;
	    transition: all .3s;
	    border: none;
	    outline: none;
	    margin-top: -8px;
	    width: 100px;
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
            <h2><em>編輯</em>課程</h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">首頁</a></li>
                    <li class="breadcrumb-item"><a href="#">教練專區</a></li>
                    <li class="breadcrumb-item"><a href="#">私人課程賣場</a></li>
                    <li class="breadcrumb-item active" aria-current="page">編輯課程</li>
                </ol>
            </nav>
        </div>
    </div>
</div>

<!-------------------------------------------------------------------------------------------------------------------------------------------------->

<section class="section" id="section-content">
  <div class="container">
  	<!-- 小標 -->
    <div class="row">
        <div class="col-lg-6 offset-lg-3"><div class="section-heading mb-0">
            <h2>填寫<em> 課程內容</em></h2>
            <img src="${pageContext.request.contextPath}/front-end/Jessica/static/img/line-dec.png" alt="">
        </div></div>
    </div>

    <!-- form開始 -->
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/course/course.do">
    <div class="form-group row mx-5 mt-1 mb-4 p-4 bg-light">
    	<div class="col-md-12 order-md-1">   
    	
    		<!-- 第一行 -->
    		<div class="form-group row my-3">
    			<div class="col-md-8 mb-3">
    				<label for="cname">課程名稱</label>　
    				<span class="errorMsg">${errorMsgs.cname}</span>
    				<input type="text" class="form-control" id="cname" name="cname" value="${ empty courseVo ? '' : courseVo.cname}" placeholder="請輸入您的課程名稱，於100字內。" />                    
    			</div>
    			<div class="col-md-4 mb-3">
    				<label for="exp_id">課程類別</label>　
    				<span class="errorMsg">${errorMsgs.exp_id} </span>
    				<select class="custom-select d-block w-100" id="exp_id" name="exp_id">
    					<option value="">Choose...</option>
    					<c:forEach var="expVo" items="${expSvc.oneCoachHowManySkillIsExp(coachLogIn.coach_Id)}">
    						<option value="${expVo.exp_Id}" ${(expVo.exp_Id == courseVo.exp_id)? 'selected':''}>${exptypeSvc.getOneExptype(expVo.exp_Id).exp_Name}</option> 
    					</c:forEach>
    				</select>
    			</div>
    		</div>
    		
			<!-- 第二行:方案 -->
            <div class="card">
            	<div class="card-header bg-white">
	            	<div class="form-group row">
	            		<div class="col-5">價格方案　<span class="errorMsg">${errorMsgs.set}</span></div>
	            		<div class="col-6"></div>
	            		<div class="col-1 text-center"></div>
	            	</div>
                </div>
                <div class="card-body">
                	<table>
                		<thead>
                			<tr>
                				<th>堂數  <span class="errorMsg">${errorMsgs.lesson}</span></th>
                				<th>總價 <span class="errorMsg">${errorMsgs.price}</span></th>
                				<th>平均$/堂</th>
                				<th><i class="fa fa-plus-circle" aria-hidden="true"></i></th>
                			</tr>
                		</thead>
                		<tbody id="setArea">
                			<c:if test="${empty sets}">
                				<tr>
	                				<td><input type="text" class="form-control"></td>
	                				<td><input type="text" class="form-control"></td>
	                				<td><input type="text" class="form-control" readonly></td>
	                				<td>
	                					<input type="hidden" name="" value="">
	                					<i class="fa fa-minus-circle" aria-hidden="true"></i>
	                				</td>
	                			</tr>
                			</c:if>
                			<c:if test="${not empty sets}">
	                			<c:forEach var="setVo" items="${sets}">
	                			<tr>
	                				<td><input type="text" class="form-control" value="${setVo.lesson}"></td>
	                				<td><input type="text" class="form-control" value="${setVo.price}"></td>
	                				<td><input type="text" class="form-control" value="${setVo.price / setVo.lesson}" readonly></td>
	                				<td>
	                					<input type="hidden" name="set" value="${setVo.lesson}:${setVo.price}:${setVo.set_id}">
	                					<i class="fa fa-minus-circle" aria-hidden="true"></i>
	                				</td>
	                			</tr>
	                			</c:forEach>
                			</c:if>
                		</tbody>
                	</table>
                </div>
            </div>
            
			<!-- 第三行:地址 -->
			<div class="form-group row my-3">
				<div class="col-md-12 mb-3">
					<label for="keyword">上課地點 　<span class="errorMsg">${errorMsgs.loc}</span></label> 					
					<input type="text" class="form-control" id="keyword" placeholder="請輸入相關地名關鍵字。" value="">
					<input type="hidden" class="form-control" id="latlng" name="loc" value="${empty courseVo ? '' : courseVo.loc}">
				</div>
				<div class="col-md-12 mb-3">
					<div id="map"></div>
				</div>
			</div>

			<!-- 第四行:圖片 -->
			<div class="form-group row mt-1 my-3">
				<div class="col-4">課程圖片1(封面) <span class="errorMsg">${errorMsgs.pic}</span></div>
				<div class="col-4">課程圖片2</div>
				<div class="col-4">課程圖片3</div>
			</div>
			<div class="form-group row">
				<div class="col mb-3 mx-3 custom-file">
					<input type="hidden" name="oldPic1" value="${pic1}"/>
					<input type="file" class="custom-file-input" id="file1" name="file1">					
					<label class="custom-file-label" for="file1">Choose file</label>
				</div>
				<div class="col mb-3 mx-3 custom-file">
					<input type="hidden" name="oldPic2" value="${pic2}"/>
					<input type="file" class="custom-file-input" id="file2" name="file2">
					<label class="custom-file-label" for="file2">Choose file</label>
				</div>
				<div class="col mb-3 mx-3 custom-file">
					<input type="hidden" name="oldPic3" value="${pic3}"/>
					<input type="file" class="custom-file-input" id="file3" name="file3">
					<label class="custom-file-label" for="file3">Choose file</label>
				</div>
			</div>
			<div class="form-group row">
				<div class="col">
					<img id="img1" class="img-fluid" src="${pic1}">
				</div>
				<div class="col">
					<img id="img2" class="img-fluid" src="${pic2}">
				</div>
				<div class="col">
					<img id="img3" class="img-fluid" src="${pic3}">
				</div>
			</div>

			<!-- 第五行:課程簡介 -->
			<div class="form-group row mt-3">
				<div class="col">
					<div class="form-group">
						<label for="intro">課程簡介</label> 　<span class="errorMsg">${errorMsgs.intro}</span>
						<textarea class="form-control" id="intro" name="intro" rows="5" onkeyup="autogrow(this);" placeholder="請輸入您的課程內容文字簡述，於1000字內。">${ empty courseVo ? '' : courseVo.intro}</textarea>
					</div>
				</div>
			</div>
			
			<!-- 第六行:上下架 -->
			<div class="form-group row mt-1 my-3">
				<div class="col-4">課程狀態</div>　<span class="errorMsg">${errorMsgs.state}</span>
			</div>
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="state1" name="state" value="1" ${(empty courseVo || courseVo.state == '1')? 'checked' : ''}>
				<label class="custom-control-label" for="state1">上架</label>
			</div>
			<div class="custom-control custom-radio custom-control-inline">
				<input type="radio" class="custom-control-input" id="state0" name="state" value="0" ${(courseVo.state == '0')? 'checked' : ''} >
				<label class="custom-control-label" for="state0">下架</label>
			</div>
			
		</div>
	</div>
	
    <!-- 按鈕開始 -->
    <div class="form-group mx-5 mb-4 pb-5 px-5 sendBtn">
    	<button id="returnBtn" class="btn main-button">回上一頁</button>　
    	<input type="hidden" name="course_id" value="${courseVo.course_id}">
    	<input type="hidden" name="action" value="updateCourse">
    	<input type="submit" value="送出更新" class="btn">　
    </div>

    </form>
    <!-- form結束 -->
  </div>
</section>

<!--------------------------------------------------------------------------------------------------->

<%-- footer --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>
<%-- socket --%>
<%@ include file="/front-end/Jessica/common/socket.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script type="text/javascript">
window.onload = function() {
	connect();
	init();
};

$('#returnBtn').click(function(e){
	e.preventDefault();
	window.location.href = "${pageContext.request.contextPath}/front-end/Jessica/coach/course_productList.jsp";
});

//----------
// 新增一列set
$('.fa-plus-circle').click(function(){
	var str = '<tr>'
			+ '<td>'
			+ '<input type="text" class="form-control">'
			+ '</td>'
			+ '<td>'
			+ '<input type="text" class="form-control">'
			+ '</td>'
			+ '<td>'
			+ '<input type="text" class="form-control" readonly>'
			+ '</td>'
			+ '<td>'
			+ '<input type="hidden" name="" value="">'
			+ '<i class="fa fa-minus-circle" aria-hidden="true"></i>'
			+ '</td>'
			+ '</tr>';
	$('tbody').append(str);
	$('.fa-minus-circle').click(function(){
		$(this).parent().parent().remove();
	});
	$('#setArea tr td input').change(function(){
		var tr = $(this).parent().parent();
		var lesson = $(tr).children('td').eq(0).children('input').val();
		var price = $(tr).children('td').eq(1).children('input').val();
		
		var avgPrice = $(tr).children('td').eq(2).children('input');
		var hideInput = $(tr).children('td').eq(3).children('input');
		$(avgPrice).attr('value', price/lesson);
		$(hideInput).attr('name', 'set');
		$(hideInput).attr('value', lesson+':'+price);	
	});
});

// 算平均，和填寫隱藏值
$('#setArea tr td input').change(function(){
	var tr = $(this).parent().parent();
	var lesson = $(tr).children('td').eq(0).children('input').val();
	var price = $(tr).children('td').eq(1).children('input').val();
	
	var avgPrice = $(tr).children('td').eq(2).children('input');
	var hideInput = $(tr).children('td').eq(3).children('input');
	$(avgPrice).attr('value', price/lesson);
	$(hideInput).attr('name', 'set');
	$(hideInput).attr('value', lesson+':'+price);	
});

// 刪除按鈕
$('.fa-minus-circle').click(function(){
	$(this).parent().parent().remove();
});

//-----------
var map, initAddress, initLat, initLng;
function init() {
    var keyword = document.getElementById('keyword');
    var options = {
        componentRestrictions: { country: 'tw' } // 限制在台灣範圍
    };
    var autocomplete = new google.maps.places.Autocomplete(keyword, options);

    // 地址的輸入框，值有變動時執行
    autocomplete.addListener('place_changed', function() {
        var place = autocomplete.getPlace(); // 地點資料存進place
        // debugger;
        // 確認回來的資料有經緯度
        if (place.geometry) {
            // 改變map的中心點
            var searchCenter = place.geometry.location;
            // panTo是平滑移動、setCenter是直接改變地圖中心
            map.panTo(searchCenter);
            // 在搜尋結果的地點上放置標記
            var marker = new google.maps.Marker({
                position: searchCenter,
                map: map
            });
            // info window
            var infowindow = new google.maps.InfoWindow({
                content: `<div class="info_title">` + place.name + `</div><div class="info_body"><div><span class="info_head">地址: </span>` + place.formatted_address + `</div><div><span class="info_head">經緯度: </span>(` + place.geometry.location.lat() + `, ` + place.geometry.location.lng() + `)</div></div>`
            });
            infowindow.open(map, marker);
            $('#latlng').val(place.geometry.location.lat()+','+place.geometry.location.lng()+','+place.formatted_address);
        }

    });
}

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: 24.9576852, lng: 121.2250143 },
        zoom: 14,
    });
    
    var loc = "${empty courseVo ? '' : courseVo.loc}";
	if(loc != ""){
		var arr = loc.split(',');
		initLat = Number(arr[0]);
		initLng = Number(arr[1]);
		initAddress = arr[2];
		$('#keyword').attr('value', initAddress);
	}
	
    infoWindow = new google.maps.InfoWindow();
    
    if(initLat !== ''){
    	var latlng = new google.maps.LatLng(initLat, initLng); 
    	map.setCenter(latlng);
    	
        var marker = new google.maps.Marker({ // 紅點
            position: { lat: initLat, lng: initLng },
            map: map,
            animation: google.maps.Animation.DROP, // DROP掉下來、BOUNCE一直彈跳
            draggable: true // true、false可否拖拉
        });
        
        infowindow = new google.maps.InfoWindow({
            content: `<div class="info_title">` + initAddress + `</div><div class="info_body"><div><span class="info_head">地址: </span>` + initAddress + `</div><div><span class="info_head">經緯度: </span>(` + initLat + `, ` + initLng + `)</div></div>`
        });
        infowindow.open(map, marker);
    } 
}

//--------
$('#file1').on('change', function(e){
    const file = this.files[0];    
    const fr = new FileReader();
    fr.onload = function (e) {
      $('#img1').attr('src', e.target.result);
    };    
    fr.readAsDataURL(file);   
});
$('#file2').on('change', function(e){
    const file = this.files[0];    
    const fr = new FileReader();
    fr.onload = function (e) {
      $('#img2').attr('src', e.target.result);
    };    
    fr.readAsDataURL(file);   
});
$('#file3').on('change', function(e){
    const file = this.files[0];    
    const fr = new FileReader();
    fr.onload = function (e) {
      $('#img3').attr('src', e.target.result);
    };    
    fr.readAsDataURL(file);   
});
</script>

</body>
</html>
</c:if>