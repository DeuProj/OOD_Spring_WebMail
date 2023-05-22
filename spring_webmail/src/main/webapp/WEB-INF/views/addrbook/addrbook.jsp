<%-- 
    Document   : addrbook.jsp
    Author     : CHANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>
        
        <div id="sidebar">
            <jsp:include page="../sidebar_menu.jsp" />
        </div>
        
        <br>
        <br>
        
        <table>
            <thead>
                <tr>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>전화번호</th>
                    <th colspan='2'></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="row" items="${dataRows}">
                    <tr>
                        <td>${row.name}</td>
                        <td>${row.email}</td>
                        <td>${row.phone}</td>
                        <td><a href="/modify_addrbook?&email=${row.email}">수정</a></td>
                        <td><a id="${row.email}" class="delete-link">삭제</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <br><br>

        <a href="/insert_addrbook">주소록 추가</a>
        
        <%@include file="../footer.jspf"%>
        
        <script src="js/confirmMsg.js" type="text/javascript"></script>
        <span id="message" style="display: none;">${msg}</span><!-- alart message -->
        <script src="js/alartMsg.js"></script>
    </body>
</html>
