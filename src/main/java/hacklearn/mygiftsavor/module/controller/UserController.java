package hacklearn.mygiftsavor.module.controller;

import hacklearn.mygiftsavor.module.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static hacklearn.mygiftsavor.module.model.dto.UserDtos.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * localhost:8080/user/sign-up
     *
     * @param originalUserDto
     * @return ResponseEntity
     */
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody OriginalUserDto originalUserDto) {
        log.info("[Request] sign-up");
        userService.signUp(originalUserDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 로그인
     * localhost:8080/user/sign-in
     *
     * @param originalUserDto
     * @return ResponseEntity
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody OriginalUserDto originalUserDto) {
        log.info("[Request] sign-in " + originalUserDto.getEmail());
        return new ResponseEntity<>(userService.signIn(originalUserDto), HttpStatus.OK);
    }

    /**
     * 깃허브 간편 로그인
     * localhost:8080/user/sign-in/github
     *
     * @param githubUserDto
     * @return ResponseEntity
     */
    @PostMapping("/sign-in/github")
    public ResponseEntity<?> singInGithub(@Valid @RequestBody GithubUserDto githubUserDto) {
        log.info("[Request] sign-in with github " + githubUserDto.getUserDetails());
        return new ResponseEntity<>(userService.signInGithub(githubUserDto), HttpStatus.OK);
    }
}
