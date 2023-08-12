package SeAH.savg.service;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.entity.*;
import SeAH.savg.repository.*;
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
    private final SpecialInjuredRepository specialInjuredRepository;
    private final SpecialDangerRepository specialDangerRepository;
    private final SpecialTrapRepository specialTrapRepository;


    // 수시점검 등록화면 조회
    @Transactional
    public Map<String, Object> findCreateMenu(String masterdataPart){
        Map<String, Object> responseData = new HashMap<>();
        // 위험분류
        List<SpecialDanger> specialDangerList = specialDangerRepository.findAllOrderByDangerNum();
        responseData.put("specialDangerList", specialDangerList);

        // 부상부위
        List<SpecialInjured> specialInjuredList = specialInjuredRepository.findAllOrderByInjuredNum();
        responseData.put("specialInjuredList", specialInjuredList);

        // 위험원인
        List<SpecialCause> specialCauseList = specialCauseRepository.findAllOrderByCauseNum();
        responseData.put("specialCauseList", specialCauseList);

        // 실수함정
        List<SpecialTrap> specialTrapList = specialTrapRepository.findAllOrderByTrapNum();
        responseData.put("specialTrapList", specialTrapList);

        // 고정수신자, 파트관리자 이메일리스트
        List<Email> emailList = emailRepository.findByEmailPartOrMasterStatus(masterdataPart, Y);
        responseData.put("emailList", emailList);

        return responseData;
    }




    // 교육, 수시, 정기 카테고리 저장할 함수
    private String categoryType = "S";

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(String masterdataPart, String masterdataFacility, SpeInsFormDTO speInsFormDTO) throws Exception {
        // speIsFormDTO 나머지 데이터 세팅
        speInsFormDTO.setSpeId(makeIdService.makeId(categoryType));             // id
        speInsFormDTO.setSpeDate(LocalDateTime.now());                          // 점검일
        speInsFormDTO.setSpePart(masterdataPart);                               // 영역
        speInsFormDTO.setSpeFacility(masterdataFacility);                       // 설비
        SpeStatus.deadLineCal(speInsFormDTO);                                               // 위험도에 따른 완료요청기한

        speInsFormDTO.createSpeIns();

        // 수시점검 저장
        SpecialInspection special = speInsFormDTO.createSpeIns();
        specialInspectionRepository.save(special);

        // 파일 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            // 파일 업로드 및 파일 정보 저장
            String completeKey = "미완료";
            List<SpecialFile> uploadedFiles = specialFileService.uploadFile(speInsFormDTO, completeKey);
            for(SpecialFile specialFile : uploadedFiles)
                specialFile.setSpecialInspection(special);
        }
        return special;
    }


    // 월별현황 : 점검실시 ()건, 조치완료 ()건, 조치필요 ()건
    @Transactional
    public Map<String, Object> findSpeMonthly(){
        Map<String, Object> responseData = new HashMap<>();
        LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);

        // 이번달 전체점검 실시 건수(점검실시)
        int countMonthlyAll = specialInspectionRepository.countAllBySpeDateAndSpeIdIsNotNullSpeDateAfter(startOfToday);
        // 이번달 조치 완료건수(조치완료)
        int countMonthlyComplete = specialInspectionRepository.countBySpeActDateAndSpeComplete(OK, startOfToday);
        // 이번달 deadline중 미완료건수(조치필요)
        int countMonthlyNoComplete = specialInspectionRepository.countBySpeDeadlineAndSpeComplete(NO);

        responseData.put("monthlyAll", countMonthlyAll);                 // 이번달 전체등록건수
        responseData.put("monthlyComplete", countMonthlyComplete);       // 이번달 완료건수
        responseData.put("monthlyNoComplete", countMonthlyNoComplete);               // 이번달 deadline중 미완료건수

        return responseData ;
    }



    // 수시점검 설비별 조회
    @Transactional(readOnly = true)
    public Map<String, Object> findListOfFac(String masterdataFacility){
        Map<String, Object> responseData = new HashMap<>();
        String findId;          // 해당 설비를 가진 id를 저장할 함수

        // 설비에 해당하는 SpecialInspection 찾기
        List<SpecialInspection> listOfFac = specialInspectionRepository.findAllBySpeFacilityOrderBySpeDateDesc(masterdataFacility);
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
            List<SpecialFile> uploadFiles = specialFileService.uploadFile(speInsFormDTO, completeKey);

            for(SpecialFile specialFile : uploadFiles)
                specialFile.setSpecialInspection(special);
        }


        special.setSpeActDate(LocalDateTime.now());         // 완료시간 세팅
        special.updateSpe(speInsFormDTO.getSpeComplete());  // 완료세팅

        return special;
    }




// ----------------------------------------------------------------------------------------------------------


    //월간 수시점검 현황 통계 조회 - 위험분류별(그래프용-지금 안씀)
    public List<Map<String, Object>> setSpecialListByDangerAndMonth(int year, int month){
        List<Object[]> statisticsList = specialInspectionRepository.specialListByDangerAndMonth(year, month);

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

    //1~12월까지 월별 수시점검 위험분류 건수

   public List<Map<String,Object>> specialDetailListByDanger(int year){
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



    // 1~12월까지 월별 수시점검 건수
     public List<Map<String, Object>> setSpecialCountList(int year){
        List<Object[]> statisticsList = specialInspectionRepository.specialCountList(year);

        List<Map<String, Object>> dataPoints = new ArrayList<>();

        for(Object[] row : statisticsList){             // List+Map 형태: dataPoints = x: 협착, y: 1 .....

            Integer month = (Integer) row[0];
            Long count = (Long) row[1];

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("x", month);
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


}
