# ENJOY_TRIP - 공공데이터 기반 지역 관광 정보 플랫폼

## 📌 프로젝트 소개

**ENJOY_TRIP**은 공공데이터(Open API)를 활용하여 국내 지역별 관광지, 맛집, 문화시설 등의 정보를 제공하고, 여행 계획 기능을 통해 사용자 맞춤형 여행 경로를 설계할 수 있는 웹 플랫폼입니다.

> 본 프로젝트는 Spring Framework와 MyBatis 기반으로 구성되어 있으며, 한국관광공사/환경공단/SGIS 등의 API를 연동하여 다양한 정보를 시각화합니다.

---

## ⚙️ 주요 기능

### ✅ 기본 기능
- **지역별 관광지 정보 제공**
  - 관광지, 숙박, 음식점, 문화시설, 공연, 여행코스, 쇼핑 정보 제공
- **회원 관리**
  - 회원가입, 정보 조회/수정/삭제 (탈퇴)
- **로그인 / 로그아웃 / 비밀번호 찾기**
- **관광지 검색**
  - 지역별 및 컨텐츠별 필터링
- **관광지 상세 정보**
  - 관광지 사진


### ➕ 추가 기능
- **관광지 주변 주차장 정보**
  - 위치 및 거리 표시
- **여행 계획 기능**
  - 경로 생성, 일정 등록, 동선 조정 (Drag & Drop 지원)
  - 경로 저장 및 공유
- **핫플레이스 등록**
  - 지도와 사진을 활용한 사용자 핫플 등록 기능

### 🔍 심화 기능
- **공유 게시판**
  - 게시글 등록, 조회, 수정, 삭제

---

## 🖼️ 화면 미리보기

### 🔸 여행 계획 페이지(메인 화면)
![image.png](/document/image.png)
![image_1.png](/document/image-9.png)

![image-1.png](/document/image-1.png)
### 🔸 지역별 관광지 검색
![image-2.png](/document/image-2.png)
### 🔸 관광지 주변 주차장 조회
![image-3.png](/document/image-3.png)

### 🔸 회원 기능
![image-1.png](/document/image-4.png)

**아이디 기억하기 기능**

![image.png](/document/image-5.png)

**내 정보 수정**

![image-2.png](/document/image-6.png)

### 🔸 관리자 기능
![image-3.png](/document/image-7.png)


---
## ERD
![image.png](/document/image-8.png)

---

## 🛠 기술 스택

| 구분 | 내용 |
|------|------|
| Language | Java, HTML, CSS, JavaScript, Vue.js |
| Framework | Spring Boot, MyBatis |
| Database | MySQL |
| API 연동 | SGIS 지도 API |
| 개발 환경 | STS, Tomcat |

