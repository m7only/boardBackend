package m7.graduatework.mapper;

import m7.graduatework.dto.user.UserDto;
import m7.graduatework.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "email", source = "username")
    UserDto toDto(User user);

    @Mapping(target = "username", source = "email")
    User toEntity(UserDto userDto);

    void updateEntityFromDto(UserDto userDto, @MappingTarget User user);
}
