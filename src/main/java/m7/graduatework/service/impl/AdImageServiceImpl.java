package m7.graduatework.service.impl;

import m7.graduatework.service.AdImageService;
import m7.graduatework.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class AdImageServiceImpl implements AdImageService {

    private final FileService fileService;
    @Value("${path.to.ads.image.storage}")
    private String pathToAdImageStorage;
    @Value("${path.to.ads.image.storage.front}")
    private String pathToAdImageStorageFront;
    @Value("${path.to.ads.image.root}")
    private String pathToAdImageStorageRoot;

    public AdImageServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public String saveAdImage(MultipartFile image) {
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID() + "." + FilenameUtils.getExtension(image.getOriginalFilename());
            Path path = Path.of(pathToAdImageStorage, fileName);
            fileService.upload(image, path);
            return path.getFileName().toString();
        }
        return null;
    }

    @Override
    public void deleteAdImage(String image) {
        fileService.delete(Path.of(pathToAdImageStorageRoot, image));
    }

    @Override
    public String getPathToAdImageStorage() {
        return pathToAdImageStorage;
    }

    @Override
    public String getPathToAdImageStorageFront() {
        return pathToAdImageStorageFront;
    }

}
