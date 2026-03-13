package com.opsboard.dashboard.dto;

/**
 * 대시보드 요약 카운트 전달용 DTO.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class DashboardSummaryDto {
    private int serverCount;
    private int siteCount;
    private int accountCount;
    private int issueCount;

    public int getServerCount() {
        return serverCount;
    }

    public void setServerCount(int serverCount) {
        this.serverCount = serverCount;
    }

    public int getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(int siteCount) {
        this.siteCount = siteCount;
    }

    public int getAccountCount() {
        return accountCount;
    }

    public void setAccountCount(int accountCount) {
        this.accountCount = accountCount;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }
}
