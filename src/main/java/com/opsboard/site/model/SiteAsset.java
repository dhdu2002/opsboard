package com.opsboard.site.model;

/**
 * 사이트/관리페이지 정보를 표현하는 모델 객체다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class SiteAsset {
    private final Long id;
    private final Long categoryId;
    private final Long serverId;
    private final String siteName;
    private final String siteUrl;
    private final String adminUrl;
    private final String notes;

    public SiteAsset(Long id, Long categoryId, Long serverId, String siteName, String siteUrl, String adminUrl, String notes) {
        this.id = id;
        this.categoryId = categoryId;
        this.serverId = serverId;
        this.siteName = siteName;
        this.siteUrl = siteUrl;
        this.adminUrl = adminUrl;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getServerId() {
        return serverId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getAdminUrl() {
        return adminUrl;
    }

    public String getNotes() {
        return notes;
    }
}
