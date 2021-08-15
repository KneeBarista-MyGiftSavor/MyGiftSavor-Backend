package hackalearn.mygiftsavor.module.controller;

import hackalearn.mygiftsavor.module.model.domain.User;
import hackalearn.mygiftsavor.module.service.GifticonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static hackalearn.mygiftsavor.module.model.dto.GifticonDtos.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("gifticon")
public class GifticonController {

    private final GifticonService gifticonService;

    /**
     * 기프티콘 업로드
     * localhost:8080/gifticon
     *
     * @param user
     * @param img
     * @param gifticonReqDto
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity<?> gifticon(@AuthenticationPrincipal User user,
                                      @RequestPart MultipartFile img,
                                      @Valid @RequestPart GifticonReqDto gifticonReqDto) {
        log.info("[Request] Upload Gifticon " + user.getEmail());
        gifticonService.gifticon(user, img, gifticonReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 기프티콘 삭제
     * localhost:8080/gifticon/1
     *
     * @param user
     * @param gifticonId
     * @return ResponseEntity
     */
    @DeleteMapping("/{gifticonId}")
    public ResponseEntity<?> gifticon(@AuthenticationPrincipal User user,
                                      @PathVariable Long gifticonId) {
        log.info("[Request] Delete Gifticon "+gifticonId);
        gifticonService.deleteGifticon(user, gifticonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 기프티콘 사용 처리
     * localhost:8080/gifticon/status/1
     *
     * @param user
     * @param gifticonId
     * @return ResponseEntity
     */
    @PutMapping("/status/{gifticonId}")
    public ResponseEntity<?> gifticonStatus(@AuthenticationPrincipal User user,
                                            @PathVariable Long gifticonId) {
        log.info("[Request] Update Gifticon Status " + gifticonId);
        gifticonService.gifticonStatus(user, gifticonId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 기프티콘 연장
     * localhost:8080/gifticon/extension/1
     *
     * @param user
     * @param gifticonId
     * @param gifticonExtensionReqDto
     * @return ResponseEntity
     */
    @PutMapping("/extension/{gifticonId}")
    public ResponseEntity<?> gifticonExtension(@AuthenticationPrincipal User user,
                                               @PathVariable Long gifticonId,
                                               @Valid @RequestBody GifticonExtensionReqDto gifticonExtensionReqDto) {
        log.info("[Request] Extend Gifticon " + gifticonId);
        gifticonService.gifticonExtension(user, gifticonId, gifticonExtensionReqDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
