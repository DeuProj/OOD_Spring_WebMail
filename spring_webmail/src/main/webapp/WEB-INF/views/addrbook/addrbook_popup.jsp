<%-- 
    Document   : addrbook_popup.jsp
    Author     : CHANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록 팝업</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%-- write_mail에 주소 삽입을 위한 부분 --%>
        <input type="hidden" id="addressString" value="${addressString}" />
        
        
        <form enctype="multipart/form-data" method="POST" action="use_address.do" >
            <table>
                <thead>
                    <tr>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>전화번호</th>
                        <th>선택</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${dataRows}">
                        <tr>
                            <td>${row.name}</td>
                            <td>${row.email}</td>
                            <td>${row.phone}</td>
                            <td><input type=checkbox name="selectedAddr" value="${row.email}" /></td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4">
                        <center>
                            <input type="submit" value="선택주소 사용"/>
                        </center>
                    </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <br>
        <script src="js/insertAddresses.js"></script>
    </body>
    
    
</html>
