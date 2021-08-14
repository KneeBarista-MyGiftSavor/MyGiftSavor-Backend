package hackalearn.mygiftsavor.module.service;

import hackalearn.mygiftsavor.infra.exception.AccessDeniedException;
import hackalearn.mygiftsavor.infra.exception.NoSuchDataException;
import hackalearn.mygiftsavor.infra.exception.S3Exception;
import hackalearn.mygiftsavor.module.model.domain.Gifticon;
import hackalearn.mygiftsavor.module.model.domain.User;
import hackalearn.mygiftsavor.module.model.dto.GifticonDtos;
import hackalearn.mygiftsavor.module.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class GifticonService {

    private final S3Service s3Service;
    private final GifticonRepository gifticonRepository;

    /**
     * gifticon
     *
     * @param user
     * @param file
     * @param gifticonReqDto
     */
    public void gifticon(User user, MultipartFile file, GifticonDtos.GifticonReqDto gifticonReqDto) {
        String img;
        try {
            img = s3Service.upload(file);
        } catch (IOException e) {
            throw new S3Exception("file = " + file.getOriginalFilename());
        }
        gifticonRepository.save(Gifticon.builder()
                .user(user)
                .img(img)
                .createdAt(LocalDateTime.now())
                .expirationDate(gifticonReqDto.getExpirationDate())
                .memo(gifticonReqDto.getMemo())
                .isUsed(false)
                .build());
    }

    /**
     * deleteGifticon
     *
     * @param user
     * @param id
     */
    public void deleteGifticon(User user, Long id){
        Gifticon gifticon = getGifticon(user, id);
        gifticonRepository.delete(gifticon);
    }

    /**
     * gifticonStatus
     *
     * @param user
     * @param id
     */
    public void gifticonStatus(User user, Long id) {
        Gifticon gifticon = getGifticon(user, id);
        gifticon.updateStatus();
        gifticonRepository.save(gifticon);
    }

    /**
     * gifticonExtension
     *
     * @param user
     * @param id
     * @param gifticonExtensionReqDto
     */
    public void gifticonExtension(User user, Long id, GifticonDtos.GifticonExtensionReqDto gifticonExtensionReqDto) {
        Gifticon gifticon = getGifticon(user, id);
        gifticon.updateExpirationDate(gifticonExtensionReqDto.getExpirationDate());
        gifticonRepository.save(gifticon);
    }

    private Gifticon getGifticon(User user, Long id) {
        Gifticon gifticon = gifticonRepository.findById(id)
                .orElseThrow(() -> new NoSuchDataException("Gifticon id = " + id));
        if (!gifticon.getUser().equals(user))
            throw new AccessDeniedException("Gifticon id = " + id);
        return gifticon;
    }
}
