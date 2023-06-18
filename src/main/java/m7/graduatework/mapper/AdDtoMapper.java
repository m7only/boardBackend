package m7.graduatework.mapper;

import m7.graduatework.dto.ad.AdDto;
import m7.graduatework.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdDtoMapper {
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "pk", source = "id")
    AdDto toDto(Ad ad);

    List<AdDto> toDtoList(List<Ad> ads);

}
