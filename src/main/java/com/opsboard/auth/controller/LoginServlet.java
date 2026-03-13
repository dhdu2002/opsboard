package com.opsboard.auth.controller;

import com.opsboard.auth.model.SessionKeys;
import com.opsboard.auth.model.UserAccount;
import com.opsboard.auth.service.AuthService;
import com.opsboard.auth.service.impl.AuthServiceImpl;
import com.opsboard.common.util.WebParamUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * 사용자 로그인 요청(화면/인증)을 처리한다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private final AuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Login");
        req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = WebParamUtils.text(req.getParameter("username"));
        String password = WebParamUtils.text(req.getParameter("password"));

        UserAccount account = authService.authenticate(username, password);
        if (account == null) {
            LOGGER.info(() -> "[AUTH] login failed user=" + username + ", from=" + req.getRemoteAddr());
            req.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
            req.setAttribute("pageTitle", "Login");
            req.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute(SessionKeys.AUTH_USER, account.getUsername());
        session.setAttribute(SessionKeys.AUTH_ROLE, account.getRole());
        LOGGER.info(() -> "[AUTH] login success user=" + username + ", role=" + account.getRole() + ", from=" + req.getRemoteAddr());
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
