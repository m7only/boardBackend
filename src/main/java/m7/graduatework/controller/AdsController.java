package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import m7.graduatework.entity.ads.AdDTO;
import m7.graduatework.entity.ads.AdsDTO;
import m7.graduatework.entity.ads.FullAdDTO;
import m7.graduatework.entity.ads.UpdateAdsDTO;
import m7.graduatework.service.AdsService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequestMapping
@CrossOrigin(value = "http://localhost:3000")
@EnableMethodSecurity
@Validated
@Tag(name = "Объявления", description = "CRUD объявлений, Secured")
public class AdsController {

    private final AdsService adsService;

    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping("/ads")
    @Operation(summary = "Получить все объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат запроса получен")
    })
    public ResponseEntity<AdsDTO> getAds() {
        return ResponseEntity.of(adsService.getAds());
    }

    @PostMapping(
            value = "/ads",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Добавить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление добавлено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<AdDTO> addAds(@RequestPart(value = "properties") @Valid AdDTO adDTO,
                                        @RequestPart(value = "image") MultipartFile image) {
        Optional<AdDTO> optionalAdsDTO = adsService.addAds(adDTO, image);
        return optionalAdsDTO.map(dto -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(dto))
                .orElseGet(() -> ResponseEntity
                        .notFound()
                        .build());
    }

    @GetMapping("/ads/{id}")
    @Operation(summary = "Получить полные данные объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Данные получены"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<FullAdDTO> getFullAd(@PathVariable @NotNull Long id) {
        return ResponseEntity.of(adsService.getFullAd(id));
    }

    @DeleteMapping("/ads/{id}")
    @Operation(summary = "Удалить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Объявление удалено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
    })
    public ResponseEntity<Void> removeAds(@PathVariable @NotNull Long id) {
        adsService.removeAds(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/ads/{id}")
    @Operation(summary = "Обновить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявление обновлено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<AdsDTO> updateAds(@PathVariable @NotNull Long id,
                                            @RequestBody @Valid UpdateAdsDTO updateAdsDTO) {
        return ResponseEntity.of(adsService.updateAds(id, updateAdsDTO));
    }

    @GetMapping("/ads/me")
    @Operation(summary = "Получить объявления авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявления получены"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации")
    })
    public ResponseEntity<AdsDTO> getAdsMe() {
        return ResponseEntity.of(adsService.getAdsMe());
    }

    @PatchMapping(
            value = "/image/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Обновить изображение объявления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение обновлено"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<InputStreamResource> updateAdsImage(@PathVariable @NotNull Long id,
                                                              @RequestParam MultipartFile image) throws IOException {
        Path path = adsService.updateAdsImage(id, image);
        return path != null
                ? ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(Files.size(path))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString())
                .body(new InputStreamResource(Files.newInputStream(path)))
                : ResponseEntity.notFound().build();
    }
}
