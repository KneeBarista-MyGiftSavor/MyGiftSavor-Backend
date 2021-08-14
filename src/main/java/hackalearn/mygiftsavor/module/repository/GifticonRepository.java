package hackalearn.mygiftsavor.module.repository;

import hackalearn.mygiftsavor.module.model.domain.Gifticon;
import hackalearn.mygiftsavor.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
    List<Gifticon> findByUser(User user);
}
