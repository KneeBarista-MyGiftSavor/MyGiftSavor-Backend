package hacklearn.mygiftsavor.module.repository;

import hacklearn.mygiftsavor.module.model.domain.Gifticon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
}
