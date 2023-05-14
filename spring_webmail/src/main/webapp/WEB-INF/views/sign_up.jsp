<%-- 
    Document   : sign_up
    Created on : 2023. 4. 12., 오전 12:45:09
    Author     : 이수진
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원가입 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="header.jspf" %>

        <div id="sidebar">
            <br> <br>
        
            <a href="${pageContext.request.contextPath}"> 이전 메뉴로 </a>
        </div>

        <div id="main">
            회원가입을 위해 정보를 입력해주세요. <br> <br>

            <form name="SignUp" action="sign_up.do" method="POST">
                <table border="0" align="left">
                    <tr>
                        <td>사용자 ID</td>
                        <td> <input type="text" name="id" value="" size="20" maxlength="10" />  </td>
                    </tr>
                    <tr>
                        <td>비밀번호 </td>
                        <td> <input type="password" name="password" value="" maxlength="20" /> </td>
                    </tr>
                    <tr>
                        <td>비밀번호 확인 </td>
                        <td> <input type="password" name="checkPassword" value="" maxlength="20"/></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="등록" name="register" />
                            <input type="reset" value="초기화" name="reset" />
                        </td>
                    </tr>
                </table>
                
            </form>
        </div>

        <%@include file="footer.jspf" %>
    </body>
</html>
