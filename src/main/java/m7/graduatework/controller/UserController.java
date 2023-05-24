package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import m7.graduatework.entity.user.PasswordDTO;
import m7.graduatework.entity.user.UserDTO;
import m7.graduatework.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@CrossOrigin(value = "http://localhost:3000")
@EnableMethodSecurity
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
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<PasswordDTO> setPassword(@RequestBody @Valid PasswordDTO passwordDTO) {
        return ResponseEntity.of(userService.setUserPassword(passwordDTO));
    }

    @GetMapping("/me")
    @Operation(summary = "Получить данные пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные получены"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.of(userService.getUser());
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновить данные пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные обновлены"),
            @ApiResponse(responseCode = "204", description = "Данные обновлены"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.of(userService.updateUser(userDTO));
    }

    @PatchMapping("/me/image")
    @Operation(summary = "Изменить изображение пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение изменено"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDTO> updateUserImage(@RequestParam MultipartFile image) {
        return ResponseEntity.of(userService.updateUserImage(image));
    }
}
