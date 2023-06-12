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
import m7.graduatework.service.AdImageService;
import m7.graduatework.service.AdsService;
import m7.graduatework.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class AdsServiceImpl implements AdsService {

    private final CreateOrUpdateAdsDTOMapper createOrUpdateAdsDtoMapper;
    private final AdDtoMapper adDtoMapper;
    private final FullAdDtoMapper fullAdDtoMapper;
    private final AdRepository adRepository;
    private final UserService userService;
    private final AdImageService adImageService;

    public AdsServiceImpl(CreateOrUpdateAdsDTOMapper createOrUpdateAdsDtoMapper,
                          AdRepository adRepository,
                          UserService userService,
                          AdDtoMapper adDtoMapper,
                          FullAdDtoMapper fullAdDtoMapper,
                          AdImageService adImageService) {
        this.createOrUpdateAdsDtoMapper = createOrUpdateAdsDtoMapper;
        this.adRepository = adRepository;
        this.userService = userService;
        this.adDtoMapper = adDtoMapper;
        this.fullAdDtoMapper = fullAdDtoMapper;
        this.adImageService = adImageService;
    }

    @Override
    public String updateAdsImage(Long id, MultipartFile image) {
        Optional<Ad> optionalAd = adRepository.findById(id);
        if (optionalAd.isEmpty()) {
            return null;
        }
        Ad ad = optionalAd.get();
        adImageService.deleteAdImage(ad.getImage());
        ad.setImage(adImageService.getPathToAdImageStorageFront() + adImageService.saveAdImage(image));
        adRepository.save(ad);
        return ad.getImage();
    }

    @Override
    public AdDto addAds(CreateOrUpdateAdDto properties, MultipartFile image) {
        Ad ad = createOrUpdateAdsDtoMapper.toEntity(properties);
        ad.setAuthor(userService.getCurrentUser());
        ad.setImage(adImageService.getPathToAdImageStorageFront() + adImageService.saveAdImage(image));
        return adDtoMapper.toDto(adRepository.save(ad));
    }

    @Override
    public AdsDto getAds() {
        List<Ad> ads = adRepository.findAll();
        ads.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
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
        Optional<Ad> optionalAd = adRepository.findById(id);
        if (optionalAd.isPresent()) {
            adRepository.deleteById(id);
            adImageService.deleteAdImage(optionalAd.get().getImage());
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

    @Override
    public AdsDto findAdsByTitle(String q) {
        List<Ad> ads = adRepository.findByTitleLikeIgnoreCase(q);
        return new AdsDto(ads.size(), adDtoMapper.toDtoList(ads));
    }
}
