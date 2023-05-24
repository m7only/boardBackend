package m7.graduatework.entity.ads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAdsDTO {
    @NotEmpty
    private String description;
    @NotNull
    private Integer price;
    @NotEmpty
    private String title;
}
