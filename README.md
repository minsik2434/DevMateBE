# DevMateBE

# 프로젝트 소개

- DevMate 사이트 API 서버
- <b>스웨거 주소 (API 명세) : https://devmate.store/swagger-ui/index.html</b>
- <b>사이트 링크 : [데브메이트 바로가기](https://devmate-fe.vercel.app)</b>
- <b> 테스트 계정 : 아이디:test, 비밀번호: test123

# 기술스택

<div>
<img src="https://img.shields.io/badge/java-cc7623?style=for-the-badge">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=black">
<img src="https://img.shields.io/badge/spring boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=black">
<img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=black">
<img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge">
<img src="https://img.shields.io/badge/jpa-b0a271?style=for-the-badge">
<img src="https://img.shields.io/badge/querydsl-1777c0?style=for-the-badge">
<img src="https://img.shields.io/badge/mysql-1777c0?style=for-the-badge&logo=mysql&logoColor=black">
<img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=black">
<img src="https://img.shields.io/badge/amazonec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=black">   
</div>
<br>
<br>

# API 기능 설명

### 회원

- 등록 : 회원 정보를 입력해 데이터베이스에 저장, 회원의 비밀번호는 BCrypt로 암호화 하여 데이터베이스에 저장
- 로그인 : 회원 정보를 입력해 데이터베이스에 조회, 인증 완료후 JWT AccessToken, RefreshToken 반환
- 수정 : 회원의 수정 정보를 입력받아 회원 조회 후 액세스 토큰으로 회원 인증후 회원 정보 변경
- 삭제 : 회원 액세스 토큰 정보로 회원 조회 후 회원 삭제
- 조회(토큰) : 액세스토큰 정보로 회원 정보 조회
- 조회(닉네임) : 회원의 닉네임 정보로 회원 정보를 조회
- 회원 로그인 아이디, 닉네임 중복 체크 : validation을 위한 회원 정보 중복 체크

### 관심 직종
- 조회(리스트) : 모든 관심 직종 리스트 조회
- 조회(개별) : 관심 직종 아이디를 입력받아 해당 관심 직종 정보 조회

### 게시글
- 등록 : 게시글 정보를 입력 받아 데이터베이스에 저장, 카테고리 별로 저장
- 수정 : 게시글 아이디를 입력받아 데이터베이스에 조회 후 정보 수정
- 조회(아이디) : 게시글 아이디를 입력받아 해당 게시글 정보를 조회
- 조회(리스트) : 해당 카테고리 게시판의 게시글 리스트를 조회, 파라미터로 정렬, 태그, 검색 조건 추가 가능
- 조회(회원관련) : 액세스 토큰을 입력해 회원이 게시하거나 댓글,좋아요한 게시글 리스트 조회
- 삭제 : 작성한 게시글을 삭제, 회원의 액세스 토큰을 입력받아 인증후 수행


### 댓글
- 등록 : 댓글 정보를 입력받아 해당 게시글 아이디정보로 게시글 댓글로 저장
- 수정 : 댓글 아이디로 댓글 조회후 회원 액세스토큰으로 인증, 댓글 정보 수정
- 삭제 : 댓글 아이디로 댓글 조회후 회원 액세스토큰으로 인증, 댓글 삭제
- 조회 : 게시글 아이디로 해당 게시글의 댓글 리스트 조회

### 좋아요
- 등록 : 게시글 아이디로 해당 게시글의 좋아요 추가
- 삭제 : 좋아요 아이디로 좋아요 삭제
- 조회 : 해당 게시글 아이디와 유저 액세스 토큰을 입력받아 해당 게시글의 좋아요 유무 조회

### 이미지 관련
- 등록 : 이미지 정보를 multypart 로 입력 받아 AWS S3 에 저장
- 삭제 : 이미지 url 입력받아 S3 이미지 삭제
