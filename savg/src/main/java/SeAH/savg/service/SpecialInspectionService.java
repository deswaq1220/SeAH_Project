package SeAH.savg.service;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.SpecialFile;
import SeAH.savg.entity.SpecialInspection;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import SeAH.savg.repository.SpeicalFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static SeAH.savg.constant.MasterStatus.Y;

@Service
@RequiredArgsConstructor
public class SpecialInspectionService {
    private final SpecialInspectionRepository specialInspectionRepository;
    private final EmailRepository emailRepository;
    private final SpecialFileService specialFileService;
    private final MakeIdService makeIdService;
    private final SpeicalFileRepository specialFileRepository;


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
        speIns.setSpePart("주조");          // 영역 세팅
        speIns.setSpeFacility("주조1");    // 설비 세팅

        // 고정수신자, 파트관리자 이메일리스트
        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(speIns.getSpePart(), Y);
        return emailList;
    }

    // 교육, 수시, 정기 카테고리 저장할 함수
    private String categoryType = "S";

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(SpeInsFormDTO speInsFormDTO) throws Exception {
        speInsFormDTO.setSpeId(makeIdService.makeId(categoryType));

        // 영역이랑 설비도 선택해서 안넘어오면 파라미터로 받아서 세팅하던지, 아니면 requestData로 하던지
        // 세팅해줘야함

        // 점검일 세팅
        speInsFormDTO.setSpeDate(LocalDateTime.now());
        // 위험도에 따른 완료요청기한 세팅
        SpeStatus.deadLineCal(speInsFormDTO);
        // 수시점검 저장
        SpecialInspection special = speInsFormDTO.createSpeIns();
        specialInspectionRepository.save(special);

        // 파일 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            // 파일 업로드 및 파일 정보 저장
            List<SpecialFile> uploadedFiles = specialFileService.uploadFile(speInsFormDTO.getFiles());
            for(SpecialFile specialFile : uploadedFiles)
                specialFile.setSpecialInspection(special);
        }
        return special;
    }

    // 수시점검 전체 조회
    @Transactional(readOnly = true)
    public Map<String, Object> findSpeAll(){
        Map<String, Object> responseData = new HashMap<>();

        List<SpecialInspection> specialInspectionList = specialInspectionRepository.findAll();
        List<SpecialFile> specialFileList = specialFileRepository.findAll();

        responseData.put("specialData", specialInspectionList);
        responseData.put("specialFildData", specialFileList);
        return responseData;
    }

    // 수시점검 설비별 조회
    @Transactional(readOnly = true)
    public List<SpecialInspection> findListOfFac(String masterdataFacility){
        System.out.println("수시점검 서비스 in 111111111111111111111111");
        System.out.println(masterdataFacility);

        List<SpecialInspection> listOfFac = specialInspectionRepository.findAllBySpeFacility(masterdataFacility);

        return listOfFac;
    }
}
