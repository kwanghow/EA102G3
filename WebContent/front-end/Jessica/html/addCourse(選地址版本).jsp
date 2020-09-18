<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="coachSvc" scope="page"	class="com.coach.model.CoachService" />
<jsp:useBean id="exptypeSvc" scope="page" class="com.exptype.model.ExptypeService" />
<jsp:useBean id="expSvc" scope="page" class="com.exp.model.ExpService" />
<!DOCTYPE html>
<html>
<head>
<title>EA102G3｜新增課程</title>
<%-- 靜態include: css樣式、js文件、style --%>
<%@ include file="/front-end/Jessica/common/head.jsp"%>

<%-- 單頁所需css樣式、js文件 --%>
<!-- sweetalert -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/6.10.3/sweetalert2.js" type="text/javascript"></script>
<!-- google map -->
<script src="https://cdn.jsdelivr.net/npm/tw-city-selector@2.1.1/dist/tw-city-selector.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAm0PnJOlagjyo4b6waNYMXuUd5RnjDKf4&libraries=places&callback=initMap" async defer></script>
<style>
	.errorMsg {
	    width: 100%;
	    margin-top: .25rem;
	    font-size: 80%;
	    color: #dc3545;
	}
	.errorMsgHide{
		display: none;
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
            <h2><em>新增</em>課程</h2>
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb justify-content-center">
                    <li class="breadcrumb-item"><a href="#">首頁</a></li>
                    <li class="breadcrumb-item"><a href="#">教練專區</a></li>
                    <li class="breadcrumb-item active" aria-current="page">新增課程</li>
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
    <form method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/course/course.do?action=addCourse">
    <div class="row mx-5 mt-1 mb-4 p-4 bg-light">
    	<div class="col-md-12 order-md-1">
    	
    		<!-- 第一行 -->
    		<div class="row my-3">
    			<div class="col-md-8 mb-3">
    				<label for="cname">課程名稱</label>　
    				<span class="errorMsg errorMsgHide">請填寫課程名稱。</span>
    				<input type="text" class="form-control" id="cname" name="cname" value="${ empty courseVo ? '' : courseVo.cname}" placeholder="請輸入您的課程名稱，於100字內。" />                    
    			</div>
    			<div class="col-md-4 mb-3">
    				<label for="exp_id">課程類別</label>　
    				<span class="errorMsg errorMsgHide">請選擇課程類別。 </span>
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
	            	<div class="row">
	            		<div class="col-5">價格方案　<span class="errorMsg errorMsgHide">請至少填寫1種方案。</span></div>
	            		<div class="col-6"></div>
	            		<div class="col-1 text-center"></div>
	            	</div>
                </div>
                <div class="card-body" id="setArea">
                	<table>
                		<thead>
                			<tr>
                				<th>堂數</th>
                				<th>總價</th>
                				<th><i class="fa fa-plus-circle" aria-hidden="true"></i></th>
                			</tr>
                		</thead>
                		<tbody>
                			<tr>
                				<td>
                					<span class="errorMsg errorMsgHide">堂數需大於0。 </span>
                					<input type="text" class="form-control" name="lesson">
                				</td>
                				<td>
                					<span class="errorMsg errorMsgHide">價格需大於0。 </span>
                					<input type="text" class="form-control" name="price">
                				</td>
                				<td>
                					<input type="hidden" name="" value="" class="oneSet">
                					<i class="fa fa-minus-circle" aria-hidden="true"></i>
                				</td>
                			</tr>
                		</tbody>
                	</table>
                </div>
            </div>
            
			<!-- 第三行:地址 -->
			<div class="row my-3">
				<div class="col-md-12 mb-3">
					<label for="keyword">上課地點 <span class="errorMsg errorMsgHide">請輸入相關地名關鍵字。 </span></label> 					
					<input type="text" class="form-control" id="keyword" name="address" placeholder="請輸入相關地名關鍵字。">
					<input type="hidden" class="form-control" id="latlng" name="loc">
				</div>
				<div class="col-md-12 mb-3">
					<div id="map"></div>
				</div>
			</div>		
			
<!-- 	<div class="display"> -->
<!--         <span id="panel">請輸入相關地名關鍵字：<input type="text" id="keyword"></span> -->
<!--         <div id="map"></div> -->
<!--     </div> -->
<!-- 			<div class="row city-selector-set my-3"> -->
<!-- 				<div class="col-md-3 mb-3"> -->
<!-- 					<label for="county">縣市 <span class="errorMsg errorMsgHide">請選擇。 </span></label> 					 -->
<!-- 					<select class="county custom-select d-block w-100" id="county"></select> -->
<!-- 				</div> -->
<!-- 				<div class="col-md-3 mb-3"> -->
<!-- 					<label for="district">區域 <span class="errorMsg errorMsgHide">請選擇。 </span></label> 					 -->
<!-- 					<select class="district custom-select d-block w-100" id="district"></select> -->
<!-- 				</div> -->
<!-- 				<div class="col-md-6 mb-3"> -->
<!-- 					<label for="address">地址</label>  -->
<!-- 					<span class="errorMsg errorMsgHide">請填寫完整地址。 </span> -->
<!-- 					<input type="text" class="form-control" id="address" name="address"> -->
<!-- 				</div> -->
<!-- 			</div> -->

			<!-- 第四行:圖片 -->
			<div class="row mt-1 my-3">
				<div class="col-4">課程圖片1(封面)</div>
				<div class="col-4">課程圖片2</div>
				<div class="col-4">課程圖片3</div>
			</div>
			<div class="row">
				<div class="col mb-3 mx-3 custom-file">
					<input type="file" class="custom-file-input" id="customFile1">
					<label class="custom-file-label" for="customFile">Choose file</label>
				</div>
				<div class="col mb-3 mx-3 custom-file">
					<input type="file" class="custom-file-input" id="customFile2">
					<label class="custom-file-label" for="customFile">Choose file</label>
				</div>
				<div class="col mb-3 mx-3 custom-file">
					<input type="file" class="custom-file-input" id="customFile3">
					<label class="custom-file-label" for="customFile">Choose file</label>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<img id="img1" class="img-fluid" src="">
				</div>
				<div class="col">
					<img id="img2" class="img-fluid" src="">
				</div>
				<div class="col">
					<img id="img3" class="img-fluid" src="">
				</div>
			</div>

			<!-- 第五行:課程簡介 -->
			<div class="row mt-3">
				<div class="col">
					<div class="form-group">
						<label for="intro">課程簡介</label>
						<textarea class="form-control" id="intro" name="intro" rows="5" onkeyup="autogrow(this);" placeholder="請輸入您的課程內容文字簡述，於1000字內。"></textarea>
					</div>
				</div>
			</div>
			
			<!-- 第六行:上下架 -->
			<div class="row mt-1 my-3">
				<div class="col-4">課程狀態</div>
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
    <div class="row mx-5 mb-4 pb-5 px-5 text-center">
    	<div class="col-4"></div>
    	<div class="col">
    		<button class="btn" id="clear">清除</button>
    	</div>
    	<div class="col">
    		<input class="btn" type="submit" name="" value="送出">
    	</div>
    	<div class="col-4"></div>
    </div>
    
    </form>
    <!-- form結束 -->
  </div>
</section>

<!--------------------------------------------------------------------------------------------------->

<%-- 沒登入跳窗 & footer & 錯誤表列 --%>
<%@ include file="/front-end/Jessica/common/footer.jsp"%>

<!--------------------------------------------------------------------------------------------------->

<script type="text/javascript">
//---------
// new TwCitySelector({
//     el: '.city-selector-set',
//     elCounty: '.county', // 在 el 裡查找 element
//     elDistrict: '.district', // 在 el 裡查找 element
//     elZipcode: '.zipcode' // 在 el 裡查找 element
// });
//----------
$('.fa-plus-circle').click(function(){
	var str = '<tr>'
			+ '<td>'
			+ '<span class="errorMsg errorMsgHide">堂數需大於0。 </span><input type="text" class="form-control" id="lesson" name="lesson">'
			+ '</td>'
			+ '<td>'
			+ '<span class="errorMsg errorMsgHide">價格需大於0。 </span>'
			+ '<input type="text" class="form-control" id="price" name="price">'
			+ '</td>'
			+ '<td>'
			+ '<i class="fa fa-minus-circle" aria-hidden="true"></i>'
			+ '</td>'
			+ '</tr>';
	$('tbody').append(str);
	$('.fa-minus-circle').click(function(){
		$(this).parent().parent().remove();
	})
});
//-----------
        var map;

        function init() {
            var keyword = document.getElementById('keyword');
            var options = {
                componentRestrictions: { country: 'tw' } // 限制在台灣範圍
            };
            var autocomplete = new google.maps.places.Autocomplete(keyword, options);

            // 地址的輸入框，值有變動時執行
            autocomplete.addListener('place_changed', function() {
                var place = autocomplete.getPlace(); // 地點資料存進place
                console.log(place);
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
                        content: `<div class="info_title">` + place.name + `</div>
                            <div class="info_body"><div><span class="info_head">地址: </span>` + place.formatted_address + `</div><div><span class="info_head">經緯度: </span>(` + place.geometry.location.lat() + `, ` + place.geometry.location.lng() + `)</div></div>
                        `
                    });
                    infowindow.open(map, marker);
                    $('#latlng').val(place.geometry.location.lat()+','+place.geometry.location.lng()+','+place.formatted_address);
                }

            });
        }

        function initMap() {
            map = new google.maps.Map(document.getElementById('map'), {
                center: { lat: 24.978391, lng: 121.268641 },
                zoom: 12,
            });
        }

        window.onload = init;
</script>


</body>
</html>