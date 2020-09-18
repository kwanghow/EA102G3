<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>查詢附近便利超商</title>
    <style type="text/css">
        .display {
            text-align: right;
            width: 61%;
            padding: 5px;
            display: inline-block;
        }
        .side_panel {
            width: 36%;
            padding: 5px;
            display: inline-block;
            vertical-align: top;
        }

        .site_list {
            height: 600px;
            overflow-y: scroll;
        }
        .site_list::-webkit-scrollbar {
            display: none;
        }
        .site {
            border: 1px solid #abc;
            padding: 10px 20px;
            font-size: 16px;
            margin: 2px;
            border-radius: 8px;
        }
        .site .img {
            display: inline-block;
            width: 86px;
            height: 75px;
            float: left;
            background-size: cover !important;
        }
        .site .info {
            display: inline-block;
            padding: 0px 0px 0px 20px;
            width: 73%;
            height: 70px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .site .title {
            font-size: 20px;
            font-weight: 800;
            display: inline-block;
            width: 230px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        .site .distance {
            float: right;
            line-height: 26px;
        }
        .site .address {
            font-size: 10px;
            color: grey;
        }
        .site:hover {
            cursor: pointer;
            box-shadow: 1px 5px 5px 2px rgba(0, 0, 0, .25);
            transition: all .2s ease-in-out;
            border-color: #abc123;
            background: #5e7725;
            color: white;
        }
        .site:hover .address {
            color: white;
        }

        #map {
            width: 740px;
            height: 500px;
            margin: 10px auto;
        }
        #panel {
            font-size: 20px;
            padding-right: 20px;
        }
        #keyword, #dist {
            width: 200px;
            font-size: 20px;
            color: #a32130;
            font-weight: 700;
            padding: 5px 15px;
        }
        #start {
            border-radius: 10px;
            background-color: #ed563b;
            color: #f8f9fa;
            margin: 5px 11px;
            font-size: 20px;
            padding: 5px 18px;
            cursor: pointer;
        }
    </style>
</head>

<body>
    <div class="display">
        <span id="panel">
            請輸入現在位置：<input type="text" id="keyword">
            距離：<input type="number" id="dist" value="1000" required> 公尺
        </span>
        <br>
        <label>
            <input type="radio" name="travel" value="DRIVING" > 開車
        </label>
        <label>
            <input type="radio" name="travel" value="BICYCLING"> 腳踏車
        </label>
        <label>
            <input type="radio" name="travel" value="TRANSIT"> 大眾運輸
        </label>
        <label>
            <input type="radio" name="travel" value="WALKING" checked> 走路
        </label>
        <button id="start">開始搜尋</button>
        <div id="map"></div>
    </div>
    <div class="side_panel">
        <div class="site_list">
            <!--  <div class="site" data-index="0">
                <div class="img" style="background: url('https://maps.googleapis.com/maps/api/place/js/PhotoService.GetPhoto?1sCmRaAAAA81fXvqjPYLbsQxLAR8p8vFJdKvA9r7ziLrtiaKtCiomsmqTh9VCQ_wlVbfe5GmbFzlbRlLx4qy7Qv7cvfBI1Gc0tHl7h_QbgnvpfbIQmmtkvVVHpnm6FpRUCWpSyKm-KEhAXB-HQyCzBRVtAAk1Yelm1GhTxTTsH4ba2vzPVz0QrFwIAYXlZuw&3u4324&5m1&2e1&callback=none&key=AIzaSyASrR1CoMRPJQsy29nOG9v-J3PZf7ROiRI&token=62169') center center;"></div>
                <div class="info">
                    <div class="title">台北101</div>
                    <div class="distance">2.1 公里</div>
                    <div class="address">xxxxxxxxxxxxjewiojewoqjfqwoejgqoiegxxxxxxxxxxxxxxxxxxxxxxxxxxxx</div>
                    <div class="time">20 分鐘</div>
                </div>
            </div> -->
        </div>
    </div>
    <script>
        var map, placeService, distMatService, cityCircle;
        var markers = [];
        var places = [];
        var keyword, dist, site_list, start, center;
        var timer; // debounce timer

        function init() {
            keyword = document.getElementById('keyword');
            dist = document.getElementById('dist');
            site_list = document.getElementsByClassName('site_list')[0];
            start = document.getElementById('start');

            var options = {
                componentRestrictions: { country: 'tw' } // 限制在台灣範圍
            };
            var autocomplete = new google.maps.places.Autocomplete(keyword, options);

            // 地址的輸入框，值有變動時執行
            autocomplete.addListener('place_changed', function() {
                var place = autocomplete.getPlace(); // 地點資料存進place
                // console.log(place);
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
                        map: map,
                        animation: google.maps.Animation.BOUNCE
                    });

                    center = { lat: searchCenter.lat(), lng: searchCenter.lng() };

                    if (cityCircle) cityCircle.setMap(null);

                    cityCircle = new google.maps.Circle({
                        strokeColor: '#f1c40f', // 線條顏色
                        strokeOpacity: 1, // 線條透明度
                        strokeWeight: 1, // 線條粗度
                        fillColor: '#f1c40f', // 圓形裡填滿的顏色
                        fillOpacity: 0.35, // 圓形裡，填滿顏色的透明度
                        map: map,
                        center: center, // 中心點
                        radius: parseInt(dist.value) // 半徑
                    });
                }
            });

            start.addEventListener('click', function() {
                if (!center) {
                    alert('請先輸入現在位置！');
                    return;
                }

                deleteMarkersAndPlaces();

                var request = {
                    location: center,
                    radius: parseInt(dist.value),
                    types: ['convenience_store'] // place type: https://developers.google.com/places/web-service/supported_types
                };

                placeService = new google.maps.places.PlacesService(map);
                placeService.nearbySearch(request, function(res, status) {
                    if (status == google.maps.places.PlacesServiceStatus.OK) {
                        for (var i = 0; i < res.length; i++) {
                            var place = res[i];
                            var obj = {
                                name: place.name,
                                location: { lat: place.geometry.location.lat(), lng: place.geometry.location.lng() },
                                icon: place.icon,
                                photo: (place.photos) ? place.photos[0].getUrl() : '',
                                vicinity: place.vicinity
                            };
                            places.push(obj);
                        }
                        calculateDistance();
                    } else {
                        alert('PlacesService Error!');
                    }
                });
            });


            site_list.addEventListener('mouseover', function(e) {
                var site = e.target.closest('.site');
                if (site) {
                    let index = site.getAttribute('data-index');
                    index = parseInt(index);

                    if (timer) {
                        clearTimeout(timer);
                        timer = null;
                    }
                    timer = setTimeout(function() { // debounce the effect
                        toggleBounce(index);
                    }, 300);
                }
            });
        }

        function calculateDistance() {
            distMatService = new google.maps.DistanceMatrixService();
            var origin = new google.maps.LatLng(center.lat, center.lng);
            var destinations = [];
            for (var i = 0; i < places.length; i++) {
                destinations.push(new google.maps.LatLng(places[i].location.lat, places[i].location.lng));
            }
            var travelMode = 'DRIVING';
            var travel = document.getElementsByName('travel');
            for (var i = 0; i < travel.length; i++) {
                if (travel[i].checked) {
                    travelMode = travel[i].value;
                }
            }
            var request = {
                origins: [origin],
                destinations: destinations,
                travelMode: travelMode
            };

            distMatService.getDistanceMatrix(request, function(res, status) {
                if (status === "OK") {
                    var results = res.rows[0].elements;
                    for (var i = 0; i < results.length; i++) {
                        places[i].distance = results[i].distance.text;
                        places[i].duration = results[i].duration.text;
                        places[i].distanceValue = results[i].distance.value;
                        places[i].durationValue = results[i].duration.value;
                    }
                    sortPlacesAndDisplay()
                } else {
                    alert('DistanceMatrixService Error!');
                }
            });
        }

        function sortPlacesAndDisplay() {
            places.sort(function(a, b) {
                return a.distanceValue - b.distanceValue; // 從小排到大
            });
            for (var i = 0; i < places.length; i++) {
                // 加入標記與位置
                addMarker(places[i]);
                addSite(places[i], i);
            }
        }

        function addMarker(place) {
            var marker = new google.maps.Marker({
                position: place.location,
                map: map,
                animation: google.maps.Animation.DROP
            });
            markers.push(marker);
        }

        function addSite(place, index) {
            var div = document.createElement('div');
            div.classList.add('site');
            div.setAttribute('data-index', index);
            div.innerHTML = `
                <div class="img" style="background: url('` + place.photo + `') center center;"></div>
                <div class="info">
                    <div class="title">` + place.name + `</div>
                    <div class="distance">` + place.distance + `</div>
                    <div class="address">` + place.vicinity + `</div>
                    <div class="time">` + place.duration + `</div>
                </div>
            `;
            site_list.append(div);
        }

        function toggleBounce(index) {
            console.log(index);
            for (var i = 0; i < markers.length; i++) {
                if (i !== index) {
                    markers[i].setAnimation(null);
                } else {
                    markers[i].setAnimation(google.maps.Animation.BOUNCE);
                }
            }

        }

        function deleteMarkersAndPlaces() {
            // clear markers
            for (var i = 0; i < markers.length; i++) {
                markers[i].setMap(null);
            }

            markers = [];
            places = [];
            site_list.innerHTML = '';
        }

        function initMap() {
            map = new google.maps.Map(document.getElementById('map'), {
                center: { lat: 24.978391, lng: 121.268641 },
                zoom: 15,
            });
        }

        window.onload = init;
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAm0PnJOlagjyo4b6waNYMXuUd5RnjDKf4&libraries=places&callback=initMap" async defer></script>
</body>

</html>