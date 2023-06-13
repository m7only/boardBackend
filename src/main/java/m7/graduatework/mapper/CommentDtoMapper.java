package m7.graduatework.mapper;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorImage", source = "author.image")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "createdAt", source = "createdAt")
    CommentDto toDto(Comment comment);

    default Long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+3")).toEpochMilli();
    }

    default LocalDateTime longToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.of("+3"));
    }

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    void updateEntityFromDto(CommentDto commentDto, @MappingTarget Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);
}
