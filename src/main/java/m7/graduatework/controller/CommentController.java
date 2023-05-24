package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import m7.graduatework.entity.coments.CommentDTO;
import m7.graduatework.entity.coments.CommentTextDTO;
import m7.graduatework.entity.coments.CommentsDTO;
import m7.graduatework.service.AdsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin(value = "http://localhost:3000")
@EnableMethodSecurity
@Validated
@Tag(name = "Комментарии", description = "CRUD комментариев, Secured")
public class CommentController {

    private final AdsService adsService;

    public CommentController(AdsService adsService) {
        this.adsService = adsService;
    }


    @GetMapping("/ads/{id}/comments")
    @Operation(summary = "Получить комментарии к товару")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<CommentsDTO> getComments(@PathVariable(value = "id") @NotEmpty String adId) {
        return ResponseEntity.of(adsService.getComments(adId));
    }

    @PostMapping("/ads/{id}/comments")
    @Operation(summary = "Добавить комментарий к объявлению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий добавлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации")
    })
    public ResponseEntity<CommentDTO> addComment(@PathVariable(value = "id") @NotEmpty String adId,
                                                 @RequestBody @Valid CommentTextDTO commentTextDTO) {
        return ResponseEntity.of(adsService.addComment(adId, commentTextDTO));
    }


    @DeleteMapping("/ads/{adId}/comments/{id}")
    @Operation(summary = "Удалить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удален"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    public ResponseEntity<Void> deleteComments(@PathVariable(value = "adId") @NotEmpty String adId,
                                               @PathVariable @NotNull Long id) {
        return adsService.deleteComments(adId, id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/ads/{adId}/comments/{id}")
    @Operation(summary = "Обновить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий обновлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    public ResponseEntity<CommentDTO> updateComments(@PathVariable(value = "adId") @NotEmpty String adId,
                                                     @PathVariable @NotNull Long id,
                                                     @RequestBody @Valid CommentDTO commentDTO) {
        return ResponseEntity.of(adsService.updateComments(adId, id, commentDTO));
    }
}
