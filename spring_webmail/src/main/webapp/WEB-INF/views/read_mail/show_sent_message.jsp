<%-- 
    Document   : show_sent_message
    Created on : 2023. 5. 1., 오후 4:05:45
    Author     : Changjin Kim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>보낸 메일 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>

        <div id="sidebar">
            <jsp:include page="../sidebar_menu.jsp" />
        </div>

        <!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
        <div id="main">
            ${messageList}
        </div>

        <%@include file="../footer.jspf"%>
        
        <span id="message" style="display: none;">${msg}</span><!-- alart message -->
        <script src="js/alartMsg.js"></script>
    </body>
</html>
