package com.opsboard.server.store;

import com.opsboard.server.model.ServerAsset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class ServerStore {
    private static final Map<Long, ServerAsset> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);

    static {
        create(1L, "ops-main-01", "10.10.1.20", "PROD", "사내 운영 포털", "기본 운영 서버");
    }

    private ServerStore() {
    }

    public static List<ServerAsset> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(ServerAsset::getId))
                .toList();
    }

    public static List<ServerAsset> findByKeyword(String keyword) {
        return findByCondition(keyword, 0L);
    }

    public static List<ServerAsset> findByCondition(String keyword, long categoryId) {
        if (keyword == null || keyword.isBlank()) {
            return DATA.values().stream()
                    .filter(item -> categoryMatch(item.getCategoryId(), categoryId))
                    .sorted(Comparator.comparingLong(ServerAsset::getId))
                    .toList();
        }
        String q = keyword.trim().toLowerCase();
        return DATA.values().stream()
                .filter(item -> categoryMatch(item.getCategoryId(), categoryId)
                        && (contains(item.getName(), q)
                        || contains(item.getIpAddress(), q)
                        || contains(item.getEnvironment(), q)
                        || contains(item.getUsageDescription(), q)
                        || contains(item.getNotes(), q)))
                .sorted(Comparator.comparingLong(ServerAsset::getId))
                .toList();
    }

    public static ServerAsset findById(long id) {
        return DATA.get(id);
    }

    public static ServerAsset create(long categoryId, String name, String ipAddress, String environment, String usageDescription, String notes) {
        long id = SEQ.incrementAndGet();
        ServerAsset item = new ServerAsset(id, categoryId, name, ipAddress, environment, usageDescription, notes);
        DATA.put(id, item);
        return item;
    }

    public static void update(long id, long categoryId, String name, String ipAddress, String environment, String usageDescription, String notes) {
        if (!DATA.containsKey(id)) {
            return;
        }
        DATA.put(id, new ServerAsset(id, categoryId, name, ipAddress, environment, usageDescription, notes));
    }

    public static void delete(long id) {
        DATA.remove(id);
    }

    public static List<ServerAsset> findAllMutable() {
        return new ArrayList<>(findAll());
    }

    private static boolean contains(String text, String keyword) {
        return text != null && text.toLowerCase().contains(keyword);
    }

    private static boolean categoryMatch(Long itemCategoryId, long categoryId) {
        return categoryId <= 0 || (itemCategoryId != null && itemCategoryId == categoryId);
    }
}
