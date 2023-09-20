package SeAH.savg.service;

import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.entity.RegularFile;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.RegularFileRepository;
import SeAH.savg.repository.RegularInspectionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
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

    @Autowired
    private RegularInspectionRepository regularInspectionRepository;

    @Value("${regularFileLocation}")
    private String regularFileLocation;

    // 정기점검 파일 등록
    public void regularUploadFile(RegularInspection regularInspection, RegularDTO regularDTO) throws Exception {

        List<RegularFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<MultipartFile> files ;
        String regPart = regularDTO.getRegularPart();       // 영역
        String regName = regularDTO.getRegularInsName();    // 점검항목

        for (String str : regularDTO.getFile().keySet()) {
            log.info("파일 이름 표시" + str);
            log.info(regularDTO.getFile().get(str).get(0).getOriginalFilename());
            files = regularDTO.getFile().get(str);


            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();       // 원래 파일명

                // 원본 이미지를 바이트 배열로 읽어들임
                byte[] originalImageData = file.getBytes();

                // 이미지 리사이징
                byte[] resizedImageData = resizeImageToByteArray(originalImageData);

                String dbSaveFileName = fileService.makeRegFileName(regularFileLocation, originalFilename, regPart, regName, resizedImageData);    // 새로만든 파일명
                String fileUploadFullUrl = "/images/regular/" + dbSaveFileName;     // url


                // 파일 정보 생성 및 저장
                RegularFile regularFile = new RegularFile();

                regularFile.setRegularFileName(dbSaveFileName);     // 파일이름
                regularFile.setRegularOriName(originalFilename);       // 원래 파일명
                regularFile.setRegularFileUrl(fileUploadFullUrl);          // url
                regularFile.setRegularInspection(regularInspection);
                regularFile.setRegularCheckId(str);
                regularFile.setIsComplete("처리 전");
                regularFileRepository.save(regularFile); // 데이터베이스에 저장

                uploadedFiles.add(regularFile);
            }
        }
    }

    // 파일사이즈변경
    public byte[] resizeImageToByteArray(byte[] originalImageData) throws IOException {
        // 원본 이미지를 바이트 배열로 읽어들임
        ByteArrayInputStream bis = new ByteArrayInputStream(originalImageData);
        BufferedImage originalImg = ImageIO.read(bis);

        // 새 사이즈
        int newWidth = 0;
        int newHeight = 0;
        if(originalImg.getHeight() < originalImg.getWidth()) {   // 가로이미지이면
            newWidth = 640;
            newHeight = 480;
        } else if(originalImg.getHeight() > originalImg.getWidth()){  // 세로이미지이면
            newWidth = 480;
            newHeight = 640;
        } else {  // 정방형이면
            newWidth = 480;
            newHeight = 480;
        }

        // 새 사이즈
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

        // 실제 리사이징(그려주는 부분)
        Graphics2D g = resizedImg.createGraphics();
        g.drawImage(originalImg, 0, 0, newWidth, newHeight, null);
        g.dispose();

        // 리사이즈된 이미지를 바이트 배열로 변환하여 반환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImg, "jpg", baos);
        return baos.toByteArray();
    }

    //파일삭제
    public void deleteFile(String fileName) {
        String filePath = regularFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }


    public void regularFileUpadte(RegularDetailDTO regularDetailDTO) throws Exception {

        RegularInspection regularInspection = regularInspectionRepository.findById(regularDetailDTO.getRegularInspectionId()).orElseThrow();

        List<RegularFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String regularInsId = regularDetailDTO.getRegularInspectionId();
        RegularInspection regData = regularInspectionRepository.findByRegularId(regularInsId);

        String regPart = regData.getRegularPart();       // 영역
        String regName = regData.getRegularInsName();    // 점검항목

        for (MultipartFile file : regularDetailDTO.getFiles()) {
            String originalFilename = file.getOriginalFilename();       // 원래 파일명

            // 원본 이미지를 바이트 배열로 읽어들임
            byte[] originalImageData = file.getBytes();

            // 이미지 리사이징
            byte[] resizedImageData = resizeImageToByteArray(originalImageData);

            String dbSaveFileName = fileService.makeRegFileName(regularFileLocation, originalFilename, regPart, regName, resizedImageData);    // 새로만든 파일명
            String fileUploadFullUrl = "/images/regular/" + dbSaveFileName;     // url


            // 파일 정보 생성 및 저장
            RegularFile regularFile = new RegularFile();

            regularFile.setRegularFileName(dbSaveFileName);     // 파일이름
            regularFile.setRegularOriName(originalFilename);       // 원래 파일명
            regularFile.setRegularFileUrl(fileUploadFullUrl);
            regularFile.setRegularInspection(regularInspection);
            regularFile.setRegularCheckId(regularDetailDTO.getId());
            regularFile.setIsComplete("처리 후");
            regularFileRepository.save(regularFile); // 데이터베이스에 저장

            uploadedFiles.add(regularFile);
        }
    }
}
