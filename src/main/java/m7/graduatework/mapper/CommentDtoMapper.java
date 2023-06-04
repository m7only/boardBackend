package m7.graduatework.mapper;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorImage", source = "author.image")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "HH:mm dd.MM.yyyy")
    CommentDto toDto(Comment comment);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    void updateEntityFromDto(CommentDto commentDto, @MappingTarget Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);
}
