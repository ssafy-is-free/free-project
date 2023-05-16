## 개발환경

```bash
npm install
npm run dev
```
- 환경변수 : 프로젝트 경로에 `.env` 파일 생성후 입력
```bash
NEXT_PUBLIC_API_URL="https://k8b102.p.ssafy.io/api"
NEXT_PUBLIC_MODE='dev'
```

<br/>

## 배포환경

```bash
npm install
npm run build
npm run start
```
- 환경변수 : 프로젝트 경로에 `.env` 파일 생성후 입력
```bash
NEXT_PUBLIC_API_URL="https://chpo.kr/api"
```