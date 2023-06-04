package m7.graduatework.mapper;

import m7.graduatework.dto.coment.CommentTextDto;
import m7.graduatework.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentTextDtoMapper {
    Comment toEntity(CommentTextDto commentTextDto);
}
