package m7.graduatework.service.impl;

import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.dto.coment.CommentTextDto;
import m7.graduatework.dto.coment.CommentsDto;
import m7.graduatework.entity.Ad;
import m7.graduatework.entity.Comment;
import m7.graduatework.entity.User;
import m7.graduatework.mapper.CommentDtoMapper;
import m7.graduatework.mapper.CommentTextDtoMapper;
import m7.graduatework.repository.CommentRepository;
import m7.graduatework.service.AdsService;
import m7.graduatework.service.CommentService;
import m7.graduatework.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdsService adsService;
    private final UserService userService;
    private final CommentTextDtoMapper commentTextDtoMapper;
    private final CommentDtoMapper commentDtoMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentTextDtoMapper commentTextDtoMapper,
                              AdsService adsService,
                              UserService userService, CommentDtoMapper commentDtoMapper) {
        this.commentRepository = commentRepository;
        this.commentTextDtoMapper = commentTextDtoMapper;
        this.adsService = adsService;
        this.userService = userService;
        this.commentDtoMapper = commentDtoMapper;
    }

    /**
     * Добавление комментария. Присвоение комментарию автора, даты и времени создания
     *
     * @param adId           идентификатор объявления
     * @param commentTextDto Dto текста комментария
     * @return {@code CommentDto} при успешном добавлении, {@code null} - при попытке добавить комментарий к несуществующему объявлению или не аутентифицированным пользователем
     */
    @Override
    public CommentDto addComment(Long adId, CommentTextDto commentTextDto) {
        Comment comment = commentTextDtoMapper.toEntity(commentTextDto);
        Ad ad = adsService.getAdById(adId);
        User user = userService.getCurrentUser();
        if (ad == null || user == null) {
            return null;
        }
        comment.setAd(ad);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDateTime.now());
        return commentDtoMapper.toDto(commentRepository.save(comment));
    }

    /**
     * Получение комментариев к объявлению
     *
     * @param adId идентификатор объявления
     * @return {@code CommentsDto} - Dto списка комментариев
     */
    @Override
    public CommentsDto getComments(Long adId) {
        Ad ad = adsService.getAdById(adId);
        return new CommentsDto(
                ad.getComments().size(),
                commentDtoMapper.toDtoList(ad.getComments().stream().toList())
        );
    }

    /**
     * Удаление комментария по идентификатору объявления и комментария
     *
     * @param adId идентификатор объявления
     * @param id   идентификатор комментария
     * @return идентификатор удаленного комментария в случае успеха, {@code null} - если комментарий с заданными идентификаторами не найден
     */
    @Override
    @PreAuthorize("@checkPermit.isCommentOwnerOrAdmin(authentication, #id)")
    public Long deleteComment(Long adId, Long id) {
        if (commentRepository.findByIdAndAd_Id(id, adId).isPresent()) {
            commentRepository.deleteById(id);
            return id;
        }
        return null;
    }

    /**
     * Обновление комментария по идентификатору объявления и комментария
     *
     * @param adId           идентификатор объявления
     * @param id             идентификатор комментария
     * @param commentTextDto Dto текста комментария
     * @return {@code CommentDto} с обновленным комментарием, {@code null} - если комментарий с заданными идентификаторами не найден
     */
    @Override
    @PreAuthorize("@checkPermit.isCommentOwnerOrAdmin(authentication, #id)")
    public CommentDto updateComment(Long adId, Long id, CommentTextDto commentTextDto) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if (commentOptional.isEmpty()) {
            return null;
        }
        Comment comment = commentOptional.get();
        commentTextDtoMapper.updateEntityFromDto(commentTextDto, comment);
        return commentDtoMapper.toDto(commentRepository.save(comment));
    }

    /**
     * Получение комментария по идентификатору
     *
     * @param id идентификатор комментария
     * @return {@code Comment} комментарий, {@code null} - если комментарий не найден
     */
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
}
