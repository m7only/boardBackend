package m7.graduatework.dto.ad;

import lombok.Data;

@Data
public class FullAdDto {
    private Long pk;
    private Integer price;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String phone;
    private String title;
    private String image;
}
