package hackalearn.mygiftsavor.module.service;

import hackalearn.mygiftsavor.module.model.domain.User;
import hackalearn.mygiftsavor.module.model.dto.GifticonDtos;
import hackalearn.mygiftsavor.module.repository.GifticonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<GifticonDtos.GifticonResDto> home(User user) {
        return gifticonRepository.findByUser(user).stream()
                .map(o -> GifticonDtos.GifticonResDto.builder()
                        .id(o.getId())
                        .img(o.getImg())
                        .expirationDate(o.getExpirationDate())
                        .memo(o.getMemo())
                        .isUsed(o.isUsed())
                        .build())
                .collect(Collectors.toList());
    }
}
