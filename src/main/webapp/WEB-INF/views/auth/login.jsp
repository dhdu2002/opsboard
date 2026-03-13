<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>OpsBoard Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css" />
</head>
<body>
<main class="shell" style="padding:40px 0;">
    <section class="crud-form" style="max-width:460px; margin:0 auto;">
        <h2 style="margin-top:0;">OpsBoard 로그인</h2>
        <p style="color:#64748b;">사내 운영 포털 접근을 위해 로그인하세요.</p>

        <% if (request.getAttribute("loginError") != null) { %>
        <div style="background:#fee2e2; color:#991b1b; border:1px solid #fecaca; border-radius:8px; padding:10px; margin-bottom:12px;">
            <%= request.getAttribute("loginError") %>
        </div>
        <% } %>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="form-row">
                <label>아이디</label>
                <input type="text" name="username" required />
            </div>
            <div class="form-row">
                <label>비밀번호</label>
                <input type="password" name="password" required />
            </div>
            <div class="form-actions">
                <button type="submit" class="button">로그인</button>
            </div>
        </form>

        <hr style="border:none; border-top:1px solid #d8e0eb; margin:14px 0;">
        <small style="color:#64748b;">
            기본 계정: admin/admin1234! (ADMIN), user/user1234! (USER)
        </small>
    </section>
</main>
</body>
</html>
