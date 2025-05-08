<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>마이페이지</title>
    <!-- CSS 파일 연결 -->
    <link rel="stylesheet" href="${root}/assets/css/mypage-form.css" />
</head>
<body>
    <section class="mypage-section">
        <div class="container">
            <div class="mypage-logo">
                <img src="${root}/assets/img/ssafy_logo.png" alt="SSAFY 로고" />
            </div>
            <h2>마이페이지</h2>

            <!-- 마이페이지 프로필 수정 폼 -->
            <form id="mypage-form" action="${root}/member/my-page" method="POST" class="mypage-form">
                
                <!-- 이름 수정 -->
                <div class="form-group">
                    <label for="name">이름 수정</label>
                    <input type="text" id="name" name="name" value="${loginUser.name}" />
                </div>

                <!-- 비밀번호 수정 -->
                <div class="form-group">
                    <label for="password">비밀번호 수정</label>
                    <input type="password" id="password" name="password" placeholder="새로운 비밀번호" />
                </div>

                <!-- 이메일 수정 -->
                <div class="form-group">
                    <label for="email">이메일 수정</label>
                    <div class="email-input">
                        <input type="text" id="email" name="email" value="${loginUser.email}" />
                        <span>@</span>
                        <select id="domainSelect" name="domainSelect">
                            <option value="">직접 입력</option>
                            <option value="gmail.com">gmail.com</option>
                            <option value="naver.com">naver.com</option>
                            <option value="daum.net">daum.net</option>
                            <option value="kakao.com">kakao.com</option>
                        </select>
                    </div>
                </div>

                <!-- 전화번호 수정 -->
                <div class="form-group">
                    <label for="pnumber">전화번호 수정</label>
                    <input type="tel" id="pnumber" name="pnumber" value="${loginUser.pnumber}" />
                </div>

                <!-- 수정 완료 버튼 -->
                <div class="form-group">
                    <button type="submit" class="btn-submit">정보 수정</button>
                </div>
            </form>

            <!-- 탈퇴 관련 폼 -->
            <div id="delete-form">
                <form action="${root}/member/delete" id="unregisterForm">
                    
                    <!-- 숨겨져 있는 비밀번호 입력란 -->
                    <div class="form-group" id="deletePasswordGroup" style="display:none;">
                        <label for="deletePassword">비밀번호</label>
                        <input type="password" id="deletePassword" name="password" placeholder="비밀번호를 입력하세요" required />
                    </div>

                    <!-- 탈퇴 버튼 (비밀번호 입력 후 표시됨) -->
                    <button type="button" class="btn-expire" onclick="showDeletePasswordForm()">탈퇴</button>
                    <button type="submit" class="btn-expire" id="deleteSubmitButton" style="display:none;">탈퇴 확인</button>
                </form>
            </div>

            <!-- 취소 버튼 (index 페이지로 돌아가기) -->
            <form action="${root}/" method="GET">
                <input type="hidden" name="action" value="index" />
                <button type="submit" class="btn-cancel">취소</button>
            </form>
        </div>
    </section>

    <!-- JavaScript 파일 연결 -->
    <script src="${root}/assets/js/mypage.js"></script>
    
    <!-- 탈퇴 비밀번호 입력 폼 보이기 -->
    <script>
        function showDeletePasswordForm() {
            document.getElementById('deletePasswordGroup').style.display = 'block'; // 비밀번호 입력 폼 보이기
            document.querySelector('.btn-expire').style.display = 'none'; // 기존 탈퇴 버튼 숨기기
            document.getElementById('deleteSubmitButton').style.display = 'block'; // 탈퇴 확인 버튼 보이기
        }
    </script>
</body>
</html>
