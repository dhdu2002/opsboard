package com.opsboard.account.store;

import com.opsboard.account.model.AccountCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AccountCategoryStoreTest {

    @Test
    void defaultCategory_is전체() {
        AccountCategory category = AccountCategoryStore.findById(AccountCategoryStore.defaultCategoryId());

        assertNotNull(category);
        assertEquals("전체", category.getName());
    }

    @Test
    void create_returnsExisting_whenSameNameIgnoringCase() {
        AccountCategory first = AccountCategoryStore.create("account-test");
        AccountCategory second = AccountCategoryStore.create("ACCOUNT-TEST");

        assertEquals(first.getId(), second.getId());
        AccountCategoryStore.delete(first.getId());
    }

    @Test
    void delete_doesNothing_forDefaultCategory() {
        long defaultId = AccountCategoryStore.defaultCategoryId();
        AccountCategoryStore.delete(defaultId);

        assertNotNull(AccountCategoryStore.findById(defaultId));
    }
}
