package timedotcom.dwms.controller;

import timedotcom.dwms.model.User;
import timedotcom.dwms.model.UserDto;
import timedotcom.dwms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody User user) {
        User createUser = userService.createUser(user);
        return ResponseEntity.ok(new UserDto(createUser.getId(),createUser.getUsername(),createUser.getEmail(),
            createUser.getCreatedAt(),createUser.getUpdatedAt(),createUser.getProfiles()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updateUser = userService.updateUser(id,userDetails);
            return ResponseEntity.ok(new UserDto(updateUser.getId(),updateUser.getUsername(),updateUser.getEmail(),
                updateUser.getCreatedAt(),updateUser.getUpdatedAt(),updateUser.getProfiles()));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
