package com.opsboard.activity.model;

import java.time.LocalDateTime;

/**
 * 변경 이력 로그 정보를 표현하는 모델 객체다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class ActivityLog {
    private final Long id;
    private final String module;
    private final String action;
    private final String actor;
    private final String summary;
    private final LocalDateTime createdAt;

    public ActivityLog(Long id, String module, String action, String actor, String summary, LocalDateTime createdAt) {
        this.id = id;
        this.module = module;
        this.action = action;
        this.actor = actor;
        this.summary = summary;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getModule() {
        return module;
    }

    public String getAction() {
        return action;
    }

    public String getActor() {
        return actor;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
