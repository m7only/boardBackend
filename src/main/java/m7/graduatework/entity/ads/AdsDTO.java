package m7.graduatework.entity.ads;

import lombok.Data;

import java.util.List;

@Data
public class AdsDTO {
    private Integer count;
    private List<AdDTO> results;
}
