package com.opsboard.common.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * 정적 리소스(css/js) 요청을 직접 서빙하는 서블릿이다.
 * 생성일: 2026-03-13
 * 작성자: OpsBoard
 */
@WebServlet(name = "assetServlet", urlPatterns = {"/assets/*"})
public class AssetServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(AssetServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.isBlank() || "/".equals(pathInfo)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String resourcePath = "/assets" + pathInfo;
        InputStream in = getServletContext().getResourceAsStream(resourcePath);
        if (in == null) {
            LOGGER.info("[ASSET] not found: " + resourcePath);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = getServletContext().getMimeType(resourcePath);
        if (contentType == null) {
            if (resourcePath.endsWith(".css")) {
                contentType = "text/css";
            } else if (resourcePath.endsWith(".js")) {
                contentType = "application/javascript";
            } else {
                contentType = "application/octet-stream";
            }
        }

        resp.setContentType(contentType);
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);
        LOGGER.info("[ASSET] serving " + resourcePath + " as " + contentType);

        try (InputStream input = in; OutputStream output = resp.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.write(buffer, 0, read);
            }
            output.flush();
        }
    }
}
