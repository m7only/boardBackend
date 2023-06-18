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

    /**
     * Сохранение изображения на диск с формированием уникального имени с помощью UUID
     *
     * @param image                   {@code MultipartFile} файл изображения
     * @param pathToImageStorageRoot  путь к хранилищу изображений
     * @param pathToImageStorageFront путь для формирования относительной ссылки для фронта
     * @return {@code String} - путь при удачном сохранении изображения, {@code null} - при ошибке сохранения
     */
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

    /**
     * Удаление изображения с диска
     *
     * @param pathToImageStorageRoot путь к хранилищу изображений
     * @param image                  название файла
     */
    @Override
    public void deleteImage(String pathToImageStorageRoot, String image) {
        fileService.delete(Path.of(pathToImageStorageRoot, image));
    }
}
