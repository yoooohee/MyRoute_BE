<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.servletContext.contextPath }" />

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
<link rel="stylesheet" href="${root}/assets/css/header.css" />

<header class="navbar navbar-expand-lg navbar-light bg-white shadow-sm border-bottom">
    <div class="container-fluid">
        <!-- 로고 -->
        <a class="navbar-brand" href="${root}/">
            <img src="${root}/assets/img/logo.png" alt="로고"/>
        </a>

        <!-- 모바일 메뉴 버튼 -->
        <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
        >
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- 네비게이션 메뉴 -->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <!-- 전체명소조회 드롭다운 메뉴 -->
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle text-dark" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        전체명소조회
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                    	<li><a class="dropdown-item" href="${root}/att/list">전체명소조회</a></li>
                        <li><a class="dropdown-item" href="#">관광/숙박/음식점 조회</a></li>
                        <li><a class="dropdown-item" href="#">문화시설/공연/여행코스/쇼핑 조회</a></li>
                    </ul>
                </li>

                <li class="nav-item"><a class="nav-link text-dark" href="#section2">나의여행계획</a></li>
                <li class="nav-item"><a class="nav-link text-dark" href="#section3">핫플자랑하기</a></li>
                <li class="nav-item"><a class="nav-link text-dark" href="#section4">지역별관광지조회</a></li>

                <!-- 로그인 관련 메뉴 -->
                <c:choose>
                    <c:when test="${empty loginUser }">
                        <li id="nav-signup" class="nav-item">
                            <a class="nav-link text-dark" href="${root }/member/regist">회원가입</a>
                        </li>
                        <li id="nav-login" class="nav-item">
                            <a class="nav-link text-dark" href="${root }/member/login">로그인</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li id="nav-mypage" class="nav-item">
                            <a class="nav-link text-dark" href="${root }/member/my-page">마이페이지</a>
                        </li>
                        <c:if test="${loginUser.role.equalsIgnoreCase('admin')}">
                            <li id="nav-name" class="nav-item">
                            	<a class="nav-link text-dark" href="${root}/member/admin-page">관리자페이지</a>
                        	</li>
                        </c:if>
                        <li id="nav-name" class="nav-item">
                            <a class="nav-link text-dark" href="${root}/member/logout">${loginUser.name }님, 안녕하세요.</a>
                        </li>
                        <li id="nav-logout" class="nav-item">
                            <a class="nav-link text-dark" href="${root}/member/logout">로그아웃</a>
                        </li>
                        <c:if test="${loginUser.role eq 'admin'}">
                            <li id="nav-admin" class="nav-item">
                                <a class="nav-link text-dark" href="${root}/member/admin-page">관리자</a>
                            </li>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</header>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
