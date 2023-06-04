package m7.graduatework.dto.user;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class UserRegisterDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
}
