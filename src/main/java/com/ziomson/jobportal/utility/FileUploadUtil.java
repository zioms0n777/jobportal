package com.ziomson.jobportal.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String filename, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }


        try (InputStream inputStream = file.getInputStream();) {
            Path path = uploadPath.resolve(filename);
            System.out.println("FilePath: " + path);
            System.out.println("FileName: " + filename);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            throw new IOException("Could not save file " + filename, e);
        }
        }
}
