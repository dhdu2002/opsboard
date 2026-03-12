package com.opsboard.domain;

public record IssueItem(
        Long id,
        String issueTitle,
        String issueStatus,
        String assignee,
        Long relatedServerId,
        Long relatedSiteId,
        Long relatedAccountId,
        String details
) {
}
