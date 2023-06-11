package m7.graduatework.service.impl;

import m7.graduatework.dto.ad.AdDto;
import m7.graduatework.dto.ad.AdsDto;
import m7.graduatework.dto.ad.CreateOrUpdateAdDto;
import m7.graduatework.dto.ad.FullAdDto;
import m7.graduatework.entity.Ad;
import m7.graduatework.mapper.AdDtoMapper;
import m7.graduatework.mapper.CreateOrUpdateAdsDTOMapper;
import m7.graduatework.mapper.FullAdDtoMapper;
import m7.graduatework.repository.AdRepository;
import m7.graduatework.service.AdsService;
import m7.graduatework.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class AdsServiceImpl implements AdsService {

    private final CreateOrUpdateAdsDTOMapper createOrUpdateAdsDtoMapper;
    private final AdDtoMapper adDtoMapper;
    private final FullAdDtoMapper fullAdDtoMapper;
    private final AdRepository adRepository;
    private final UserService userService;

    public AdsServiceImpl(CreateOrUpdateAdsDTOMapper createOrUpdateAdsDtoMapper,
                          AdRepository adRepository,
                          UserService userService,
                          AdDtoMapper adDtoMapper,
                          FullAdDtoMapper fullAdDtoMapper) {
        this.createOrUpdateAdsDtoMapper = createOrUpdateAdsDtoMapper;
        this.adRepository = adRepository;
        this.userService = userService;
        this.adDtoMapper = adDtoMapper;
        this.fullAdDtoMapper = fullAdDtoMapper;
    }

    @Override
    public Path updateAdsImage(Long id, MultipartFile image) {
        return Path.of("/");
    }

    @Override
    public AdDto addAds(CreateOrUpdateAdDto properties, MultipartFile image) {
        Ad ad = createOrUpdateAdsDtoMapper.toEntity(properties);
        ad.setAuthor(userService.getCurrentUser());
        return adDtoMapper.toDto(adRepository.save(ad));
    }

    @Override
    public AdsDto getAds() {
        List<Ad> ads = adRepository.findAll();
        return new AdsDto(ads.size(), adDtoMapper.toDtoList(ads));
    }

    @Override
    public FullAdDto getFullAd(Long id) {
        return fullAdDtoMapper.toDto(getAdById(id));
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id).orElse(null);
    }

    @Override
    public Long removeAds(Long id) {
        if (adRepository.findById(id).isPresent()) {
            adRepository.removeById(id);
            return id;
        }
        return null;
    }

    @Override
    public Optional<AdDto> updateAd(Long id, CreateOrUpdateAdDto createOrUpdateAdDTO) {
        Optional<Ad> optionalAd = adRepository.findById(id);
        if (optionalAd.isEmpty()) {
            return Optional.empty();
        }
        Ad ad = optionalAd.get();
        createOrUpdateAdsDtoMapper.updateEntityFromDto(createOrUpdateAdDTO, ad);
        return Optional.of(adDtoMapper.toDto(adRepository.save(ad)));
    }

    @Override
    public AdsDto getAdsMe() {
        List<Ad> ads = adRepository.findByAuthor(userService.getCurrentUser());
        return new AdsDto(ads.size(), adDtoMapper.toDtoList(ads));
    }
}
