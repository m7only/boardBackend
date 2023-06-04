package m7.graduatework.service;

import m7.graduatework.dto.ad.AdDto;
import m7.graduatework.dto.ad.AdsDto;
import m7.graduatework.dto.ad.CreateOrUpdateAdDto;
import m7.graduatework.dto.ad.FullAdDto;
import m7.graduatework.entity.Ad;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

public interface AdsService {
    Path updateAdsImage(Long id, MultipartFile image);

    Optional<AdDto> addAds(CreateOrUpdateAdDto properties, MultipartFile image);

    Optional<AdsDto> getAds();

    Optional<FullAdDto> getFullAd(Long id);

    Ad getAdById(Long id);

    Long removeAds(Long id);

    Optional<AdDto> updateAd(Long id, CreateOrUpdateAdDto createOrUpdateAdDto);

    Optional<AdsDto> getAdsMe();
}
