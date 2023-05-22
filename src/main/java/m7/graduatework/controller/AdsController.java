package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import m7.graduatework.entity.ads.*;
import m7.graduatework.service.AdsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/ads")
@CrossOrigin(value = "http://localhost:3000")
@EnableMethodSecurity
@Validated
@Tag(name = "Объявления", description = "CRUD объявлений, Secured")
public class AdsController {

    private final AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping
    @Operation(summary = "Получить все объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат запроса получен")
    })
    public ResponseEntity<AdsDTO> getAds() {
        return ResponseEntity.of(adsService.getAds());
    }

    @PostMapping
    @Operation(summary = "Добавить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление добавлено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AdDTO> addAds(@RequestBody @Valid AdDTO adDTO) {
        Optional<AdDTO> optionalAdsDTO = adsService.addAds(adDTO);
        return optionalAdsDTO.map(dto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(dto))
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping("/{ad_pk}/comments")
    @Operation(summary = "Получить комментарии к товару")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<CommentsDTO> getComments(@PathVariable(value = "ad_pk") @NotEmpty String adPk) {
        return ResponseEntity.of(adsService.getComments(adPk));
    }

    @PostMapping("/{ad_pk}/comments")
    @Operation(summary = "Добавить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий добавлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CommentDTO> addComment(@PathVariable(value = "ad_pk") @NotEmpty String adPk,
                                                 @RequestBody @Valid CommentDTO commentDTO) {
        return ResponseEntity.of(adsService.addComment(adPk, commentDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить полные данные объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<FullAdDTO> getFullAd(@PathVariable @NotNull Long id) {
        return ResponseEntity.of(adsService.getFullAd(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Объявление удалено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> removeAds(@PathVariable @NotNull Long id) {
        adsService.removeAds(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявление обновлено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AdsDTO> updateAds(@PathVariable @NotNull Long id,
                                            @RequestBody @Valid UpdateAdsDTO updateAdsDTO) {
        return ResponseEntity.of(adsService.updateAds(id, updateAdsDTO));
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Получить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий получен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<CommentDTO> getComments(@PathVariable(value = "ad_pk") @NotEmpty String adPk,
                                                  @PathVariable @NotNull Long id) {
        return ResponseEntity.of(adsService.getComments(adPk, id));
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Удалить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удален"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Void> deleteComments(@PathVariable(value = "ad_pk") @NotEmpty String adPk,
                                               @PathVariable @NotNull Long id) {
        return adsService.deleteComments(adPk, id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    @Operation(summary = "Обновить комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий обновлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable(value = "ad_pk") @NotEmpty String adPk,
                                                     @PathVariable @NotNull Long id,
                                                     @RequestBody @Valid CommentDTO commentDTO) {
        return ResponseEntity.of(adsService.updateComments(adPk, id, commentDTO));
    }

    @GetMapping("/me")
    @Operation(summary = "Получить объявления пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявления получены"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AdsDTO> getAdsMe() {
        return ResponseEntity.of(adsService.getAdsMe());
    }
}
