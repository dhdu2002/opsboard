package com.opsboard.dashboard.controller;

import com.opsboard.activity.store.ActivityStore;
import com.opsboard.issue.store.IssueStore;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 대시보드 진입 페이지 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "homeServlet", urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(HomeServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info(() -> "[HOME] GET /home from " + req.getRemoteAddr());
        req.setAttribute("pageTitle", "OpsBoard Dashboard");
        req.setAttribute("recentIssues", IssueStore.findRecent(10));
        req.setAttribute("recentActivities", ActivityStore.findRecent(10));
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
