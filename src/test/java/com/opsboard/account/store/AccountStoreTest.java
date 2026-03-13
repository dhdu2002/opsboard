package com.opsboard.account.store;

import com.opsboard.account.model.AccountAsset;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountStoreTest {

    @Test
    void findByCondition_filtersByKeywordAndCategory() {
        String token = "account-test-" + UUID.randomUUID();

        AccountAsset cat1 = AccountStore.create(1L, 1L, token + "-a", "pw-a", "owner-a", "note-a");
        AccountAsset cat2 = AccountStore.create(2L, 1L, token + "-b", "pw-b", "owner-b", "note-b");

        try {
            List<AccountAsset> result = AccountStore.findByCondition(token, 2L);
            assertTrue(result.stream().noneMatch(item -> item.getId().equals(cat1.getId())));
            assertTrue(result.stream().anyMatch(item -> item.getId().equals(cat2.getId())));
        } finally {
            AccountStore.delete(cat1.getId());
            AccountStore.delete(cat2.getId());
        }
    }
}
