package SeAH.savg.service;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.dto.SpecialFileFormDTO;
import SeAH.savg.entity.*;
import SeAH.savg.repository.*;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
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
    private final SpecialPartRepository specialPartRepository;
    private final MasterDataRepository masterDataRepository;

    private ModelMapper modelMapper = new ModelMapper();

    // 수시점검 등록화면 조회
    @Transactional
    public Map<String, Object> findCreateMenu(String masterdataPart, String masterdataId){
        Map<String, Object> responseData = new HashMap<>();
        // 설비명
        MasterData idToFacilityData = masterDataRepository.findByMasterdataId(masterdataId);
        String idToFacility = idToFacilityData.getMasterdataFacility();
        responseData.put("facilityName", idToFacility);

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

        // 파트관리자 이메일리스트
        List<Email> emailList = emailRepository.findByEmailPart(masterdataPart);
        responseData.put("emailList", emailList);

        // 고정수신자 이메일리스트
        List<Email> staticEmailList = emailRepository.findByMasterStatus(Y);
        responseData.put("staticEmailList", staticEmailList);

        return responseData;
    }




    // 교육, 수시, 정기 카테고리 저장할 함수
    private String categoryType = "S";

    // 수시점검 저장
    @Transactional
    public SpecialInspection speCreate(String masterdataPart, String masterdataId, SpeInsFormDTO speInsFormDTO) throws Exception {
        // speIsFormDTO 나머지 데이터 세팅
        System.out.println("서비스확인용  : "+speInsFormDTO);
        speInsFormDTO.setSpeId(makeIdService.makeId(categoryType));             // id
        speInsFormDTO.setSpeDate(LocalDateTime.now());                          // 점검일
        speInsFormDTO.setSpePart(masterdataPart);                               // 영역

        MasterData idToFacilityData = masterDataRepository.findByMasterdataId(masterdataId);   // 설비 ID로 설비정보찾기
        String idToFacility = idToFacilityData.getMasterdataFacility();         // 설비명 얻기
        speInsFormDTO.setSpeFacility(idToFacility);

        SpeStatus.deadLineCal(speInsFormDTO);                                               // 위험도에 따른 완료요청기한

        speInsFormDTO.createSpeIns();

        // 수시점검 저장
        SpecialInspection special = speInsFormDTO.createSpeIns();
        specialInspectionRepository.save(special);

        // 파일 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            // 파일 업로드 및 파일 정보 저장
            List<SpecialFile> uploadedFiles = specialFileService.uploadFile(speInsFormDTO, idToFacility, NO);
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
    public Map<String, Object> findListOfFac(String masterdataId){
        Map<String, Object> responseData = new HashMap<>();
        String findId;          // 해당 설비를 가진 id를 저장할 함수

        // 설비 id에 해당하는 설비명 찾기
        MasterData idToFacilityData = masterDataRepository.findByMasterdataId(masterdataId);
        String idToFacility = idToFacilityData.getMasterdataFacility();
        responseData.put("facilityName", idToFacility);

        // 설비에 해당하는 SpecialInspection 찾기
        List<SpecialInspection> listOfFac = specialInspectionRepository.findAllBySpeFacilityOrderBySpeDateDesc(idToFacility);
        responseData.put("listOfFac", listOfFac);

        // 찾은 SpecialInspection의 id를 이용해 파일찾기
        List<SpecialFileFormDTO> speFileOfFac = new ArrayList<>();
        for(SpecialInspection listOfFacId : listOfFac){
            findId = listOfFacId.getSpeId();        // id세팅
            List<SpecialFileFormDTO> files= specialFileRepository.findBySpecialInspection_SpeId(findId);
            speFileOfFac.addAll(files);
        }
        responseData.put("speFileOfFac", speFileOfFac);
        return responseData;
    }

    // 수시점검 상세조회
    public Map<String, Object> getSpecialDetail(String speId) {
        Map<String, Object> detailMap = new HashMap<>();

        // 수시점검 데이터
        SpecialInspection special = specialInspectionRepository.findAllBySpeId(speId);
        detailMap.put("specialData", special);

        // 이미지 데이터
        List<SpecialFileFormDTO> speFileDTOList = specialFileRepository.findBySpecialInspection_SpeId(speId);
        if (!speFileDTOList.isEmpty()) {
            List<String> compImageUrls = new ArrayList<>();         // 완료이미지 url
            List<String> noCompImageUrls = new ArrayList<>();       // 미완료이미지 url

            for (SpecialFileFormDTO speFileDTO : speFileDTOList) {
                String imagePath = speFileDTO.getSpeFileUrl();

                if(speFileDTO.getIsComplete() == OK){            // 완료이미지 세팅
                    compImageUrls.add(imagePath);
                } else if (speFileDTO.getIsComplete() == NO){    // 미완료이미지 세팅
                    noCompImageUrls.add(imagePath);
                }
            }
            detailMap.put("compImageUrls", compImageUrls);      // 완료이미지
            detailMap.put("noCompImageUrls", noCompImageUrls);  // 미완료이미지
        }

        return detailMap;
    }


//    // 완료처리:업데이트
    @Transactional
    public SpecialInspection speUpdate(String speId, SpeInsFormDTO speInsFormDTO) throws Exception {
        System.out.println("-------------서비스 speDto: " +speInsFormDTO);
        SpecialInspection special = specialInspectionRepository.findAllBySpeId(speId);
        String facilityName = special.getSpeFacility();     // 설비명

        // 파일이 있으면 저장
        if(!(speInsFormDTO.getFiles() == null || speInsFormDTO.getFiles().isEmpty())){
            List<SpecialFile> uploadFiles;

            if(speInsFormDTO.getSpeComplete() == OK){           // 완료처리일 경우
                uploadFiles = specialFileService.uploadFile(speInsFormDTO, facilityName, OK);
            } else {                                            // 수정일 경우
                uploadFiles = specialFileService.uploadFile(speInsFormDTO, facilityName, NO);
            }

            for(SpecialFile specialFile : uploadFiles)
                specialFile.setSpecialInspection(special);
        }

        // 완료여부가 OK일 경우만 완료시간 세팅
        if(speInsFormDTO.getSpeComplete() == OK){
            speInsFormDTO.setSpeActDate(LocalDateTime.now());         // 완료시간 세팅
        }

        // dto -> entity 업데이트
        modelMapper.map(speInsFormDTO, special);
        // 저장(업데이트)
        specialInspectionRepository.save(special);

        return special;
    }

  
  
  
      // 수시점검 전체 조회
//    @Transactional(readOnly = true)
//    public Map<String, Object> findSpeAll(){
//        Map<String, Object> responseData = new HashMap<>();
//
//        List<SpecialInspection> specialInspectionList = specialInspectionRepository.findAll(Sort.by(Sort.Direction.DESC, "speId"));
//        List<SpecialFile> specialFileList = specialFileRepository.findAll();
//
//        responseData.put("specialData", specialInspectionList);
//        responseData.put("specialFileData", specialFileList);
//        return responseData;
//    }
  
  
  
   // 저장된 영역, 설비 리스트
    public Map<String, Object> getPartAndFacilityDataList(){
        Map<String, Object> responseData = new HashMap<>();
        List<SpecialPart> specialPartList = specialPartRepository.findAllOrderByPartNum();        // 영역 리스트
        List<MasterData> facilityList = masterDataRepository.findAllOrderBymasterdataId();             // 설비 리스트

        responseData.put("specialPartList", specialPartList);
        responseData.put("facilityList", facilityList);

        return responseData;
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
  
  

    //월간 수시점검 위험원인 건수(기타값 포함)
    public List<Object[]> specialDetailListByCauseAndMonth(int year, int month){
        List<Object[]> specialList = specialInspectionRepository.specialListByCauseAndMonth(year, month);
        System.out.println("입력리스트"+ specialList);


        //'기타(직접입력)'에 값 수정: 통계로 보여줄 때 기타(직접입력)으로 보여주기 위함.
        Long allValue = specialInspectionRepository.specialCountByCauseAndMonth(year, month);//모든값
        Long otherExcluedallValue = specialInspectionRepository.specialCountOtherExcludedByCauseAndMonth(year, month); //모든값-예외값

        //기타 수정값 넣기
        for (Object[] value : specialList) {
            if ("기타(직접입력)".equals(value[0])) { // 값이 기타(직접입력)이면
                value[1] = allValue - otherExcluedallValue; // 두 번째 값 수정
                break; // 수정 후 루프 종료
            }
        }

        // "선택" 값을 제외한 새로운 리스트 생성
        List<Object[]> filteredList = new ArrayList<>();
        for (Object[] item : specialList) {
            String value = (String) item[0];
            if (!value.equals("선택")) { //"선택" 제거
                filteredList.add(item);
            }
        }

        System.out.println(filteredList);
        return filteredList;
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



    // 1~12월까지 연간 수시점검 건수
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

    // (차트용) 월간 수시점검 영역별 건수
    public List<Map<String, Object>> setSpecialListByPartAndMonth(int year, int month){

        List<Object[]> statisticsList = specialInspectionRepository.specialListByPartAndMonth(year, month);

        List<Map<String, Object>> keyValueList = new ArrayList<>(); // 최종 List

        for (Object[] row : statisticsList) {

            String part = (String) row[0];
            Long count = (Long) row[1];

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("sort", part);
            dataPoint.put("수시점검", count);
            keyValueList.add(dataPoint);
        }
        return keyValueList;
    }



}
