package timedotcom.dwms.controller;

import timedotcom.dwms.model.User;
import timedotcom.dwms.model.UserDto;
import timedotcom.dwms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new UserDto(
            1L,
            "testuser",
            "testuser@example.com",
            LocalDateTime.now(),
            LocalDateTime.now(),
            Collections.emptyList()
        );
    }

    @Test
    void testGetAllUsers() {
        List<UserDto> users = Arrays.asList(userDto);
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDto>> result = userController.getAllUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
        assertEquals(userDto.getUsername(), result.getBody().get(0).getUsername());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userDto));

        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto.getUsername(), response.getBody().getUsername());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        user.setPassword("newpassword");

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("hashedpassword");
        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<UserDto> response = userController.createUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUsername("updateduser");
        user.setEmail("updateduser@example.com");
        user.setPassword("updatedpassword");

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("hashedpassword");
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(user);

        ResponseEntity<UserDto> response = userController.updateUser(1L, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        User user = new User();
        user.setUsername("updateduser");
        user.setEmail("updateduser@example.com");
        user.setPassword("updatedpassword");

        when(userService.updateUser(eq(1L), any(User.class)))
                .thenThrow(new RuntimeException("User not found with id 1"));

        ResponseEntity<UserDto> response = userController.updateUser(1L, user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(eq(1L), any(User.class));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }
}
