package m7.graduatework.service;

import m7.graduatework.dto.ad.AdDto;
import m7.graduatework.dto.ad.AdsDto;
import m7.graduatework.dto.ad.CreateOrUpdateAdDto;
import m7.graduatework.dto.ad.FullAdDto;
import m7.graduatework.entity.Ad;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AdsService {
    String updateAdsImage(Long id, MultipartFile image);

    AdDto addAds(CreateOrUpdateAdDto properties, MultipartFile image);

    AdsDto getAds();

    FullAdDto getFullAd(Long id);

    Ad getAdById(Long id);

    Long removeAds(Long id);

    Optional<AdDto> updateAd(Long id, CreateOrUpdateAdDto createOrUpdateAdDto);

    AdsDto getAdsMe();

    AdsDto findAdsByTitle(String q);

    String getPathToAdImageStorageFront();

    String getPathToAdImageStorageRoot();
}
