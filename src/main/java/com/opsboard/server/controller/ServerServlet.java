package com.opsboard.server.controller;

import com.opsboard.activity.store.ActivityStore;
import com.opsboard.auth.model.SessionKeys;
import com.opsboard.common.util.WebParamUtils;
import com.opsboard.server.model.ServerAsset;
import com.opsboard.server.model.ServerCategory;
import com.opsboard.server.store.ServerCategoryStore;
import com.opsboard.server.store.ServerStore;
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
 * 서버 자산 관리 화면의 메모리 기반 CRUD 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "serverServlet", urlPatterns = {"/servers"})
public class ServerServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServerServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(() -> "[SERVER] GET /servers from " + req.getRemoteAddr());
        String keyword = WebParamUtils.text(req.getParameter("keyword"));
        long selectedCategoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), 0L);
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedCategoryId", selectedCategoryId);
        req.setAttribute("pageTitle", "Server Management");
        req.setAttribute("items", ServerStore.findByCondition(keyword, selectedCategoryId));
        req.setAttribute("categories", ServerCategoryStore.findAll());
        Map<Long, String> categoryNameMap = ServerCategoryStore.findAll().stream()
                .collect(Collectors.toMap(ServerCategory::getId, ServerCategory::getName));
        req.setAttribute("categoryNameMap", categoryNameMap);
        req.setAttribute("isAdmin", "ADMIN".equals(String.valueOf(req.getSession().getAttribute(SessionKeys.AUTH_ROLE))));

        long editId = WebParamUtils.parseLong(req.getParameter("editId"), -1L);
        if (editId > 0) {
            ServerAsset editItem = ServerStore.findById(editId);
            req.setAttribute("editItem", editItem);
        }

        req.getRequestDispatcher("/WEB-INF/views/server/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String action = WebParamUtils.text(req.getParameter("action"));
        long id = WebParamUtils.parseLong(req.getParameter("id"), -1L);
        long categoryId = WebParamUtils.parseLong(req.getParameter("categoryId"), ServerCategoryStore.defaultCategoryId());
        boolean isAdmin = "ADMIN".equals(String.valueOf(req.getSession().getAttribute(SessionKeys.AUTH_ROLE)));

        LOGGER.info(() -> "[SERVER] POST /servers action=" + action + ", id=" + id + ", by=" + req.getRemoteAddr());

        if ("create".equals(action)) {
            String name = WebParamUtils.text(req.getParameter("name"));
            ServerStore.create(
                    categoryId,
                    name,
                    WebParamUtils.text(req.getParameter("ipAddress")),
                    WebParamUtils.text(req.getParameter("environment")),
                    WebParamUtils.text(req.getParameter("usageDescription")),
                    WebParamUtils.text(req.getParameter("notes"))
            );
            ActivityStore.add("SERVER", "CREATE", req.getRemoteAddr(), "서버 등록: " + name);
        } else if ("update".equals(action) && id > 0) {
            String name = WebParamUtils.text(req.getParameter("name"));
            ServerStore.update(
                    id,
                    categoryId,
                    name,
                    WebParamUtils.text(req.getParameter("ipAddress")),
                    WebParamUtils.text(req.getParameter("environment")),
                    WebParamUtils.text(req.getParameter("usageDescription")),
                    WebParamUtils.text(req.getParameter("notes"))
            );
            ActivityStore.add("SERVER", "UPDATE", req.getRemoteAddr(), "서버 수정 ID=" + id + ", name=" + name);
        } else if ("delete".equals(action) && id > 0 && isAdmin) {
            ServerStore.delete(id);
            ActivityStore.add("SERVER", "DELETE", req.getRemoteAddr(), "서버 삭제 ID=" + id);
        } else if ("createCategory".equals(action) && isAdmin) {
            String categoryName = WebParamUtils.text(req.getParameter("categoryName"));
            if (!categoryName.isBlank()) {
                ServerCategoryStore.create(categoryName);
                ActivityStore.add("SERVER_CATEGORY", "CREATE", req.getRemoteAddr(), "서버 카테고리 등록: " + categoryName);
            }
        } else if ("deleteCategory".equals(action) && isAdmin) {
            long deleteCategoryId = WebParamUtils.parseLong(req.getParameter("deleteCategoryId"), -1L);
            ServerCategoryStore.delete(deleteCategoryId);
            ActivityStore.add("SERVER_CATEGORY", "DELETE", req.getRemoteAddr(), "서버 카테고리 삭제 ID=" + deleteCategoryId);
        }

        resp.sendRedirect(req.getContextPath() + "/servers");
    }
}
