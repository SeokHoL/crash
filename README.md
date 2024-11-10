# 📝 Crash



## 1. 프로젝트 소개
- # 📝 Crash



## 1. 프로젝트 소개
- IT회사의 컨퍼런스 신청 서비스 프로젝트 입니다.
- 프론트엔드, 백엔드, AI, 클라우드 등 듣고싶은 행사를 신청 할 수 있습니다.

# 📝 포트폴리오 개요


  <img src="https://github.com/user-attachments/assets/25cb7664-19b2-41b9-aa76-0e3f4dcfba25" width="700" height="400" alt="포트폴리오 메인 이미지" />


> ### 프로젝트: 개인 포트폴리오 사이트
>
> - 개발자: 이석호  
>- 분류: 개인 프로젝트  
>- 제작 기간: 2024.11.01 ~ 11.06  
><!-- - 배포일: 2021.10.05-->
> - 주요 기능: 로그인, 회원가입, 세션 목록 조회, 세션 선택 필터, 등록 현황


## 🛠 사용기술 및 도구

### Frontend
DockerHub에서 제공하는 images 사용

### Backend
![Spring Boot](https://img.shields.io/badge/-Spring_Boot-6DB33F?logo=springboot&logoColor=white&style=flat)
![Spring Security](https://img.shields.io/badge/-Spring_Security-6DB33F?logo=springsecurity&logoColor=white&style=flat)
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-4169E1?logo=postgresql&logoColor=white&style=flat)

### DevOps
![Docker](https://img.shields.io/badge/-Docker-2496ED?logo=docker&logoColor=white&style=flat)
![Redis](https://img.shields.io/badge/-Redis-DC382D?logo=redis&logoColor=white&style=flat)
![GitHub](https://img.shields.io/badge/-GitHub-181717?logo=github&logoColor=white&style=flat)

<!-- ## 🔗 링크
- 웹사이트: [https://keemtj.com](https://keemtj.com)-->

## ✨ 업데이트
- 

## 🖼 기능 구현

### 1. 로그인/회원가입
<img src="https://github.com/user-attachments/assets/e81719bb-dd4b-4e54-b903-c072f7a17678" width="70%" alt="Board Demo">


- 로그인/회원가입 기능 구현함.
- 회원가입 안되면 로그인이 안되게 구현함.
  

### 2. 세션 목록 조회/신청/취소 등 기능구현
<img src="https://github.com/user-attachments/assets/e5abd156-6dda-4509-bb06-dfcc41400055" width="70%" alt="Board Demo">

- 세션목록을 조회하고 세션 필터를 통해 원하는 것만 선택하여 보고 세션을 신청 할 수 있도록 함.
- 세션을 신청하고 세션 현황조회로 가서 본인시 신청한 세션을 볼 수 있고 취소 할수 있도록 함.
  
### 3. Redis 활용
<img src="https://github.com/user-attachments/assets/59d66073-cf5f-4193-9b95-ce1369d2f1c7" width="500" height="300" alt="포트폴리오 메인 이미지" />   <img src="https://github.com/user-attachments/assets/695879dd-7862-4688-b6d6-b2b755e78041" width="500" height="300" alt="포트폴리오 메인 이미지" />

### 첫 번째 요청 (868ms)

- Redis에 데이터가 아직 캐시되어 있지 않은 상태
- 데이터베이스에서 데이터를 조회해야 함
- 조회한 데이터를 Redis에 캐싱하는 과정이 필요
- 이러한 전체 과정이 **868ms** 소요

### 두 번째 요청 (14ms)

- 이미 Redis에 데이터가 캐시되어 있는 상태
- 데이터베이스 조회 없이 Redis에서 바로 데이터를 가져옴
- Redis는 인메모리 데이터베이스라서 매우 빠른 응답 속도
- 따라서 **14ms**라는 매우 빠른 응답 시간을 보여줌





