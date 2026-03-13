package com.opsboard.account.store;

import com.opsboard.account.model.AccountAsset;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class AccountStore {
    private static final Map<Long, AccountAsset> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);

    static {
        create(1L, 1L, "admin", "admin1234!", "운영팀", "초기 테스트 계정");
    }

    private AccountStore() {
    }

    public static List<AccountAsset> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(AccountAsset::getId))
                .toList();
    }

    public static List<AccountAsset> findByKeyword(String keyword) {
        return findByCondition(keyword, 0L);
    }

    public static List<AccountAsset> findByCondition(String keyword, long categoryId) {
        if (keyword == null || keyword.isBlank()) {
            return DATA.values().stream()
                    .filter(item -> categoryMatch(item.getCategoryId(), categoryId))
                    .sorted(Comparator.comparingLong(AccountAsset::getId))
                    .toList();
        }
        String q = keyword.trim().toLowerCase();
        return DATA.values().stream()
                .filter(item -> categoryMatch(item.getCategoryId(), categoryId)
                        && (contains(item.getAccountId(), q)
                        || contains(item.getContactOwner(), q)
                        || contains(item.getNotes(), q)))
                .sorted(Comparator.comparingLong(AccountAsset::getId))
                .toList();
    }

    public static AccountAsset findById(long id) {
        return DATA.get(id);
    }

    public static AccountAsset create(long categoryId, long siteId, String accountId, String accountPassword, String contactOwner, String notes) {
        long id = SEQ.incrementAndGet();
        AccountAsset item = new AccountAsset(id, categoryId, siteId, accountId, accountPassword, contactOwner, notes);
        DATA.put(id, item);
        return item;
    }

    public static void update(long id, long categoryId, long siteId, String accountId, String accountPassword, String contactOwner, String notes) {
        if (!DATA.containsKey(id)) {
            return;
        }
        DATA.put(id, new AccountAsset(id, categoryId, siteId, accountId, accountPassword, contactOwner, notes));
    }

    public static void delete(long id) {
        DATA.remove(id);
    }

    private static boolean contains(String text, String keyword) {
        return text != null && text.toLowerCase().contains(keyword);
    }

    private static boolean categoryMatch(Long itemCategoryId, long categoryId) {
        return categoryId <= 0 || (itemCategoryId != null && itemCategoryId == categoryId);
    }
}
