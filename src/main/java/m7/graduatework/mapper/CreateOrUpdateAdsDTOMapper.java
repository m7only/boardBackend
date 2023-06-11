package m7.graduatework.mapper;

import m7.graduatework.dto.ad.CreateOrUpdateAdDto;
import m7.graduatework.entity.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreateOrUpdateAdsDTOMapper {
    Ad toEntity(CreateOrUpdateAdDto dto);

    void updateEntityFromDto(CreateOrUpdateAdDto createOrUpdateAdDto, @MappingTarget Ad ad);
}
