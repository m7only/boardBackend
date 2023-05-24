package m7.graduatework.service;

import m7.graduatework.entity.user.PasswordDTO;
import m7.graduatework.entity.user.UserDTO;
import m7.graduatework.entity.user.UserLoginDTO;
import m7.graduatework.entity.user.UserRegisterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    Optional<PasswordDTO> setUserPassword(PasswordDTO passwordDTO);

    Optional<UserDTO> getUser();

    Optional<UserDTO> updateUser(UserDTO userDTO);

    Optional<UserDTO> updateUserImage(MultipartFile image);

    UserDTO register(UserRegisterDTO userRegisterDTO);

    boolean login(UserLoginDTO userLoginDTO);
}
