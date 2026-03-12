<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="common/header.jspf" %>

<section class="card-grid">
    <article class="card">
        <h2>서버 관리</h2>
        <p>서버 자산, IP, 운영환경, 메모를 관리합니다.</p>
        <a class="button" href="${pageContext.request.contextPath}/servers">이동</a>
    </article>
    <article class="card">
        <h2>사이트/관리페이지</h2>
        <p>사이트 URL, 관리자 페이지 URL, 서버 연계를 관리합니다.</p>
        <a class="button" href="${pageContext.request.contextPath}/sites">이동</a>
    </article>
    <article class="card">
        <h2>계정 정보</h2>
        <p>ID/PW 및 담당자 정보를 관리합니다.</p>
        <a class="button" href="${pageContext.request.contextPath}/accounts">이동</a>
    </article>
    <article class="card">
        <h2>이슈 관리</h2>
        <p>운영 이슈 상태와 관련 대상을 관리합니다.</p>
        <a class="button" href="${pageContext.request.contextPath}/issues">이동</a>
    </article>
</section>

<%@ include file="common/footer.jspf" %>
