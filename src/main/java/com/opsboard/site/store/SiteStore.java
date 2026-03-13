package com.opsboard.site.store;

import com.opsboard.site.model.SiteAsset;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class SiteStore {
    private static final Map<Long, SiteAsset> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);

    static {
        create(1L, 1L, "저작권 업무포털", "https://portal.internal.local", "https://portal-admin.internal.local", "관리페이지 연동 대상");
    }

    private SiteStore() {
    }

    public static List<SiteAsset> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(SiteAsset::getId))
                .toList();
    }

    public static List<SiteAsset> findByKeyword(String keyword) {
        return findByCondition(keyword, 0L);
    }

    public static List<SiteAsset> findByCondition(String keyword, long categoryId) {
        if (keyword == null || keyword.isBlank()) {
            return DATA.values().stream()
                    .filter(item -> categoryMatch(item.getCategoryId(), categoryId))
                    .sorted(Comparator.comparingLong(SiteAsset::getId))
                    .toList();
        }
        String q = keyword.trim().toLowerCase();
        return DATA.values().stream()
                .filter(item -> categoryMatch(item.getCategoryId(), categoryId)
                        && (contains(item.getSiteName(), q)
                        || contains(item.getSiteUrl(), q)
                        || contains(item.getAdminUrl(), q)
                        || contains(item.getNotes(), q)))
                .sorted(Comparator.comparingLong(SiteAsset::getId))
                .toList();
    }

    public static SiteAsset findById(long id) {
        return DATA.get(id);
    }

    public static SiteAsset create(long categoryId, long serverId, String siteName, String siteUrl, String adminUrl, String notes) {
        long id = SEQ.incrementAndGet();
        SiteAsset item = new SiteAsset(id, categoryId, serverId, siteName, siteUrl, adminUrl, notes);
        DATA.put(id, item);
        return item;
    }

    public static void update(long id, long categoryId, long serverId, String siteName, String siteUrl, String adminUrl, String notes) {
        if (!DATA.containsKey(id)) {
            return;
        }
        DATA.put(id, new SiteAsset(id, categoryId, serverId, siteName, siteUrl, adminUrl, notes));
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
