package com.opsboard.auth.dto;

/**
 * 로그인 요청 전달용 DTO.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class LoginRequestDto {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
