package com.opsboard.auth.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 사용자 로그아웃 요청을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "logoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("AUTH_USER");
            LOGGER.info(() -> "[AUTH] logout user=" + user + ", from=" + req.getRemoteAddr());
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
