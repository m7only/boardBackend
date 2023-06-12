package m7.graduatework.dto.coment;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentTextDto {
    @NotEmpty
    private String text;
}
