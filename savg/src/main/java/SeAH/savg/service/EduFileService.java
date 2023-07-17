package SeAH.savg.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
public class EduFileService {

    @Value("${eduFileLocation}")
    private String eduFileLocation;

    //파일등록
    public String uploadFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String savedFileName = generateUniqueFileName(originalFilename);
        String fileUploadFullUrl = eduFileLocation + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(file.getBytes());
        fos.close();
        return savedFileName;
    }

    //파일 업데이트
    public void updateFile(String fileName, MultipartFile file) throws Exception {
        deleteFile(fileName);
        uploadFile(file);
    }

    //삭제
    public void deleteFile(String fileName) {
        String filePath = eduFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    //파일명생성
    private String generateUniqueFileName(String originalFilename) {
        UUID uuid = UUID.randomUUID();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return uuid.toString() + "." + extension;
    }
}
