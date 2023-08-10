package top.flapypan.blog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 上传相关服务
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UploadService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss_");

    @Value("${blog.upload-path}")
    private String uploadPath;

    /**
     * 上传文件
     */
    public String upload(MultipartFile multipartFile) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        // 获取时间
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        String day = String.valueOf(now.getDayOfMonth());
        // 检查并创建父文件夹
        Path fileUploadDirPath = Path.of(uploadPath, year, month, day);
        if (Files.notExists(fileUploadDirPath)) {
            Files.createDirectories(fileUploadDirPath);
        }
        // 获取后缀名
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        // 生成随机文件名
        String filename = now.format(dateTimeFormatter) + UUID.randomUUID()
                + (extension == null ? ".jpg" : ("." + extension));
        // 例子 .../upload/2023/6/1/2023-06-01_12-00-00_620be734-a06d-426b-bc09-fe0f48efb314.jpg
        Path filePath = fileUploadDirPath.resolve(filename);
        log.info("上传文件: {}", filePath);
        // 保存文件
        multipartFile.transferTo(filePath);
        // 返回访问路径
        return "/static/" + year + "/" + month + "/" + day + "/" + filename;
    }

}
