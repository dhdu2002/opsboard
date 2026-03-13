<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../common/header.jspf" %>

<h2>사이트/관리페이지 목록</h2>
<p>DB 연동 전 단계: 메모리 기반 CRUD</p>

<form method="get" action="${pageContext.request.contextPath}/sites" class="search-form">
    <input type="text" name="keyword" value="${keyword}" placeholder="사이트명, URL, 메모로 검색" />
    <select name="categoryId">
        <option value="0" ${selectedCategoryId == 0 ? 'selected' : ''}>전체</option>
        <c:forEach var="category" items="${categories}">
            <c:if test="${category.name != '전체'}">
                <option value="${category.id}" ${selectedCategoryId == category.id ? 'selected' : ''}>${category.name}</option>
            </c:if>
        </c:forEach>
    </select>
    <button type="submit" class="button secondary">검색</button>
    <a class="button secondary" href="${pageContext.request.contextPath}/sites">초기화</a>
</form>

<form method="post" action="${pageContext.request.contextPath}/sites" class="crud-form">
    <input type="hidden" name="action" value="${editItem != null ? 'update' : 'create'}" />
    <c:if test="${editItem != null}">
        <input type="hidden" name="id" value="${editItem.id}" />
    </c:if>

    <div class="form-row">
        <label>카테고리</label>
        <select name="categoryId" required>
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}" ${editItem != null && editItem.categoryId == category.id ? 'selected' : ''}>${category.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-row">
        <label>연결 서버</label>
        <select name="serverId" required>
            <c:forEach var="server" items="${servers}">
                <option value="${server.id}" ${editItem != null && editItem.serverId == server.id ? 'selected' : ''}>
                    ${server.name}
                </option>
            </c:forEach>
        </select>
    </div>
    <div class="form-row">
        <label>사이트명</label>
        <input type="text" name="siteName" value="${editItem != null ? editItem.siteName : ''}" required />
    </div>
    <div class="form-row">
        <label>사이트 URL</label>
        <input type="text" name="siteUrl" value="${editItem != null ? editItem.siteUrl : ''}" required />
    </div>
    <div class="form-row">
        <label>관리 URL</label>
        <input type="text" name="adminUrl" value="${editItem != null ? editItem.adminUrl : ''}" required />
    </div>
    <div class="form-row">
        <label>메모</label>
        <input type="text" name="notes" value="${editItem != null ? editItem.notes : ''}" />
    </div>

    <div class="form-actions">
        <button type="submit" class="button">${editItem != null ? '수정 저장' : '신규 등록'}</button>
        <c:if test="${editItem != null}">
            <a class="button secondary" href="${pageContext.request.contextPath}/sites">취소</a>
        </c:if>
    </div>
</form>

<table class="data-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>카테고리</th>
        <th>사이트명</th>
        <th>사이트 URL</th>
        <th>관리 URL</th>
        <th>연결 서버</th>
        <th>메모</th>
        <th>작업</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td>${categoryNameMap[item.categoryId]}</td>
            <td>${item.siteName}</td>
            <td>${item.siteUrl}</td>
            <td>${item.adminUrl}</td>
            <td>${serverNameMap[item.serverId]}</td>
            <td>${item.notes}</td>
            <td>
                <a class="inline-link" href="${pageContext.request.contextPath}/sites?editId=${item.id}">수정</a>
                <c:if test="${isAdmin}">
                    <form method="post" action="${pageContext.request.contextPath}/sites" class="inline-form">
                        <input type="hidden" name="action" value="delete" />
                        <input type="hidden" name="id" value="${item.id}" />
                        <button type="submit" class="inline-link danger">삭제</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${isAdmin}">
    <h3>카테고리 관리</h3>
    <form method="post" action="${pageContext.request.contextPath}/sites" class="crud-form compact-form">
        <input type="hidden" name="action" value="createCategory" />
        <div class="form-row">
            <label>신규 카테고리</label>
            <input type="text" name="categoryName" required />
        </div>
        <div class="form-actions">
            <button type="submit" class="button">카테고리 추가</button>
        </div>
    </form>

    <table class="data-table">
        <thead>
        <tr>
            <th>ID</th>
            <th>카테고리명</th>
            <th>작업</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>
                    <c:if test="${category.name != '전체'}">
                        <form method="post" action="${pageContext.request.contextPath}/sites" class="inline-form">
                            <input type="hidden" name="action" value="deleteCategory" />
                            <input type="hidden" name="deleteCategoryId" value="${category.id}" />
                            <button type="submit" class="inline-link danger">삭제</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<%@ include file="../common/footer.jspf" %>
