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

import java.util.NoSuchElementException;
import java.util.Optional;

@Data
@Slf4j
@Service
@Getter @Setter
public class BringDataFromJames {

    private UserRepository userRepository;

    public BringDataFromJames(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void bringData(String userId, String password){

        Pop3Agent pop3Agent = new Pop3Agent("127.0.0.1", userId, password);
        try {
            if (pop3Agent.validate()) {
                if (userRepository.findByUsername(userId).isEmpty()) {
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
                }
            }
        }catch (NullPointerException e){
            throw new BadCredentialsException("유효하지 않은 아이디");
        }
    }
}
