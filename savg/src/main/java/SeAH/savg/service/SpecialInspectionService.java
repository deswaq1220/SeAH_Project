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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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



    // 수시점검 등록화면 조회 : 프론트연결용
//    @Transactional
//    public List<Email> findEmail(Map<String, Object> requestData){
//        SpecialInspection speIns = new SpecialInspection();
//        speIns.setSpePart((String) requestData.get("masterdataPart"));          // 영역 세팅
//        speIns.setSpeFacility((String) requestData.get("masterdataFacility"));  // 설비 세팅
//
//
//        // 고정수신자, 파트관리자 이메일리스트
//        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(speIns.getSpePart(), Y);
//        return emailList;
//    }

    // 수시점검 등록화면 조회 : 테스트용
    @Transactional
    public List<Email> findEmail(String masterdataPart){
        // 고정수신자, 파트관리자 이메일리스트
        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(masterdataPart, Y);
        return emailList;
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

//    // 일별현황 : 점검 완료()건, 미완료 ()건
//    @Transactional
//    public int findSpeDaily(){
//        LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
////        int countNotComplete = specialInspectionRepository.countBySpeDateAndSpeCompleteAndSpeIdIsNotNullAndSpeDateAfter(NO, startOfToday);
//        int countNotComplete = specialInspectionRepository.countBySpeId();
//
//
//
//        return countNotComplete ;
//    }


    //월별 수시점검 현황 통계 조회 - 위험분류별
    public List<Map<String, Object>> setSpecialListByDangerAndMonth(int year, int month){
        List<Object[]> statisticsList = specialInspectionRepository.specialListByDangerAndMonthPlus0(year, month);

        List<Map<String, Object>> dataPoints = new ArrayList<>();

        for(Object[] row : statisticsList){             // List+Map 형태: dataPoints = x: 협착, y: 1 .....

            String dangerType = (String) row[0];
            Long count = (Long) row[1];

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", dangerType);
            dataPoint.put("y", count);

            dataPoints.add(dataPoint);
        }

        Map<String, Object> finalDate = new HashMap<>();   //Map형태:
        finalDate.put("id", "수시점검");
        finalDate.put("data", dataPoints);

        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(finalDate);


        return resultList;
    }

    //차트용
 /*   public List<Map<String, Object>> setSpecialListByDangerAndMonth(int month){
        List<Object[]> statisticsList = specialInspectionRepository.specialListByDangerAndMonth(month);

        List<Map<String, Object>> dataPoints = new ArrayList<>();

        for(Object[] row : statisticsList){             // List+Map 형태: dataPoints = x: 협착, y: 1 .....

            String dangerType = (String) row[0];
            Long count = (Long) row[1];

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", dangerType);
            dataPoint.put("y", count);

            dataPoints.add(dataPoint);
        }

        Map<String, Object> finalData = new HashMap<>();   //Map형태:
        finalData.put("id", "수시점검");
        finalData.put("data", dataPoints);

        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(finalData);


        return resultList;
    }*/

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

    //특정년도의 전체 월별 수시점검 위험분류 건수
   public List<Map<String,Object>> SpecialDetailListByDanger(int year){
        List<Object[]> specialList = specialInspectionRepository.specialDetailListByDanger(year);

        Map<Integer, Map<String, Object>> dataByMonth = new HashMap<>();


        for(Object[] row : specialList){

            Integer month = (Integer) row[0];
            String dangerKind = (String) row[1];
            Long count = (Long) row[2];

            if(!dataByMonth.containsKey(month)){
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("month", month);
                dataByMonth.put(month, dataPoint);
            }
            Map<String, Object> dataPoint = dataByMonth.get(month);
            dataPoint.put(dangerKind, count);
        }
       List<Map<String, Object>> finalData = new ArrayList<>(dataByMonth.values());

        return finalData;
    }


}
