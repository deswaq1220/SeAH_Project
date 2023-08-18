package SeAH.savg.service;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.SpecialFile;
import SeAH.savg.repository.SpeicalFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    // 파일 등록
    public List<SpecialFile> uploadFile(SpeInsFormDTO speInsFormDTO) throws Exception {
        List<SpecialFile> uploadedFiles = new ArrayList<>();

        List<MultipartFile> files = speInsFormDTO.getFiles();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String makeSpeFileName = fileService.uploadFile(speFileLocation, originalFilename, file.getBytes());
            String fileUploadFullUrl = "/images/specialInspection/" + makeSpeFileName;

            System.out.println("파일경로: " + fileUploadFullUrl);

            // 파일 정보 생성 및 저장
            SpecialFile specialFile = new SpecialFile();

            specialFile.setSpeFileOriName(originalFilename);
            specialFile.setSpeFileUrl(fileUploadFullUrl);
            specialFile.setSpeFileName(makeSpeFileName);
            speicalFileRepository.save(specialFile); // 데이터베이스에 저장

            uploadedFiles.add(specialFile);
        }

        return uploadedFiles;
    }





//    // 파일 등록
//    public List<SpecialFile> uploadFile(SpeInsFormDTO speInsFormDTO) throws Exception {
//        List<SpecialFile> uploadedFiles = new ArrayList<>();
////        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        List<MultipartFile> files = speInsFormDTO.getFiles();
//        for (MultipartFile file : files) {
//            String originalFilename = file.getOriginalFilename();
//            String makeSpeFileName = generateUniqueFileName(originalFilename);
//            String fileUploadFullUrl = speFileLocation + File.separator + makeSpeFileName;
//
//            System.out.println("파일경로: " + fileUploadFullUrl);
//            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
//            fos.write(file.getBytes());
//            fos.close();
//
//            // 파일 정보 생성 및 저장
//            SpecialFile specialFile = new SpecialFile();
//
//            specialFile.setSpeFileOriName(originalFilename);
//            specialFile.setSpeFileUrl(fileUploadFullUrl);
//            specialFile.setSpeFileName(makeSpeFileName);
//            speicalFileRepository.save(specialFile); // 데이터베이스에 저장
//
//            uploadedFiles.add(specialFile);
//        }
//
//        return uploadedFiles;
//    }




    // 파일 경로 반환
    private String getFilePath(String fileName) {
        String speFileLocation = "C:\\seah\\specialInspection"; // 파일 업로드 위치
        return Paths.get(speFileLocation, fileName).toString();
    }
}
