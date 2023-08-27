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
    private final MakeIdService makeIdService;


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
        speIns.setSpePart("압출");          // 영역 세팅
        speIns.setSpeFacility("압출#1");    // 설비 세팅
        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(speIns.getSpePart(), Y);
        return emailList;
    }

    // 교육, 수시, 정기 카테고리 저장할 함수
    private String categoryType = "S";

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(SpeInsFormDTO speInsFormDTO) throws Exception {
        // pk(speId) 설정
        speInsFormDTO.setSpeId(makeIdService.makeId(categoryType));
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

//    private String makingId = "";   // id 저장할 변수
//    private String previousYearAndMonth = "";      // 이전 todayYearAndMonth 저장할 변수
//    private int seqNumber = 0;
//    public String makeId(){
//        String todayYearAndMonth = new SimpleDateFormat("yyMM").format(new Date());
//        // 이전 todayYearAndMonth 현재 비교해서 바뀌었을 경우 sequenceNumber를 0으로 초기화
//        if(!(todayYearAndMonth.equals(previousYearAndMonth))) { seqNumber = 0; }
//
//        // seqNumber가 10 이하이면 00, 01 .... : 앞에 0 붙여주기
//        if(seqNumber<10) { makingId = "S" + todayYearAndMonth + "-0" + seqNumber;  }
//        else { makingId = "S" + todayYearAndMonth + "-" + seqNumber; }
//
//        System.out.println("여기 todayYearAndMonth : " + todayYearAndMonth);
//        seqNumber++;
//        previousYearAndMonth = todayYearAndMonth;
//        return makingId;
//    }
}
