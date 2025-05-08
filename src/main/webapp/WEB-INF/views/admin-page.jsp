<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>관리자 페이지</title>
    <link rel="stylesheet" href="${root}/assets/css/admin.css" />
    <style>
        /* 전체 레이아웃 */
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }

        /* 헤더 상단 고정 */
        header {
            width: 100%;
            position: relative; /* 고정 위치 */
        }

        /* 관리자 컨테이너 중앙 정렬 */
        .admin-container {
            width: 100%;
            max-width: 2000px;
            padding: 30px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            flex-grow: 1;
            margin-top: auto;
            margin-bottom: auto;
        }

        /* 검색 영역 */
        .search-area {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 8px;
            box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
            margin-bottom: 25px;
        }

        .search-area select,
        .search-area input,
        .search-area button {
        	
            padding: 12px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 16px;
        }

        .search-area select {
            width: 18%;
        }

        .search-area input {
            width: 70%;
        }

        .search-area button {
            width: 10%;
            background-color: #3c90e2;
            color: white;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        .search-area button:hover {
            background-color: #357ABD;
        }

        /* 데이터 테이블 */
        .data-table {
            width: 100%;
            border-collapse: collapse;
            text-align: center;
            font-size: 16px;
        }

        .data-table th, .data-table td {
            border: 1px solid #ddd;
            padding: 12px;
        }

        .data-table th {
            background-color: #f4f4f4;
            font-weight: bold;
        }

        /* 수정/삭제 버튼 */
        .btn-edit, .btn-delete {
            padding: 8px 15px;
            border: none;
            cursor: pointer;
            border-radius: 3px;
            font-size: 14px;
        }

        .btn-edit {
            background-color: #4CAF50;
            color: white;
        }

        .btn-edit:hover {
            background-color: #45a049;
        }

        .btn-delete {
            background-color: #e74c3c;
            color: white;
        }

        .btn-delete:hover {
            background-color: #c0392b;
        }
    </style>
</head>
<body>
    <%@ include file="fragments/header.jsp" %>

    <div class="admin-container">
        <!-- 검색 영역 -->
        <div class="search-area">
            <select id="search-option" name="search-option">
                <option value="name">이름</option>
                <option value="email">이메일</option>
                <option value="id">아이디</option>
                <option value="pnumber">전화번호</option>
                <option value="role">권한</option>
            </select>
            <input type="text" id="search-input" placeholder="검색어 입력" />
            <button id="search-button">검색</button>
        </div>

        <!-- 회원 목록 테이블 -->
        <table class="data-table">
            <thead>
                <tr>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>아이디</th>
                    <th>비밀번호</th>
                    <th>전화번호</th>
                    <th>권한</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="member" items="${membersList}">
                    <tr>
                        <td>${member.name}</td>
                        <td>${member.email}</td>
                        <td>${member.id}</td>
                        <td>${member.password}</td>
                        <td>${member.pnumber}</td>
                        <td>${member.role}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
