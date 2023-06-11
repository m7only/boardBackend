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
import m7.graduatework.service.UserService;
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


    public UserServiceImpl(UserRepository userRepository, UserRegisterDtoMapper userRegisterDtoMapper, UserDtoMapper userDtoMapper, HttpServletRequest httpServletRequest, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRegisterDtoMapper = userRegisterDtoMapper;
        this.userDtoMapper = userDtoMapper;
        this.httpServletRequest = httpServletRequest;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

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

    @Override
    public Optional<UserDto> getCurrentUserDto() {
        return Optional.ofNullable(userDtoMapper.toDto(getCurrentUser()));
    }

    @Override
    public User getCurrentUser() {
        Principal principal = httpServletRequest.getUserPrincipal();
        return principal != null
                ? userRepository.findByUsername(principal.getName()).orElse(null)
                : null;
    }

    @Override
    public Optional<UserDto> updateUser(UserDto userDto) {
        User userFromDto = userDtoMapper.toEntity(userDto);
        Optional<User> optionalUser = userRepository.findByUsername(userFromDto.getUsername());
        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        userDtoMapper.updateEntityFromDto(userDto, user);
        return Optional.of(userDtoMapper.toDto(userRepository.save(user)));
    }

    @Override
    public Optional<UserDto> updateUserImage(MultipartFile image) {
        return Optional.of(new UserDto());
    }

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

    @Override
    public boolean login(UserLoginDto userLoginDTO) {
        Optional<User> optionalUser = findByUsername(userLoginDTO.getUsername());
        if (optionalUser.isEmpty()) {
            return false;
        }
        return passwordEncoder.matches(userLoginDTO.getPassword(), optionalUser.get().getPassword());
//        return userRepository
//                .findByUsernameAndPassword(
//                        userLoginDTO.getUsername(),
//                        userLoginDTO.getPassword())
//                .isPresent();
    }
}
