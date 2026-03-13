package com.opsboard.site.controller;

import com.opsboard.activity.store.ActivityStore;
import com.opsboard.auth.model.SessionKeys;
import com.opsboard.common.util.WebParamUtils;
import com.opsboard.server.store.ServerStore;
import com.opsboard.site.model.SiteAsset;
import com.opsboard.site.model.SiteCategory;
import com.opsboard.site.store.SiteCategoryStore;
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
 * 사이트/관리페이지 관리 화면의 메모리 기반 CRUD 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "siteServlet", urlPatterns = {"/sites"})
public class SiteServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SiteServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(() -> "[SITE] GET /sites from " + req.getRemoteAddr());
        String keyword = WebParamUtils.text(req.getParameter("keyword"));
        long selectedCategoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), 0L);
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedCategoryId", selectedCategoryId);
        req.setAttribute("pageTitle", "Site/Admin Page Management");
        req.setAttribute("items", SiteStore.findByCondition(keyword, selectedCategoryId));
        req.setAttribute("servers", ServerStore.findAll());
        req.setAttribute("categories", SiteCategoryStore.findAll());
        Map<Long, String> serverNameMap = ServerStore.findAll().stream()
                .collect(Collectors.toMap(item -> item.getId(), item -> item.getName()));
        Map<Long, String> categoryNameMap = SiteCategoryStore.findAll().stream()
                .collect(Collectors.toMap(SiteCategory::getId, SiteCategory::getName));
        req.setAttribute("serverNameMap", serverNameMap);
        req.setAttribute("categoryNameMap", categoryNameMap);
        req.setAttribute("isAdmin", "ADMIN".equals(String.valueOf(req.getSession().getAttribute(SessionKeys.AUTH_ROLE))));

        long editId = WebParamUtils.parseLong(req.getParameter("editId"), -1L);
        if (editId > 0) {
            SiteAsset editItem = SiteStore.findById(editId);
            req.setAttribute("editItem", editItem);
        }

        req.getRequestDispatcher("/WEB-INF/views/site/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String action = WebParamUtils.text(req.getParameter("action"));
        long id = WebParamUtils.parseLong(req.getParameter("id"), -1L);
        long categoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), SiteCategoryStore.defaultCategoryId());
        long serverId = WebParamUtils.parseLong(req.getParameter("serverId"), 0L);
        boolean isAdmin = "ADMIN".equals(String.valueOf(req.getSession().getAttribute(SessionKeys.AUTH_ROLE)));

        LOGGER.info(() -> "[SITE] POST /sites action=" + action + ", id=" + id + ", serverId=" + serverId + ", by=" + req.getRemoteAddr());

        if ("create".equals(action)) {
            String siteName = WebParamUtils.text(req.getParameter("siteName"));
            SiteStore.create(
                    categoryId,
                    serverId,
                    siteName,
                    WebParamUtils.text(req.getParameter("siteUrl")),
                    WebParamUtils.text(req.getParameter("adminUrl")),
                    WebParamUtils.text(req.getParameter("notes"))
            );
            ActivityStore.add("SITE", "CREATE", req.getRemoteAddr(), "사이트 등록: " + siteName);
        } else if ("update".equals(action) && id > 0) {
            String siteName = WebParamUtils.text(req.getParameter("siteName"));
            SiteStore.update(
                    id,
                    categoryId,
                    serverId,
                    siteName,
                    WebParamUtils.text(req.getParameter("siteUrl")),
                    WebParamUtils.text(req.getParameter("adminUrl")),
                    WebParamUtils.text(req.getParameter("notes"))
            );
            ActivityStore.add("SITE", "UPDATE", req.getRemoteAddr(), "사이트 수정 ID=" + id + ", name=" + siteName);
        } else if ("delete".equals(action) && id > 0 && isAdmin) {
            SiteStore.delete(id);
            ActivityStore.add("SITE", "DELETE", req.getRemoteAddr(), "사이트 삭제 ID=" + id);
        } else if ("createCategory".equals(action) && isAdmin) {
            String categoryName = WebParamUtils.text(req.getParameter("categoryName"));
            if (!categoryName.isBlank()) {
                SiteCategoryStore.create(categoryName);
                ActivityStore.add("SITE_CATEGORY", "CREATE", req.getRemoteAddr(), "사이트 카테고리 등록: " + categoryName);
            }
        } else if ("deleteCategory".equals(action) && isAdmin) {
            long deleteCategoryId = WebParamUtils.parseLong(req.getParameter("deleteCategoryId"), -1L);
            SiteCategoryStore.delete(deleteCategoryId);
            ActivityStore.add("SITE_CATEGORY", "DELETE", req.getRemoteAddr(), "사이트 카테고리 삭제 ID=" + deleteCategoryId);
        }

        resp.sendRedirect(req.getContextPath() + "/sites");
    }
}
