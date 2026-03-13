package com.opsboard.site.model;

/**
 * 사이트/관리페이지 분류 카테고리를 표현한다.
 */
public class SiteCategory {
    private final Long id;
    private final String name;

    public SiteCategory(Long id, String name) {
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
