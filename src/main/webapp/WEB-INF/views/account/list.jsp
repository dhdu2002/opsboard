<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../common/header.jspf" %>

<h2>계정 목록</h2>
<p>DB 연동 전 단계: 메모리 기반 CRUD</p>

<form method="get" action="${pageContext.request.contextPath}/accounts" class="search-form">
    <input type="text" name="keyword" value="${keyword}" placeholder="계정 ID, 담당자, 메모로 검색" />
    <select name="categoryId">
        <option value="0" ${selectedCategoryId == 0 ? 'selected' : ''}>전체</option>
        <c:forEach var="category" items="${categories}">
            <c:if test="${category.name != '전체'}">
                <option value="${category.id}" ${selectedCategoryId == category.id ? 'selected' : ''}>${category.name}</option>
            </c:if>
        </c:forEach>
    </select>
    <button type="submit" class="button secondary">검색</button>
    <a class="button secondary" href="${pageContext.request.contextPath}/accounts">초기화</a>
</form>

<form method="post" action="${pageContext.request.contextPath}/accounts" class="crud-form">
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
        <label>대상 사이트</label>
        <select name="siteId" required>
            <c:forEach var="site" items="${sites}">
                <option value="${site.id}" ${editItem != null && editItem.siteId == site.id ? 'selected' : ''}>
                    ${site.siteName}
                </option>
            </c:forEach>
        </select>
    </div>
    <div class="form-row">
        <label>계정 ID</label>
        <input type="text" name="accountId" value="${editItem != null ? editItem.accountId : ''}" required />
    </div>
    <div class="form-row">
        <label>비밀번호</label>
        <input type="text" name="accountPassword" value="${editItem != null ? editItem.accountPassword : ''}" required />
    </div>
    <div class="form-row">
        <label>담당자</label>
        <input type="text" name="contactOwner" value="${editItem != null ? editItem.contactOwner : ''}" />
    </div>
    <div class="form-row">
        <label>메모</label>
        <input type="text" name="notes" value="${editItem != null ? editItem.notes : ''}" />
    </div>

    <div class="form-actions">
        <button type="submit" class="button">${editItem != null ? '수정 저장' : '신규 등록'}</button>
        <c:if test="${editItem != null}">
            <a class="button secondary" href="${pageContext.request.contextPath}/accounts">취소</a>
        </c:if>
    </div>
</form>

<table class="data-table">
    <thead>
    <tr>
        <th>ID</th>
        <th>카테고리</th>
        <th>계정 ID</th>
        <th>비밀번호</th>
        <th>대상 사이트</th>
        <th>담당자</th>
        <th>메모</th>
        <th>작업</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td>${categoryNameMap[item.categoryId]}</td>
            <td>${item.accountId}</td>
            <td>${item.accountPassword}</td>
            <td>${siteNameMap[item.siteId]}</td>
            <td>${item.contactOwner}</td>
            <td>${item.notes}</td>
            <td>
                <a class="inline-link" href="${pageContext.request.contextPath}/accounts?editId=${item.id}">수정</a>
                <form method="post" action="${pageContext.request.contextPath}/accounts" class="inline-form">
                    <input type="hidden" name="action" value="delete" />
                    <input type="hidden" name="id" value="${item.id}" />
                    <button type="submit" class="inline-link danger">삭제</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<c:if test="${isAdmin}">
    <h3>카테고리 관리</h3>
    <form method="post" action="${pageContext.request.contextPath}/accounts" class="crud-form compact-form">
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
                        <form method="post" action="${pageContext.request.contextPath}/accounts" class="inline-form">
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
