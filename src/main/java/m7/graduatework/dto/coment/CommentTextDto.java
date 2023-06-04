package m7.graduatework.dto.coment;

import jakarta.validation.constraints.NotEmpty;

public class CommentTextDto {
    @NotEmpty
    private String text;
}
