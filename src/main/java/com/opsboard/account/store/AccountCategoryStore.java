package com.opsboard.account.store;

import com.opsboard.account.model.AccountCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 계정 카테고리 메모리 저장소다.
 */
public final class AccountCategoryStore {
    public static final String ALL_NAME = "전체";
    private static final Map<Long, AccountCategory> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);
    private static final long DEFAULT_CATEGORY_ID;

    static {
        DEFAULT_CATEGORY_ID = create(ALL_NAME).getId();
        create("관리자 계정");
        create("운영 계정");
    }

    private AccountCategoryStore() {
    }

    public static List<AccountCategory> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(AccountCategory::getId))
                .toList();
    }

    public static AccountCategory findById(long id) {
        return DATA.get(id);
    }

    public static AccountCategory create(String name) {
        String normalized = name == null ? "" : name.trim();
        AccountCategory existing = DATA.values().stream()
                .filter(item -> item.getName().equalsIgnoreCase(normalized))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            return existing;
        }

        long id = SEQ.incrementAndGet();
        AccountCategory item = new AccountCategory(id, normalized);
        DATA.put(id, item);
        return item;
    }

    public static void delete(long id) {
        if (id == DEFAULT_CATEGORY_ID) {
            return;
        }
        DATA.remove(id);
    }

    public static long defaultCategoryId() {
        return DEFAULT_CATEGORY_ID;
    }
}
