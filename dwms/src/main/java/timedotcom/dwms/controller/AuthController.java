package timedotcom.dwms.controller;

import timedotcom.dwms.config.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public Map<String, Object> postLogin(@RequestBody UserLogin loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();            

            String token = JwtUtil.generateToken(userDetails.getUsername());
            response.put("token", token);
            response.put("user", userDetails.getUsername());
            response.put("message", "Login successful");
        } catch (Exception e) {
            response.put("message", "Invalid username or password");
        }

        return response;
    }

    public static class UserLogin {
        public String Username;
        public String Password;

        // Getters and setters
        public String getUsername() {
            return Username;
        }

        public void setUsername(String username) {
            this.Username = username;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            this.Password = password;
        }
    }
}
