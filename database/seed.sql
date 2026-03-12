INSERT INTO server_asset (name, ip_address, environment, usage_description, notes)
VALUES ('ops-main-01', '10.10.1.20', 'PROD', '사내 운영 포털', '기본 운영 서버');

INSERT INTO site_asset (server_id, site_name, site_url, admin_url, notes)
VALUES (1, '저작권 업무포털', 'https://portal.internal.local', 'https://portal-admin.internal.local', '관리페이지 연동 대상');

INSERT INTO account_asset (site_id, account_id, account_password, contact_owner, notes)
VALUES (1, 'admin', 'admin1234!', '운영팀', '초기 테스트 계정');

INSERT INTO issue_item (issue_title, issue_status, assignee, related_server_id, related_site_id, related_account_id, details)
VALUES ('관리페이지 접속 오류', 'IN_PROGRESS', '홍길동', 1, 1, 1, '사내 테스트 이슈 샘플');
