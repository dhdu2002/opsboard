<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../common/header.jspf" %>

<h2>이슈 목록</h2>
<p>DB 연동 전 단계: 메모리 기반 CRUD</p>

<form method="get" action="${pageContext.request.contextPath}/issues" class="search-form">
    <input type="text" name="keyword" value="${keyword}" placeholder="제목, 상태, 담당자, 상세로 검색" />
    <select name="categoryId">
        <option value="0" ${selectedCategoryId == 0 ? 'selected' : ''}>전체</option>
        <c:forEach var="category" items="${categories}">
            <c:if test="${category.name != '전체'}">
                <option value="${category.id}" ${selectedCategoryId == category.id ? 'selected' : ''}>${category.name}</option>
            </c:if>
        </c:forEach>
    </select>
    <button type="submit" class="button secondary">검색</button>
    <a class="button secondary" href="${pageContext.request.contextPath}/issues">초기화</a>
</form>

<form method="post" action="${pageContext.request.contextPath}/issues" class="crud-form">
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
        <label>제목</label>
        <input type="text" name="issueTitle" value="${editItem != null ? editItem.issueTitle : ''}" required />
    </div>
    <div class="form-row">
        <label>상태</label>
        <input type="text" name="issueStatus" value="${editItem != null ? editItem.issueStatus : ''}" required />
    </div>
    <div class="form-row">
        <label>담당자</label>
        <input type="text" name="assignee" value="${editItem != null ? editItem.assignee : ''}" />
    </div>
    <div class="form-row">
        <label>관련 서버</label>
        <select name="relatedServerId">
            <option value="">선택 안함</option>
            <c:forEach var="server" items="${servers}">
                <option value="${server.id}" ${editItem != null && editItem.relatedServerId == server.id ? 'selected' : ''}>${server.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="form-row">
        <label>관련 사이트</label>
        <select name="relatedSiteId">
            <option value="">선택 안함</option>
            <c:forEach var="site" items="${sites}">
                <option value="${site.id}" ${editItem != null && editItem.relatedSiteId == site.id ? 'selected' : ''}>${site.siteName}</option>
            </c:forEach>
        </select>
    </div>
    <c:if test="${isAdmin}">
    <div class="form-row">
        <label>관련 계정</label>
        <select name="relatedAccountId">
            <option value="">선택 안함</option>
            <c:forEach var="account" items="${accounts}">
                <option value="${account.id}" ${editItem != null && editItem.relatedAccountId == account.id ? 'selected' : ''}>${account.accountId}</option>
            </c:forEach>
        </select>
    </div>
    </c:if>
    <div class="form-row">
        <label>상세</label>
        <input type="text" name="details" value="${editItem != null ? editItem.details : ''}" />
    </div>

    <div class="form-actions">
        <button type="submit" class="button">${editItem != null ? '수정 저장' : '신규 등록'}</button>
        <c:if test="${editItem != null}">
            <a class="button secondary" href="${pageContext.request.contextPath}/issues">취소</a>
        </c:if>
    </div>
</form>

<table class="data-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>카테고리</th>
            <th>제목</th>
            <th>상태</th>
            <th>담당자</th>
            <th>관련 서버</th>
            <th>관련 사이트</th>
            <c:if test="${isAdmin}">
            <th>관련 계정</th>
            </c:if>
            <th>상세</th>
            <th>작업</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td>${categoryNameMap[item.categoryId]}</td>
            <td>${item.issueTitle}</td>
            <td>${item.issueStatus}</td>
            <td>${item.assignee}</td>
            <td>${serverNameMap[item.relatedServerId]}</td>
            <td>${siteNameMap[item.relatedSiteId]}</td>
            <c:if test="${isAdmin}">
            <td>${accountNameMap[item.relatedAccountId]}</td>
            </c:if>
            <td>${item.details}</td>
            <td>
                <a class="inline-link" href="${pageContext.request.contextPath}/issues?editId=${item.id}">수정</a>
                <c:if test="${isAdmin}">
                    <form method="post" action="${pageContext.request.contextPath}/issues" class="inline-form">
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
    <form method="post" action="${pageContext.request.contextPath}/issues" class="crud-form compact-form">
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
                        <form method="post" action="${pageContext.request.contextPath}/issues" class="inline-form">
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
