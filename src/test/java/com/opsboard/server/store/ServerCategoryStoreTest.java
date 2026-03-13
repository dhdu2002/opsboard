package com.opsboard.server.store;

import com.opsboard.server.model.ServerCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServerCategoryStoreTest {

    @Test
    void defaultCategory_is전체() {
        ServerCategory category = ServerCategoryStore.findById(ServerCategoryStore.defaultCategoryId());

        assertNotNull(category);
        assertEquals("전체", category.getName());
    }

    @Test
    void create_returnsExisting_whenSameNameIgnoringCase() {
        ServerCategory first = ServerCategoryStore.create("infra-test");
        ServerCategory second = ServerCategoryStore.create("INFRA-TEST");

        assertEquals(first.getId(), second.getId());
        ServerCategoryStore.delete(first.getId());
    }

    @Test
    void delete_doesNothing_forDefaultCategory() {
        long defaultId = ServerCategoryStore.defaultCategoryId();
        ServerCategoryStore.delete(defaultId);

        assertNotNull(ServerCategoryStore.findById(defaultId));
    }
}
