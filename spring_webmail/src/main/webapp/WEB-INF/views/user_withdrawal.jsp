<%-- 
    Document   : user_withdraw
    Created on : 2023. 5. 2.
    Author     : 이수진
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원 탈퇴 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script>
            <c:if test="${!empty msg}">
                alert("${msg}");
            </c:if>
        </script>
    </head>
    <body>
        <%@include file="header.jspf" %>
        <div id="sidebar">
            <jsp:include page="sidebar_previous_menu.jsp" />
        </div>

        <div id="main">
            현재 비밀번호를 입력하고 탈퇴 버튼을 눌러주세요. <br> <br>
            <form name="UserWithdraw" action="user_withdraw.do" method="POST">
            <table border="0" align="left">
                <tr>
                    <td>현재 비밀번호 </td>
                    <td> <input type="password" name="currentPassword" value="" /> </td>
                </tr>
                <tr>
                    <td colspan="2">
                    <input type="submit" value="탈퇴" name="withdraw" onclick="return confirm('정말로 탈퇴하시겠습니까?');" />
                    <input type="reset" value="초기화" name="reset" />
                    </td>
                </tr>
            </table>
            </form>
        </div>
        <%@include file="footer.jspf" %>
    </body>
</html>
