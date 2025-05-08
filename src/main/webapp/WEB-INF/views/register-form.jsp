<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <title>회원가입</title>

    <!-- CSS 파일 연결 -->
    <link rel="stylesheet" href="${root}/assets/css/register-form.css" />
    
    <!-- Bootstrap 추가 (필요할 경우) -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
</head>
<body>
    <section class="signup-section">
        <div class="container">
            <div class="signup-logo">
                <img src="${root}/assets/img/ssafy_logo.png" alt="로고" />
            </div>
            <h2>회원가입</h2>

            <!-- 회원가입 폼 -->
            <form action="${root}/member/regist" method="POST">
                <!-- 이름 -->
                <div class="form-group">
                    <label for="name">이름</label>
                    <input type="text" id="name" name="name" placeholder="이름" required />
                </div>

                <!-- 이메일 -->
                <div class="form-group">
                    <label>이메일</label>
                    <div class="email-input">
                        <input type="text" id="email" name="email" placeholder="이메일" required />
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

                <!-- 아이디 -->
                <div class="form-group">
                    <label for="id">아이디</label>
                    <div class="id-input-group">
                        <input type="text" id="id" name="id" placeholder="아이디" required />
                        <button type="button" id="checkIdButton">중복확인</button>
                    </div>
                </div>

                <!-- 비밀번호 -->
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" id="password" name="password" placeholder="비밀번호" required />
                </div>

                <!-- 비밀번호 확인 -->
                <div class="form-group">
                    <label for="passwordConfirm">비밀번호 확인</label>
                    <input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="비밀번호 확인" required />
                    <span id="passwordError" style="color: red; display: none; font-size: 14px">비밀번호가 일치하지 않습니다.</span>
                </div>

                <!-- 휴대폰 번호 -->
                <div class="form-group">
                    <label for="pnumber">휴대폰번호</label>
                    <input type="tel" id="pnumber" name="pnumber" placeholder="전화번호" required />
                </div>

                <!-- 회원가입 버튼 -->
                <div class="form-group">
                    <button type="submit" class="btn-submit">회원가입</button>
                    <button type="button" class="btn-cancel" onclick="window.history.back();">취소</button>
                </div>
            </form>
        </div>
    </section>

    <!-- JavaScript 파일 연결 -->
    <script src="${root}/assets/js/signup.js"></script>

    <!-- Bootstrap JS 추가 (필요할 경우) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
