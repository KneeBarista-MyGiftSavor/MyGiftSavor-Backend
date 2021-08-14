package hackalearn.mygiftsavor.module.service;

import hackalearn.mygiftsavor.infra.exception.NoSuchDataException;
import hackalearn.mygiftsavor.module.model.domain.Gifticon;
import hackalearn.mygiftsavor.module.model.domain.User;
import hackalearn.mygiftsavor.module.model.domain.UserSource;
import hackalearn.mygiftsavor.module.repository.UserRepository;
import hackalearn.mygiftsavor.infra.exception.AccessDeniedException;
import hackalearn.mygiftsavor.module.repository.GifticonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static hackalearn.mygiftsavor.module.model.dto.GifticonDtos.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@DisplayName("Gifticon Service Test")
class GifticonServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GifticonService gifticonService;
    @Autowired
    GifticonRepository gifticonRepository;

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
    @DisplayName("Gifticon | Fail : No Such Data")
    void gifticonFailNoData() throws Exception {
        User user = userRepository.findByEmail("new@new.com").orElse(null);
        assertNotNull(user);

        NoSuchDataException e = assertThrows(NoSuchDataException.class, () ->
                gifticonService.gifticonStatus(user, 2L));
        assertEquals("Gifticon id = 2", e.getMessage());
    }

    @Test
    @DisplayName("Gifticon | Fail : Access Deny")
    void gifticonFailAccessDeny() throws Exception {
        User user = userRepository.findByEmail("new2@new.com").orElse(null);
        assertNotNull(user);
        Gifticon gifticon = gifticonRepository.findAll().get(0);

        AccessDeniedException e = assertThrows(AccessDeniedException.class, () ->
                gifticonService.gifticonStatus(user, gifticon.getId()));
        assertEquals("Gifticon id = " + gifticon.getId(), e.getMessage());
    }

    @Test
    @DisplayName("Delete Gifticon | Success")
    void deleteGifticonSuccess() throws Exception {
        User user = userRepository.findByEmail("new@new.com").orElse(null);
        assertNotNull(user);
        List<Gifticon> list = gifticonRepository.findAll();
        Gifticon gifticon = list.get(0);

        gifticonService.deleteGifticon(user, gifticon.getId());
        assertEquals(list.size() - 1, gifticonRepository.findAll().size());
    }

    @Test
    @DisplayName("Change Gifticon Status | Success")
    void gifticonStatus() throws Exception {
        User user = userRepository.findByEmail("new@new.com").orElse(null);
        assertNotNull(user);
        Gifticon gifticon = gifticonRepository.findAll().get(0);

        gifticonService.gifticonStatus(user, gifticon.getId());
        assertTrue(gifticon.isUsed());
    }

    @Test
    @DisplayName("Extend Gifticon | Success")
    void gifticonExtension() throws Exception {
        User user = userRepository.findByEmail("new@new.com").orElse(null);
        assertNotNull(user);
        Gifticon gifticon = gifticonRepository.findAll().get(0);

        gifticonService.gifticonExtension(user, gifticon.getId(),
                GifticonExtensionReqDto.builder()
                        .expirationDate(LocalDate.parse("2022-01-11"))
                        .build());
        assertEquals(LocalDate.parse("2022-01-11"), gifticon.getExpirationDate());
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