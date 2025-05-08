<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath }" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    @font-face {
    font-family: 'GowunDodum-Regular';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2108@1.1/GowunDodum-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
	h1 {
	font-family: 'GowunDodum-Regular';
	margin-left:20px;
	padding-top:20px;
	padding-bottom:10px;
	}
	
    .search-part {
        display: flex;
        gap: 10px;
        padding: 10px;
        background: #f8f9fa;
        border-radius: 8px;
        margin-bottom: 20px;
    }
    
    select, button {
        padding: 8px;
        font-size: 16px;
        border: 1px solid #ddd;
        border-radius: 4px;
    }

    button {
        background-color: #007bff;
        color: white;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }

    .result-table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        height: 600px; 
        overflow-y: scroll; /* 내용이 많을 경우 스크롤 가능 */
        display: block;
    }

    .result-table th, .result-table td {
        border: 1px solid #ddd;
        padding: 10px;
        text-align: left;
        height: 50px;  /* 각 tr의 높이 설정 */
        vertical-align: middle;  /* 내용이 중앙 정렬 */
    }

    .result-table th {
        background-color: #007bff;
        color: white;
    }

    .result-table tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .result-table td {
        cursor: pointer; /* 관광지명을 클릭할 수 있도록 커서 모양 변경 */
    }
</style>
</head>
<body>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<%@ include file="../fragments/header.jsp" %>

    <h1>지역, 타입별 명소 조회</h1>
    <script>
    $('document').ready(function() {
    	var areaData = {
    			   "서울" : ["강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구","동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구","용산구","은평구","종로구","중구","중랑구"],
    			   "인천" : ["계양구","남구","남동구","동구","부평구","서구","연수구","중구","강화군","옹진군"],
    			   "대전" : ["대덕구","동구","서구","유성구","중구"],
    			   "대구" : ["남구","달서구","동구","북구","서구","수성구","중구","달성군"],
    			   "광주" : ["광산구","남구","동구","북구","서구"],
    			   "부산" : ["강서구","금정구","남구","동구","동래구","부산진구","북구","사상구","사하구","서구","수영구","연제구","영도구","중구","해운대구","기장군"],
    			   "울산" : ["남구","동구","북구","중구","울주군"],
    			   "세종특별자치시" : [ "세종특별자치시" ],
    			   "경기도" : ["고양시","과천시","광명시","광주시","구리시","군포시","김포시","남양주시","동두천시","부천시","성남시","수원시","시흥시","안산시","안성시","안양시","양주시","오산시","용인시","의왕시","의정부시","이천시","파주시","평택시","포천시","하남시","화성시","가평군","양평군","여주군","연천군"],
    			   "강원특별자치도" : ["강릉시","동해시","삼척시","속초시","원주시","춘천시","태백시","고성군","양구군","양양군","영월군","인제군","정선군","철원군","평창군","홍천군","화천군","횡성군"],
    			   "충청북도" : ["제천시","청주시","충주시","괴산군","단양군","보은군","영동군","옥천군","음성군","증평군","진천군","청원군"],
    			   "충청남도" : ["계룡시","공주시","논산시","보령시","서산시","아산시","천안시","금산군","당진군","부여군","서천군","연기군","예산군","청양군","태안군","홍성군"],
    			   "경상북도" : ["경산시","경주시","구미시","김천시","문경시","상주시","안동시","영주시","영천시","포항시","고령군","군위군","봉화군","성주군","영덕군","영양군","예천군","울릉군","울진군","의성군","청도군","청송군","칠곡군"],
    			   "경상남도" : ["거제시","김해시","마산시","밀양시","사천시","양산시","진주시","진해시","창원시","통영시","거창군","고성군","남해군","산청군","의령군","창녕군","하동군","함안군","함양군","합천군"],
    			   "전북특별자치도" : ["군산시","김제시","남원시","익산시","전주시","정읍시","고창군","무주군","부안군","순창군","완주군","임실군","장수군","진안군"],
    			   "전라남도" : ["광양시","나주시","목포시","순천시","여수시","강진군","고흥군","곡성군","구례군","담양군","무안군","보성군","신안군","영광군","영암군","완도군","장성군","장흥군","진도군","함평군","해남군","화순군"],
    			   "제주도" : ["서귀포시","제주시","남제주군","북제주군"]
    			
    	}
	 
    	var $sidoSelect = $("#areaCode");
        var $gugunSelect = $("#sigunguCode");
	
		$.each(areaData, function (sido, guguns) {
            $sidoSelect.append("<option value='" + sido + "'>" + sido + "</option>");
        });
	 
	
   	$sidoSelect.change(function () {
           var selectedSido = $(this).val();
           $gugunSelect.empty().append("<option value='' selected disabled>시군구 선택</option>");

           if (selectedSido && areaData[selectedSido]) {
               $.each(areaData[selectedSido], function (index, gugun) {
                   $gugunSelect.append("<option value='" + gugun + "'>" + gugun + "</option>");
               });
           }
       });
	 
	
    $("#btn_trip_search").click(function (e) {
        e.preventDefault(); // 기본 폼 제출 방지

        var sido = $sidoSelect.val();
        var gugun = $gugunSelect.val();
        var contentType = $("#contentType").val();

        if (!sido || !gugun ||!contentType) {
            alert("모두 선택해주세요!");
            return;
        }
        
        $("#searchForm").submit();
    });

});
    </script>
    
    <form class="search-part" id="searchForm" action="search" method="post">
            <select id="areaCode" name = "sido">
                <option value="" selected disabled>시도 선택</option>
            </select>
            <select id="sigunguCode" name = "gugun">
                <option value="" selected disabled>시군구 선택</option>
            </select>
            <select id="contentType" name = "contentType">
                <option value="" selected disabled>관광타입 선택</option>
                <option value="12">관광지</option>
                <option value="14">문화시설</option>
                <option value="15">축제공연행사</option>
                <option value="25">여행코스</option>
                <option value="28">레포츠</option>
                <option value="32">숙박</option>
                <option value="38">쇼핑</option>
                <option value="39">음식점</option>
            </select>
            <button id="btn_trip_search">관광지 조회</button>
        <hr />
    </form>
                    
   <script type="text/javascript" src="https://sgisapi.kostat.go.kr/OpenAPI3/auth/javascriptAuth?consumer_key=08e09bfd90c6443abd27"></script>

<div id="map" style="width:calc(100% - 20px); height:480px"></div>

<script type="text/javascript">
    var map = sop.map('map');
    map.setView(sop.utmk(953820, 1953437), 9);

    // marker 목록
    let markers = [];
    // 경계 목록
    const bounds = [];

    // 마커와 경계 초기화
    const resetMarker = () => {
        markers.forEach((item) => item.remove());
        bounds.length = 0;
    };
    
    function updateMapToMarker(index) {
        var marker = markers[index];
        if (marker) {
            // 마커로 지도 업데이트
            map.setView(marker.getLatLng(), 9);  // 해당 마커 위치로 지도 이동
            marker.openPopup();  // 마커의 팝업 열기
        }
    }

    <c:if test="${not empty atts}">
        resetMarker(); // 맵 초기화
        try {

            var latitudes = 0;
            var longitudes = 0;

            <c:forEach var="att" items="${atts}">
                // JavaScript 내부에서 변수로 값을 전달
                var title = "${att.title}";
                var firstImage = "${att.first_image1}";
                var contentTypeId = "${att.content_type_id}";
                var latitude = "${att.latitude}";
                var longitude = "${att.longitude}";

                var html = '';
                html += `<div style='font-family: dotum, arial, sans-serif; font-size: 15px; font-weight: bold; margin-bottom: 5px;'>${att.title}</div>`;

                // 이미지 처리
                if (firstImage === '') {
                    html += `<img src='/resource/defaultImg.jpg' width="100" height="100"><br>`;

                }
                else html += `<img src="${att.first_image1}" width="100" height="100"><br>`;

                // 아이콘 설정
                var Icon;
                if (contentTypeId === '12') {
                    Icon = sop.icon({
                        iconUrl: '/resource/trip12.png',
                        iconSize: [40, 40],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '14') {
                    Icon = sop.icon({
                        iconUrl: '/resource/culture14.png',
                        iconSize: [40, 40],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '15') {
                    Icon = sop.icon({
                        iconUrl: '/resource/festival15.png',
                        iconSize: [40, 40],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '25') {
                    Icon = sop.icon({
                        iconUrl: '/resource/trip25.png',
                        iconSize: [30, 30],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '28') {
                    Icon = sop.icon({
                        iconUrl: '/resource/leisure28.png',
                        iconSize: [40, 40],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '32') {
                    Icon = sop.icon({
                        iconUrl: '/resource/hotel32.png',
                        iconSize: [40, 40],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '38') {
                    Icon = sop.icon({
                        iconUrl: '/resource/shopping38.png',
                        iconSize: [30, 30],
                        iconAnchor: [22, 0],
                    });
                } else if (contentTypeId === '39') {
                    Icon = sop.icon({
                        iconUrl: '/resource/restraunt39.png',
                        iconSize: [30, 30],
                        iconAnchor: [22, 0],
                    });
                } else {
                    Icon = sop.icon({
                        iconUrl: '/resource/marker.png',
                        iconSize: [30, 30],
                        iconAnchor: [22, 0],
                    });
                }

                var utmkXY = new sop.LatLng(latitude, longitude); // 유효한 좌표로 변경
                var marker = sop.marker([utmkXY.x, utmkXY.y], { icon: Icon });
                marker.addTo(map).bindInfoWindow(html);

                // 마커와 경계 추가
                markers.push(marker);

                if (latitudes == 0 && longitudes == 0) { 
                    latitudes = utmkXY.x;
                    longitudes = utmkXY.y;
                } else {
                    if (utmkXY.x > latitudes * 1.1 || utmkXY.x < latitudes * 0.9 || 
                    	utmkXY.y > longitudes * 1.1 || utmkXY.y < longitudes * 0.9) {
                        console.log("Outlier removed:", utmkXY.x, utmkXY.y);
                    }
                    else {
                        bounds.push([utmkXY.x, utmkXY.y]);
                        latitudes = utmkXY.x;
                        longitudes = utmkXY.y;
                    }
                }
            </c:forEach>
            
            $(document).ready(function() {
                $(".table-row").click(function() {
                    var index = $(this).data("index");  // 테이블의 data-index를 가져옵니다.
                    
                    // 해당 인덱스의 마커를 찾아서 지도 위치를 이동
                    var marker = markers[index];
                    if (marker) {
                        map.setView(marker.getLatLng(), 9);  // 해당 마커 위치로 지도 이동
                        marker.openPopup();  // 마커 팝업 열기
                    }
                });
            });

            // 경계를 기준으로 map을 중앙에 위치하도록 함
            if (bounds.length > 1) {
            	console.log(bounds);
            	map.setView(map._getBoundsCenterZoom(bounds).center, map._getBoundsCenterZoom(bounds).zoom);
            } else if (bounds.length === 1) {
                map.setView(bounds[0], 9);  // 하나의 마커만 있을 경우 줌 레벨을 9로 설정
            } else {
                console.log('No markers to display');
            }
        } catch (e) {
            console.log('Error in updating map: ', e);
        }
    </c:if>
    
    <c:if test="${not empty parkings}">
    
    <c:set var="latValue" value="${empty lat ? '0' : lat}" />
    <c:set var="lngValue" value="${empty lng ? '0' : lng}" />
        
    const lat = parseFloat('${latValue}');
    const lng = parseFloat('${lngValue}');
    const firstImage = "${first_image1}";
    const contentTypeId = "${id}";
    
    resetMarker(); // 맵 초기화
    try {

        var latitudes = 0;
        var longitudes = 0;

        <c:forEach var="parking" items="${parkings}">
            // JavaScript 내부에서 변수로 값을 전달
            var prkplceNm = "${parking.prkplceNm}";
            var latitude = "${parking.latitude}";
            var longitude = "${parking.longitude}";

            var html = '';
            html += `<div style='font-family: dotum, arial, sans-serif; font-size: 15px; font-weight: bold; margin-bottom: 5px;'>${parking.prkplceNm}</div>`;

            // 아이콘 설정
            var Icon;
            Icon = sop.icon({
                iconUrl: '/resource/parking.png',
                iconSize: [30, 30],
                iconAnchor: [22, 0],
            });

            var utmkXY = new sop.LatLng(latitude, longitude); // 유효한 좌표로 변경
            var marker = sop.marker([utmkXY.x, utmkXY.y], { icon: Icon });
            marker.addTo(map).bindInfoWindow(html);

            // 마커와 경계 추가
            markers.push(marker);

            if (latitudes == 0 && longitudes == 0) { 
                latitudes = utmkXY.x;
                longitudes = utmkXY.y;
            } else {
                if (utmkXY.x > latitudes * 1.1 || utmkXY.x < latitudes * 0.9 || 
                	utmkXY.y > longitudes * 1.1 || utmkXY.y < longitudes * 0.9) {
                    console.log("Outlier removed:", utmkXY.x, utmkXY.y);
                }
                else {
                    bounds.push([utmkXY.x, utmkXY.y]);
                    latitudes = utmkXY.x;
                    longitudes = utmkXY.y;
                }
            }
        </c:forEach>
        
        var html = '';
        html += `<div style='font-family: dotum, arial, sans-serif; font-size: 15px; font-weight: bold; margin-bottom: 5px;'>${name}</div>`;

        // 이미지 처리
        if (firstImage === '') {
            html += `<img src='/resource/defaultImg.jpg' width="100" height="100"><br>`;

        }
        else html += `<img src="${att.first_image1}" width="100" height="100"><br>`;

        // 아이콘 설정
        var Icon;
        if (contentTypeId === '12') {
            Icon = sop.icon({
                iconUrl: '/resource/trip12.png',
                iconSize: [40, 40],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '14') {
            Icon = sop.icon({
                iconUrl: '/resource/culture14.png',
                iconSize: [40, 40],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '15') {
            Icon = sop.icon({
                iconUrl: '/resource/festival15.png',
                iconSize: [40, 40],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '25') {
            Icon = sop.icon({
                iconUrl: '/resource/trip25.png',
                iconSize: [30, 30],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '28') {
            Icon = sop.icon({
                iconUrl: '/resource/leisure28.png',
                iconSize: [40, 40],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '32') {
            Icon = sop.icon({
                iconUrl: '/resource/hotel32.png',
                iconSize: [40, 40],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '38') {
            Icon = sop.icon({
                iconUrl: '/resource/shopping38.png',
                iconSize: [30, 30],
                iconAnchor: [22, 0],
            });
        } else if (contentTypeId === '39') {
            Icon = sop.icon({
                iconUrl: '/resource/restraunt39.png',
                iconSize: [30, 30],
                iconAnchor: [22, 0],
            });
        } else {
            Icon = sop.icon({
                iconUrl: '/resource/marker.png',
                iconSize: [30, 30],
                iconAnchor: [22, 0],
            });
        }

        var utmkXY = new sop.LatLng(lat, lng); // 유효한 좌표로 변경
        var marker = sop.marker([utmkXY.x, utmkXY.y], { icon: Icon });
        marker.addTo(map).bindInfoWindow(html);

        // 마커와 경계 추가
        markers.push(marker);

        
                
        $(document).ready(function() {
            $(".table-row").click(function() {
                var index = $(this).data("index");  // 테이블의 data-index를 가져옵니다.
                
                // 해당 인덱스의 마커를 찾아서 지도 위치를 이동
                var marker = markers[index];
                if (marker) {
                    map.setView(marker.getLatLng(), 9);  // 해당 마커 위치로 지도 이동
                    marker.openPopup();  // 마커 팝업 열기
                }
            });
        });

        // 경계를 기준으로 map을 중앙에 위치하도록 함
        if (bounds.length > 1) {
        	console.log(bounds);
        	map.setView(map._getBoundsCenterZoom(bounds).center, map._getBoundsCenterZoom(bounds).zoom);
        } else if (bounds.length === 1) {
            map.setView(bounds[0], 9);  // 하나의 마커만 있을 경우 줌 레벨을 9로 설정
        } else {
            console.log('No markers to display');
        }
    } catch (e) {
        console.log('Error in updating map: ', e);
    }
</c:if>

</script>
    
    <c:if test="${!empty error }">
        <div class="alert alert-danger" role="alert">${error }</div>
    </c:if>
    
    <c:if test="${not empty atts }">
     <table class="result-table">
            <thead>
                <tr>
                    <th>관광지명</th>
                    <th>분류</th>
                    <th>주소</th>
                    <th>설명</th>
                    <th>주차장</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="att" items="${atts}">
                    <tr data-index="${status.index}" class="table-row">
		                <td>${att.title}</td>
		                <td>${att.content_type_name}</td>
		                <td>${att.addr1}</td>
		                <td>${att.overview}</td>
		                <td>
		                <form action="search-parking" method="post">
		                    <input type="hidden" name="lat" value="${att.latitude}" />
		                    <input type="hidden" name="lng" value="${att.longitude}" />
		                    <input type="hidden" name="name" value="${att.title}" />
		                    <input type="hidden" name="id" value="${att.content_type_id}" />
		                    <input type="hidden" name="image" value="${att.first_image1}" />
		                    <button type="submit">주차장조회</button>
		                </form>
		                </td>         
		            </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${not empty parkings}">
		    <h3>반경 1km 이내 주차장 (최대 10개)</h3>
		    
		    <a href="javascript:history.back()">목록으로 돌아가기</a>
		    <table class="result-table">
		        <thead>
		            <tr>
		                <th>주차장명</th>
		                <th>주소</th>
		                <th>거리 (km)</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach var="p" items="${parkings}">
		                <tr>
		                    <td>${p.prkplceNm}</td>
		                    <td>${p.rdnmadr}</td>
		                    <td>${p.distance}</td>
		                </tr>
		            </c:forEach>
		        </tbody>
		    </table>
		</c:if>
</body>
</html>
