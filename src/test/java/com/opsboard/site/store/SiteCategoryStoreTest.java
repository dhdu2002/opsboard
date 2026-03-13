package com.opsboard.site.store;

import com.opsboard.site.model.SiteCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SiteCategoryStoreTest {

    @Test
    void defaultCategory_is전체() {
        SiteCategory category = SiteCategoryStore.findById(SiteCategoryStore.defaultCategoryId());

        assertNotNull(category);
        assertEquals("전체", category.getName());
    }

    @Test
    void create_returnsExisting_whenSameNameIgnoringCase() {
        SiteCategory first = SiteCategoryStore.create("site-test");
        SiteCategory second = SiteCategoryStore.create("SITE-TEST");

        assertEquals(first.getId(), second.getId());
        SiteCategoryStore.delete(first.getId());
    }

    @Test
    void delete_doesNothing_forDefaultCategory() {
        long defaultId = SiteCategoryStore.defaultCategoryId();
        SiteCategoryStore.delete(defaultId);

        assertNotNull(SiteCategoryStore.findById(defaultId));
    }
}
