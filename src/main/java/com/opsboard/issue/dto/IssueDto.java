package com.opsboard.issue.dto;

/**
 * 이슈 기능 요청/응답 전달용 DTO.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class IssueDto {
    private Long id;
    private String issueTitle;
    private String issueStatus;
    private String assignee;
    private Long relatedServerId;
    private Long relatedSiteId;
    private Long relatedAccountId;
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(String issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Long getRelatedServerId() {
        return relatedServerId;
    }

    public void setRelatedServerId(Long relatedServerId) {
        this.relatedServerId = relatedServerId;
    }

    public Long getRelatedSiteId() {
        return relatedSiteId;
    }

    public void setRelatedSiteId(Long relatedSiteId) {
        this.relatedSiteId = relatedSiteId;
    }

    public Long getRelatedAccountId() {
        return relatedAccountId;
    }

    public void setRelatedAccountId(Long relatedAccountId) {
        this.relatedAccountId = relatedAccountId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
