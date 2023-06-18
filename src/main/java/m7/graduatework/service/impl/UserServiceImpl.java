package m7.graduatework.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import m7.graduatework.dto.user.PasswordDto;
import m7.graduatework.dto.user.UserDto;
import m7.graduatework.dto.user.UserLoginDto;
import m7.graduatework.dto.user.UserRegisterDto;
import m7.graduatework.entity.Role;
import m7.graduatework.entity.User;
import m7.graduatework.mapper.UserDtoMapper;
import m7.graduatework.mapper.UserRegisterDtoMapper;
import m7.graduatework.repository.UserRepository;
import m7.graduatework.service.ImageService;
import m7.graduatework.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegisterDtoMapper userRegisterDtoMapper;
    private final UserDtoMapper userDtoMapper;
    private final HttpServletRequest httpServletRequest;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    /**
     * Относительный путь к картинке для фронта
     */
    @Value("${path.to.users.image.storage.front}")
    private String pathToUsersImageStorageFront;

    /**
     * Путь для хранения картинки относительно корня проекта для бэка
     */
    @Value("${path.to.users.image.root}")
    private String pathToUsersImageStorageRoot;


    public UserServiceImpl(UserRepository userRepository, UserRegisterDtoMapper userRegisterDtoMapper, UserDtoMapper userDtoMapper, HttpServletRequest httpServletRequest, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userRepository = userRepository;
        this.userRegisterDtoMapper = userRegisterDtoMapper;
        this.userDtoMapper = userDtoMapper;
        this.httpServletRequest = httpServletRequest;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    /**
     * Поиск пользователя по юзернейму (email)
     *
     * @param username уникальное имя пользователя (email)
     * @return {@code Optional<User>}
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Изменения пароля пользователя
     *
     * @param passwordDTO {@link PasswordDto DTO для изменения пароля}
     * @return {@code true} - пароль изменен, {@code false} - пароль не изменен.
     */
    @Override
    public boolean setUserPassword(PasswordDto passwordDTO) {
        User user = getCurrentUser();
        if (!user.getPassword().equals(passwordDTO.getCurrentPassword())) {
            return false;
        }
        user.setPassword(passwordDTO.getNewPassword());
        userRepository.save(user);
        return true;
    }

    /**
     * Получение {@link UserDto} текущего (аутентифицированного) пользователя
     *
     * @return {@code UserDto} аутентифицированный пользователь,  {@code null} - если получить не удалось
     */
    @Override
    public UserDto getCurrentUserDto() {
        User user = getCurrentUser();
        return user != null
                ? userDtoMapper.toDto(user)
                : null;
    }

    /**
     * Получение текущего аутентифицированного {@link User} пользователя
     *
     * @return {@code User} аутентифицированный пользователь, {@code null} - если получить не удалось
     */

    @Override
    public User getCurrentUser() {
        Principal principal = httpServletRequest.getUserPrincipal();
        return principal != null
                ? userRepository.findByUsername(principal.getName()).orElse(null)
                : null;
    }

    /**
     * Обновление данных пользователя
     *
     * @param userDto Dto пользователя
     * @return {@code UserDto} с обновленными данными,  {@code null} - если обновить не удалось
     */
    @Override
    public UserDto updateUser(UserDto userDto) {
        User userFromDto = userDtoMapper.toEntity(userDto);
        Optional<User> optionalUser = userRepository.findByUsername(userFromDto.getUsername());
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        userDtoMapper.updateEntityFromDto(userDto, user);
        return userDtoMapper.toDto(userRepository.save(user));
    }

    /**
     * Изменение аватарки пользователя. Проверка наличия пользователя, удаление существующей аватарки, сохранение новой.
     *
     * @param image {@code MultipartFile} файл аватарки
     * @return {@code UserDto} с обновленными данными, {@code null} - если обновить не удалось
     */
    @Override
    public UserDto updateUserImage(MultipartFile image) {
        User user = getCurrentUser();
        if (user == null) {
            return null;
        }
        imageService.deleteImage(pathToUsersImageStorageRoot, user.getImage());
        user.setImage(pathToUsersImageStorageFront + imageService.saveImage(image, pathToUsersImageStorageRoot, pathToUsersImageStorageFront));
        userRepository.save(user);
        return userDtoMapper.toDto(user);
    }

    /**
     * Регистрация пользователя. Проверка на существование пользователя с таким же username (email). Установка роли {@code USER} по умолчанию
     *
     * @param userRegisterDto Dto регистрации пользователя
     * @return {@code User} если зарегистрирован, {@code null} - если отказано в регистрации
     */
    @Override
    public User register(UserRegisterDto userRegisterDto) {
        User user = userRegisterDtoMapper.toEntity(userRegisterDto);
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return null;
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Аутентификация пользователя
     *
     * @param userLoginDTO Dto аутентификации
     * @return {@code true} - аутентификация успешна, {@code false} - в аутентификации  отказано
     */
    @Override
    public boolean login(UserLoginDto userLoginDTO) {
        Optional<User> optionalUser = findByUsername(userLoginDTO.getUsername());
        if (optionalUser.isEmpty()) {
            return false;
        }
        return passwordEncoder.matches(userLoginDTO.getPassword(), optionalUser.get().getPassword());
    }

    @Override
    public String getPathToUsersImageStorageFront() {
        return pathToUsersImageStorageFront;
    }

    @Override
    public String getPathToUsersImageStorageRoot() {
        return pathToUsersImageStorageRoot;
    }
}
