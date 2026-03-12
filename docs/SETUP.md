# OpsBoard Setup Guide

## 1) Eclipse Import
1. Eclipse 실행
2. `File > Import > Maven > Existing Maven Projects`
3. 프로젝트 루트로 `C:\OpsBoard` 선택
4. `pom.xml` 확인 후 import

## 2) Database Init
1. 테스트 DB 생성
2. `database/init.sql` 실행
3. 샘플 데이터가 필요하면 `database/seed.sql` 실행

## 3) Build
```bash
mvn clean package
```

## 4) Deploy (Internal Server)
1. 빌드된 `target/opsboard.war` 확인
2. 내부 톰캣 서버의 `webapps`에 배포
3. 서버 기동 후 `/opsboard` 경로 접속

## 5) Current Scope
- 권한 구분 없음 (요청사항 반영)
- VPN 제한 없음 (요청사항 반영)
- 백업 구성 없음 (요청사항 반영)
