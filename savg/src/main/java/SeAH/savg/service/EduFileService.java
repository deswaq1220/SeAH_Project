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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public List<EduFile> uploadFile(List<MultipartFile> files) throws Exception {
        List<EduFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String fileUploadFullUrl = eduFileLocation + File.separator + todayDate + "_" + originalFilename;

            System.out.println("파일경로: " + fileUploadFullUrl);
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(file.getBytes());
            fos.close();

            // 파일 정보 생성 및 저장
            EduFile eduFile = new EduFile();
            eduFile.setEduFileName(todayDate + "_" + originalFilename);
            eduFile.setEduFileOriName(originalFilename);
            eduFile.setEduFileUrl(fileUploadFullUrl);
            eduFileRepository.save(eduFile); // 데이터베이스에 저장

            uploadedFiles.add(eduFile);
        }

        return uploadedFiles;
    }


    //파일업뎃
    public void updateFile(String fileName, List<MultipartFile> files) throws Exception {
        deleteFile(fileName);
        uploadFile(files);
    }

    //파일삭제
    public void deleteFile(String fileName) {
        String filePath = eduFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    //파일명설정 : 오늘날짜_원본파일명 조합
    private String generateUniqueFileName(String originalFilename) {
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return todayDate + "_" + originalFilename;
    }


    // 파일 경로 반환
    private String getFilePath(String fileName) {
        String eduFileLocation = "C:\\seah\\edu"; // 파일 업로드 위치
        return Paths.get(eduFileLocation, fileName).toString();
    }
}