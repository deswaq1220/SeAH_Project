package SeAH.savg.service;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.SpecialCause;
import SeAH.savg.entity.SpecialFile;
import SeAH.savg.entity.SpecialInspection;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.SpecialCauseRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import SeAH.savg.repository.SpeicalFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static SeAH.savg.constant.MasterStatus.Y;
import static SeAH.savg.constant.SpeStatus.NO;
import static SeAH.savg.constant.SpeStatus.OK;

@Service
@RequiredArgsConstructor
public class SpecialInspectionService {
    private final SpecialInspectionRepository specialInspectionRepository;
    private final EmailRepository emailRepository;
    private final SpecialFileService specialFileService;
    private final MakeIdService makeIdService;
    private final SpeicalFileRepository specialFileRepository;
    private final SpecialCauseRepository specialCauseRepository;


    // 수시점검 등록화면 조회
    @Transactional
    public Map<String, Object> findCreateMenu(String masterdataPart){
        Map<String, Object> responseData = new HashMap<>();

        // 고정수신자, 파트관리자 이메일리스트
        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(masterdataPart, Y);
        responseData.put("emailList", emailList);

        // 위험원인
        List<SpecialCause> specialCauseList = specialCauseRepository.findAll();
        responseData.put("specialCauseList", specialCauseList);

        return responseData;
    }





    // 교육, 수시, 정기 카테고리 저장할 함수
    private String categoryType = "S";

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(String masterdataPart, String masterdataFacility, SpeInsFormDTO speInsFormDTO) throws Exception {
        speInsFormDTO.setSpeId(makeIdService.makeId(categoryType));     // id 세팅
        speInsFormDTO.setSpePart(masterdataPart);               // 영역 세팅
        speInsFormDTO.setSpeFacility(masterdataFacility);       // 설비 세팅
        speInsFormDTO.setSpeDate(LocalDateTime.now());          // 점검일 세팅
        SpeStatus.deadLineCal(speInsFormDTO);                   // 위험도에 따른 완료요청기한 세팅
        // 수시점검 저장
        SpecialInspection special = speInsFormDTO.createSpeIns();
        specialInspectionRepository.save(special);

        // 파일 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            // 파일 업로드 및 파일 정보 저장
            String completeKey = "미완료";
            List<SpecialFile> uploadedFiles = specialFileService.uploadFile(speInsFormDTO.getFiles(), completeKey);
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
        responseData.put("specialFileData", specialFileList);
        return responseData;
    }


    // 수시점검 설비별 조회
    @Transactional(readOnly = true)
    public Map<String, Object> findListOfFac(String masterdataFacility){
        Map<String, Object> responseData = new HashMap<>();
        String findId;          // 해당 설비를 가진 id를 저장할 함수

        // 설비에 해당하는 SpecialInspection 찾기
        List<SpecialInspection> listOfFac = specialInspectionRepository.findAllBySpeFacility(masterdataFacility);
        responseData.put("listOfFac", listOfFac);

        // 찾은 SpecialInspection의 id를 이용해 파일찾기
        List<SpecialFile> speFileOfFac = new ArrayList<>();
        for(SpecialInspection listOfFacId : listOfFac){
            findId = listOfFacId.getSpeId();        // id세팅
            List<SpecialFile> files= specialFileRepository.findBySpecialInspection_SpeId(findId);
            speFileOfFac.addAll(files);
        }
        responseData.put("speFileOfFac", speFileOfFac);
        return responseData;
    }

    // 수시점검 상세조회
    @Transactional
    public Map<String, Object> findSpeDetail(String speId){
        Map<String, Object> responseData = new HashMap<>();
        SpecialInspection speDetailFindId = specialInspectionRepository.findAllBySpeId(speId);
        List<SpecialFile> speFileDetailFindIds = specialFileRepository.findBySpecialInspection_SpeId(speId);

        responseData.put("speDetailFindId", speDetailFindId);
        responseData.put("speFileDetailFindIds", speFileDetailFindIds);

        return responseData;
    }

    // 완료처리:업데이트
    @Transactional
    public SpecialInspection speUpdate(String speId, SpeInsFormDTO speInsFormDTO) throws Exception {
        SpecialInspection special = specialInspectionRepository.findAllBySpeId(speId);
        // 파일이 있으면 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            String completeKey = "완료";
            List<SpecialFile> uploadFiles = specialFileService.uploadFile(speInsFormDTO.getFiles(), completeKey);

            for(SpecialFile specialFile : uploadFiles)
                specialFile.setSpecialInspection(special);
        }

        // 완료세팅
//        if(!(speInsFormDTO.getSpeComplete() == null)) {
//            special.updateSpe(speInsFormDTO.getSpeComplete());
//        }

        special.setSpeActDate(LocalDateTime.now());         // 완료시간 세팅
        special.updateSpe(speInsFormDTO.getSpeComplete());  // 완료세팅




        return special;
    }

    // 월별현황 : 점검실시 ()건, 조치완료 ()건, 조치필요 ()건
    @Transactional
    public Map<String, Object> findSpeMonthly(){
        Map<String, Object> reponseData = new HashMap<>();
        LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);


        // 미완료건수
//        int countNotComplete = specialInspectionRepository.countBySpeDateAndSpeCompleteAndSpeIdIsNotNullAndSpeDateAfter(startOfToday, NO, startOfToday);
//        reponseData.put("dailyNotComplete", countNotComplete);       // 오늘미완료건수

        // 전체점검건수
        int countMonthlyAll = specialInspectionRepository.countAllBySpeDateAndSpeIdIsNotNullSpeDateAfter(startOfToday);
        // 완료건수
        int countMonthlyComplete = specialInspectionRepository.countBySpeActDateAndSpeComplete(OK, startOfToday);
        // 이번달 deadline중 미완료건수
        int countMonthlyNoComplete = specialInspectionRepository.countBySpeDeadlineAndSpeComplete(NO);


        reponseData.put("monthlyAll", countMonthlyAll);                 // 이번달 전체등록건수
        reponseData.put("monthlyComplete", countMonthlyComplete);       // 이번달 완료건수
        reponseData.put("monthlyNoComplete", countMonthlyNoComplete);               // 이번달 deadline중 미완료건수

        return reponseData ;
    }
}
