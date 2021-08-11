package hacklearn.mygiftsavor.module.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hacklearn.mygiftsavor.module.service.UserService;
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

import static hacklearn.mygiftsavor.module.model.dto.UserDtos.*;
import static org.junit.jupiter.api.Assertions.*;
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
        OriginalUserDto originalUserDto = getOriginalUserDto();
        String jsonString = objectMapper.writeValueAsString(originalUserDto);
        mockMvc.perform(post("/user/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("User Sign-Up | Fail : Duplicate User")
    void signUpFailDuplicate() throws Exception {
        OriginalUserDto originalUserDto = getOriginalUserDto();
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

    private OriginalUserDto getOriginalUserDto() {
        return OriginalUserDto.builder()
                .email("newUser@new.com")
                .password("1234")
                .build();
    }

    @Test
    @DisplayName("User Sign-In with Github | Success : Sign-Up")
    void signInSuccessUp() throws Exception {
        GithubUserDto githubUserDto = GithubUserDto.builder()
                .userDetails("newUser@new.com")
                .identityProvider("github")
                .build();
        String jsonString = objectMapper.writeValueAsString(githubUserDto);

        mockMvc.perform(post("/user/sign-in/github")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User Sign-In with Github | Success : Sign-In")
    void signInSuccessIn() throws Exception {
        GithubUserDto githubUserDto = GithubUserDto.builder()
                .userDetails("newUser@new.com")
                .identityProvider("github")
                .build();
        userService.signInGithub(githubUserDto);
        String jsonString = objectMapper.writeValueAsString(githubUserDto);

        mockMvc.perform(post("/user/sign-in/github")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());
    }
}