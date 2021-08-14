package hackalearn.mygiftsavor.module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hackalearn.mygiftsavor.module.model.dto.UserDtos;
import hackalearn.mygiftsavor.module.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@DisplayName("User Controller Test")
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("User Sign-Up | Success")
    void signUpSuccess() throws Exception {
        UserDtos.OriginalUserDto originalUserDto = getOriginalUserDto("1234");
        String jsonString = objectMapper.writeValueAsString(originalUserDto);
        mockMvc.perform(post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("User Sign-Up | Fail : Duplicate User")
    void signUpFailDuplicate() throws Exception {
        UserDtos.OriginalUserDto originalUserDto = getOriginalUserDto("1234");
        userService.signUp(originalUserDto);
        String jsonString = objectMapper.writeValueAsString(originalUserDto);

        Map<String, String> error = new HashMap<>();
        error.put("DuplicateException", "email = newUser@new.com");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User Sign-In | Success")
    void signInSuccess() throws Exception {
        UserDtos.OriginalUserDto originalUserDto = getOriginalUserDto("1234");
        userService.signUp(originalUserDto);
        String jsonString = objectMapper.writeValueAsString(originalUserDto);

        mockMvc.perform(post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User Sign-In | Fail : Wrong Email")
    void signInFailEmail() throws Exception {
        UserDtos.OriginalUserDto originalUserDto = getOriginalUserDto("1234");
        String jsonString = objectMapper.writeValueAsString(originalUserDto);

        Map<String, String> error = new HashMap<>();
        error.put("NoSuchDataException", "email = newUser@new.com");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User Sign-In | Fail : Wrong Password")
    void signInFailPassword() throws Exception {
        UserDtos.OriginalUserDto originalUserDto = getOriginalUserDto("1234");
        userService.signUp(originalUserDto);

        UserDtos.OriginalUserDto wrongUserDto = getOriginalUserDto("2222");
        String jsonString = objectMapper.writeValueAsString(wrongUserDto);

        Map<String, String> error = new HashMap<>();
        error.put("NoSuchDataException", "password = 2222");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(post("/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    @Test
    @DisplayName("User Sign-In with Github | Success : Sign-Up")
    void signInSuccessUp() throws Exception {
        UserDtos.GithubUserDto githubUserDto = getGithubUserDto("github");
        String jsonString = objectMapper.writeValueAsString(githubUserDto);

        mockMvc.perform(post("/user/sign-in/github")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User Sign-In with Github | Success : Sign-In")
    void signInSuccessIn() throws Exception {
        UserDtos.GithubUserDto githubUserDto = getGithubUserDto("github");
        userService.signInGithub(githubUserDto);
        String jsonString = objectMapper.writeValueAsString(githubUserDto);

        mockMvc.perform(post("/user/sign-in/github")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User Sign-In with Github | Fail : Invalid Req")
    void signInFailInvalid() throws Exception {
        UserDtos.GithubUserDto githubUserDto = getGithubUserDto("wrong");
        String jsonString = objectMapper.writeValueAsString(githubUserDto);

        Map<String, String> error = new HashMap<>();
        error.put("InvalidReqBodyException", "IdentityProvider must be github");
        String response = objectMapper.writeValueAsString(error);

        mockMvc.perform(post("/user/sign-in/github")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(response));
    }

    private UserDtos.GithubUserDto getGithubUserDto(String provider) {
        return UserDtos.GithubUserDto.builder()
                .userDetails("newUser@new.com")
                .identityProvider(provider)
                .build();
    }

    private UserDtos.OriginalUserDto getOriginalUserDto(String password) {
        return UserDtos.OriginalUserDto.builder()
                .email("newUser@new.com")
                .password(password)
                .build();
    }
}