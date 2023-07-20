package SeAH.savg.service;

import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;



@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class EduService {

    private final EduRepository eduRepository;
    private final EduFileService eduFileService;

//    public EduService(EduRepository eduRepository, EduFileService eduFileService) {
//        this.eduRepository = eduRepository;
//        this.eduFileService = eduFileService;
//    }

//, @RequestParam("file") MultipartFile file
    //교육일지 등록
   /* public ResponseEntity<?> handleEduReg(@ModelAttribute EduDTO eduDTO) {
        try {
            // 데이터 처리 로직: 유효성 검사
            if (eduDTO.getEduContent() == null || eduDTO.getEduContent().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Content is required.");
            }
            if (eduDTO.getEduInstructor() == null || eduDTO.getEduInstructor().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Instructor is required.");
            }



            // 데이터 처리 로직: 데이터 저장
            Edu edu = eduDTO.toEntity();
            eduRepository.save(edu); // Edu 엔티티 저장
            if(eduDTO.getFile() == null){
                log.info("파일 없음");
            }else {
                // 데이터 처리 로직: 파일 업로드 및 파일 정보 저장
                EduFile eduFile = eduFileService.uploadFile(eduDTO.getFile());
                eduFile.setEdu(edu); // EduFile 엔티티와 연결
            }
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }*/
}
