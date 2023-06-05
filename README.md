<img src = "https://img.shields.io/badge/build-passing-brightgreen"/>&nbsp;<img src = "https://img.shields.io/badge/docker%20build-passing-brightgreen"/>&nbsp;<img src = "https://img.shields.io/badge/deployment-passing-brightgreen"/>&nbsp;<img src = https://img.shields.io/badge/Coverage-76.08%25-brightgreen>

# 🏆 CHPO(Check Position) - 개발자 랭킹 서비스

CHPO 링크(PC 화면) : [https://chpo.kr](https://chpo.kr/)
CHPO UCC(YOUTUBE) : [https://chpo.kr](https://chpo.kr/)
중간발표자료
최종발표자료

<br>
<br>

# 목차
- [프로젝트 진행 기간](#🎞-프로젝트-진행-기간)
- [개요](#✨-개요)
- [주요 기능](#💻-주요-기능)
- [서비스 화면](#🖼-서비스-화면)
- [주요 기술](#🛠-주요-기술)
- [프로젝트 파일 구조](#🗂-프로젝트-파일-구조)
- [프로젝트 산출물](#📋-프로젝트-산출물)
- [팀원 역할 분배](#👩‍💻-팀원-역할-분배)

<br>
<br>

# 🎞 프로젝트 진행 기간

2023.04.10(월) ~ 2023.05.19(금) (39일간 진행)

SSAFY 8기 2학기 자율프로젝트

<br>
<br>

# ✨ 개요

CHPO는 이용자의 깃허브와 백준 정보를 바탕으로 랭킹 기능과 프로필 비교 기능, 기업 지원 현황 관리 기능을 제공하여 개발자들의 취업 준비를 돕고 개인의 성장을 측정하며, 이를 통해 자신의 기술적 역량을 개선할 수 있도록 돕는 서비스입니다. 


<br>
<br>


# 💻 주요 기능

### 랭킹
- 깃허브 정보(커밋, 스타, 레포지토리, 팔로워)를 가지고 점수를 부여합니다.
- 백준 정보(티어, 맞은 문제 수, 시도했지만 맞지 못한 문제 수, 제출 횟수, 틀렸습니다 수)를 가지고 점수를 부여합니다.

### 기업 지원 현황 관리

- 기업 지원내역을 등록한다.
- 지원상태(서류접수, 서류합력, 면접합격, 면접탈락, etc)를 변경한다.
- 다음 일정 및 메모를 등록한다.
- 지원자들의 랭킹을 확인한다.

### 유저 비교
- 자신과 다른 유저 1명을 비교한다.
- 자신과 기업에 지원한 지원자들의 평균과 비교한다.
- 비교 정보
    - 깃허브 : 커밋, 스타, 레포지토리, 사용언어
    - 백준 : 티어, 맞은 문제, 틀린 문제, 제출횟수, 시도했지만 틀린 문제, 사용언어


<br>
<br>

# 🖼 서비스 화면



## 랭킹


| ![로그인](https://github.com/ssafy-is-free/free-project/assets/76441040/768b42e8-dac0-4e3e-994e-1d90fd6aacc0) | ![유저검색](https://github.com/ssafy-is-free/free-project/assets/76441040/d9ee45ff-6ac9-4b4f-a866-ed5023d96b22) | ![검색필터](https://github.com/ssafy-is-free/free-project/assets/76441040/8a8c9d89-a8f9-43d7-8988-b8136ea07cc5) | ![랭킹-조회](https://github.com/ssafy-is-free/free-project/assets/76441040/59c0574c-3f7a-4c40-bfe2-08e6bb5504a8) |
| ------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------- |
| 깃허브 로그인                                                                                                 | 닉네임으로 유저 검색                                                                                            | 검색 필터(언어, 취업 공고)                                                                                      | 랭킹 조회                                                                                                        |

## 상세 조회

| ![유저비교](https://github.com/ssafy-is-free/free-project/assets/76441040/178e2877-46d6-43f2-8db2-e81fa6ad1d28) | ![유저조회](https://github.com/ssafy-is-free/free-project/assets/76441040/3f1acd27-4632-4ca9-a9e4-ad939ea0a2d4) |
| --------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- |
| 다른 사용자와 비교                                                                                              | 유저 레포, 리드미 등등 상세 조회                                                                                |

## 기업 지원 현황 관리

| ![취업-등록](https://github.com/ssafy-is-free/free-project/assets/76441040/96bff020-55db-44bf-9edc-7dbf1183143d) | ![취업관리](https://github.com/ssafy-is-free/free-project/assets/76441040/5df325ca-ed4b-43ce-ae34-1d04ce89cf08) | ![지원자-통계](https://github.com/ssafy-is-free/free-project/assets/76441040/9e34654c-c8a7-4d2d-954a-97b1f9b6e7a0) |
| ---------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| 공고를 통해 취업 상태 등록                                                                                       | 현재 취업 상태 관리                                                                                             | 공고에 지원한 타 유저 및 평균 조회                                                                                 |









<br>
<br>

# 🛠 주요 기술


**Backend**
<br>

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/fastapi-009688?style=for-the-badge&logo=fastapi&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
- Java : Oracle OpenJDK 11.0.17
- SpringBoot 2.7.9
- Spring Security 5.7.7
- Spring Data Jpa 2.7.9
- Spring Boot Actuator
- Junit 5.8.2
- Gradle 7.6.1
- FastAPI
- MySQL 운영서버 : 8.0.28, 개발서버 : 8.0.32

<br>

**FrontEnd**
<br>

<img src="https://img.shields.io/badge/nextjs-000000?style=for-the-badge&logo=nextdotjs&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black">&nbsp;<img src="https://img.shields.io/badge/Redux-764ABC?style=for-the-badge&logo=redux&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/styled components-DB7093?style=for-the-badge&logo=styledcomponents&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/node.js-339933?style=for-the-badge&logo=nodedotjs&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">&nbsp;

- Next 13.3.0
- React 18.2.0
- Node.js 16.16.0
- TypeScript 5.0.4
- Redux 8.0.5
- Redux-toolkit 1.9.4
- Redux-persist 6.0.0
- Styled-component 5.3.9
- Axios 1.3.5

<br>

**CI/CD**
<br>

<img src="https://img.shields.io/badge/aws ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/openssl-721412?style=for-the-badge&logo=openssl&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/sonarqube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white">&nbsp;


- AWS EC2
- Ubuntu 20.04 LTS
- Jenkins 2.387.1
- Docker Engine 23.0.1
- Nginx 1.23.4
- SSL
- SonarQube 
- Grafana latest
- Prometheus 2.44.0
- Ngrinder-controller 3.5.8
- Ngrinder-agent 3.5.8

<br>

**협업 툴**
<br>

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jirasoftware&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">&nbsp;<img src="https://img.shields.io/badge/figma-EA4335?style=for-the-badge&logo=figma&logoColor=white">&nbsp;
- 형상 관리 : Git
- 이슈 관리 : Jira
- 커뮤니케이션 : Mattermost, Webex, Notion
- 디자인 : Figma


<br>
<br>

# 🗂 프로젝트 파일 구조

### Backend

```markdown
backend
|-- 📂domain
|   |-- 📂algorithm
|   |-- 📂analysis
|   |-- 📂github
|   |-- 📂job
|   |-- 📂user
|   |-- 📂util
|   └-- 📂entity
└-- 📂global
    |-- 📂auth
        |-- 📂auth
        └-- 📂config
        └-- 📂exception
        └-- 📂oauth 
        └-- 📂response 

chpo-test
└-- 📂domain
    |-- 📂algorithm
    |-- 📂analysis
    |-- 📂github
    |-- 📂job
    |-- 📂user
    |-- 📂util
    └-- 📂entity 
```

### FrontEnd

```markdown
frontend
|-- 📂components
|   |-- 📂common
|   |-- 📂jobrank
|   |-- 📂login
|   |-- 📂proflie
|   └-- 📂rank
└-- 📂pages
└-- 📂public
└-- 📂redux
└-- 📂styles
└-- 📂utils
    └-- 📂api 

```

<br>
<br>


# 📋 프로젝트 산출물

- [요구사항 명세서](https://delicate-utensil-152.notion.site/782e5fa0ff3d42079a7da6567f9f4be0)
- [API 명세서](https://delicate-utensil-152.notion.site/BE-API-4035ed956b704378b7e037b122230d96)
- [ERD](https://www.erdcloud.com/d/GoqPjtBce7YNAsvdQ)
- [와이어프레임](https://www.figma.com/file/4aSX8CBKJOMVTcKrKrXYZT/Untitled?type=design&node-id=0%3A1&t=pFt6XQMXvmhevv14-1)
- [시스템 아키텍처](https://delicate-utensil-152.notion.site/29283b619ef54fc6bf8f54e86c0278bc?pvs=4)




<br>
<br>

# 👩‍💻 팀원 역할 분배

| 정연진            | 이상현   | 정혜주   | 유제균  | 이성복  | 김태학  |
| ----------------- | -------- | -------- | ------- | ------- | ------- |
| <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/0bca5a29-15fd-478b-8ecf-cdc371c644ed" width="100"> |<img src="https://github.com/ssafy-is-free/free-project/assets/76441040/602edccd-2ad6-4c57-b983-0a8344a0e3a9" width="100">  | <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/84af1b89-9a8d-4b8b-b197-4e8b8ded6425" width="100"> | <img src="https://user-images.githubusercontent.com/109879750/219442808-96f0f7db-cf4d-46d4-9310-0e0f448ccec7.png" width="100"> | <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/cb25a44e-d63d-4bcc-855d-7558b8051088" width="100">  | <img src="https://github.com/ssafy-is-free/free-project/assets/76441040/0c16e0c4-651d-4fd7-90b7-28317af9fbb5" width="100">  |
| Leader & Backend | Backend | Frontend | Backend | Backend | Frontend |

<br>
<br>
