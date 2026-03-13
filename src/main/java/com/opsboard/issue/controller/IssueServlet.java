package com.opsboard.issue.controller;

import com.opsboard.account.store.AccountStore;
import com.opsboard.activity.store.ActivityStore;
import com.opsboard.common.util.WebParamUtils;
import com.opsboard.issue.model.IssueItem;
import com.opsboard.issue.model.IssueCategory;
import com.opsboard.issue.store.IssueCategoryStore;
import com.opsboard.issue.store.IssueStore;
import com.opsboard.server.store.ServerStore;
import com.opsboard.site.store.SiteStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * 이슈 관리 화면의 메모리 기반 CRUD 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "issueServlet", urlPatterns = {"/issues"})
public class IssueServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(IssueServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(() -> "[ISSUE] GET /issues from " + req.getRemoteAddr());
        String keyword = WebParamUtils.text(req.getParameter("keyword"));
        long selectedCategoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), 0L);
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedCategoryId", selectedCategoryId);
        boolean isAdmin = "ADMIN".equals(String.valueOf(req.getSession().getAttribute("AUTH_ROLE")));
        req.setAttribute("pageTitle", "Issue Management");
        req.setAttribute("items", IssueStore.findByCondition(keyword, selectedCategoryId));
        req.setAttribute("servers", ServerStore.findAll());
        req.setAttribute("sites", SiteStore.findAll());
        req.setAttribute("accounts", isAdmin ? AccountStore.findAll() : Collections.emptyList());
        req.setAttribute("categories", IssueCategoryStore.findAll());
        Map<Long, String> serverNameMap = ServerStore.findAll().stream()
                .collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        Map<Long, String> siteNameMap = SiteStore.findAll().stream()
                .collect(Collectors.toMap(item -> item.getId(), item -> item.getSiteName()));
        Map<Long, String> accountNameMap = AccountStore.findAll().stream()
                .collect(Collectors.toMap(item -> item.getId(), item -> item.getAccountId()));
        Map<Long, String> categoryNameMap = IssueCategoryStore.findAll().stream()
                .collect(Collectors.toMap(IssueCategory::getId, IssueCategory::getName));
        req.setAttribute("serverNameMap", serverNameMap);
        req.setAttribute("siteNameMap", siteNameMap);
        req.setAttribute("accountNameMap", isAdmin ? accountNameMap : Collections.emptyMap());
        req.setAttribute("categoryNameMap", categoryNameMap);
        req.setAttribute("isAdmin", isAdmin);

        long editId = WebParamUtils.parseLong(req.getParameter("editId"), -1L);
        if (editId > 0) {
            IssueItem editItem = IssueStore.findById(editId);
            req.setAttribute("editItem", editItem);
        }

        req.getRequestDispatcher("/WEB-INF/views/issue/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String action = WebParamUtils.text(req.getParameter("action"));
        long id = WebParamUtils.parseLong(req.getParameter("id"), -1L);
        long categoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), IssueCategoryStore.defaultCategoryId());
        boolean isAdmin = "ADMIN".equals(String.valueOf(req.getSession().getAttribute("AUTH_ROLE")));

        LOGGER.info(() -> "[ISSUE] POST /issues action=" + action + ", id=" + id + ", by=" + req.getRemoteAddr());

        if ("create".equals(action)) {
            String issueTitle = WebParamUtils.text(req.getParameter("issueTitle"));
            IssueStore.create(
                    categoryId,
                    issueTitle,
                    WebParamUtils.text(req.getParameter("issueStatus")),
                    WebParamUtils.text(req.getParameter("assignee")),
                    WebParamUtils.parseNullableLong(req.getParameter("relatedServerId")),
                    WebParamUtils.parseNullableLong(req.getParameter("relatedSiteId")),
                    WebParamUtils.parseNullableLong(req.getParameter("relatedAccountId")),
                    WebParamUtils.text(req.getParameter("details"))
            );
            ActivityStore.add("ISSUE", "CREATE", req.getRemoteAddr(), "이슈 등록: " + issueTitle);
        } else if ("update".equals(action) && id > 0) {
            String issueTitle = WebParamUtils.text(req.getParameter("issueTitle"));
            IssueStore.update(
                    id,
                    categoryId,
                    issueTitle,
                    WebParamUtils.text(req.getParameter("issueStatus")),
                    WebParamUtils.text(req.getParameter("assignee")),
                    WebParamUtils.parseNullableLong(req.getParameter("relatedServerId")),
                    WebParamUtils.parseNullableLong(req.getParameter("relatedSiteId")),
                    WebParamUtils.parseNullableLong(req.getParameter("relatedAccountId")),
                    WebParamUtils.text(req.getParameter("details"))
            );
            ActivityStore.add("ISSUE", "UPDATE", req.getRemoteAddr(), "이슈 수정 ID=" + id + ", title=" + issueTitle);
        } else if ("delete".equals(action) && id > 0 && isAdmin) {
            IssueStore.delete(id);
            ActivityStore.add("ISSUE", "DELETE", req.getRemoteAddr(), "이슈 삭제 ID=" + id);
        } else if ("createCategory".equals(action) && isAdmin) {
            String categoryName = WebParamUtils.text(req.getParameter("categoryName"));
            if (!categoryName.isBlank()) {
                IssueCategoryStore.create(categoryName);
                ActivityStore.add("ISSUE_CATEGORY", "CREATE", req.getRemoteAddr(), "이슈 카테고리 등록: " + categoryName);
            }
        } else if ("deleteCategory".equals(action) && isAdmin) {
            long deleteCategoryId = WebParamUtils.parseLong(req.getParameter("deleteCategoryId"), -1L);
            IssueCategoryStore.delete(deleteCategoryId);
            ActivityStore.add("ISSUE_CATEGORY", "DELETE", req.getRemoteAddr(), "이슈 카테고리 삭제 ID=" + deleteCategoryId);
        }

        resp.sendRedirect(req.getContextPath() + "/issues");
    }
}
