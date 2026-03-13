package com.opsboard.account.model;

/**
 * 계정(ID/PW/담당자) 정보를 표현하는 모델 객체다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
public class AccountAsset {
    private final Long id;
    private final Long categoryId;
    private final Long siteId;
    private final String accountId;
    private final String accountPassword;
    private final String contactOwner;
    private final String notes;

    public AccountAsset(Long id, Long categoryId, Long siteId, String accountId, String accountPassword, String contactOwner, String notes) {
        this.id = id;
        this.categoryId = categoryId;
        this.siteId = siteId;
        this.accountId = accountId;
        this.accountPassword = accountPassword;
        this.contactOwner = contactOwner;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public String getContactOwner() {
        return contactOwner;
    }

    public String getNotes() {
        return notes;
    }
}
