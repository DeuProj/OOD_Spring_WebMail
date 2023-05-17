package deu.cse.spring_webmail.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private BringDataFromJames bringDataFromJames;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BringDataFromJames bringDataFromJames, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bringDataFromJames = bringDataFromJames;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, passwordEncoder.encode(userDetails.getPassword()))) {
            throw new BadCredentialsException("Invalid password");
        }
        bringDataFromJames.setUserId(username);
        bringDataFromJames.setPassword(password);
        bringDataFromJames.bringData();
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
