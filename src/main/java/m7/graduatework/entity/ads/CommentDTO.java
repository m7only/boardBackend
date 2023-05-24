package m7.graduatework.entity.ads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    @NotNull
    private Long author;
    @NotNull
    private Long pk;
    @NotEmpty
    private String createdAt;
    @NotEmpty
    private String text;
}
