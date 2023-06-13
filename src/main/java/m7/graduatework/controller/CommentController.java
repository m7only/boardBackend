package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import m7.graduatework.dto.coment.CommentDto;
import m7.graduatework.dto.coment.CommentTextDto;
import m7.graduatework.dto.coment.CommentsDto;
import m7.graduatework.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@Validated
@Tag(name = "Комментарии", description = "CRUD комментариев, Secured")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получить комментарии к товару")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<CommentsDto> getComments(@PathVariable(value = "id") @NotNull Long adId) {
        return ResponseEntity.ofNullable(commentService.getComments(adId));
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Добавить комментарий к объявлению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий добавлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации")
    })
    public ResponseEntity<CommentDto> addComment(@PathVariable(value = "id") @NotNull Long adId,
                                                 @RequestBody CommentTextDto commentTextDto) {
        return ResponseEntity.of(commentService.addComment(adId, commentTextDto));
    }


    @DeleteMapping("/{adId}/comments/{id}")
    @Operation(summary = "Удалить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удален"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @PreAuthorize("@checkPermit.isCommentOwnerOrAdmin(authentication, #id)")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "adId") @NotNull Long adId,
                                              @PathVariable @NotNull Long id) {
        return commentService.deleteComment(adId, id) != null
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{adId}/comments/{id}")
    @Operation(summary = "Обновить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий обновлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    @PreAuthorize("@checkPermit.isCommentOwnerOrAdmin(authentication, #id)")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "adId") @NotNull Long adId,
                                                    @PathVariable @NotNull Long id,
                                                    @RequestBody @Valid CommentTextDto commentTextDto) {
        return ResponseEntity.of(commentService.updateComment(adId, id, commentTextDto));
    }
}
