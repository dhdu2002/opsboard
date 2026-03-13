package com.opsboard.site.store;

import com.opsboard.site.model.SiteAsset;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SiteStoreTest {

    @Test
    void findByCondition_filtersByKeywordAndCategory() {
        String token = "site-test-" + UUID.randomUUID();

        SiteAsset cat1 = SiteStore.create(1L, 1L, token + "-A", "https://" + token + "-a", "https://admin-a", "note-a");
        SiteAsset cat2 = SiteStore.create(2L, 1L, token + "-B", "https://" + token + "-b", "https://admin-b", "note-b");

        try {
            List<SiteAsset> result = SiteStore.findByCondition(token, 1L);
            assertTrue(result.stream().anyMatch(item -> item.getId().equals(cat1.getId())));
            assertTrue(result.stream().noneMatch(item -> item.getId().equals(cat2.getId())));
        } finally {
            SiteStore.delete(cat1.getId());
            SiteStore.delete(cat2.getId());
        }
    }
}
