package com.opsboard.server.store;

import com.opsboard.server.model.ServerAsset;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerStoreTest {

    @Test
    void findByCondition_filtersByKeywordAndCategory() {
        String token = "server-test-" + UUID.randomUUID();

        ServerAsset cat1 = ServerStore.create(1L, token + "-A", "10.0.0.10", "PROD", "portal", "note-a");
        ServerAsset cat2 = ServerStore.create(2L, token + "-B", "10.0.0.11", "DEV", "batch", "note-b");

        try {
            List<ServerAsset> result = ServerStore.findByCondition(token, 1L);

            assertTrue(result.stream().anyMatch(item -> item.getId().equals(cat1.getId())));
            assertTrue(result.stream().noneMatch(item -> item.getId().equals(cat2.getId())));
        } finally {
            ServerStore.delete(cat1.getId());
            ServerStore.delete(cat2.getId());
        }
    }

    @Test
    void findByCondition_withAllCategoryZero_returnsAllMatchingKeyword() {
        String token = "server-test-all-" + UUID.randomUUID();

        ServerAsset cat1 = ServerStore.create(1L, token + "-A", "10.0.1.10", "PROD", "search-a", "note-a");
        ServerAsset cat2 = ServerStore.create(2L, token + "-B", "10.0.1.11", "DEV", "search-b", "note-b");

        try {
            List<ServerAsset> result = ServerStore.findByCondition(token, 0L);

            assertTrue(result.stream().anyMatch(item -> item.getId().equals(cat1.getId())));
            assertTrue(result.stream().anyMatch(item -> item.getId().equals(cat2.getId())));
        } finally {
            ServerStore.delete(cat1.getId());
            ServerStore.delete(cat2.getId());
        }
    }
}
