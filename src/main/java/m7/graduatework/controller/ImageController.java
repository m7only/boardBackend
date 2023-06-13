package m7.graduatework.controller;

import m7.graduatework.service.AdsService;
import m7.graduatework.service.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {

    private final AdsService adsService;
    private final UserService userService;

    public ImageController(AdsService adsService, UserService userService) {
        this.adsService = adsService;
        this.userService = userService;
    }

    @GetMapping(value = "/images/ads/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getAdImage(@PathVariable String fileName) throws IOException {
        Path path = Path.of(adsService.getPathToAdImageStorageRoot(), adsService.getPathToAdImageStorageFront(), fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(Files.size(path))
                .body(new InputStreamResource(Files.newInputStream(path)));
    }

    @GetMapping(value = "/images/users/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getUserImage(@PathVariable String fileName) throws IOException {
        Path path = Path.of(userService.getPathToUsersImageStorageRoot(), userService.getPathToUsersImageStorageFront(), fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(Files.size(path))
                .body(new InputStreamResource(Files.newInputStream(path)));
    }
}
