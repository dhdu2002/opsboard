package com.opsboard.domain;

public record AccountAsset(
        Long id,
        Long siteId,
        String accountId,
        String accountPassword,
        String contactOwner,
        String notes
) {
}
