package SeAH.savg.service;

import SeAH.savg.dto.SpeInsFormDTO;
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
    public List<SpecialFile> uploadFile(SpeInsFormDTO speInsFormDTO, String completeKey) throws Exception {
        List<SpecialFile> uploadedFiles = new ArrayList<>();
//        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        List<MultipartFile> files = speInsFormDTO.getFiles();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String makeSpeFileName = generateUniqueFileName(originalFilename, completeKey);
            String fileUploadFullUrl = speFileLocation + File.separator + makeSpeFileName;

            System.out.println("파일경로: " + fileUploadFullUrl);
            FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
            fos.write(file.getBytes());
            fos.close();

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

    //파일명설정 : 오늘날짜_seqenceNumber_미완료/완료_원본파일명 조합
    private List<SpecialFile> previousDatelist = null; // 이전날짜 저장하는 변수
    private int sequenceNumber = 0;
    private String generateUniqueFileName(String originalFilename, String completeKey) {
        String todayDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        previousDatelist = speicalFileRepository.findFilesByToday(todayDate);

        // DB에 저장된 오늘 날짜 구하기
        if(previousDatelist.isEmpty() || previousDatelist.size() == 0){
            // 오늘날짜 저장된게 없으면(다음날이면) : sequenceNumber 초기화
            sequenceNumber = 0;
        } else{
            // 오늘날짜중 가장 높은 seq찾아서 sequenceNumber++ 세팅하기
            sequenceNumber = speicalFileRepository.getMaxSeqNumberByToday(todayDate);
            sequenceNumber++;
        }

        String newName = todayDate + "_" +  sequenceNumber + "_" + completeKey  +"_" + originalFilename;

        return newName;
    }


    // 파일 경로 반환
    private String getFilePath(String fileName) {
        String speFileLocation = "C:\\seah\\specialInspection"; // 파일 업로드 위치
        return Paths.get(speFileLocation, fileName).toString();
    }
}
