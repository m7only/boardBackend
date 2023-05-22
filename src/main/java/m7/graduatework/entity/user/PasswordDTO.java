package m7.graduatework.entity.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class PasswordDTO {
    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String newPassword;
}
