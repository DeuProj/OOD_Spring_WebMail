package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.data.UserEntity;
import deu.cse.spring_webmail.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginController {
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
    private UserRepository userRepository;
    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index() {
        session.setAttribute("host", JAMES_HOST);
        session.setAttribute("debug", "false");
        return "redirect:/loginSuccess";
    }

    @GetMapping("/login")
    public String login() {
        session.setAttribute("host", JAMES_HOST);
        session.setAttribute("debug", "false");
        return "thymeleaf/login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user=null;
        Optional<UserEntity> userEntity = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userEntity.isPresent()) {
            user= userEntity.get();
            session.setAttribute("host", "127.0.0.1");
            session.setAttribute("userid", user.getUsername());
            session.setAttribute("password", user.getPassword());
        }
        if(user.getUsername().equals("admin")) return "redirect:/admin_menu";
        return "redirect:/main_menu";
    }
}