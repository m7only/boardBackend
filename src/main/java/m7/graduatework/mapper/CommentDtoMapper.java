package m7.graduatework.mapper;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentDtoMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "authorImage", source = "author.image")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "createdAt", source = "createdAt")
    CommentDto toDto(Comment comment);

    /**
     * Маппинг {@code LocalDateTime} в Unix Time
     *
     * @param localDateTime для перевода
     * @return время в формате Unix Time
     */
    default Long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZonedDateTime.now().getOffset()).toEpochMilli();
    }

    /**
     * Маппинг Unix Time в {@code LocalDateTime}
     *
     * @param time Unix Time для перевода
     * @return время в формате {@code LocalDateTime}
     */
    default LocalDateTime longToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZonedDateTime.now().getOffset());
    }

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "ad", ignore = true)
    void updateEntityFromDto(CommentDto commentDto, @MappingTarget Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);
}
