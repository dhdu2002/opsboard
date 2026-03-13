# opsboard

사내 운영정보(서버, 사이트/관리페이지, 계정, 이슈)를 관리하는 JSP 기반 웹 프로젝트입니다.

## Stack
- Java 17
- JSP + jQuery + CSS
- Maven (war)
- Eclipse

## Module Scope (MVP)
- 서버 관리
- 사이트/관리페이지 관리
- 계정 관리 (ID/PW 포함)
- 이슈 관리
- 로그인 및 권한 관리 (ADMIN / USER)

## Project Structure
- `src/main/java` : servlet + domain code
- `src/main/webapp` : JSP/정적자원
- `database` : 초기 스키마 및 시드 SQL
- `docs` : 실행/설정 문서

## Build
```bash
mvn clean package
```

## Run (local servlet container)
WAR 산출물을 톰캣 등 서블릿 컨테이너에 배포해서 실행합니다.

## 운영 가이드
- 기본 설정/배포: `docs/SETUP.md`
- Tomcat 단독 운영 체크리스트: `docs/TOMCAT-ONLY.md`

## Auth
- 로그인 URL: `/opsboard/login`
- 기본 계정
  - ADMIN: `admin / admin1234!`
  - USER: `user / user1234!`
- 권한 정책
  - ADMIN: 전체 메뉴 접근 가능
  - USER: 계정/변경이력 메뉴 제외 접근

## Notes
- 현재는 DB 연동 전 단계로, 메모리 기반 CRUD가 동작합니다.
- DB 연동 시 Store 레이어를 DAO/Repository 구현으로 교체하면 됩니다.

## CI/CD
- Jenkins 파이프라인 파일: `Jenkinsfile`
- Jenkins 설정 가이드: `docs/JENKINS.md`
