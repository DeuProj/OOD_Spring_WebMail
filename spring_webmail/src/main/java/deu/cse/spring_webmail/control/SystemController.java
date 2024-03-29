/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.model.Pop3Agent;
import deu.cse.spring_webmail.model.UserAdminAgent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 초기 화면과 관리자 기능(사용자 추가, 삭제)에 대한 제어기
 *
 * @author skylo
 */
@Controller
@PropertySource("classpath:/system.properties")
@Slf4j
public class SystemController {

    @Autowired
    private ServletContext ctx;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @Value("${root.id}")
    private String ROOT_ID;
    @Value("${root.password}")
    private String ROOT_PASSWORD;
    @Value("${admin.id}")
    private String ADMINISTRATOR;  //  = "admin";
    @Value("${james.control.port}")
    private Integer JAMES_CONTROL_PORT;
    @Value("${james.host}")
    private String JAMES_HOST;


    @GetMapping("/login_fail")
    public String loginFail() {
        return "login_fail";
    }

    protected boolean isAdmin(String userid) {
        boolean status = false;

        if (userid.equals(this.ADMINISTRATOR)) {
            status = true;
        }

        return status;
    }

    @GetMapping("/main_menu")
    public String mainmenu(Model model, @RequestParam(value = "page", required = false) Integer page,
                           @SessionAttribute("host") String host,
                           @SessionAttribute("userid") String userid,
                           @SessionAttribute("password") String password) {
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));

        // 현재 페이지 요청
        int currentPage = (page != null) ? page : 1;

        String messageList = pop3.getMessageList(currentPage);
        model.addAttribute("messageList", messageList);
        return "main_menu";
    }

    @GetMapping("/admin_menu")
    public String adminMenu(Model model) {
        log.debug("root.id = {}, root.password = {}, admin.id = {}",
                ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

        model.addAttribute("userList", getUserList());
        return "admin/admin_menu";
    }

    @GetMapping("/sign_up")
    public String signUp() {
        return "sign_up";
    }

    @PostMapping("/sign_up.do")
    public String signUpDo(@RequestParam String id, @RequestParam String password, @RequestParam String checkPassword, RedirectAttributes attrs) {
        log.debug("sign_up.do: id = {}, password = {}, checkPassword={}, port = {}", id, password, checkPassword, JAMES_CONTROL_PORT);

        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd, ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

            // if 회원가입 성공 팝업창
            // else 회원가입 실패 팝업창
            if (agent.regularExpression(id, true)) { //아이디에 영어, 숫자만 존재하는지
                if (agent.regularExpression(password, false)) {//비밀번호에 영어, 숫자, 일부 특수문자만 존재하는지
                    if (agent.signUp(id, password, checkPassword)) {
                        attrs.addFlashAttribute("msg", String.format("(%s)님 회원가입을 성공하였습니다.", id));
                    } else {
                        attrs.addFlashAttribute("msg", String.format("(%s)로 회원가입을 실패하였습니다.", id));
                    }
                } else {
                    attrs.addFlashAttribute("msg", String.format("비밀번호에 영어, 숫자, 일부 특수문자만 입력할 수 있습니다."));
                }
            } else {
                attrs.addFlashAttribute("msg", String.format("ID에 영어, 숫자만 입력할 수 있습니다."));
            }
        } catch (Exception ex) {
            log.error("sign_up.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/add_user")
    public String addUser() {
        return "admin/add_user";
    }

    @PostMapping("/add_user.do")
    public String addUserDo(@RequestParam String id, @RequestParam String password,
            RedirectAttributes attrs) {
        log.debug("add_user.do: id = {}, password = {}, port = {}",
                id, password, JAMES_CONTROL_PORT);
        
        Pop3Agent pop3 = new Pop3Agent();
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid(id);
        pop3.setPassword(password);
        
        if (pop3.validate()) {
            attrs.addFlashAttribute("msg", String.format("이미 존재하는 사용자입니다.", id));
            return "redirect:/add_user";
        }
        
        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

            // if (addUser successful)  사용자 등록 성공 팦업창
            // else 사용자 등록 실패 팝업창
            if (agent.addUser(id, password)) {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 성공하였습니다.", id));
            } else {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 실패하였습니다.", id));
            }
        } catch (Exception ex) {
            log.error("add_user.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
        }

        return "redirect:/admin_menu";
    }

    @GetMapping("/set_password")
    public String setPassword() {
        return "set_password";
    }

    @PostMapping("/set_password.do")
    public String setPasswordDo(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String checkPassword,
            RedirectAttributes attrs, HttpSession session) {
        // 세션에 있는 id, pw값 가져오기
        String s_id = (String) session.getAttribute("userid");
        String s_pw = (String) session.getAttribute("password");
        log.debug("change_password.do: currentPassword = {}, newPassword = {}, port = {}",
                currentPassword, newPassword, JAMES_CONTROL_PORT);
        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

            // 영어, 숫자, 특수문자에 대한 검사
            if (!agent.regularExpression(newPassword, false)) {
                attrs.addFlashAttribute("msg", String.format("영어, 숫자, 일부 특수문자만 입력할 수 있습니다."));
                if (isAdmin(s_id)) {
                    return "redirect:/admin_menu";
                } else {
                    return "redirect:/main_menu";
                }
            } else {
                // if (setPassword successful) 비밀번호 변경 성공 팝업창
                // else 비밀번호 변경 실패 팝업창
                if (agent.setPassword(s_id, s_pw, currentPassword, newPassword, checkPassword)) {
                    attrs.addFlashAttribute("msg", String.format("비밀번호 변경을 성공하였습니다."));
                    return "redirect:/";
                } else {
                    attrs.addFlashAttribute("msg", String.format("비밀번호 변경을 실패하였습니다."));
                    if (isAdmin(s_id)) {
                        return "redirect:/admin_menu";
                    } else {
                        return "redirect:/main_menu";
                    }
                }
            }
        } catch (Exception ex) {
            log.error("set_password.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
            if (isAdmin(s_id)) {
                return "redirect:/admin_menu";
            } else {
                return "redirect:/main_menu";
            }
        }
    }

    @GetMapping("/delete_user")
    public String deleteUser(Model model) {
        log.debug("delete_user called");
        model.addAttribute("userList", getUserList());
        return "admin/delete_user";
    }

    /**
     *
     * @param selectedUsers <input type=checkbox> 필드의 선택된 이메일 ID. 자료형: String[]
     * @param attrs
     * @return
     */
    @PostMapping("delete_user.do")
    public String deleteUserDo(@RequestParam String[] selectedUsers, RedirectAttributes attrs) {
        log.debug("delete_user.do: selectedUser = {}", List.of(selectedUsers));

        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
            agent.deleteUsers(selectedUsers);  // 수정!!!
            attrs.addFlashAttribute("msg", "선택한 사용자가 제거되었습니다."); //사용자 제거 완료 메세지 
        } catch (Exception ex) {
            log.error("delete_user.do : 예외 = {}", ex);
        }

        return "redirect:/admin_menu";
    }

    @GetMapping("/user_withdrawal") //회원 탈퇴
    public String userWithdrawal() {
        return "user_withdrawal";
    }

    @PostMapping("user_withdraw.do")
    public String userWithdrawalDo(@RequestParam String currentPassword, RedirectAttributes attrs, HttpSession session) {
        // 세션에 있는 id, pw값 가져오기
        String s_id = (String) session.getAttribute("userid");
        String s_pw = (String) session.getAttribute("password");
        log.debug("withdrawal.do: currentPassword = {}", currentPassword);
        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);

            // 비밀번호 일치 검사
            if (!s_pw.equals(currentPassword)) {
                attrs.addFlashAttribute("msg", String.format("비밀번호가 일치하지 않습니다."));
                return "redirect:/main_menu";
            } else {
                // 회원탈퇴 처리
                if (agent.userWithdrawal(s_id)) {
                    attrs.addFlashAttribute("msg", String.format("회원탈퇴를 성공하였습니다."));
                    session.invalidate(); // 세션 제거
                    return "redirect:/";
                } else {
                    attrs.addFlashAttribute("msg", String.format("회원탈퇴를 실패하였습니다."));
                    return "redirect:/main_menu";
                }
            }
        } catch (Exception ex) {
            log.error("withdrawal.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
            return "redirect:/main_menu";
        }
    }

    private List<String> getUserList() {
        String cwd = ctx.getRealPath(".");
        UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
        List<String> userList = agent.getUserList();
        log.debug("userList = {}", userList);

        //(주의) root.id와 같이 '.'을 넣으면 안 됨.
        userList.sort((e1, e2) -> e1.compareTo(e2));
        return userList;
    }

    @GetMapping("/img_test")
    public String imgTest() {
        return "img_test/img_test";
    }

    /**
     * https://34codefactory.wordpress.com/2019/06/16/how-to-display-image-in-jsp-using-spring-code-factory/
     *
     * @param imageName
     * @return
     */
    @RequestMapping(value = "/get_image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imageName) {
        try {
            String folderPath = ctx.getRealPath("/WEB-INF/views/img_test/img");
            return getImageBytes(folderPath, imageName);
        } catch (Exception e) {
            log.error("/get_image 예외: {}", e.getMessage());
        }
        return new byte[0];
    }

    private byte[] getImageBytes(String folderPath, String imageName) {
        ByteArrayOutputStream byteArrayOutputStream;
        BufferedImage bufferedImage;
        byte[] imageInByte;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedImage = ImageIO.read(new File(folderPath + File.separator + imageName));
            String format = imageName.substring(imageName.lastIndexOf(".") + 1);
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageInByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return imageInByte;
        } catch (FileNotFoundException e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        } catch (Exception e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        }
        return null;
    }

}
