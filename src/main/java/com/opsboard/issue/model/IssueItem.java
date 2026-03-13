package com.opsboard.issue.model;

import java.time.LocalDateTime;

/**
 * 운영 이슈 정보를 표현하는 모델 객체다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class IssueItem {
    private final Long id;
    private final Long categoryId;
    private final String issueTitle;
    private final String issueStatus;
    private final String assignee;
    private final Long relatedServerId;
    private final Long relatedSiteId;
    private final Long relatedAccountId;
    private final String details;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public IssueItem(
            Long id,
            Long categoryId,
            String issueTitle,
            String issueStatus,
            String assignee,
            Long relatedServerId,
            Long relatedSiteId,
            Long relatedAccountId,
            String details,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.issueTitle = issueTitle;
        this.issueStatus = issueStatus;
        this.assignee = assignee;
        this.relatedServerId = relatedServerId;
        this.relatedSiteId = relatedSiteId;
        this.relatedAccountId = relatedAccountId;
        this.details = details;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public String getAssignee() {
        return assignee;
    }

    public Long getRelatedServerId() {
        return relatedServerId;
    }

    public Long getRelatedSiteId() {
        return relatedSiteId;
    }

    public Long getRelatedAccountId() {
        return relatedAccountId;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
