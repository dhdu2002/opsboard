package com.opsboard.issue.store;

import com.opsboard.issue.model.IssueCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IssueCategoryStoreTest {

    @Test
    void defaultCategory_is전체() {
        IssueCategory category = IssueCategoryStore.findById(IssueCategoryStore.defaultCategoryId());

        assertNotNull(category);
        assertEquals("전체", category.getName());
    }

    @Test
    void create_returnsExisting_whenSameNameIgnoringCase() {
        IssueCategory first = IssueCategoryStore.create("issue-test");
        IssueCategory second = IssueCategoryStore.create("ISSUE-TEST");

        assertEquals(first.getId(), second.getId());
        IssueCategoryStore.delete(first.getId());
    }

    @Test
    void delete_doesNothing_forDefaultCategory() {
        long defaultId = IssueCategoryStore.defaultCategoryId();
        IssueCategoryStore.delete(defaultId);

        assertNotNull(IssueCategoryStore.findById(defaultId));
    }
}
