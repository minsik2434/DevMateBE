# DevMateBE

# 프로젝트 소개

- DevMate 사이트 API 서버
- <b>스웨거 주소 (API 명세) : https://devmate.store/swagger-ui/index.html</b>
- <b>사이트 링크 : [데브메이트 바로가기](https://devmate-fe.vercel.app)</b>
- <b> 테스트 계정 : 아이디:test, 비밀번호: test123
- <b> 상세 설명 및 아키텍쳐,ERD, 기획 정보는 https://github.com/minsik2434/DevMateFE 에 기재되어 있습니다
 
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

  # API 명세 
### 회원
| 기능   | 설명   | 메소드 | 엔드포인트 | Request | Response | 기타   |
|--------|--------|-----|------------|---------|----------|--------|
| 등록  | 회원 정보 등록 | POST | /members/register | <img width="264" alt="스크린샷 2024-07-11 오후 4 37 53" src="https://github.com/minsik2434/DevMateBE/assets/119111149/00450802-cfb7-4d87-a7d3-b3e8e6264b76">  | <img width="430" alt="스크린샷 2024-07-11 오후 4 46 37" src="https://github.com/minsik2434/DevMateBE/assets/119111149/44f1ca18-d4f9-43fe-b409-e864c7ed0a3d"> | 201:회원 등록 성공, 400: 입력값 검증실패, 404: 존재하지 않는 관심직종, 409: 중복 리소스,  |
| 로그인  | 회원 로그인  | POST | /members/signin | <img width="172" alt="스크린샷 2024-07-11 오후 4 50 07" src="https://github.com/minsik2434/DevMateBE/assets/119111149/0937fee4-9300-4109-86d7-235bd3680cb8"> | <img width="460" alt="스크린샷 2024-07-11 오후 4 51 04" src="https://github.com/minsik2434/DevMateBE/assets/119111149/bbe855dc-1008-4dac-b4b5-b82214c316a3"> | 200: 로그인 성공, 400:BadRequest, 401:로그인 실패 |
| 수정 | 회원 수정, 회원 accessToken 필요 | PATCH | /members | <img width="197" alt="스크린샷 2024-07-11 오후 4 55 41" src="https://github.com/minsik2434/DevMateBE/assets/119111149/1a71cce3-81c3-43ff-a862-2838e705ab7b"> | <img width="366" alt="스크린샷 2024-07-11 오후 4 56 52" src="https://github.com/minsik2434/DevMateBE/assets/119111149/e994564e-ed1d-4f73-9163-711df305a44a"> | 200: 수정 성공, 400:BadRequest, 401:인증 실패, 404:존재하지 않는 리소스 |
| 회원 조회 | 회원 AccessToken으로 회원 정보 조회 | GET | /members | /members | <img width="357" alt="스크린샷 2024-07-11 오후 5 00 51" src="https://github.com/minsik2434/DevMateBE/assets/119111149/bd62b688-3396-47e0-a4a7-860bf6f2f993"> | 200: 회원 조회 성공, 404: 존재하지 않는 리소스 | 
| 회원 조회 | 회원 닉네임으로 회원 정보 조회 | GET | /members/{nickName} | /members/tester |<img width="434" alt="스크린샷 2024-07-11 오후 5 15 23" src="https://github.com/minsik2434/DevMateBE/assets/119111149/521d4530-2cb3-4c4a-88b2-aecf6dadff8b"> | 200: 조회 성공, 404 : 존재하지 않는 리소스 |
| 회원 리소스 중복 체크 | 회원 아이디, 회원 닉네임 중복 확인 path경로에 확인할 값, 쿼리파라미터 type에 확인할 타입(로그인 아이디, 닉네임) | GET | /members/{value}/check | /members/tester/check?type=loginId, /members/tester/check?type=nickName | <img width="388" alt="스크린샷 2024-07-11 오후 5 20 13" src="https://github.com/minsik2434/DevMateBE/assets/119111149/3bedc092-5c47-4702-9f69-dbc279165df1"> | 200: 조회 성공 |
| 삭제 | 회원 탈퇴, 회원 AccessToken 필요 | DELETE | /members | /members | 204 no content | 204: 삭제 성공, 401 : 인증 에러, 404:존재하지 않는 리소스 |

### 관심 직종
| 기능   | 설명   | 메소드 | 엔드포인트 | Request | Response | 기타   |
|--------|--------|-----|------------|---------|----------|--------|
| 조회 | 모든 관심직종 조회 | GET | /interests | /interests |<img width="398" alt="스크린샷 2024-07-11 오후 5 24 03" src="https://github.com/minsik2434/DevMateBE/assets/119111149/7dcbb2ef-fbd4-4779-863a-9b442bb0398b"> | 200 : 조회 성공 | 
| 조회 | 관심직종 아이디로 조회 | GET | /interests/{id} | /interests/1 | <img width="380" alt="스크린샷 2024-07-11 오후 5 25 50" src="https://github.com/minsik2434/DevMateBE/assets/119111149/7a9575d7-bda4-4035-b847-ada2070f23d2"> | 200 : 조회 성공, 404: 존재하지 않는 리소스 | 

### 게시글 
| 기능   | 설명   | 메소드 | 엔드포인트 | Request | Response | 기타   |
|--------|--------|-----|------------|---------|----------|--------|
| 등록 | 게시글 등록(qna, community, job, review ) 회원 액세스 토큰 필요 | POST | /post/{category} | <img width="220" alt="스크린샷 2024-07-11 오후 5 30 25" src="https://github.com/minsik2434/DevMateBE/assets/119111149/73381446-1c98-4ca6-8325-bdb2694bda5c"> | <img width="407" alt="스크린샷 2024-07-11 오후 5 33 02" src="https://github.com/minsik2434/DevMateBE/assets/119111149/7c6fd1c9-31ca-48d3-bfbf-85d59cc0415b"> | 201:등록 성공, 400:BadRequest, 401:인증에러, 404:존재하지 않는 리소스 |
| 등록 | 게시글 등록 (study) 회원 액세스 토큰 필요 | POST | /post/study |  <img width="303" alt="스크린샷 2024-07-11 오후 5 36 08" src="https://github.com/minsik2434/DevMateBE/assets/119111149/eae834d8-7885-48d2-953b-1e3b179d8d01"> | <img width="413" alt="스크린샷 2024-07-11 오후 5 36 30" src="https://github.com/minsik2434/DevMateBE/assets/119111149/7d5225f3-d235-442b-94b4-3e2c77b38b82"> | 201: 등록 성공, 400:BadRequest, 401: 인증 에러, 404: 존재하지 않는 리소스 |
| 등록 | 게시글 등록 (mentoring) 회원 액세스 토큰 필요 | POST | /post/mentoring | <img width="230" alt="스크린샷 2024-07-11 오후 5 40 03" src="https://github.com/minsik2434/DevMateBE/assets/119111149/b4205446-2ee4-44fd-b974-9b8e37bf3c6e"> | <img width="409" alt="스크린샷 2024-07-11 오후 5 39 39" src="https://github.com/minsik2434/DevMateBE/assets/119111149/50ea01a2-8e72-4827-849c-7132373dbe67"> | 201: 등록 성공, 400:BadRequest, 401: 인증 에러, 404: 존재하지 않는 리소스 | 
| 수정 | 게시글 수정(qna, community, job, review) 회원 액세스 토큰 필요 | PATCH | /post/{postId}/{category} | <img width="237" alt="스크린샷 2024-07-11 오후 5 43 12" src="https://github.com/minsik2434/DevMateBE/assets/119111149/a99f73b8-c4c1-4ea0-87db-9125affc61df"> | <img width="412" alt="스크린샷 2024-07-11 오후 5 43 25" src="https://github.com/minsik2434/DevMateBE/assets/119111149/abf6bb2a-4dd0-48c4-b47c-0bcf90807359"> | 200: 게시글 수정 성공 , 401:인증에러, 404: 존재하지 않는 리소스 |
| 수정 | 게시글 수정 (study) 회원 액세스 토큰 필요 | PATCH | /post/{postId}/study | <img width="283" alt="스크린샷 2024-07-11 오후 5 46 05" src="https://github.com/minsik2434/DevMateBE/assets/119111149/7075d709-7098-429f-a98e-e00daee9a872"> | <img width="422" alt="스크린샷 2024-07-11 오후 5 45 44" src="https://github.com/minsik2434/DevMateBE/assets/119111149/f498a2d4-1106-45ca-bda6-2bec446b47aa"> | 200: 게시글 수정 성공 , 401:인증에러, 404: 존재하지 않는 리소스 |
| 수정 | 게시글 수정 (mentoring) 회원 액세스 토큰 필요 | PATCH | /post/{postId}/mentoring | <img width="293" alt="스크린샷 2024-07-11 오후 5 49 04" src="https://github.com/minsik2434/DevMateBE/assets/119111149/354141b7-4b3e-41cd-8256-5b68f61b86d6"> | <img width="413" alt="스크린샷 2024-07-11 오후 5 49 19" src="https://github.com/minsik2434/DevMateBE/assets/119111149/6d3609ad-b9c9-42aa-ade6-3c5c60b51c57"> | 200: 게시글 수정 성공 , 401:인증에러, 404: 존재하지 않는 리소스 |
| 조회 | 게시글 아이디로 조회 | GET | /post/{id} | /post/1 | <img width="419" alt="스크린샷 2024-07-11 오후 5 51 10" src="https://github.com/minsik2434/DevMateBE/assets/119111149/3f67b804-2214-4c8f-8d0d-942d7ad88d4d"> | 200: 조회 성공, 404:존재하지 않는 리소스 |
| 조회 | 게시글 리스트 조회, 쿼리 파라미터 sort(정렬 기준): comment, latest, good sc(검색어), tag(태그 검색), page(페이지) : 0 | GET | /post/{category}/list | /post/qna/list?sort=latest&tag=spring&page=0 |<img width="461" alt="스크린샷 2024-07-11 오후 5 56 15" src="https://github.com/minsik2434/DevMateBE/assets/119111149/120c8d9f-af95-490f-9bbb-09d33d4ec7d1"> | 200:게시글 조회 성공, 404: 존재하지 않는 리소스 |
| 조회 | 해당 회원이 작성,좋아요,댓글 단 게시글 조회, 인증 헤더 필요, 쿼리 파라미터 type(조회 기준) : comment, post, good 쿼리 파라미터 page(페이지) : 0부터 시작 | GET | /post/member | /post/member?type=post&page=0 | <img width="431" alt="스크린샷 2024-07-11 오후 5 59 26" src="https://github.com/minsik2434/DevMateBE/assets/119111149/a15a7f75-0a91-479f-895a-638726634001"> | 200: 조회 성공, 401: 인증 에러, 404:존재하지 않는 리소스 | 
| 삭제 | 게시글 삭제, 인증 헤더 필요 | DELETE | /post/{postId} | /post/1 | no Content | 204: 삭제 성공, 401: 인증에러, 404:존재하지 않는 리소스 |

### 좋아요 
| 기능   | 설명   | 메소드 | 엔드포인트 | Request | Response | 기타   |
|--------|--------|-----|------------|---------|----------|--------|
| 등록 | 좋아요 추가, 인증 헤더 필요, 회원은 한 게시글에 좋아요 한번만 가능 | POST | /goods/{postId} | /goods/1 | <img width="391" alt="스크린샷 2024-07-11 오후 6 08 39" src="https://github.com/minsik2434/DevMateBE/assets/119111149/51960ecf-ad40-4105-8ea6-652c7105d1a9"> | 201:성공, 401:인증 오류, 404: 존재하지 않는 리소스 | 409: 이미 존재하는 리소스 |
| 조회 | 좋아요 아이디로 조회,인증 헤더 필요 | GET | /goods/{goodId} | /goods/16 / <img width="384" alt="스크린샷 2024-07-11 오후 6 11 11" src="https://github.com/minsik2434/DevMateBE/assets/119111149/58e1b51e-888c-4c86-8354-7b0f168b24af"> | 200: 조회 성공, 401 인증에러, 404:존재하지 않는 리소스 |
| 삭제 | 좋아요 아이디로 좋아요 삭제, 인증 헤더 필요 | DELETE | /goods/{goodId} | /goods/16 | noContent | 204: 삭제 성공, 401:인증 에러, 404:존재하지 않는 리소스 |

### 댓글
| 기능   | 설명   | 메소드 | 엔드포인트 | Request | Response | 기타   |
|--------|--------|-----|------------|---------|----------|--------|
| 등록 | 댓글 등록, 인증 헤더 필요 | POST | /comments/{postId} | <img width="254" alt="스크린샷 2024-07-11 오후 6 17 09" src="https://github.com/minsik2434/DevMateBE/assets/119111149/97b82126-3116-43a5-a6e0-bff1ff669ef4"> | <img width="424" alt="스크린샷 2024-07-11 오후 6 16 52" src="https://github.com/minsik2434/DevMateBE/assets/119111149/6cf5f034-bd23-4300-b0b6-da33c7c956b7"> | 201: 댓글 등록성공, 400:BadRequest, 401:인증에러, 404:존재하지 않는 리소스 | 
| 수정 | 댓글 수정, 인증 헤더 필요 | PATCH | /comments/{commentId} |<img width="223" alt="스크린샷 2024-07-11 오후 6 19 30" src="https://github.com/minsik2434/DevMateBE/assets/119111149/35f52806-f4b7-4978-817b-ad35267023a2"> | <img width="420" alt="스크린샷 2024-07-11 오후 6 19 04" src="https://github.com/minsik2434/DevMateBE/assets/119111149/55c59991-6e50-4c81-8132-e43081640b9f"> | 200: 수정 성공, 400:BadRequest, 404: 존재하지 않는 리소스 |
| 삭제 | 댓글 삭제, 인증 헤더 필요 | DELETE | /comments/{commnetId} | /comments/16 | noContent | 204:삭제 성공, 401:인증에러, 404:존재하지 않는 리소스 | 
| 조회 | 게시글 댓글 리스트 조회 | GET | /comments/{postId} | /comments/1 | <img width="432" alt="스크린샷 2024-07-11 오후 6 23 13" src="https://github.com/minsik2434/DevMateBE/assets/119111149/430115ac-c0b0-4b8e-a053-10d73b203c6b"> | 200:댓글 조회 성공, 404:존재하지 않는 리소스 | 

### 이미지
| 기능   | 설명   | 메소드 | 엔드포인트 | Request | Response | 기타   |
|--------|--------|-----|------------|---------|----------|--------|
| 등록 | 이미지 s3에 업로드,인증 헤더 필요, 이미지 multypart로 입력 | POST | /image/upload | <img width="145" alt="스크린샷 2024-07-11 오후 6 25 33" src="https://github.com/minsik2434/DevMateBE/assets/119111149/81554ec1-c1f3-4b38-b3e3-84106dcb1801"> | <img width="170" alt="스크린샷 2024-07-11 오후 6 25 58" src="https://github.com/minsik2434/DevMateBE/assets/119111149/e94a532c-3ed0-465e-95f5-091e42a46495"> | 200 : 이미지 업로드 성공, 401 인증 에러 | 
| 삭제 | s3에 업로드된 이미지 삭제, 인증 헤더 필요 | POST | /image/delete | <img width="163" alt="스크린샷 2024-07-11 오후 6 26 59" src="https://github.com/minsik2434/DevMateBE/assets/119111149/17f58b3a-11ea-4641-91d1-d898a78ea24d"> | noContent | 204:삭제 성공, 401:인증 에러 |

## APM
- 네이버에서 오픈소스로 제공하는 핀포인트를 사용해 서버 트랜잭션 추적 및 관리
<img width="1425" alt="스크린샷 2024-07-03 오후 2 31 52" src="https://github.com/user-attachments/assets/dd7e46d1-6c6b-4bbd-bbd5-ca204d0f1311">
