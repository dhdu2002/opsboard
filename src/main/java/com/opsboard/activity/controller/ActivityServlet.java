package com.opsboard.activity.controller;

import com.opsboard.activity.store.ActivityStore;
import com.opsboard.common.util.WebParamUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 변경이력 조회 화면 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "activityServlet", urlPatterns = {"/activities"})
public class ActivityServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ActivityServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(() -> "[ACTIVITY] GET /activities from " + req.getRemoteAddr());
        String keyword = WebParamUtils.text(req.getParameter("keyword"));
        String module = WebParamUtils.text(req.getParameter("module"));
        String action = WebParamUtils.text(req.getParameter("action"));

        req.setAttribute("pageTitle", "Change History");
        req.setAttribute("keyword", keyword);
        req.setAttribute("selectedModule", module.isBlank() ? "전체" : module);
        req.setAttribute("selectedAction", action.isBlank() ? "전체" : action);
        req.setAttribute("modules", ActivityStore.modules());
        req.setAttribute("actions", ActivityStore.actions());
        req.setAttribute("items", ActivityStore.findByCondition(200, keyword, module, action));
        req.getRequestDispatcher("/WEB-INF/views/activity/list.jsp").forward(req, resp);
    }
}
