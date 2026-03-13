package com.opsboard.server.model;

/**
 * 서버 업무 분류 카테고리를 표현한다.
 */
public class ServerCategory {
    private final Long id;
    private final String name;

    public ServerCategory(Long id, String name) {
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
