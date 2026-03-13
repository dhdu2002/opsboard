<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="common/header.jspf" %>

<section class="home-actions">
    <a class="button" href="${pageContext.request.contextPath}/servers">서버 빠른 등록/관리</a>
    <a class="button" href="${pageContext.request.contextPath}/sites">사이트 빠른 등록/관리</a>
    <c:if test="${sessionScope.AUTH_ROLE eq 'ADMIN'}">
    <a class="button" href="${pageContext.request.contextPath}/accounts">계정 빠른 등록/관리</a>
    </c:if>
    <a class="button" href="${pageContext.request.contextPath}/issues">이슈 빠른 등록/관리</a>
</section>

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
    <c:if test="${sessionScope.AUTH_ROLE eq 'ADMIN'}">
    <article class="card">
        <h2>계정 정보</h2>
        <p>ID/PW 및 담당자 정보를 관리합니다.</p>
        <a class="button" href="${pageContext.request.contextPath}/accounts">이동</a>
    </article>
    </c:if>
    <article class="card">
        <h2>이슈 관리</h2>
        <p>운영 이슈 상태와 관련 대상을 관리합니다.</p>
        <a class="button" href="${pageContext.request.contextPath}/issues">이동</a>
    </article>
</section>

<section class="dashboard-block">
    <h2>최근 이슈 10건</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>제목</th>
            <th>상태</th>
            <th>담당자</th>
            <th>수정시각</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty recentIssues}">
                <tr>
                    <td colspan="5">최근 이슈가 없습니다.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="issue" items="${recentIssues}">
                    <tr>
                        <td>${issue.id}</td>
                        <td>${issue.issueTitle}</td>
                        <td>${issue.issueStatus}</td>
                        <td>${issue.assignee}</td>
                        <td>${issue.updatedAt}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
</section>

<section class="dashboard-block">
    <h2>최근 변경 내역 10건</h2>
    <table class="data-table">
        <thead>
        <tr>
            <th>모듈</th>
            <th>작업</th>
            <th>수행자</th>
            <th>요약</th>
            <th>시간</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${empty recentActivities}">
                <tr>
                    <td colspan="5">최근 변경 내역이 없습니다.</td>
                </tr>
            </c:when>
            <c:otherwise>
                <c:forEach var="item" items="${recentActivities}">
                    <tr>
                        <td>${item.module}</td>
                        <td>${item.action}</td>
                        <td>${item.actor}</td>
                        <td>${item.summary}</td>
                        <td>${item.createdAt}</td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <c:if test="${sessionScope.AUTH_ROLE eq 'ADMIN'}">
    <div class="dashboard-more">
        <a class="inline-link" href="${pageContext.request.contextPath}/activities">전체 변경이력 보기</a>
    </div>
    </c:if>
</section>

<%@ include file="common/footer.jspf" %>
