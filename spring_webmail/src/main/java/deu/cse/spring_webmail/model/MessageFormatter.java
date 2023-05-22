/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import jakarta.mail.Message;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author skylo
 */
@Slf4j
@RequiredArgsConstructor
public class MessageFormatter {
    @NonNull private String userid;  // 파일 임시 저장 디렉토리 생성에 필요
    @Getter @Setter
    private HttpServletRequest request = null;
    
    // 220612 LJM - added to implement REPLY
    @Getter private String sender;
    @Getter private String subject;
    @Getter private String body;
    @Getter @Setter
    private int currentPage = 1;
    @Getter @Setter
    private int messagesPerPage = 10;


    public String getMessageTable(Message[] messages) {
        StringBuilder buffer = new StringBuilder();

        //javascript로 메시지 삭제 확인 팝업창 생성
        buffer.append("<script>\n" +
                "function clickButton(i) {\n" +
                "  var confirmed = confirm('메일을 영구적으로 삭제합니다.');\n" +
                "  if (confirmed) {\n" +
                "    var tmp = Number(i) + 1;\n" +
                "    window.location.href = 'delete_mail.do?msgid=' + tmp;\n" +
                "  }\n" +
                "}\n" +
                "</script>");

        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 보낸 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");

//        int firstIndex = Math.min(currentPage * messagesPerPage - 1, messages.length - 1);
        int firstIndex = (messages.length - 1) - (currentPage - 1) * messagesPerPage;
        int lastIndex = Math.max(firstIndex - messagesPerPage + 1, 0);

        for (int i = firstIndex; i >= lastIndex; i--) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);  // envelope 정보만 필요
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기

            if (!parser.getFromAddress().equalsIgnoreCase(userid)) {
            buffer.append("<tr> "
                    + " <td id=no>" + (i + 1) + " </td> "
                    + " <td id=sender>" + parser.getFromAddress() + "</td>"
                    + " <td id=subject> "
                    + " <a href=show_message?msgid=" + (i + 1) + " title=\"메일 보기\"> "
                    + parser.getSubject() + "</a> </td>"
                    + " <td id=date>" + parser.getSentDate() + "</td>"
                    + " <td id=delete>"
//                    + "<a href=delete_mail.do"
                    + "<a onclick=\"clickButton(" + (i + 1) + ")\""
                    + "?msgid=" + (i + 1) + "> 삭제 </a>" + "</td>"
                    + " </tr>");
            }
        }
        buffer.append("</table>");

        int totalPages = (int) Math.ceil((double) messages.length / messagesPerPage);
        buffer.append("<div style='text-align: center;'><br>");
        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                buffer.append("[" + i + "] ");
            } else {
                buffer.append("<a href='main_menu?page=" + i + "'>" + i + "</a> ");
            }
        }
        buffer.append("</div><br>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getSentMessageTable(Message[] messages) {
        StringBuilder buffer = new StringBuilder();
        List<Message> sentMessages = new ArrayList<>();
        List<Integer> indexOfSentMessage = new ArrayList<>();

        // 전체 메시지 중에서 보낸 메일만 추출
        for (int i = 0; i < messages.length; i++) {
            MessageParser parser = new MessageParser(messages[i], userid);
            parser.parse(false);
            if (parser.getFromAddress().equalsIgnoreCase(userid)) {
                sentMessages.add(0, messages[i]);
                indexOfSentMessage.add(0, i + 1);
            }
        }

        int startMessageIndex = (currentPage - 1) * messagesPerPage;
        int endMessageIndex = Math.min(startMessageIndex + messagesPerPage, sentMessages.size());

        // 메시지 제목 보여주기
        buffer.append("<table>");  // table start
        buffer.append("<tr> "
                + " <th> No. </td> "
                + " <th> 받는 사람 </td>"
                + " <th> 제목 </td>     "
                + " <th> 보낸 날짜 </td>   "
                + " <th> 삭제 </td>   "
                + " </tr>");

        for (int i = startMessageIndex; i < endMessageIndex; i++) {
            MessageParser parser = new MessageParser(sentMessages.get(i), userid);
            parser.parse(false);  // envelope 정보만 필요
            // 메시지 헤더 포맷
            // 추출한 정보를 출력 포맷 사용하여 스트링으로 만들기
            buffer.append("<tr> "
                    + " <td id=no>" + (sentMessages.size() - i) + " </td> "
                    + " <td id=receiver>" + parser.getToAddress() + "</td>"
                    + " <td id=subject> "
                    + " <a href=show_message?msgid=" + (indexOfSentMessage.get(i)) + " title=\"메일 보기\"> "
                    + parser.getSubject() + "</a> </td>"
                    + " <td id=date>" + parser.getSentDate() + "</td>"
                    + " <td id=delete>"
                    + "<a href=delete_mail.do"
                    + "?msgid=" + (indexOfSentMessage.get(i)) + "> 삭제 </a>" + "</td>"
                    + " </tr>");
        }
        buffer.append("</table>");

        int totalPages = (int) Math.ceil((double) sentMessages.size() / messagesPerPage);
        buffer.append("<div style='text-align: center;'><br>");
        for (int i = 1; i <= totalPages; i++) {
            if (i == currentPage) {
                buffer.append("[" + i + "] ");
            } else {
                buffer.append("<a href='sent_mail?page=" + i + "'>" + i + "</a> ");
            }
        }
        buffer.append("</div><br>");

        return buffer.toString();
//        return "MessageFormatter 테이블 결과";
    }

    public String getMessage(Message message) {
        StringBuilder buffer = new StringBuilder();

        // MessageParser parser = new MessageParser(message, userid);
        MessageParser parser = new MessageParser(message, userid, request);
        parser.parse(true);
        
        sender = parser.getFromAddress();
        subject = parser.getSubject();
        body = parser.getBody();

        buffer.append("보낸 사람: " + parser.getFromAddress() + " <br>");
        buffer.append("받은 사람: " + parser.getToAddress() + " <br>");
        buffer.append("Cc &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : " + parser.getCcAddress() + " <br>");
        buffer.append("보낸 날짜: " + parser.getSentDate() + " <br>");
        buffer.append("제 &nbsp;&nbsp;&nbsp;  목: " + parser.getSubject() + " <br> <hr>");

        buffer.append(parser.getBody());

        String attachedFile = parser.getFileName();
        if (attachedFile != null) {
            buffer.append("<br> <hr> 첨부파일: <a href=download"
                    + "?userid=" + this.userid
                    + "&filename=" + attachedFile.replaceAll(" ", "%20")
                    + " target=_top> " + attachedFile + "</a> <br>");
        }

        return buffer.toString();
    }

}