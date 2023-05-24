package m7.graduatework.service;

import m7.graduatework.entity.ads.AdDTO;
import m7.graduatework.entity.ads.AdsDTO;
import m7.graduatework.entity.ads.FullAdDTO;
import m7.graduatework.entity.ads.UpdateAdsDTO;
import m7.graduatework.entity.coments.CommentDTO;
import m7.graduatework.entity.coments.CommentTextDTO;
import m7.graduatework.entity.coments.CommentsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

public interface AdsService {
    Path updateAdsImage(Long id, MultipartFile image);

    Optional<CommentDTO> addComment(String adPk, CommentTextDTO commentTextDTO);

    Optional<CommentsDTO> getComments(String ad_pk);

    boolean deleteComments(String adPk, Long id);

    Optional<CommentDTO> updateComments(String adPk, Long id, CommentDTO commentDTO);

    Optional<AdDTO> addAds(AdDTO adDTO, MultipartFile image);

    Optional<AdsDTO> getAds();

    Optional<FullAdDTO> getFullAd(Long id);

    Long removeAds(Long id);

    Optional<AdsDTO> updateAds(Long id, UpdateAdsDTO updateAdsDTO);

    Optional<AdsDTO> getAdsMe();
}
