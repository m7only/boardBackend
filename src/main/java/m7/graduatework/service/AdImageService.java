package m7.graduatework.service;

import org.springframework.web.multipart.MultipartFile;

public interface AdImageService {
    String saveAdImage(MultipartFile image);

    String getPathToAdImageStorage();

    String getPathToAdImageStorageFront();

    void deleteAdImage(String image);
}
