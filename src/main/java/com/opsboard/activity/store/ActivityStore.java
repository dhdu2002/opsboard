package com.opsboard.activity.store;

import com.opsboard.activity.model.ActivityLog;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class ActivityStore {
    private static final Map<Long, ActivityLog> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);

    private ActivityStore() {
    }

    public static ActivityLog add(String module, String action, String summary) {
        return add(module, action, "SYSTEM", summary);
    }

    public static ActivityLog add(String module, String action, String actor, String summary) {
        long id = SEQ.incrementAndGet();
        ActivityLog item = new ActivityLog(id, module, action, actor, summary, LocalDateTime.now());
        DATA.put(id, item);
        return item;
    }

    public static List<ActivityLog> findRecent(int limit) {
        return findByCondition(limit, "", "", "");
    }

    public static List<ActivityLog> findByCondition(int limit, String keyword, String module, String action) {
        String q = keyword == null ? "" : keyword.trim().toLowerCase();
        String moduleFilter = module == null ? "" : module.trim();
        String actionFilter = action == null ? "" : action.trim();

        return DATA.values().stream()
                .filter(item -> moduleFilter.isBlank() || "전체".equals(moduleFilter) || moduleFilter.equals(item.getModule()))
                .filter(item -> actionFilter.isBlank() || "전체".equals(actionFilter) || actionFilter.equals(item.getAction()))
                .filter(item -> q.isBlank()
                        || contains(item.getModule(), q)
                        || contains(item.getAction(), q)
                        || contains(item.getActor(), q)
                        || contains(item.getSummary(), q))
                .sorted(Comparator.comparingLong(ActivityLog::getId).reversed())
                .limit(limit)
                .toList();
    }

    public static List<String> modules() {
        return DATA.values().stream()
                .map(ActivityLog::getModule)
                .distinct()
                .sorted()
                .toList();
    }

    public static List<String> actions() {
        return DATA.values().stream()
                .map(ActivityLog::getAction)
                .distinct()
                .sorted()
                .toList();
    }

    private static boolean contains(String text, String keyword) {
        return text != null && text.toLowerCase().contains(keyword);
    }
}
