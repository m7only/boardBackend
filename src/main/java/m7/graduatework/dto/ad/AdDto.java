package m7.graduatework.dto.ad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdDto {
    @NotNull
    private Long author;
    private String image;
    @NotNull
    private Long pk;
    @NotNull
    private Integer price;
    @NotEmpty
    private String title;
}
