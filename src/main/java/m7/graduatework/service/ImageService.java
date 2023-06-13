package m7.graduatework.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile image, String pathToImageStorageRoot, String pathToImageStorageFront);
    void deleteImage(String pathToImageStorageRoot,String image);

//    String getPathToImageStorage();
//    String getPathToImageStorageFront();

}
