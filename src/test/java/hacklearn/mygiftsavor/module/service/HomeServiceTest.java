package hacklearn.mygiftsavor.module.service;

import hacklearn.mygiftsavor.module.model.domain.Gifticon;
import hacklearn.mygiftsavor.module.model.domain.User;
import hacklearn.mygiftsavor.module.model.domain.UserSource;
import hacklearn.mygiftsavor.module.repository.GifticonRepository;
import hacklearn.mygiftsavor.module.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("Home Service Test")
class HomeServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GifticonRepository gifticonRepository;
    @Autowired
    HomeService homeService;

    @BeforeEach
    void init() throws Exception {
        User user = userRepository.save(getUser("new@new.com"));
        userRepository.save(getUser("new2@new.com"));

        gifticonRepository.save(Gifticon.builder()
                .user(user)
                .img("hello.com")
                .expirationDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .isUsed(false)
                .build());
    }

    @Test
    @DisplayName("Retrieve all Gifticon | Success")
    void gifticonSuccess() throws Exception {
        User user1 = userRepository.findByEmail("new@new.com").orElse(null);
        User user2 = userRepository.findByEmail("new2@new.com").orElse(null);
        assertNotNull(user1);
        assertNotNull(user2);

        assertEquals(1, homeService.home(user1).size());
        assertTrue(homeService.home(user2).isEmpty());
    }

    private User getUser(String email) {
        return User.builder()
                .email(email)
                .password("1234")
                .createdBy(UserSource.ORIGINAL)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}