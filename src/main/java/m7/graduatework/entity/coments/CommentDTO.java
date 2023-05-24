package m7.graduatework.entity.coments;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDTO {
    @NotNull
    private Long author;
    private String authorImage;
    @NotEmpty
    private String authorFirstName;
    @NotNull
    private Long pk;
    @NotEmpty
    private String createdAt;
    @NotEmpty
    private String text;
}
