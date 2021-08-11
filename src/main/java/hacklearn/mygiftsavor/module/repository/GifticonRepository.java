package hacklearn.mygiftsavor.module.repository;

import hacklearn.mygiftsavor.module.model.domain.Gifticon;
import hacklearn.mygiftsavor.module.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
    List<Gifticon> findByUser(User user);
}
