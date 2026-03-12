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

## Notes
- 현재는 초기 스캐폴드 단계입니다.
- CRUD 본 구현은 다음 작업 파동에서 진행합니다.

## CI/CD
- Jenkins 파이프라인 파일: `Jenkinsfile`
- Jenkins 설정 가이드: `docs/JENKINS.md`
