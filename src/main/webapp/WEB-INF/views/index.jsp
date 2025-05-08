<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath }" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>GoodTrip - 국내 여행지 찾기</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0f2f5;
            font-family: 'Apple SD Gothic Neo', sans-serif;
        }

        .main-container {
            max-width: 1100px;
            margin: 60px auto;
            background-color: #fff;
            border-radius: 16px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            overflow: hidden;
            display: flex;
        }

        .form-section {
            flex: 1;
            padding: 40px;
            
            display: flex;
		    flex-direction: column;
		    justify-content: center; /* 수직 가운데 정렬 */
        }

        .form-section h2 {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 30px;
            color: #333;
        }

        .form-select, .btn {
            border-radius: 12px;
            margin-bottom: 20px;
        }

        .btn-primary {
            width: 100%;
            padding: 12px;
            font-size: 16px;
        }

        .map-section {
            flex: 2;
		    display: flex;
		    align-items: center;
		    justify-content: center;
		    padding: 20px;
        }

        @media (max-width: 768px) {
            .main-container {
                flex-direction: column;
            }
        }
        
    </style>
</head>
<body>

<%@ include file="fragments/header.jsp" %>

<div class="main-container">
    <div class="form-section">
        <h2>나만의 여행 계획 세우기</h2>
        <p>지역을 선택해주세요</p>
        <form id="searchForm" action="${root}/att/attplan" method="post">
            <select id="areaCode" name="sido" class="form-select">
                <option value="" selected disabled>시도 선택</option>
            </select>

            <select id="sigunguCode" name="gugun" class="form-select">
                <option value="" selected disabled>시군구 선택</option>
            </select>
            
            <input type="hidden" id="att_id" name="att_id" value=0 />
            
             <button id="btn_trip_search" class="btn btn-primary" type="submit">계획 생성하기</button>
        </form>
    </div>

    <div class="map-section">
        <object id="koreaMap" type="image/svg+xml" data="${root}/img/kr.svg" class="svg-map"></object>
    </div>

    
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
    $('document').ready(function () {

    	const currentPath = window.location.pathname;

        // "attplan" 페이지가 아니면 localStorage 삭제
        if (!currentPath.includes("/attplan")) {
            localStorage.removeItem("planItems");
        }

        
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
            var sido = $sidoSelect.val();
            var gugun = $gugunSelect.val();

            if (!sido || !gugun) {
                e.preventDefault();
                alert("모두 선택해주세요!");
            }
        });
    });

    var sidoMap = {
        "KR11": "서울",
        "KR28": "인천",
        "KR41": "경기도",
        "KR42": "강원특별자치도",
        "KR43": "충청북도",
        "KR44": "충청남도",
        "KR30": "대전",
        "KR50": "세종특별자치시",
        "KR45": "전북특별자치도",
        "KR46": "전라남도",
        "KR29": "광주",
        "KR27": "대구",
        "KR47": "경상북도",
        "KR48": "경상남도",
        "KR26": "부산",
        "KR31": "울산",
        "KR49": "제주도"
    };
    
    var sidoNameToId = {};
    Object.keys(sidoMap).forEach(function(id) {
        sidoNameToId[sidoMap[id]] = id;
    });
    
    function highlightRegion(svgDoc, selectedName) {
        Object.keys(sidoMap).forEach(function(id) {
            var region = svgDoc.getElementById(id);
            if (!region) return;

            if (sidoMap[id] == selectedName) {
                region.style.filter = 'brightness(1.3)';
                region.style.strokeWidth = '5px';
            } else {
                region.style.filter = '';
                region.style.strokeWidth = '';
                region.removeAttribute('transform');
            }
        });
    }

    $('#koreaMap').on('load', function () {
        svgDoc = this.contentDocument;
        if (!svgDoc) return;

        Object.keys(sidoMap).forEach(function (id) {
            const region = svgDoc.getElementById(id);
            if (!region) return;

            region.style.cursor = 'pointer';

            region.addEventListener('mouseenter', function () {
                region.setAttribute('transform', 'scale(1.01)');
                region.style.strokeWidth = '3px';
                region.style.filter = 'brightness(1.3)';
                region.style.transition = 'transform 1s ease';
                region.parentNode.appendChild(region);
            });

            region.addEventListener('mouseleave', function () {
                const selected = $('#areaCode').val();
                if (sidoMap[id] !== selected) {
                    region.removeAttribute('transform');
                    region.style.strokeWidth = '';
                    region.style.filter = '';
                }
            });

            region.addEventListener('click', function () {
                const name = sidoMap[id];
                $('#areaCode').val(name).trigger('change');
            });
        });

        const selected = $('#areaCode').val();
        if (selected) {
            highlightRegion(svgDoc, selected);
        }
    });

    $('#areaCode').on('change', function () {
        if (!svgDoc) return;
        const selected = $(this).val();
        highlightRegion(svgDoc, selected);
    });
</script>
</body>
</html>
