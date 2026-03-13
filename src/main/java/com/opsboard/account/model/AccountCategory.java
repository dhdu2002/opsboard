package com.opsboard.account.model;

/**
 * 계정 분류 카테고리를 표현한다.
 */
public class AccountCategory {
    private final Long id;
    private final String name;

    public AccountCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
