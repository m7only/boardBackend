package m7.graduatework.mapper;

import m7.graduatework.dto.user.UserRegisterDto;
import m7.graduatework.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegisterDtoMapper {
    User toEntity(UserRegisterDto userRegisterDto);
}
