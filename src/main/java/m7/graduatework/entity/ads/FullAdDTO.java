package m7.graduatework.entity.ads;

import lombok.Data;
import m7.graduatework.entity.image.Image;

import java.util.List;

@Data
public class FullAdDTO {
    private Long pk;
    private Integer price;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String phone;
    private String title;
    private List<Image> image;
}
