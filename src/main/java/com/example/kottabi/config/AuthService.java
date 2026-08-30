package com.example.kottabi.config;




import com.example.kottabi.DTO.AuthResponse;
import com.example.kottabi.DTO.UserAuthRequest;
import com.example.kottabi.DTO.UserLoginDTO;
import com.example.kottabi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserService userDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public void register(UserAuthRequest request) {

    }




public AuthResponse login(UserLoginDTO request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
    UserDetails user = userDetailsService.loadUserByUsername(request.getUserName());
    String token = jwtUtil.generateToken(user);
    return new AuthResponse(token);
}


}

