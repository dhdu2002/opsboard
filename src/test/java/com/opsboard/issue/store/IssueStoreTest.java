package com.opsboard.issue.store;

import com.opsboard.issue.model.IssueItem;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IssueStoreTest {

    @Test
    void findByCondition_filtersByKeywordAndCategory() {
        String token = "issue-test-" + UUID.randomUUID();

        IssueItem cat1 = IssueStore.create(1L, token + "-A", "OPEN", "a", 1L, 1L, 1L, "detail-a");
        IssueItem cat2 = IssueStore.create(2L, token + "-B", "OPEN", "b", 1L, 1L, 1L, "detail-b");

        try {
            List<IssueItem> result = IssueStore.findByCondition(token, 1L);
            assertTrue(result.stream().anyMatch(item -> item.getId().equals(cat1.getId())));
            assertTrue(result.stream().noneMatch(item -> item.getId().equals(cat2.getId())));
        } finally {
            IssueStore.delete(cat1.getId());
            IssueStore.delete(cat2.getId());
        }
    }

    @Test
    void update_preservesCreatedAt() {
        String token = "issue-update-" + UUID.randomUUID();
        IssueItem created = IssueStore.create(1L, token, "OPEN", "tester", 1L, 1L, 1L, "detail");

        try {
            IssueStore.update(created.getId(), 1L, token + "-new", "DONE", "tester2", 1L, 1L, 1L, "detail2");
            IssueItem updated = IssueStore.findById(created.getId());

            assertEquals(created.getCreatedAt(), updated.getCreatedAt());
            assertEquals(token + "-new", updated.getIssueTitle());
        } finally {
            IssueStore.delete(created.getId());
        }
    }
}
