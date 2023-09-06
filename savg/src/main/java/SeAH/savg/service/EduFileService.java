package SeAH.savg.service;


import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.RegularDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.entity.RegularFile;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.repository.EduRepository;
import SeAH.savg.repository.RegularFileRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class EduFileService {

    @Autowired
    private  EduRepository eduRepository;

    @Autowired
    private EduFileRepository eduFileRepository;

    @Autowired
    private final FileService fileService;

    @Autowired
    private RegularFileRepository regularFileRepository;

    @Value("${eduFileLocation}")
    private  String eduFileLocation;

    public EduFileService(FileService fileService) {
        this.fileService = fileService;
    }


    //파일 등록
    public List<EduFile> uploadFile(EduDTO eduDTO, String eduCatory) throws Exception {
        Edu edu = eduRepository.findByEduId(eduDTO.getEduId());
        List<EduFile> uploadedFiles = new ArrayList<>();

        String todayDate = new SimpleDateFormat("yyMMdd").format(new Date());

        List<MultipartFile> files = eduDTO.getFiles();
        for (MultipartFile file : files) {
            byte[] resizedImageData = fileService.resizeImageToByteArray(file);
            String originalFilename = file.getOriginalFilename();
            String makeEduFileName = fileService.makeEduFileName(eduFileLocation, originalFilename, eduCatory, resizedImageData);
            String fileUploadFullUrl = "/images/edu/" + makeEduFileName;  // 업로드 url

            System.out.println("파일경로: " + fileUploadFullUrl);


            // 파일 정보 생성 및 저장
            EduFile eduFile = new EduFile();
            eduFile.setEduFileName(makeEduFileName);
            eduFile.setEduFileOriName(originalFilename);
            eduFile.setEduFileUrl(fileUploadFullUrl);
            eduFile.setEdu(edu);
            eduFileRepository.save(eduFile); // 데이터베이스에 저장

            uploadedFiles.add(eduFile);
        }

        return uploadedFiles;
    }

    //여기 교육인데 왜 정기점검이 있나요 정답을 알려조
    public void uploadFile2(RegularInspection regularInspection, RegularDTO regularDTO) throws Exception {

        List<RegularFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<MultipartFile> files;
        for(String str : regularDTO.getFile().keySet()){
            log.info("파일 이름 표시" + str);
            log.info(regularDTO.getFile().get(str).get(0).getOriginalFilename());
            files  = regularDTO.getFile().get(str);

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
                regularFile.setRegularCheckId(str);
                regularFileRepository.save(regularFile); // 데이터베이스에 저장

                uploadedFiles.add(regularFile);
            }

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