package hacklearn.mygiftsavor.module.service;

import hacklearn.mygiftsavor.infra.exception.DuplicateException;
import hacklearn.mygiftsavor.infra.exception.InvalidReqBodyException;
import hacklearn.mygiftsavor.infra.exception.NoSuchDataException;
import hacklearn.mygiftsavor.infra.jwt.JwtTokenProvider;
import hacklearn.mygiftsavor.module.model.domain.User;
import hacklearn.mygiftsavor.module.model.domain.UserSource;
import hacklearn.mygiftsavor.module.model.dto.JwtDto;
import hacklearn.mygiftsavor.module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static hacklearn.mygiftsavor.module.model.dto.UserDtos.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * signUp
     *
     * @param originalUserDto
     */
    public void signUp(OriginalUserDto originalUserDto) {
        String newEmail = originalUserDto.getEmail() + "-o";
        checkDuplicateUser(newEmail);
        userRepository.save(buildOriginalUser(newEmail, originalUserDto));
    }

    private void checkDuplicateUser(String email) {
        userRepository.findByEmail(email)
                .ifPresent(param -> {
                    throw new DuplicateException("email = " + email.split("-")[0]);
                });
    }

    /**
     * signIn
     *
     * @param originalUserDto
     * @return JwtDto
     */
    public JwtDto signIn(OriginalUserDto originalUserDto) {
        String newEmail = originalUserDto.getEmail() + "-o";
        User user = userRepository.findByEmail(newEmail)
                .orElseThrow(() -> new NoSuchDataException("email = " + originalUserDto.getEmail()));
        if (!passwordEncoder.matches(originalUserDto.getPassword(), user.getPassword()))
            throw new NoSuchDataException("password = " + originalUserDto.getPassword());
        return buildJwtDto(user);
    }

    /**
     * signInGithub
     *
     * @param githubUserDto
     * @return JwtDto
     */
    public JwtDto signInGithub(GithubUserDto githubUserDto) {
        if(!githubUserDto.getIdentityProvider().equalsIgnoreCase("GITHUB"))
            throw new InvalidReqBodyException("IdentityProvider must be github");
        String newUserDetails = githubUserDto.getUserDetails() + "-g";
        User user = userRepository.findByEmail(newUserDetails)
                .orElseGet(() -> userRepository.save(buildGithubUser(newUserDetails))); //최초 로그인이라면 회원정보 삽입
        return buildJwtDto(user);
    }

    private User buildOriginalUser(String email, OriginalUserDto originalUserDto) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(originalUserDto.getPassword()))
                .createdBy(UserSource.ORIGINAL)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    private User buildGithubUser(String userDetails) {
        return User.builder()
                .email(userDetails)
                .createdBy(UserSource.GITHUB)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }

    private JwtDto buildJwtDto(User user) {
        return JwtDto.builder()
                .userId(user.getId())
                .accessToken(jwtTokenProvider.createAccessToken(user.getUsername(), user.getRoles()))
                .build();
    }
}
