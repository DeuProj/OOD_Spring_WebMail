<%-- 
    Document   : write_mail.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%-- @taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" --%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>메일 쓰기 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>

        <div id="sidebar">
            <jsp:include page="../sidebar_previous_menu.jsp" /> <br> <br>
            <a href="/addrbook_popup" class="popup-link">주소록</a> <br>
        </div>
                
        <div id="main">
            <jsp:include page="mail_send_form.jsp" />
        </div>

        <%@include file="../footer.jspf"%>
        <script src="/js/popup.js"></script><!-- popup address book -->
        
        <span id="message" style="display: none;">${msg}</span><!-- alart message -->
        <script src="js/alartMsg.js"></script>
    </body>
</html>
