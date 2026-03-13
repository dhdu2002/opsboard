package com.opsboard.server.model;

/**
 * 서버 자산 정보를 표현하는 모델 객체다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class ServerAsset {
    private final Long id;
    private final Long categoryId;
    private final String name;
    private final String ipAddress;
    private final String environment;
    private final String usageDescription;
    private final String notes;

    public ServerAsset(Long id, Long categoryId, String name, String ipAddress, String environment, String usageDescription, String notes) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.ipAddress = ipAddress;
        this.environment = environment;
        this.usageDescription = usageDescription;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getUsageDescription() {
        return usageDescription;
    }

    public String getNotes() {
        return notes;
    }
}
