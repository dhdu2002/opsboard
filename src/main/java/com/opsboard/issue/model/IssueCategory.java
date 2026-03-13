package com.opsboard.issue.model;

/**
 * 이슈 분류 카테고리를 표현한다.
 */
public class IssueCategory {
    private final Long id;
    private final String name;

    public IssueCategory(Long id, String name) {
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
