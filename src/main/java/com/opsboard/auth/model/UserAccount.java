package com.opsboard.auth.model;

/**
 * 로그인 사용자 계정 정보를 표현하는 모델 객체다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class UserAccount {
    private final String username;
    private final String password;
    private final String role;

    public UserAccount(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
