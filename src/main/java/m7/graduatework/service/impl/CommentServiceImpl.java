package m7.graduatework.service.impl;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.dto.coment.CommentTextDto;
import m7.graduatework.dto.coment.CommentsDto;
import m7.graduatework.entity.Ad;
import m7.graduatework.entity.Comment;
import m7.graduatework.mapper.CommentDtoMapper;
import m7.graduatework.mapper.CommentTextDtoMapper;
import m7.graduatework.repository.CommentRepository;
import m7.graduatework.service.AdsService;
import m7.graduatework.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsService adsService;
    private final CommentTextDtoMapper commentTextDtoMapper;
    private final CommentDtoMapper commentDtoMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentTextDtoMapper commentTextDtoMapper,
                              AdsService adsService,
                              CommentDtoMapper commentDtoMapper) {
        this.commentRepository = commentRepository;
        this.commentTextDtoMapper = commentTextDtoMapper;
        this.adsService = adsService;
        this.commentDtoMapper = commentDtoMapper;
    }

    @Override
    public Optional<CommentDto> addComment(Long adId, CommentTextDto commentTextDto) {
        Comment comment = commentTextDtoMapper.toEntity(commentTextDto);
        Ad ad = adsService.getAdById(adId);
        if (ad == null) {
            return Optional.empty();
        }
        comment.setAd(ad);
        comment.setAuthor(ad.getAuthor());
        comment.setCreatedAt(LocalDateTime.now());
        return Optional.of(commentDtoMapper.toDto(commentRepository.save(comment)));
    }

    @Override
    public CommentsDto getComments(Long adId) {
        Ad ad = adsService.getAdById(adId);
        return new CommentsDto(
                ad.getComments().size(),
                commentDtoMapper.toDtoList(ad.getComments().stream().toList())
        );
    }

    @Override
    public Long deleteComment(Long adId, Long id) {
        if (commentRepository.findByIdAndAd_Id(id, adId).isPresent()) {
            commentRepository.removeById(id);
            return id;
        }
        return null;
    }

    @Override
    public Optional<CommentDto> updateComment(Long adId, Long id, CommentDto commentDto) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            return Optional.empty();
        }
        Comment comment = commentOptional.get();
        commentDtoMapper.updateEntityFromDto(commentDto, comment);
        return Optional.of(commentDtoMapper.toDto(commentRepository.save(comment)));
    }
}
