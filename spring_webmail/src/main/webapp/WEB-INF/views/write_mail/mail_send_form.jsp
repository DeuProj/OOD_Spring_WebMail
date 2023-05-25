<%-- 
    Document   : mail_send_form
    Created on : 2023. 5. 23., 오전 2:56:46
    Author     : CHANG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<form enctype="multipart/form-data" method="POST" action="write_mail.do" id="mailForm" >
<table>
    <tr>
        <td> 수신 </td>
        <td> <input type="text" name="to" size="80"
                    value="${!empty param['sender'] ? param['sender'] : ''}" />
        </td>
    </tr>
    <tr>
        <td>참조</td>
        <td> <input type="text" name="cc" size="80">  </td>
    </tr>
    <tr>
        <td> 메일 제목 </td>
        <td> <input type="text" name="subj" size="80" 
                    value="${!empty param['sender'] ? "RE: " + sessionScope['subject'] : ''}" >  </td>
    </tr>
    <tr>
        <td colspan="2">본  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 문</td>
    </tr>
    <tr>  <%-- TextArea    --%>
        <td colspan="2">
            <textarea rows="15" name="body" cols="80">${!empty param['sender'] ?
"



----
" + sessionScope['body'] : ''}</textarea> 
        </td>
    </tr>
    <tr>
        <td>첨부 파일</td>
        <td> <input type="file" name="file1" id="fileInput" size="80">  </td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="메일 보내기">
            <input type="reset" value="다시 입력">
        </td>
    </tr>
</table>
</form>
<script src="js/checkFile.js"></script>
