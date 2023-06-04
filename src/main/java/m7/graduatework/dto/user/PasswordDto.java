package m7.graduatework.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class PasswordDto {
    @NotEmpty
    private String currentPassword;
    @NotEmpty
    private String newPassword;
}
