package m7.graduatework.dto.ad;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrUpdateAdDto {
    @NotEmpty
    private String description;
    @NotNull
    private Integer price;
    @NotEmpty
    private String title;
}
