package m7.graduatework.service;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.dto.coment.CommentTextDto;
import m7.graduatework.dto.coment.CommentsDto;
import m7.graduatework.entity.Comment;

public interface CommentService {
    CommentDto addComment(Long adPk, CommentTextDto commentTextDto);

    CommentsDto getComments(Long adId);

    Long deleteComment(Long adPk, Long id);

    CommentDto updateComment(Long adPk, Long id, CommentTextDto commentTextDto);

    Comment getCommentById(Long id);
}
