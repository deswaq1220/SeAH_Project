package SeAH.savg.service;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.SpecialInspection;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialInspectionService {
    private final SpecialInspectionRepository specialInspectionRepository;
    private final EmailRepository emailRepository;

    // 수시점검 등록화면 조회 : 프론트연결용
//    @Transactional
//    public List<Email> findEmail(Map<String, Object> requestData){
//        SpecialInspection speIns = new SpecialInspection();
//        speIns.setSpePart((String) requestData.get("masterdataPart"));          // 영역 세팅
//        speIns.setSpeFacility((String) requestData.get("masterdataFacility"));  // 설비 세팅
//
//
//
//        List<Email> emailList = emailRepository.findByEmailPart(speIns.getSpePart());
//        return emailList;
//    }

    // 수시점검 등록화면 조회 : 테스트용
    @Transactional
    public List<Email> findEmail(){
        SpecialInspection speIns = new SpecialInspection();
        speIns.setSpePart("압출");          // 영역 세팅
        speIns.setSpeFacility("압출1");  // 설비 세팅

        List<Email> emailList = emailRepository.findByEmailPart(speIns.getSpePart());
        return emailList;
    }

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(SpeInsFormDTO speInsFormDTO){
        SpecialInspection special = speInsFormDTO.createSpeIns();
        specialInspectionRepository.save(special);
        return special;
    }
}
