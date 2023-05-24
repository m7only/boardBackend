package m7.graduatework.entity.ads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdDTO {
    @NotEmpty
    MultipartFile[] image;
    @NotNull
    private Long author;
    @NotNull
    private Long pk;
    @NotNull
    private Integer price;
    @NotEmpty
    private String title;
}
