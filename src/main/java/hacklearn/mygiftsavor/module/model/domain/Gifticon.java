package hacklearn.mygiftsavor.module.model.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Gifticon {

    @Id
    @Column(name = "gift_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @Column(length = 300)
    @NotNull
    private String img;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDate expirationDate;

    @Lob
    private String memo;

    @NotNull
    private boolean isUsed;

    public void updateStatus() {
        this.isUsed = true;
    }

    public void updateExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
