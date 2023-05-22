<%-- 
    Document   : modify_addrbook.jsp
    Author     : CHANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록 수정</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>

        <div id="sidebar">
            <jsp:include page="../sidebar_menu.jsp" />
        </div>

        <div id="main">
            <form enctype="multipart/form-data" method="POST" action="update.do" >
                <table>
                    <tbody>
                        <tr>
                            <td>이메일</td>
                            <td>${email}</td>
                            <input type="hidden" name="email" value="${email}" />
                        </tr>
                        <tr>
                            <td>이름</td>
                            <td><input type="text" name="name" size="20"/></td>
                        </tr>
                        <tr>
                            <td>전화번호</td>
                            <td><input type="text" name="phone" value="" size="20"/></td>
                        </tr>
                        <tr>
                            <td colspan="2">
                    <center>
                        <input type="submit" value="추가"/><input type="reset" value="취소"/>
                    </center>
                    </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>

        <br><br>

        <%@include file="../footer.jspf"%>
        
        <span id="message" style="display: none;">${msg}</span><!-- alart message -->
        <script src="js/alartMsg.js"></script>
    </body>
</html>
