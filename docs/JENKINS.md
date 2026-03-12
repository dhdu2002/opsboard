# OpsBoard Jenkins CI/CD Guide

## 1) Jenkins 준비
- Jenkins 에이전트에 Java 17, Maven 설치
- Jenkins 서버에서 GitHub 저장소 접근 가능해야 함
- 내부 배포 서버(톰캣 서버)로 SSH 접근 가능해야 함

## 2) Jenkins Credentials
다음 Credential ID를 Jenkins에 등록합니다.

- `opsboard-ssh-key` : 배포 서버 접속용 SSH private key

## 3) Jenkins Job/Pipeline 파라미터
파이프라인 실행 시 아래 파라미터를 지정합니다.

- `RUN_DEPLOY` : 배포 단계 실행 여부 (기본 false)
- `DEPLOY_HOST` : 내부 톰캣 서버 호스트/IP
- `DEPLOY_USER` : 배포 SSH 계정

기본값은 `Jenkinsfile` 내부 환경변수로 설정되어 있습니다.

- `DEPLOY_DIR=/opt/tomcat/webapps`
- `TOMCAT_SERVICE=tomcat`
- `APP_NAME=opsboard`

## 4) 동작 방식
1. Checkout
2. `mvn clean test package`
3. WAR 아카이브
4. (옵션) `RUN_DEPLOY=true` + `DEPLOY_HOST` + `DEPLOY_USER` 입력 시, 그리고 브랜치가 `main/master`일 때 배포 수행

## 5) 배포 스크립트
- `scripts/deploy_war.sh` 사용
- 실행 내용:
  - WAR 업로드 (`scp`)
  - 톰캣 서비스 재시작 (`ssh + systemctl`)

## 6) 주의사항
- 배포 서버에서 `sudo systemctl restart tomcat` 권한이 필요합니다.
- 사내 정책에 맞게 SSH 계정 권한을 최소화하세요.
