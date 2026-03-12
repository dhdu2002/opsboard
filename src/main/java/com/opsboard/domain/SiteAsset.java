package com.opsboard.domain;

public record SiteAsset(
        Long id,
        Long serverId,
        String siteName,
        String siteUrl,
        String adminUrl,
        String notes
) {
}
