package SeAH.savg.service;

import SeAH.savg.entity.SpecialFile;
import SeAH.savg.repository.SpeicalFileRepository;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class SpecialFileService {
    private final SpeicalFileRepository speicalFileRepository;

    @Value("${speFileLocation}")
    private String speFileLocation;

    // 파일 등록
    public List<SpecialFile> uploadFile(List<MultipartFile> files) throws Exception {
        List<SpecialFile> uploadedFiles = new ArrayList<>();
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String fileUploadFullUrl = speFileLocation + File.separator + todayDate + "_" + originalFilename;

            System.out.println("파일경로: " + fileUploadFullUrl);
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(file.getBytes());
            fos.close();

            // 파일 정보 생성 및 저장
            SpecialFile specialFile = new SpecialFile();
//            specialFile.setSpeFileName(generateUniqueFileName());
            specialFile.setSpeFileOriName(originalFilename);
            specialFile.setSpeFileUrl(fileUploadFullUrl);
            specialFile.setSpeFileName(generateUniqueFileName(originalFilename));
            speicalFileRepository.save(specialFile); // 데이터베이스에 저장

            uploadedFiles.add(specialFile);
        }

        return uploadedFiles;
    }

    // ---------------------------------- 파일명설정 : 오늘날짜_원본파일명 조합 : 수정중
    private int fileSeq = 0; // 파일 업로드 시마다 증가할 인덱스 변수
    private int getNextFileSeqFromDatabase() {

        return fileSeq + 1;
    }

    private void updateFileSeqInDatabase(int newFileSeq) {

        fileSeq = newFileSeq;
    }

    private String generateUniqueFileName(String originalFileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd.HHmmss.SSS");
        String returnFileName = "";
        String ext = "";
        int index = originalFileName.lastIndexOf(".");

        String date = format.format(new Date());
//        int seq = getNextFileSeqFromDatabase(); // 데이터베이스에서 다음 파일 시퀀스 값을 가져옴
//        String uniKey = date + "_" + seq;
//
//        // 파일명 생성 후 데이터베이스의 fileSeq 업데이트
//        updateFileSeqInDatabase(seq);

        if (index > -1) {
            ext = originalFileName.substring(index + 1);
            returnFileName = originalFileName.substring(0, index) + "_" + "." + ext;
        } else {
            returnFileName = originalFileName.substring(0) + "_" ;
        }
        System.out.println("[returnFileName]" + returnFileName);

        return returnFileName;
    }

    // ---------------- 수정중

    // 파일 경로 반환
    private String getFilePath(String fileName) {
        String speFileLocation = "C:\\seah\\specialInspection"; // 파일 업로드 위치
        return Paths.get(speFileLocation, fileName).toString();
    }
}
