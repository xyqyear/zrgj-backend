package zrgj.order_everyday.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import zrgj.order_everyday.pojo.dto.ResultMap;

public class FileService {
    public static String UPLOADED_FOLDER = "/home/www/waimai.muzi.fun/uploads/";
    public static String BASE_URL = "http://waimai.muzi.fun/uploads/";

    public static ResultMap saveFile(byte[] file, String fileExtension) {
        String fileName = String.valueOf(System.currentTimeMillis());
        try {
            Path path = Paths.get(UPLOADED_FOLDER + fileName + "." + fileExtension);
            Files.write(path, file);
        } catch (IOException e) {
            return ResultMap.failure("upload failed: " + e.getMessage());
        }
        return ResultMap.success(Map.of("fileUrl", BASE_URL + fileName + fileExtension));
    }
}
