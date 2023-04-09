<%-- 
    Document   : set_password
    Created on : 2023. 4. 6., 오전 1:35:06
    Author     : 이수진
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType" %>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>비밀번호 변경 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    <script>
            <c:if test="${!empty msg}">
            alert("${msg}");    
            </c:if>
    </script>
    </head>
    <body>
        <%@include file="header.jspf" %>
        <%
            String id = (String) session.getAttribute("userid");
            if(id.equals("admin")){
        %>
        <div id="sidebar">
            <jsp:include page="admin/sidebar_admin_previous_menu.jsp" />
        </div>
        <%}else{%>
        <div id="sidebar">
            <jsp:include page="sidebar_previous_menu.jsp" />
        </div>
        <%}%>
        <div id="main">
            현재 비밀번호와 변경할 비밀번호를 입력해 주시기 바랍니다. <br> <br>

            <form name="SetPassword" action="set_password.do" method="POST">
                <table border="0" align="left">
                    <tr>
                        <td>현재 비밀번호 </td>
                        <td> <input type="password" name="currentPassword" value="" /> </td>
                    </tr>
                    <tr>
                        <td>새 비밀번호 </td>
                        <td> <input type="password" name="newPassword" value="" /> </td>
                    </tr>
                    <tr>
                        <td>새 비밀번호 확인 </td>
                        <td> <input type="password" name="checkPassword" value="" /> </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="변경" name="change" />
                            <input type="reset" value="초기화" name="reset" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <%@include file="footer.jspf" %>
    </body>
</html>