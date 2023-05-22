package m7.graduatework.service.impl;

import m7.graduatework.entity.user.PasswordDTO;
import m7.graduatework.entity.user.UserDTO;
import m7.graduatework.entity.user.UserLoginDTO;
import m7.graduatework.entity.user.UserRegisterDTO;
import m7.graduatework.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public Optional<PasswordDTO> setUserPassword(PasswordDTO passwordDTO) {
        return Optional.of(new PasswordDTO());
    }

    @Override
    public Optional<UserDTO> getUser() {
        return Optional.of(new UserDTO());
    }

    @Override
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(new UserDTO());
    }

    @Override
    public Optional<UserDTO> updateUserImage(MultipartFile image) {
        return Optional.of(new UserDTO());
    }

    @Override
    public UserDTO register(UserRegisterDTO userRegisterDTO) {
        return new UserDTO();
    }

    @Override
    public boolean login(UserLoginDTO userLoginDTO) {
        return true;
    }
}
