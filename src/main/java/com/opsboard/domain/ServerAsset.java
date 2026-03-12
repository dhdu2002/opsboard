package com.opsboard.domain;

public record ServerAsset(
        Long id,
        String name,
        String ipAddress,
        String environment,
        String usageDescription,
        String notes
) {
}
