package com.opsboard.issue.store;

import com.opsboard.issue.model.IssueCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 이슈 카테고리 메모리 저장소다.
 */
public final class IssueCategoryStore {
    public static final String ALL_NAME = "전체";
    private static final Map<Long, IssueCategory> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);
    private static final long DEFAULT_CATEGORY_ID;

    static {
        DEFAULT_CATEGORY_ID = create(ALL_NAME).getId();
        create("장애");
        create("개선");
    }

    private IssueCategoryStore() {
    }

    public static List<IssueCategory> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(IssueCategory::getId))
                .toList();
    }

    public static IssueCategory findById(long id) {
        return DATA.get(id);
    }

    public static IssueCategory create(String name) {
        String normalized = name == null ? "" : name.trim();
        IssueCategory existing = DATA.values().stream()
                .filter(item -> item.getName().equalsIgnoreCase(normalized))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            return existing;
        }

        long id = SEQ.incrementAndGet();
        IssueCategory item = new IssueCategory(id, normalized);
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
