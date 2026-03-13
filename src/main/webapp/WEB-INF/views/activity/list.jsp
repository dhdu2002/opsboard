<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../common/header.jspf" %>

<h2>변경이력</h2>
<p>최근 변경 내역(최대 200건)을 확인합니다.</p>

<form method="get" action="${pageContext.request.contextPath}/activities" class="search-form">
    <input type="text" name="keyword" value="${keyword}" placeholder="모듈, 작업, 수행자, 요약으로 검색" />
    <select name="module">
        <option value="전체" ${selectedModule == '전체' ? 'selected' : ''}>전체</option>
        <c:forEach var="moduleName" items="${modules}">
            <option value="${moduleName}" ${selectedModule == moduleName ? 'selected' : ''}>${moduleName}</option>
        </c:forEach>
    </select>
    <select name="action">
        <option value="전체" ${selectedAction == '전체' ? 'selected' : ''}>전체</option>
        <c:forEach var="actionName" items="${actions}">
            <option value="${actionName}" ${selectedAction == actionName ? 'selected' : ''}>${actionName}</option>
        </c:forEach>
    </select>
    <button type="submit" class="button secondary">검색</button>
    <a class="button secondary" href="${pageContext.request.contextPath}/activities">초기화</a>
</form>

<table class="data-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>모듈</th>
        <th>작업</th>
        <th>수행자</th>
        <th>요약</th>
        <th>시간</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${empty items}">
            <tr>
                <td colspan="6">아직 변경 이력이 없습니다.</td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.id}</td>
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

<%@ include file="../common/footer.jspf" %>
