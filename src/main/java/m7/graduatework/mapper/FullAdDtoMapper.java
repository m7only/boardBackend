package m7.graduatework.mapper;

import m7.graduatework.dto.ad.FullAdDto;
import m7.graduatework.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FullAdDtoMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "email", source = "author.username")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    FullAdDto toDto(Ad ad);

}
