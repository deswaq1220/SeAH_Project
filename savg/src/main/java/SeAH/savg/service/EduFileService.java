package SeAH.savg.service;


import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class EduFileService {

    private final EduFileRepository eduFileRepository;
    public EduFileService(EduFileRepository eduFileRepository) {
        this.eduFileRepository = eduFileRepository;
    }

    @Value("${eduFileLocation}")
    private String eduFileLocation;


    //파일 등록
/*    public String uploadFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String savedFileName = generateUniqueFileName(originalFilename);
        String fileUploadFullUrl = eduFileLocation +"\\" + savedFileName;

        System.out.println("파일경로" + fileUploadFullUrl);
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(file.getBytes());
        fos.close();
        return savedFileName;
    }*/
    public EduFile uploadFile(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String savedFileName = generateUniqueFileName(originalFilename);
        String fileUploadFullUrl = eduFileLocation + File.separator + savedFileName;

        System.out.println("파일경로: " + fileUploadFullUrl);
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(file.getBytes());
        fos.close();

        // 파일 정보 생성 및 저장
        EduFile eduFile = new EduFile();
        eduFile.setEduFileName(savedFileName);
        eduFile.setEduFileOriName(originalFilename);
        eduFile.setEduFileUrl(fileUploadFullUrl);
        eduFileRepository.save(eduFile); // 데이터베이스에 저장

        return eduFile;
    }

    //파일업뎃
    public void updateFile(String fileName, MultipartFile file) throws Exception {
        deleteFile(fileName);
        uploadFile(file);
    }

    //파일삭제
    public void deleteFile(String fileName) {
        String filePath = eduFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    //파일명설정
    private String generateUniqueFileName(String originalFilename) {
        UUID uuid = UUID.randomUUID();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return uuid.toString() + "." + extension;
    }

    // 파일 경로 반환
    private String getFilePath(String fileName) {
        String eduFileLocation = "C:\\seah\\edu"; // 파일 업로드 위치
        return Paths.get(eduFileLocation, fileName).toString();
    }
}