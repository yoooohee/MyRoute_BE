<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SSAFY 로그인</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${root}/assets/css/login-form.css" />
</head>
<body>
    <div class="login-container">
        <div class="login-logo">
            <img src="${root}/assets/img/ssafy_logo.png" alt="SSAFY 로고" />
        </div>

        <!-- 로그인 폼 -->
        <form id="login-form" action="${root}/member/login" method="POST">
            <div class="login-area">
                <!-- 아이디 입력 -->
                <div class="input-group">
                    <label for="id">아이디</label>
                    <input type="text" id="id" name="id" placeholder="ID" value="${cookie.rememberMe.value}" required />
                </div>

                <!-- 비밀번호 입력 -->
                <div class="input-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="PASSWORD" required />
                </div>
                
                <!-- 로그인 실패 메시지 -->
		        <c:if test="${not empty errorMessage}">
		            <div class="alert alert-danger">
		                <strong>오류!</strong> ${errorMessage}
		            </div>
		        </c:if>

                <!-- 아이디 저장 -->
                <div class="id-status">
                    <label> 아이디 기억하기 
                        <input type="checkbox" name="rememberMe" ${cookie.rememberMe != null ? 'checked' : ''}> 
                    </label>
                </div>

                <!-- 로그인 버튼 -->
                <button type="submit" class="login-button">로그인</button>
            </div>
        </form>

        <!-- 비밀번호 찾기 폼 -->
        <form id="pwfind-form" action="${root}/member/find-password" method="POST" style="display:none;">
            <div class="input-group">
                <label for="find-id">이름</label>
                <input type="text" id="name" name="name" placeholder="아이디 입력" required />
                <label for="find-id">아이디</label>
                <input type="text" id="id" name="id" placeholder="아이디 입력" required />
                <label for="find-id">이메일</label>
                <input type="text" id="email" name="email" placeholder="아이디 입력" required />
            </div>
            <button type="submit" class="pwfind-submit-button">비밀번호 찾기</button>
        </form>

        <!-- 비밀번호 찾기 버튼 -->
        <button type="button" class="pwfind-button" id="pwfind-button" onclick="showPwFindForm()" style="display:block;">비밀번호 찾기</button>

        <!-- 로그인 실패 메시지 -->
        <p id="message">${loginError}</p>
    </div>

    <!-- JavaScript 파일 연결 -->
    <script src="${root}/assets/js/login.js"></script>

    <script>
        // 비밀번호 찾기 폼 보이기
        function showPwFindForm() {
            document.getElementById('pwfind-form').style.display = 'block';
            document.getElementById('pwfind-button').style.display = 'none';
        }
        
     	// 페이지가 로드되면 비밀번호가 있을 경우 alert로 표시
        window.onload = function() {
            // request에서 비밀번호 값을 가져오기
            var password = "${password}";
            if (password) {
                alert("찾아진 비밀번호: " + password);
            }
        }
    </script>
</body>
</html>
