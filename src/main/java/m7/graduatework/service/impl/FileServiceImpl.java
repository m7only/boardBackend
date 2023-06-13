package m7.graduatework.service.impl;

import m7.graduatework.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    /**
     * Сохранение тектового файла.
     *
     * @param data сохраняемый текст
     * @param path путь к сохраняемому файлу
     * @return {@code Path} путь к сохраненному файлу
     */
    @Override
    public Path save(String data, Path path) {
        try {
            Files.writeString(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * Чтение текстового файла.
     *
     * @param path путь читаемого файла
     * @return {@code Optional<String>} текст из файла
     */
    @Override
    public Optional<String> read(Path path) {
        try {
            return Optional.ofNullable(Files.readString(path));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Удаление файла.
     *
     * @param path путь удаляемого файла
     */
    @Override
    public void delete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохранение загружаемого файла в файловое хранилище.
     *
     * @param file загружаемый файл
     * @param path путь для сохранения
     * @return {@code true} - если успех, {@code false} - если провал
     */
    @Override
    public boolean upload(MultipartFile file, Path path) {
        try {
            Files.deleteIfExists(path);
            path.toFile().getParentFile().mkdirs();
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            IOUtils.copy(file.getInputStream(), fos);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
