package com.opsboard.site.store;

import com.opsboard.site.model.SiteCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 사이트 카테고리 메모리 저장소다.
 */
public final class SiteCategoryStore {
    public static final String ALL_NAME = "전체";
    private static final Map<Long, SiteCategory> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);
    private static final long DEFAULT_CATEGORY_ID;

    static {
        DEFAULT_CATEGORY_ID = create(ALL_NAME).getId();
        create("대민 서비스");
        create("운영/관리");
    }

    private SiteCategoryStore() {
    }

    public static List<SiteCategory> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(SiteCategory::getId))
                .toList();
    }

    public static SiteCategory findById(long id) {
        return DATA.get(id);
    }

    public static SiteCategory create(String name) {
        String normalized = name == null ? "" : name.trim();
        SiteCategory existing = DATA.values().stream()
                .filter(item -> item.getName().equalsIgnoreCase(normalized))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            return existing;
        }

        long id = SEQ.incrementAndGet();
        SiteCategory item = new SiteCategory(id, normalized);
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
