<%-- 
    Document   : login_fail
    Created on : 2022. 6. 10., 오후 3:28:28
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>




<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>로그인 실패</title>
    <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    <script type="text/javascript">
        function gohome() {
            window.location.href = "/login";
        }

        window.onload = function() {
            setTimeout(gohome, 5000);
        };
    </script>
</head>
<%@include file="header.jspf"%>

<p id="login_fail">
    <%= request.getParameter("userid")%>님, 로그인이 실패하였습니다.

    올바른 사용자 ID와 암호를 사용하여 로그인하시기 바랍니다.

    <a href="/login" title="초기 화면">초기 화면</a>
</p>

<%@include file="footer.jspf"%>

</body>
</html>
