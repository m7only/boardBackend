package m7.graduatework.service;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.dto.coment.CommentTextDto;
import m7.graduatework.dto.coment.CommentsDto;

import java.util.Optional;

public interface CommentService {
    Optional<CommentDto> addComment(Long adPk, CommentTextDto commentTextDto);

    Optional<CommentsDto> getComments(Long adId);

    Long deleteComment(Long adPk, Long id);

    Optional<CommentDto> updateComment(Long adPk, Long id, CommentDto commentDto);
}
