package m7.graduatework.service.impl;

import m7.graduatework.service.FileService;
import m7.graduatework.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final FileService fileService;

    public ImageServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public String saveImage(MultipartFile image, String pathToImageStorageRoot, String pathToImageStorageFront) {
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(image.getOriginalFilename());
            Path path = Path.of(pathToImageStorageRoot, pathToImageStorageFront, fileName);
            fileService.upload(image, path);
            return path.getFileName().toString();
        }
        return null;
    }

    @Override
    public void deleteImage(String pathToImageStorageRoot, String image) {
        fileService.delete(Path.of(pathToImageStorageRoot, image));
    }
}
