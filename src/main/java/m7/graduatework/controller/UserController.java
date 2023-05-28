package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import m7.graduatework.entity.user.PasswordDTO;
import m7.graduatework.entity.user.UserDTO;
import m7.graduatework.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль обновлен"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    public ResponseEntity<PasswordDTO> setPassword(@RequestBody @Valid PasswordDTO passwordDTO) {
        return ResponseEntity.of(userService.setUserPassword(passwordDTO));
    }

    @GetMapping("/me")
    @Operation(summary = "Получить информацию об авторизованном пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации")
    })
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.of(userService.getUser());
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновить информацию об авторизованном пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные обновлены"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации")
    })
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.of(userService.updateUser(userDTO));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "\n" +
            "Обновить аватар авторизованного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение изменено"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации")
    })
    public ResponseEntity<UserDTO> updateUserImage(@RequestParam MultipartFile image) {
        return ResponseEntity.of(userService.updateUserImage(image));
    }
}
