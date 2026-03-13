package com.opsboard.server.store;

import com.opsboard.server.model.ServerCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 서버 카테고리 메모리 저장소다.
 */
public final class ServerCategoryStore {
    public static final String ALL_NAME = "전체";
    private static final Map<Long, ServerCategory> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);
    private static final long DEFAULT_CATEGORY_ID;

    static {
        DEFAULT_CATEGORY_ID = create(ALL_NAME).getId();
        create("업무 시스템");
        create("인프라");
    }

    private ServerCategoryStore() {
    }

    public static List<ServerCategory> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(ServerCategory::getId))
                .toList();
    }

    public static ServerCategory findById(long id) {
        return DATA.get(id);
    }

    public static ServerCategory create(String name) {
        String normalized = name == null ? "" : name.trim();
        ServerCategory existing = DATA.values().stream()
                .filter(item -> item.getName().equalsIgnoreCase(normalized))
                .findFirst()
                .orElse(null);
        if (existing != null) {
            return existing;
        }

        long id = SEQ.incrementAndGet();
        ServerCategory item = new ServerCategory(id, normalized);
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
