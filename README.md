# chatbot-task

## 어플리케이션 동작 사전작업

### DB 생성
터미널에서 다음 커맨드를 입력하여 DB를 생성함 (Postgres 설치 필요)
1. Postgres 접속

    `psql -U postgres`

2. 데이터베이스 생성

    `CREATE DATABASE chatbot;`
3. 사용자 생성

    `CREATE USER season WITH PASSWORD '1234';`
4. 데이터베이스 권한 부여

    `GRANT ALL PRIVILEGES ON DATABASE chatbot TO season;`

### API 키 등록
1. 루트 디렉토리에 env 폴더를 생성
2. env 폴더에 local.env 파일 생성
3. local.env 파일에서 OPENAI_KEY={KEY} 로 환경변수 주입

## api 실행 순서

local에서 동작하므로 host를 localhost:8080으로 설정하여 진행함

### 1. 회원가입
    POST {{host}}/auth/signup
### 2. 로그인
    POST {{host}}/auth/login

해당 API의 응답으로 token이 돌아옴

### 3. (postman 사용시) 모든 요청에 Authorization 주입을 위한 준비 
    {{host}}/actuator/health

위 API에서 post-response에 아래 명령어 입력함. token_value에 로그인 response에서 받은 토큰을 사용할 것
```
pm.globals.set("auth_token", "{token_value}");
```

### 4. 