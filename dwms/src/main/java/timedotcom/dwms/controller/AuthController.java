package timedotcom.dwms.controller;

import timedotcom.dwms.config.JwtUtil;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class AuthController {

    @PostMapping
    public Map<String, Object> postLogin(@RequestBody UserLogin loginRequest) {
        Map<String, Object> response = new HashMap<>();

        if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            String token = JwtUtil.generateToken(loginRequest.getUsername());
            response.put("token", token);
            response.put("user", loginRequest.getUsername());
            response.put("message", "Login successful");
        } else {
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
