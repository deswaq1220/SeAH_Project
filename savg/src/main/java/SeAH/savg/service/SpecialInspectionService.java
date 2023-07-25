package SeAH.savg.service;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.SpecialFile;
import SeAH.savg.entity.SpecialInspection;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static SeAH.savg.constant.MasterStatus.Y;

@Service
@RequiredArgsConstructor
public class SpecialInspectionService {
    private final SpecialInspectionRepository specialInspectionRepository;
    private final EmailRepository emailRepository;
    private final SpecialFileService specialFileService;


    // 수시점검 등록화면 조회 : 프론트연결용
//    @Transactional
//    public List<Email> findEmail(Map<String, Object> requestData){
//        SpecialInspection speIns = new SpecialInspection();
//        speIns.setSpePart((String) requestData.get("masterdataPart"));          // 영역 세팅
//        speIns.setSpeFacility((String) requestData.get("masterdataFacility"));  // 설비 세팅
//
//
//
//        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(speIns.getSpePart(), Y);
//        return emailList;
//    }

    // 수시점검 등록화면 조회 : 테스트용
    @Transactional
    public List<Email> findEmail(){
        SpecialInspection speIns = new SpecialInspection();
        speIns.setSpePart("금형");          // 영역 세팅
        speIns.setSpeFacility("금");    // 설비 세팅
        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(speIns.getSpePart(), Y);
        return emailList;
    }

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(SpeInsFormDTO speInsFormDTO) throws Exception {
        // 점검일 세팅
        speInsFormDTO.setSpeDate(LocalDateTime.now());

        // 위험도에 따른 완료요청기한 세팅
        SpeStatus.deadLineCal(speInsFormDTO);

        // 수시점검 저장
        SpecialInspection special = speInsFormDTO.createSpeIns();
        specialInspectionRepository.save(special);


        System.out.println("222222222222: " + speInsFormDTO.getFiles());

        // 파일 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            System.out.println("여기111111111111111111");
            // 파일 업로드 및 파일 정보 저장
            List<SpecialFile> uploadedFiles = specialFileService.uploadFile(speInsFormDTO.getFiles());
            for(SpecialFile specialFile : uploadedFiles)
                specialFile.setSpecialInspection(special);
        }


        return special;
    }
}
