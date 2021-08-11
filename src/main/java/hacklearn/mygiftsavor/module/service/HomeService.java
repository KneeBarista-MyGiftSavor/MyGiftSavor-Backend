package hacklearn.mygiftsavor.module.service;

import hacklearn.mygiftsavor.module.model.domain.Gifticon;
import hacklearn.mygiftsavor.module.model.domain.User;
import hacklearn.mygiftsavor.module.model.dto.GifticonDtos;
import hacklearn.mygiftsavor.module.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static hacklearn.mygiftsavor.module.model.dto.GifticonDtos.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final GifticonRepository gifticonRepository;

    /**
     * home
     *
     * @param user
     * @return List<GifticonResDto>
     */
    public List<GifticonResDto> home(User user) {
        return gifticonRepository.findByUser(user).stream()
                .map(o -> GifticonResDto.builder()
                        .id(o.getId())
                        .img(o.getImg())
                        .expirationDate(o.getExpirationDate())
                        .memo(o.getMemo())
                        .isUsed(o.isUsed())
                        .build())
                .collect(Collectors.toList());
    }
}
