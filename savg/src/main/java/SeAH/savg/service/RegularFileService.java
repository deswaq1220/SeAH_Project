package SeAH.savg.service;

import SeAH.savg.dto.RegularDTO;
import SeAH.savg.entity.RegularFile;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.RegularFileRepository;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class RegularFileService {

    @Autowired
    private FileService fileService;

    @Autowired
    private RegularFileRepository regularFileRepository;

    @Value("${regularFileLocation}")
    private  String regularFileLocation;

    public void regularUploadFile(RegularInspection regularInspection, RegularDTO regularDTO) throws Exception {

        List<RegularFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<MultipartFile> files;

        for(String str : regularDTO.getFile().keySet()){
            log.info("파일 이름 표시" + str);
            log.info(regularDTO.getFile().get(str).get(0).getOriginalFilename());
            files  = regularDTO.getFile().get(str);

            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String fileUploadFullUrl = regularFileLocation + File.separator + todayDate + "_" + originalFilename;
                String dbSaveFileName = "/images/regular/"  + todayDate + "_" + originalFilename;

                System.out.println("파일경로: " + fileUploadFullUrl);
                FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
                fos.write(file.getBytes());
                fos.close();

                // 파일 정보 생성 및 저장
                RegularFile regularFile = new RegularFile();
                regularFile.setRegularFileName(todayDate + "_" + originalFilename);
                regularFile.setRegularOriName(originalFilename);
                regularFile.setRegularFileUrl(dbSaveFileName);
                regularFile.setRegularInspection(regularInspection);
                regularFile.setRegularCheckId(str);
                regularFileRepository.save(regularFile); // 데이터베이스에 저장

                uploadedFiles.add(regularFile);
            }
        }
    }


    //파일삭제
    public void deleteFile(String fileName) {
        String filePath = regularFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }


}
