package m7.graduatework.service;

import m7.graduatework.entity.ads.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

public interface AdsService {
    Path updateAdsImage(Long id, MultipartFile image);

    Optional<CommentDTO> addComment(String adPk, CommentDTO commentDTO);

    Optional<CommentsDTO> getComments(String ad_pk);

    Optional<CommentDTO> getComments(String adPk, Long id);

    boolean deleteComments(String adPk, Long id);

    Optional<CommentDTO> updateComments(String adPk, Long id, CommentDTO commentDTO);

    Optional<AdDTO> addAds(AdDTO adDTO);

    Optional<AdsDTO> getAds();

    Optional<FullAdDTO> getFullAd(Long id);

    Long removeAds(Long id);

    Optional<AdsDTO> updateAds(Long id, UpdateAdsDTO updateAdsDTO);

    Optional<AdsDTO> getAdsMe();
}
