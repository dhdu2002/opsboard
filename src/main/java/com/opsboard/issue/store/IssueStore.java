package com.opsboard.issue.store;

import com.opsboard.issue.model.IssueItem;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class IssueStore {
    private static final Map<Long, IssueItem> DATA = new ConcurrentHashMap<>();
    private static final AtomicLong SEQ = new AtomicLong(0);

    static {
        create(1L, "관리페이지 접속 오류", "IN_PROGRESS", "홍길동", 1L, 1L, 1L, "사내 테스트 이슈 샘플");
    }

    private IssueStore() {
    }

    public static List<IssueItem> findAll() {
        return DATA.values().stream()
                .sorted(Comparator.comparingLong(IssueItem::getId))
                .toList();
    }

    public static List<IssueItem> findByKeyword(String keyword) {
        return findByCondition(keyword, 0L);
    }

    public static List<IssueItem> findByCondition(String keyword, long categoryId) {
        if (keyword == null || keyword.isBlank()) {
            return DATA.values().stream()
                    .filter(item -> categoryMatch(item.getCategoryId(), categoryId))
                    .sorted(Comparator.comparingLong(IssueItem::getId))
                    .toList();
        }
        String q = keyword.trim().toLowerCase();
        return DATA.values().stream()
                .filter(item -> categoryMatch(item.getCategoryId(), categoryId)
                        && (contains(item.getIssueTitle(), q)
                        || contains(item.getIssueStatus(), q)
                        || contains(item.getAssignee(), q)
                        || contains(item.getDetails(), q)))
                .sorted(Comparator.comparingLong(IssueItem::getId))
                .toList();
    }

    public static List<IssueItem> findRecent(int limit) {
        return DATA.values().stream()
                .sorted((a, b) -> Long.compare(b.getId(), a.getId()))
                .limit(limit)
                .toList();
    }

    public static IssueItem findById(long id) {
        return DATA.get(id);
    }

    public static IssueItem create(
            long categoryId,
            String issueTitle,
            String issueStatus,
            String assignee,
            Long relatedServerId,
            Long relatedSiteId,
            Long relatedAccountId,
            String details
    ) {
        long id = SEQ.incrementAndGet();
        LocalDateTime now = LocalDateTime.now();
        IssueItem item = new IssueItem(id, categoryId, issueTitle, issueStatus, assignee, relatedServerId, relatedSiteId, relatedAccountId, details, now, now);
        DATA.put(id, item);
        return item;
    }

    public static void update(
            long id,
            long categoryId,
            String issueTitle,
            String issueStatus,
            String assignee,
            Long relatedServerId,
            Long relatedSiteId,
            Long relatedAccountId,
            String details
    ) {
        if (!DATA.containsKey(id)) {
            return;
        }
        IssueItem before = DATA.get(id);
        LocalDateTime createdAt = before.getCreatedAt();
        DATA.put(id, new IssueItem(id, categoryId, issueTitle, issueStatus, assignee, relatedServerId, relatedSiteId, relatedAccountId, details, createdAt, LocalDateTime.now()));
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
