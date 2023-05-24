package m7.graduatework.service.impl;

import m7.graduatework.entity.ads.AdDTO;
import m7.graduatework.entity.ads.AdsDTO;
import m7.graduatework.entity.ads.FullAdDTO;
import m7.graduatework.entity.ads.UpdateAdsDTO;
import m7.graduatework.entity.coments.CommentDTO;
import m7.graduatework.entity.coments.CommentTextDTO;
import m7.graduatework.entity.coments.CommentsDTO;
import m7.graduatework.service.AdsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

@Service
public class AdsServiceImpl implements AdsService {
    @Override
    public Path updateAdsImage(Long id, MultipartFile image) {
        return Path.of("/");
    }

    @Override
    public Optional<CommentDTO> addComment(String adPk, CommentTextDTO commentTextDTO) {
        return Optional.of(new CommentDTO());
    }

    @Override
    public Optional<CommentsDTO> getComments(String ad_pk) {
        return Optional.of(new CommentsDTO());
    }

    @Override
    public boolean deleteComments(String adPk, Long id) {
        return true;
    }

    @Override
    public Optional<CommentDTO> updateComments(String adPk, Long id, CommentDTO commentDTO) {
        return Optional.of(new CommentDTO());
    }

    @Override
    public Optional<AdDTO> addAds(AdDTO adDTO, MultipartFile image) {
        return Optional.of(new AdDTO());
    }

    @Override
    public Optional<AdsDTO> getAds() {
        return Optional.of(new AdsDTO());
    }

    @Override
    public Optional<FullAdDTO> getFullAd(Long id) {
        return Optional.of(new FullAdDTO());
    }

    @Override
    public Long removeAds(Long id) {
        return 0L;
    }

    @Override
    public Optional<AdsDTO> updateAds(Long id, UpdateAdsDTO updateAdsDTO) {
        return Optional.of(new AdsDTO());
    }

    @Override
    public Optional<AdsDTO> getAdsMe() {
        return Optional.of(new AdsDTO());
    }
}
