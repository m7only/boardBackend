package m7.graduatework.dto.coment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {
    @NotNull
    private Long author;
    private String authorImage;
    @NotEmpty
    private String authorFirstName;
    @NotNull
    private Long pk;
    @NotEmpty
    private Long createdAt;
    @NotEmpty
    private String text;
}
