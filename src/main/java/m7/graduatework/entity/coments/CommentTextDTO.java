package m7.graduatework.entity.coments;

import jakarta.validation.constraints.NotEmpty;

public class CommentTextDTO {
    @NotEmpty
    private String text;
}
