<%-- 
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주메뉴 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <span id="delete_url" style="display: none;">/delete_mail.do?msgid=</span><!-- delete url -->
        <meta http-equiv="refresh" content="10"><!-- 1분(60초)마다 페이지 자동 갱신 -->
    </head>
    <body>
        <%@include file="header.jspf"%>

        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>

        <!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
        <div id="main">
            ${messageList}
        </div>

        <%@include file="footer.jspf"%>
        <script src="js/confirmMsg.js" type="text/javascript"></script>
        <span id="message" style="display: none;">${msg}</span><!-- alart message -->
        <script src="js/alartMsg.js"></script>
    </body>
</html>
