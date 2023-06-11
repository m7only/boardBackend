package m7.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import m7.graduatework.dto.user.UserLoginDto;
import m7.graduatework.dto.user.UserRegisterDto;
import m7.graduatework.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class AuthorizationController {

    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь зарегистрирован"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDto userRegisterDTO) {
        return userService.register(userRegisterDTO) != null
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь авторизирован"),
            @ApiResponse(responseCode = "401", description = "Нет авторизации"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен"),
            @ApiResponse(responseCode = "404", description = "Данные не найдены")
    })
    public ResponseEntity<Void> login(@RequestBody @Valid UserLoginDto userLoginDTO) {
        return userService.login(userLoginDTO)
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
