package SeAH.savg.service;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.SpecialFile;
import SeAH.savg.repository.SpeicalFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecialFileService {
    private final SpeicalFileRepository speicalFileRepository;
    private final FileService fileService;

    @Value("${speFileLocation}")
    private String speFileLocation;

    //  수시점검 파일 등록
    public List<SpecialFile> uploadFile(SpeInsFormDTO speInsFormDTO, String facilityName, SpeStatus isComplete) throws Exception {
        List<SpecialFile> uploadedFiles = new ArrayList<>();

        List<MultipartFile> files = speInsFormDTO.getFiles();
        for (MultipartFile file : files) {
            byte[] resizedImageData = fileService.resizeImageToByteArray(file);

            String originalFilename = file.getOriginalFilename();  // 원래파일명
            String makeSpeFileName = fileService.makeFileName(speFileLocation, originalFilename, facilityName, resizedImageData); // 파일명
            String fileUploadFullUrl = "/images/specialInspection/" + makeSpeFileName;  // 업로드 url

            // 파일 정보 생성 및 저장
            SpecialFile specialFile = new SpecialFile();

            specialFile.setSpeFileOriName(originalFilename);
            specialFile.setSpeFileUrl(fileUploadFullUrl);
            specialFile.setSpeFileName(makeSpeFileName);
            specialFile.setIsComplete(isComplete);
            speicalFileRepository.save(specialFile); // 데이터베이스에 저장

            uploadedFiles.add(specialFile);
        }

        return uploadedFiles;
    }



    //파일삭제
    public void deleteFile(String fileName) {
        String filePath = speFileLocation + "/" + fileName;
        File deleteFile = new File(filePath);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }


    // 파일 경로 반환
    private String getFilePath(String fileName) {
        String speFileLocation = "C:\\seah\\specialInspection"; // 파일 업로드 위치
        return Paths.get(speFileLocation, fileName).toString();
    }
}
