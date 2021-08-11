package hacklearn.mygiftsavor.module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class GifticonDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GifticonReqDto {

        @NotNull
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate expirationDate;
        private String memo;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @NotNull
    public static class GifticonExtensionReqDto {

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate expirationDate;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class GifticonResDto {

        private Long id;
        private String img;
        private LocalDate expirationDate;
        private String memo;
        private boolean isUsed;
    }
}
