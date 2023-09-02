package SeAH.savg.service;


import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.entity.RegularFile;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.repository.EduRepository;
import SeAH.savg.repository.RegularFileRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private  EduRepository eduRepository;

    @Autowired
    private EduFileRepository eduFileRepository;
//    public EduFileService(EduFileRepository eduFileRepository) {
//        this.eduFileRepository = eduFileRepository;
//    }

    @Autowired
    private RegularFileRepository regularFileRepository;

    @Value("${eduFileLocation}")
    private  String eduFileLocation;


    //파일 등록
    public List<EduFile> uploadFile(EduDTO eduDTO) throws Exception {
        Edu edu = eduRepository.findByEduId(eduDTO.getEduId());
        List<EduFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<MultipartFile> files = eduDTO.getFiles();

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
            eduFile.setEdu(edu);
            eduFileRepository.save(eduFile); // 데이터베이스에 저장

            uploadedFiles.add(eduFile);
        }

        return uploadedFiles;
    }

    public void uploadFile2(RegularInspection regularInspection,RegularDetailDTO regularDetailDTO) throws Exception {

        List<RegularFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<MultipartFile> files = regularDetailDTO.getFiles();

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String fileUploadFullUrl = eduFileLocation + File.separator + todayDate + "_" + originalFilename;

            System.out.println("파일경로: " + fileUploadFullUrl);
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(file.getBytes());
            fos.close();

            // 파일 정보 생성 및 저장
            RegularFile regularFile = new RegularFile();
            regularFile.setRegularFileName(todayDate + "_" + originalFilename);
            regularFile.setRegularOriName(originalFilename);
            regularFile.setRegularFileUrl(fileUploadFullUrl);
            regularFile.setRegularInspection(regularInspection);
            regularFileRepository.save(regularFile); // 데이터베이스에 저장

            uploadedFiles.add(regularFile);
        }


    }



    //파일삭제
    public void deleteFile(String fileName) {
        String filePath = eduFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    public File fileUpload(String fileName) {
        String filePath = eduFileLocation + "/" + fileName;
        File uploadedFile = new File(filePath);
       return uploadedFile;
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