package com.opsboard.account.controller;

import com.opsboard.account.model.AccountAsset;
import com.opsboard.account.model.AccountCategory;
import com.opsboard.account.store.AccountCategoryStore;
import com.opsboard.account.store.AccountStore;
import com.opsboard.activity.store.ActivityStore;
import com.opsboard.common.util.WebParamUtils;
import com.opsboard.site.store.SiteStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.logging.Logger;

/**
 * 계정 관리 화면의 메모리 기반 CRUD 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "accountServlet", urlPatterns = {"/accounts"})
public class AccountServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AccountServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(() -> "[ACCOUNT] GET /accounts from " + req.getRemoteAddr());
        String keyword = WebParamUtils.text(req.getParameter("keyword"));
        long selectedCategoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), 0L);
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedCategoryId", selectedCategoryId);
        req.setAttribute("pageTitle", "Account Management");
        req.setAttribute("items", AccountStore.findByCondition(keyword, selectedCategoryId));
        req.setAttribute("sites", SiteStore.findAll());
        req.setAttribute("categories", AccountCategoryStore.findAll());
        Map<Long, String> siteNameMap = SiteStore.findAll().stream().collect(Collectors.toMap(item -> item.getId(), item -> item.getSiteName()));
        Map<Long, String> categoryNameMap = AccountCategoryStore.findAll().stream().collect(Collectors.toMap(AccountCategory::getId, AccountCategory::getName));
        req.setAttribute("siteNameMap", siteNameMap);
        req.setAttribute("categoryNameMap", categoryNameMap);
        req.setAttribute("isAdmin", true);

        long editId = WebParamUtils.parseLong(req.getParameter("editId"), -1L);
        if (editId > 0) {
            AccountAsset editItem = AccountStore.findById(editId);
            req.setAttribute("editItem", editItem);
        }

        req.getRequestDispatcher("/WEB-INF/views/account/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String action = WebParamUtils.text(req.getParameter("action"));
        long id = WebParamUtils.parseLong(req.getParameter("id"), -1L);
        long categoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), AccountCategoryStore.defaultCategoryId());
        long siteId = WebParamUtils.parseLong(req.getParameter("siteId"), 0L);

        LOGGER.info(() -> "[ACCOUNT] POST /accounts action=" + action + ", id=" + id + ", siteId=" + siteId + ", by=" + req.getRemoteAddr());

        if ("create".equals(action)) {
            String accountId = WebParamUtils.text(req.getParameter("accountId"));
            AccountStore.create(
                    categoryId,
                    siteId,
                    accountId,
                    WebParamUtils.text(req.getParameter("accountPassword")),
                    WebParamUtils.text(req.getParameter("contactOwner")),
                    WebParamUtils.text(req.getParameter("notes"))
            );
            ActivityStore.add("ACCOUNT", "CREATE", req.getRemoteAddr(), "계정 등록: " + accountId);
        } else if ("update".equals(action) && id > 0) {
            String accountId = WebParamUtils.text(req.getParameter("accountId"));
            AccountStore.update(
                    id,
                    categoryId,
                    siteId,
                    accountId,
                    WebParamUtils.text(req.getParameter("accountPassword")),
                    WebParamUtils.text(req.getParameter("contactOwner")),
                    WebParamUtils.text(req.getParameter("notes"))
            );
            ActivityStore.add("ACCOUNT", "UPDATE", req.getRemoteAddr(), "계정 수정 ID=" + id + ", accountId=" + accountId);
        } else if ("delete".equals(action) && id > 0) {
            AccountStore.delete(id);
            ActivityStore.add("ACCOUNT", "DELETE", req.getRemoteAddr(), "계정 삭제 ID=" + id);
        } else if ("createCategory".equals(action)) {
            String categoryName = WebParamUtils.text(req.getParameter("categoryName"));
            if (!categoryName.isBlank()) {
                AccountCategoryStore.create(categoryName);
                ActivityStore.add("ACCOUNT_CATEGORY", "CREATE", req.getRemoteAddr(), "계정 카테고리 등록: " + categoryName);
            }
        } else if ("deleteCategory".equals(action)) {
            long deleteCategoryId = WebParamUtils.parseLong(req.getParameter("deleteCategoryId"), -1L);
            AccountCategoryStore.delete(deleteCategoryId);
            ActivityStore.add("ACCOUNT_CATEGORY", "DELETE", req.getRemoteAddr(), "계정 카테고리 삭제 ID=" + deleteCategoryId);
        }

        resp.sendRedirect(req.getContextPath() + "/accounts");
    }
}
