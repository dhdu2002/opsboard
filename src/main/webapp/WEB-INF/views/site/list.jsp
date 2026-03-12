<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../common/header.jspf" %>

<h2>사이트/관리페이지 목록</h2>
<p>초기 스캐폴드 단계입니다. 다음 단계에서 CRUD를 연결합니다.</p>

<table class="data-table">
    <thead>
    <tr>
        <th>사이트명</th>
        <th>사이트 URL</th>
        <th>관리 URL</th>
        <th>연결 서버</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>예시 사이트</td>
        <td>https://example.internal</td>
        <td>https://admin.example.internal</td>
        <td>예시-서버-01</td>
    </tr>
    </tbody>
</table>

<%@ include file="../common/footer.jspf" %>
