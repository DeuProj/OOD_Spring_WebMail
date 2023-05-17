package deu.cse.spring_webmail.security;

import deu.cse.spring_webmail.data.UserEntity;
import deu.cse.spring_webmail.data.UserRepository;
import deu.cse.spring_webmail.model.Pop3Agent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
@Getter @Setter
public class BringDataFromJames {

    private UserRepository userRepository;

    public BringDataFromJames(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    String host;
    String userId;
    String password;

    public void bringData(){
        Pop3Agent pop3Agent = new Pop3Agent("127.0.0.1", userId, password);
        boolean isLoginSuccess = pop3Agent.validate();
        boolean exist = userRepository.existsByUsername(userId);
        if (isLoginSuccess && exist==false) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userId);
            userEntity.setPassword(password);
            if (userId.equals("admin")) {
                userEntity.setRole("ADMIN");
                userRepository.save(userEntity);
            } else {
                userEntity.setRole("USER");
                userRepository.save(userEntity);
            }
        } else if(isLoginSuccess){
            //기존에 아이디가 있으면 복제하지 않고 PASS
        } else{
            throw new BadCredentialsException("유효하지 않은 아이디");
        }
    }
}
