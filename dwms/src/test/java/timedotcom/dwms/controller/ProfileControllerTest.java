package timedotcom.dwms.controller;

import timedotcom.dwms.model.Profile;
import timedotcom.dwms.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    private Profile profile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profile = new Profile();
        profile.setId(1L);
        profile.setFirstName("testfirstname");
        profile.setLastName("testlastname");
        profile.setPic("pic/mypic.jpg");
    }

    @Test
    void testGetAllProfiles() {
        List<Profile> users = Arrays.asList(profile);
        when(profileService.getAllProfiles()).thenReturn(users);

        List<Profile> result = profileController.getAllProfiles();

        assertEquals(1, result.size());
        assertEquals(profile.getFirstName(), result.get(0).getFirstName());
        verify(profileService, times(1)).getAllProfiles();
    }

    @SuppressWarnings("null")
    @Test
    void testGetProfileById() {
        when(profileService.getProfileById(1L)).thenReturn(Optional.of(profile));

        ResponseEntity<Profile> response = profileController.getProfileById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile.getFirstName(), response.getBody().getFirstName());
        verify(profileService, times(1)).getProfileById(1L);
    }

    @Test
    void testGetProfileByIdNotFound() {
        when(profileService.getProfileById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Profile> response = profileController.getProfileById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileService, times(1)).getProfileById(1L);
    }

    @Test
    void testCreateProfile() {
        when(profileService.createProfile(any(Profile.class))).thenReturn(profile);

        Profile newProfile = new Profile();
        newProfile.setFirstName("newfirstname");
        newProfile.setLastName("newlastname");
        newProfile.setPic("pic/mypicnew.jpg");

        Profile createdProfile = profileController.createProfile(newProfile);

        assertEquals(profile.getFirstName(), createdProfile.getFirstName());
        verify(profileService, times(1)).createProfile(any(Profile.class));
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateProfile() {
        when(profileService.updateProfile(eq(1L), any(Profile.class))).thenReturn(profile);

        Profile updatedProfile = new Profile();
        updatedProfile.setFirstName("updatedfirstname");
        updatedProfile.setLastName("updatedlastname");
        updatedProfile.setPic("pic/mypicupdated.jpg");

        ResponseEntity<Profile> response = profileController.updateProfile(1L, updatedProfile);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile.getFirstName(), response.getBody().getFirstName());
        verify(profileService, times(1)).updateProfile(eq(1L), any(Profile.class));
    }

    @Test
    void testUpdateProfileNotFound() {
        when(profileService.updateProfile(eq(1L), any(Profile.class)))
                .thenThrow(new RuntimeException("Profile not found with id 1"));

        Profile updatedProfile = new Profile();
        updatedProfile.setFirstName("updatedfirstname");
        updatedProfile.setLastName("updatedlastname");
        updatedProfile.setPic("pic/mypicupdated.jpg");

        ResponseEntity<Profile> response = profileController.updateProfile(1L, updatedProfile);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(profileService, times(1)).updateProfile(eq(1L), any(Profile.class));
    }

    @Test
    void testDeleteProfile() {
        doNothing().when(profileService).deleteProfile(1L);

        ResponseEntity<Void> response = profileController.deleteProfile(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(profileService, times(1)).deleteProfile(1L);
    }
}
