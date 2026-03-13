package com.opsboard.activity.store;

import com.opsboard.activity.model.ActivityLog;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActivityStoreTest {

    @Test
    void findByCondition_filtersByKeywordModuleAndAction() {
        String token = "activity-test-" + UUID.randomUUID();

        ActivityLog target = ActivityStore.add("ISSUE", "CREATE", "tester", token + " created");
        ActivityLog other = ActivityStore.add("SITE", "DELETE", "tester", token + " deleted");

        List<ActivityLog> result = ActivityStore.findByCondition(200, token, "ISSUE", "CREATE");

        assertTrue(result.stream().anyMatch(item -> item.getId().equals(target.getId())));
        assertTrue(result.stream().noneMatch(item -> item.getId().equals(other.getId())));
    }

    @Test
    void findByCondition_withAllFilters_keepsKeywordFilteringOnly() {
        String token = "activity-all-" + UUID.randomUUID();
        
        ActivityLog issue = ActivityStore.add("ISSUE", "UPDATE", "tester", token + " issue");
        ActivityLog site = ActivityStore.add("SITE", "DELETE", "tester", token + " site");

        List<ActivityLog> result = ActivityStore.findByCondition(200, token, "전체", "전체");

        assertTrue(result.stream().anyMatch(item -> item.getId().equals(issue.getId())));
        assertTrue(result.stream().anyMatch(item -> item.getId().equals(site.getId())));
    }

    @Test
    void modulesAndActions_returnsDistinctValues() {
        String token = "activity-distinct-" + UUID.randomUUID();
        ActivityStore.add("SITE", "UPDATE", "tester", token + " first");
        ActivityStore.add("SITE", "UPDATE", "tester", token + " second");

        List<String> modules = ActivityStore.modules();
        List<String> actions = ActivityStore.actions();

        assertFalse(modules.isEmpty());
        assertFalse(actions.isEmpty());
        assertTrue(modules.stream().anyMatch("SITE"::equals));
        assertTrue(actions.stream().anyMatch("UPDATE"::equals));
    }
}
