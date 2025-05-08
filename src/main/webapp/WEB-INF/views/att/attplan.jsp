<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GoodTrip - 여행지 추천</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
    @font-face {
    font-family: 'GowunDodum-Regular';
    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_2108@1.1/GowunDodum-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

        body {
            background-color: #f8f9fa;
            font-family: 'Apple SD Gothic Neo', sans-serif;
        }

        .result-container {
            max-width: 1200px;
            margin: 50px auto;
            margin-top:10px;
            padding: 20px;
        }

        .map-box {
            background-color: #fff;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
            padding: 20px;
            margin-bottom: 40px;
        }

        .map-box h4 {
            margin-bottom: 20px;
            font-weight: 700;
        }

        .map-image {
            width: 100%;
            border-radius: 12px;
            max-height: 400px;
            object-fit: cover;
        }

        .place-card {
            border-radius: 16px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.05);
            overflow: hidden;
            transition: transform 0.2s ease;
            background-color: #fff;
        }

        .place-card:hover {
            transform: translateY(-5px);
        }

        .place-card img {
            height: 180px;
            object-fit: cover;
            width: 100%;
        }

        .place-info {
            padding: 15px;
        }

        .place-info h5 {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 10px;
        }

        .place-info p {
            font-size: 14px;
            color: #555;
        }
        
        h1 {
        	padding-top:5%;
        	text-align:center;
        	font-family:'GowunDodum-Regular';
        }
        
        .place-list-scroll {
		    max-height: 600px; /* 원하는 높이로 조절 */
		    overflow-y: auto;
		    padding-right: 10px; /* 스크롤바 안 가리도록 여백 */
		}

    </style>
</head>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>



<body>

<%@ include file="../fragments/header.jsp" %>

<h1>나만의 계획 세우기</h1>
<div class="result-container">
	<form id="searchForm" action="${root}/att/attplan" method="post">
            <input type="hidden" id="areaCode" name="sido" value="${param.sido}" />
            <input type="hidden" id="select-att" name="att_id" value="${selectedAttId}" />
			<div class="d-flex justify-content-between align-items-center mb-4" style="gap: 10px;">
			    <select id="sigunguCode" name="gugun" class="form-select" style="flex: 1; max-width: 300px;">
			        <option value="" selected disabled>군구 선택</option>
			    </select>
			    <button id="btn_trip_search" class="btn btn-primary">조회 하기</button>
			</div>
    </form>
        
    <!-- 지역 통계 지도 -->
    <div class="map-box">
        <h4>📊 ${param.sido} ${param.gugun}</h4>
		<div id="map" style="width:calc(100% - 20px); height:480px"></div>
		<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=dc1114296c501aaa901df7d025c27fba"></script>

    </div>

    <div class="row">
	    <!-- 왼쪽: 추천 관광지 목록 -->
		<div class="col-md-8">
		    <h4 class="mb-4">🌟 추천 관광지 목록</h4>
		    
		    <form id="attForm" action="${root}/att/attplan" method="post">
				<div class="d-flex justify-content-between align-items-center mb-4" style="gap: 10px;">
				<input type="hidden" id="areaCode" name="sido" value="${param.sido}" />
				<input type="hidden" id="sigunguCode" name="gugun" value="${param.gugun}" />
				    <select id="select-att" name="att_id" class="form-select" style="flex: 1; max-width: 300px;" onchange="document.getElementById('attForm').submit();">
					    <option value="0" ${selectedAttId == 0 ? 'selected' : ''}>놀거리</option>
					    <option value="39" ${selectedAttId == 39 ? 'selected' : ''}>음식점</option>
					    <option value="32" ${selectedAttId == 32 ? 'selected' : ''}>숙박</option>
					</select>
				</div>
	    	</form>
	
		    <div class="place-list-scroll">
		        <div class="row g-4">
		            <c:forEach var="place" items="${atts}">
		                <div class="col-md-6">
		                    <div class="place-card">
		                        <img src="${not empty place.first_image1 ? place.first_image1 : '/resource/tripimage.png'}" alt="${place.title}">
		                        <div class="place-info">
		                            <h5>${place.title}</h5>
		                            <p>${place.content_type_name}</p>
		                            <p>${place.addr1}</p>
		                            <c:set var="imageUrl" value="${not empty place.first_image1 ? place.first_image1 : '/resource/tripimage.png'}" />
		                       		<button type="button" class="btn btn-sm btn-outline-success add-to-plan"
									    data-no="${place.no}" 
									    data-title="${place.title}" 
									    data-addr="${place.addr1}" 
									    data-image="${imageUrl}"
									    data-lat="${place.latitude}" 
									    data-lng="${place.longitude}">
									    ➕ 추가
									</button>
		                        </div>
		                    </div>
		                </div>
		            </c:forEach>
		        </div>
		    </div>
		</div>
	
	    <!-- 오른쪽: 내가 추가한 계획 목록 -->
	    <div class="col-md-4 place-list-scroll">
	        <h4 class="mb-4">🗓️ 나의 계획</h4>
	        <div id="planList" class="card shadow-sm" style="padding: 15px; min-height: 300px;">
	            <p class="text-muted" id="emptyPlan">아직 추가된 관광지가 없습니다.</p>
	        </div>
	        <div class="d-grid mt-3">
		        <button id="createPlanBtn" class="btn btn-success">📝 계획 생성하기</button>
		    </div>
	    </div>
	</div>
</div>

<script>
		var container = document.getElementById('map');
		var options = {
			center: new kakao.maps.LatLng(33.450701, 126.570667),
			level: 3
		};

		var map = new kakao.maps.Map(container, options);
</script>

<script>
$('document').ready(function () {
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
    };

    var $sido = $("#areaCode").val();
    var $gugunSelect = $("#sigunguCode");
    

    if ($sido && areaData[$sido]) {
        $gugunSelect.empty().append("<option value='' selected disabled>시군구 선택</option>");
        $.each(areaData[$sido], function (index, gugun) {
            $gugunSelect.append("<option value='" + gugun + "'>" + gugun + "</option>");
        });
    }

    $("#btn_trip_search").click(function (e) {
        var sido = $("#areaCode").val();
        var gugun = $gugunSelect.val();

        if (!sido || !gugun) {
            e.preventDefault();
            alert("모두 선택해주세요!");
        }
    });
});
</script>

<script>
$(document).ready(function () {

    let markers = [];
    let bounds = new kakao.maps.LatLngBounds();

    renderPlanList();
    restoreMarkers();

    $(".add-to-plan").click(function () {
        const title = $(this).data("title");
        const addr = $(this).data("addr");
        const image = $(this).data("image");
        const latitude = $(this).data("lat");
        const longitude = $(this).data("lng");
        const no = $(this).data("no");

        let items = JSON.parse(localStorage.getItem("planItems") || "[]");
        
        // 중복 방지
        if (!items.some(item => item.no === no)) {
            items.push({ title, addr, image, latitude, longitude, no });
            localStorage.setItem("planItems", JSON.stringify(items));
            renderPlanList();
            addMarker({ latitude, longitude, title, image, no }); // 수정된 부분
        }
    });

    // 삭제 버튼
    $("#planList").on("click", ".remove-btn", function () {
        const no = $(this).data("no");
        let items = JSON.parse(localStorage.getItem("planItems") || "[]");
        const removedItem = items.find(item => item.no === no);
        removeMarker(removedItem.no);
        items = items.filter(item => item.no !== no);
        localStorage.setItem("planItems", JSON.stringify(items)); 
        renderPlanList();
    });
    
    // 계획 생성 버튼
    $("#createPlanBtn").click(function () {
        const plans = JSON.parse(localStorage.getItem("planItems") || "[]");

        if (plans.length === 0) {
            alert("계획에 담긴 관광지가 없습니다!");
            return;
        }

        $.ajax({
            url: "${root}/att/savePlan",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(plans),
            success: function(response) {
                alert("📝 계획이 성공적으로 저장되었습니다!");
                localStorage.removeItem("planItems");
                renderPlanList();
                clearAllMarkers(); // 저장 후 마커 초기화
            },
            error: function(err) {
                console.error(err);
                alert("저장 중 오류가 발생했습니다.");
            }
        });
    });
    
    function renderPlanList() {
        const planList = JSON.parse(localStorage.getItem("planItems") || "[]");
        const container = $("#planList");
        container.empty();

        if (planList.length === 0) {
            container.html('<p class="text-muted" id="emptyPlan">추가된 관광지가 없습니다.</p>');
            return;
        }

        planList.forEach((item) => {
            const html = `
            	<div class="card mb-2 plan-item" data-no="${'${item.no}'}">
                    <div class="card-body p-2 d-flex align-items-center">
                        <img src="${'${item.image}'}" style="width: 50px; height: 50px; object-fit: cover; border-radius: 8px; margin-right: 10px;">
                        <div>
                            <strong>${'${item.title}'}</strong><br>
                            <small>${'${item.addr}'}</small>
                        </div>
                        <button class="btn btn-sm btn-outline-danger ms-auto remove-btn" data-no="${'${item.no}'}">✖</button>
                    </div>
                </div>
            `;
            container.append(html);
        });
        
        container.sortable({
            update: function () {
                // 순서 바뀐 DOM 기준으로 localStorage 다시 저장
                const newOrder = [];
                $(".plan-item").each(function () {
                    const no = $(this).data("no");
                    const item = planList.find(i => i.no === no);
                    if (item) newOrder.push(item);
                });
                localStorage.setItem("planItems", JSON.stringify(newOrder));
            }
        });
    }
    
    // 수정된 restoreMarkers() 함수
    function restoreMarkers() {
        const saved = JSON.parse(localStorage.getItem("planItems") || "[]");
        saved.forEach(item => addMarker(item)); // 수정된 부분: item 객체를 그대로 넘김
    }
    
    // 마커 추가 함수
    let currentInfowindow = null;
    function addMarker(item) {
        const position = new kakao.maps.LatLng(item.latitude, item.longitude);
        const marker = new kakao.maps.Marker({
            map: map,
            position: position,
            image: new kakao.maps.MarkerImage('/resource/marker.png', new kakao.maps.Size(30, 30), {
                offset: new kakao.maps.Point(22, 0)
            })
        });

        const infowindow = new kakao.maps.InfoWindow({
            content: `
                <div style="display:inline-block; max-width:150px; white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
                    <strong>${'${item.title}'}</strong><br>
                    <img src="${'${item.image}'}" width="100" height="70"><br>
                </div>
            `
        });

        kakao.maps.event.addListener(marker, 'click', function () {
            if (currentInfowindow) {
            currentInfowindow.close();

            // 현재 마커의 infowindow라면 닫고 종료
            if (currentInfowindow === infowindow) {
                currentInfowindow = null;
                return;
            }
        }

            infowindow.open(map, marker);
            currentInfowindow = infowindow;
        });

        marker.customNo = item.no; // no 값을 마커에 저장
        markers.push(marker);
        bounds.extend(position);
        map.setBounds(bounds);
    }


    // 마커 제거 함수
    function removeMarker(no) {
        for (let i = 0; i < markers.length; i++) {
            if (markers[i].customNo === no) {
                markers[i].setMap(null);
                markers.splice(i, 1);
                break;
            }
        }

        // 경계 재설정
        bounds = new kakao.maps.LatLngBounds();
        markers.forEach(m => bounds.extend(m.getPosition()));
        if (markers.length > 0) {
            map.setBounds(bounds);
        }
    }

    // 모든 마커 삭제
    function clearAllMarkers() {
        markers.forEach(marker => marker.setMap(null));
        markers = [];
        bounds = new kakao.maps.LatLngBounds();
    }

});
</script>


<script>
let markers = [];
let bounds = new kakao.maps.LatLngBounds();

const resetMarker = () => {
    markers.forEach((marker) => marker.setMap(null));
    markers = [];
    bounds = new kakao.maps.LatLngBounds();
};

function updateMapToMarker(index) {
    var marker = markers[index];
    if (marker) {
        map.setCenter(marker.getPosition());
        map.setLevel(9);
        new kakao.maps.InfoWindow({
            content: marker.content
        }).open(map, marker);
    }
}

<c:if test="${not empty atts}">
resetMarker();
let latitudes = 0;
let longitudes = 0;
try {
    <c:forEach var="att" items="${atts}">
        (function() {
            var title = "${att.title}";
            var firstImage = "${att.first_image1}";
            var latitude = parseFloat("${att.latitude}");
            var longitude = parseFloat("${att.longitude}");

            var html = `<div style='font-family: dotum, arial, sans-serif; font-size: 15px; font-weight: bold; margin-bottom: 5px;'>${title}</div>`;
            html += (!firstImage || firstImage === '') 
                ? `<img src='/resource/defaultImg.jpg' width="100" height="100"><br>`
                : `<img src="${firstImage}" width="100" height="100"><br>`;

            var position = new kakao.maps.LatLng(latitude, longitude);

            var marker = new kakao.maps.Marker({
                map: map,
                position: position,
                image: new kakao.maps.MarkerImage('/resource/marker.png', new kakao.maps.Size(30, 30), {
                    offset: new kakao.maps.Point(22, 0)
                })
            });

            marker.content = html;
            marker.infowindow = new kakao.maps.InfoWindow({ content: html });

            kakao.maps.event.addListener(marker, 'click', function() {
                marker.infowindow.open(map, marker);
            });

            markers.push(marker);
            marker.setVisible(false);

         // 아웃라이어 필터링
            if (latitudes === 0 && longitudes === 0) {
                latitudes = latitude;
                longitudes = longitude;
                bounds.extend(position);
            } else {
                if (
                    latitude > latitudes * 1.05 || latitude < latitudes * 0.95 ||
                    longitude > longitudes * 1.05 || longitude < longitudes * 0.95
                ) {
                    console.log("Outlier removed:", latitude, longitude);
                } else {
                    bounds.extend(position);
                    latitudes = latitude;
                    longitudes = longitude;
                }
            }
        })();
    </c:forEach>

    if (markers.length > 1) {
        map.setBounds(bounds);
    } else if (markers.length === 1) {
    	markers[0].setVisible(true);
        map.setCenter(markers[0].getPosition());
        map.setLevel(9);
    } else {
        console.log('No markers to display');
    }
} catch (e) {
    console.log('Error in updating map: ', e);
}
</c:if>
</script>


</body>
</html>
