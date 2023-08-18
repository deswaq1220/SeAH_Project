package SeAH.savg.service;

import SeAH.savg.entity.SpecialFile;
import SeAH.savg.repository.SpeicalFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
 private final SpeicalFileRepository speicalFileRepository;

 // 파일 이름 세팅
  public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception{
   // 저장될 파일이름
   String savedFileName = generateUniqueFileName(originalFileName);
   String fileUploadFullUrl = uploadPath + "/" + savedFileName;
   // 파일이 저장될 위치, 파일의 이름 받아 파일에 쓸 파일 출력 스트림 생성
   FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
   fos.write(fileData);
   fos.close();
   return  savedFileName;

  }



 //파일명설정 : 오늘날짜_seqenceNumber_원본파일명 조합
 private List<SpecialFile> previousDatelist = null; // 이전날짜 저장하는 변수
 private int sequenceNumber = 0;
 private String generateUniqueFileName(String originalFilename) {
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

  String newName = todayDate + "_" +  sequenceNumber  +"_" + originalFilename;

  return newName;
 }

}
