package SeAH.savg.service;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.dto.*;
import SeAH.savg.entity.*;
import SeAH.savg.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static SeAH.savg.constant.MasterStatus.Y;
import static SeAH.savg.constant.RegStatus.BAD;
import static SeAH.savg.constant.RegStatus.OK;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegularInspectionService {

    private final RegularInspectionRepository regularInspectionRepository;
    private final RegularStatisticsRepository regularStatisticsRepository;
    private final RegularInspectionBadRepository regularInspectionBadRepository;
    private final RegularCheckRepository regularCheckRepository;
    private final MakeIdService makeIdService;
    private final EmailRepository emailRepository;
    private final RegularListRepository regularListRepository;
    private final RegularFileService regularFileService;
    private final RegularFileRepository regularFileRepository;

    @PersistenceContext
    private EntityManager entityManager;


    // 월별현황 : 점검실시 ()건, 조치완료 ()건, 불량건수 ()건
    @Transactional
    public Map<String, Object> findRegMonthly(){
        Map<String, Object> responseData = new HashMap<>();

        // 이번달 점검실시 건수
        int countMonthlyAll = regularInspectionRepository.countByRegTime();
        // 이번달 조치완료 건수
        int countMonthlyComplete = regularInspectionRepository.findRegularInspectionsCompletedToday(OK);
        // 이번달 등록건수 중 불량건수
        int countMonthlyBadReg = regularCheckRepository.countByRegularCheck(BAD);

        responseData.put("monthlyAll", countMonthlyAll);                 // 이번달 점검실시건수
        responseData.put("monthlyComplete", countMonthlyComplete);       // 이번달 조치완료건수
        responseData.put("monthlyBad", countMonthlyBadReg);              // 이번달 불량건수

        return responseData ;
    }


    // 정기점검 항목 불러오기
    public List<String> selectRegularName(){
        List<String> regularNameList = regularInspectionRepository.regularInsNameList();

        return regularNameList;
    }

    //정기점검 영역 불러오기
    public List<String> selectRegularPart(){
        List<String> regularPartList = regularInspectionRepository.regularPartList();
        return regularPartList;
    }



    //정기점검 항목에 맞는 체크리스트 세팅
    public List<RegularDetailDTO> selectRegularListByNum(int regularNum) {
        List<RegularDetailDTO> regularDetailDTOList = new ArrayList<>();
        List<RegularList> regularListArray = regularListRepository.findByRegularNum(regularNum);
        for(RegularList regularList: regularListArray){
            regularDetailDTOList.add(new RegularDetailDTO(regularList.getRegularId(),regularList.getRegularList()));
        }

        return regularDetailDTOList;
    }

    //정기점검 조치자 이메일 리스트
    public Map<String, Object> selectEmail(){

        Map<String, Object> responseData = new HashMap<>();

        //영역별 이메일 전체리스트
        List<Email> regularEmail = emailRepository.regularEmailList();
        responseData.put("emailList", regularEmail);

        // 고정수신자 이메일리스트
        List<Email> staticEmailList = emailRepository.findByMasterStatus(Y);
        responseData.put("staticEmailList", staticEmailList);

        return responseData;
    }


    private String categoryType = "R";

    //정기점검 등록
    public Map<String, Object>createRegular(RegularDTO regularDTO) throws Exception {
        //정기점검 ID 부여 -> ex.R2308-00
        regularDTO.setRegularId(makeIdService.makeId(categoryType));
        RegularInspection regularInspection = regularDTO.createRegular();
        regularInspection.setRegularEmail(regularDTO.getRegularEmail());


        RegularInspection savedRegularInspection = regularInspectionRepository.save(regularInspection);

        Map<String, Object> finalData = new HashMap<>();
        finalData.put("regularDate", savedRegularInspection.getRegTime());
        finalData.put("regularId", savedRegularInspection.getRegularId());


        if(regularDTO.getFile()!=null){
            regularFileService.regularUploadFile(regularInspection, regularDTO);
        }

        ObjectMapper mapper = new ObjectMapper();

        List<RegularDetailRegDTO> list = mapper.readValue(regularDTO.getRegularDetailRegDTOList(), new TypeReference<List<RegularDetailRegDTO>>(){});

        boolean allGood = true;

        for(RegularDetailRegDTO regularDetailRegDTO:  list){
            //상세정보 등록
            RegularInspectionCheck regularInspectionCheck = regularDetailRegDTO.createRegularDetail();
            regularInspectionCheck.setRegularInspection(savedRegularInspection);
            regularInspectionCheck.setRegularCheck(regularDetailRegDTO.getRegularCheck());
            regularInspectionCheck.setRegularListId(regularDetailRegDTO.getId());

            RegularInspectionCheck saveCheck = regularCheckRepository.save(regularInspectionCheck);

            if (regularDetailRegDTO.getRegularCheck() == RegStatus.BAD) {
                RegularInspectionBad regularInspectionBadEntity = regularDetailRegDTO.createRegularBad();
                regularInspectionBadEntity.setRegularComplete(RegStatus.NO);
                regularInspectionBadEntity.setRegularInspectionCheck(saveCheck);

                regularInspectionBadEntity.setRegularActContent(regularDetailRegDTO.getRegularActContent());
                regularInspectionBadEntity.setRegularActPerson(regularDetailRegDTO.getRegularActPerson());
                regularInspectionBadEntity.setRegularActEmail(regularDetailRegDTO.getRegularActEmail());

                regularInspectionBadRepository.save(regularInspectionBadEntity);
                regularInspection.setRegularComplete(RegStatus.NO);
                regularInspectionRepository.save(regularInspection);
                allGood = false;
            }
        }

        if(allGood){
            regularInspection.setRegularComplete(RegStatus.OK);
            regularInspectionRepository.save(regularInspection);
        }


        return finalData;
    }

    //정기점검 목록 조회
    public List<RegularInspection> getRegularByDate(int year, int month){
        return regularInspectionRepository.findAllByRegularDateOrderByOrderByRegTime(year, month);
    }

    //상세조회
    public RegularDTO getRegularById(String regularId) {
        RegularInspection regularInspection = regularInspectionRepository.findById(regularId).orElseThrow(()->new IllegalArgumentException("상세보기 할 데이터가 없습니다."));
        RegularDTO regularDTO = RegularDTO.of(regularInspection);

        return regularDTO;
    }





    //정기점검 조치완료
    public LocalDateTime updateRegularBad(Long regularBadId, RegularDetailDTO regularDetailDTO) throws Exception {

        RegularInspectionBad regularInspectionBad = regularInspectionBadRepository.findById(regularBadId).orElseThrow();
        regularInspectionBad.setRegularComplete(RegStatus.OK);
        regularInspectionBad.setRegularActDate(LocalDateTime.now());
        LocalDateTime actionCompleteTime = regularInspectionBad.getRegularActDate();
        System.out.println(actionCompleteTime);
        regularInspectionBadRepository.save(regularInspectionBad);
        if(regularDetailDTO.getFiles()!=null){
            regularFileService.regularFileUpadte(regularDetailDTO);
        }

        //모두 조치완료되었는지 확인 -> 모두 조치완료면 점검완료로 변경
        int InsRow = regularInspectionRepository.regularInsRow(regularDetailDTO.getRegularInspectionId()); //점검한 bad 갯수
        int completeRow = regularInspectionRepository.completeToOK(regularDetailDTO.getRegularInspectionId()); //complete: NO->OK 완료 갯수

        if(InsRow == completeRow){
            RegularInspection regularInspectionComplete = regularInspectionRepository.findById(regularDetailDTO.getRegularInspectionId()).orElseThrow();
            regularInspectionComplete.setRegularComplete(RegStatus.OK);
            regularInspectionRepository.save(regularInspectionComplete);
        }

        return actionCompleteTime;
    }

    // 정기점검내역 삭제
    @Transactional
    public void regDelete(String regId) {

        // 파일 삭제
        List<RegularFile> filesToDelete = regularFileRepository.findByRegularInspectionRegularId(regId);

        for (RegularFile file : filesToDelete) {
            regularFileRepository.deleteById(file.getRegularFileId());
            regularFileService.deleteFile(file.getRegularFileName());
        }

        // bad 및 check 삭제
        List<RegularInspectionCheck> checkToDeleteList = regularCheckRepository.findByRegularInspectionRegularId(regId);


        for (RegularInspectionCheck checkToDelete : checkToDeleteList) {

            // RegularInspectionBad 삭제
            RegularInspectionBad badToDelete = regularInspectionBadRepository.findByRegularInspectionCheck(checkToDelete);
            if (badToDelete != null) {
                regularInspectionBadRepository.delete(badToDelete);
            }

            // RegularInspectionCheck 삭제
            regularCheckRepository.delete(checkToDelete);
        }

        // RegularInspection 삭제
        RegularInspection regInspectionToDelete = regularInspectionRepository.findByRegularId(regId);
        if (regInspectionToDelete != null) {
            regularInspectionRepository.delete(regInspectionToDelete);
        }
    }


//----------------------------------------------------정기점검 전체조회 검색
public List<RegularSearchResultDTO> searchRegularList(RegularSearchDTO searchDTO) {

    List<String> completeYN = regularInspectionRepository.findByRegularCheckAllComplete(); //모두 처리완료여부

    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    QRegularInspection qRegularInspection = QRegularInspection.regularInspection;
    QRegularInspectionCheck qRegularInspectionCheck = QRegularInspectionCheck.regularInspectionCheck;


    BooleanBuilder predicate = new BooleanBuilder();

    //검색
    if (searchDTO.getRegularPart() != null) {
        BooleanExpression partPredicate = qRegularInspection.regularPart.eq(searchDTO.getRegularPart());
        predicate.and(partPredicate);
    }
    if (searchDTO.getRegularInsName() != null) {
        BooleanExpression insNamePredicate = qRegularInspection.regularInsName.eq(searchDTO.getRegularInsName());
        predicate.and(insNamePredicate);
    }
    if (searchDTO.getRegularStartTime() != null && searchDTO.getRegularEndTime() != null) {
        BooleanExpression datePredicate = qRegularInspection.regTime.between(
                searchDTO.getRegularStartTime(), searchDTO.getRegularEndTime());
        predicate.and(datePredicate);
    }
    if (searchDTO.getRegularEmpNum() != null) {
        BooleanExpression empNumPredicate = qRegularInspection.regularEmpNum.eq(searchDTO.getRegularEmpNum());
        predicate.and(empNumPredicate);
    }
    if (searchDTO.getRegularPerson() != null) {
        BooleanExpression personPredicate = qRegularInspection.regularPerson.eq(searchDTO.getRegularPerson());
        predicate.and(personPredicate);
    }
    if (searchDTO.getRegularComplete() != null) {
        BooleanExpression checkPredicate = qRegularInspection.regularComplete.eq(searchDTO.getRegularComplete());
        predicate.and(checkPredicate);
    }



    //테이블 생성
    List<Tuple> searchRegularData = queryFactory
            .select(qRegularInspection.regularPart, qRegularInspection.regularInsName, qRegularInspection.regTime, qRegularInspection.regularEmpNum,
                    qRegularInspection.regularPerson, qRegularInspection.regularComplete, qRegularInspection.regularId, qRegularInspectionCheck.regularCheck)
            .from(qRegularInspection)
            .leftJoin(qRegularInspectionCheck).on(qRegularInspection.regularId.eq(qRegularInspectionCheck.regularInspection.regularId))
            .where(predicate)
            .orderBy(qRegularInspection.regTime.desc())
            .fetch();


    //테이블 중 같은 아이디의 경우 테이블 줄이기
    List<RegularSearchResultDTO> joinResult = new ArrayList<>();
    for (Tuple tuple : searchRegularData) {
        RegularSearchResultDTO middleResultDTO = new RegularSearchResultDTO();
        middleResultDTO.setRegularPart(tuple.get(qRegularInspection.regularPart)); //영역
        middleResultDTO.setRegularInsName(tuple.get(qRegularInspection.regularInsName)); //점검항목
        middleResultDTO.setRegularDate(tuple.get(qRegularInspection.regTime));  //점검일자
        middleResultDTO.setRegularEmpNum(tuple.get(qRegularInspection.regularEmpNum));  //점검자 사원번호
        middleResultDTO.setRegularPerson(tuple.get(qRegularInspection.regularPerson));  //점검자명

        if(tuple.get(qRegularInspectionCheck.regularCheck) == BAD){
            middleResultDTO.setRegularInsCount(1); //불량갯수
        }else{
            middleResultDTO.setRegularInsCount(0); //불량 없음
        }

        middleResultDTO.setRegularComplete(tuple.get(qRegularInspection.regularComplete));   //모두 조치완료여부
        middleResultDTO.setRegularId(tuple.get(qRegularInspection.regularId));  //점검ID
        joinResult.add(middleResultDTO);
    }


    //결과- 중복 제거
    Map<String, Integer> uniqueRegularIdList = new HashMap<>();
    List<RegularSearchResultDTO> finalList = new ArrayList<>();


    for (RegularSearchResultDTO middleResultDTO : joinResult) {
        String regularId = middleResultDTO.getRegularId();

        if (!uniqueRegularIdList.containsKey(regularId)) {
            uniqueRegularIdList.put(regularId, middleResultDTO.getRegularInsCount());
            finalList.add(middleResultDTO);
        } else {
            int currentCount = uniqueRegularIdList.get(regularId);
            uniqueRegularIdList.put(regularId, currentCount + middleResultDTO.getRegularInsCount());

            for (RegularSearchResultDTO finalDTO : finalList) {
                if (finalDTO.getRegularId().equals(regularId)) {
                    finalDTO.setRegularInsCount(currentCount + middleResultDTO.getRegularInsCount());
                    break;
                }
            }
        }
    }


    return finalList;

}




//----------------------------------------------------통계 관련
    //월간
    //(pieChart) 월간 정기점검 체크 값: GOOD ()건, BAD ()건
    public List<Map<String, Object>> RegularCntByCheckAndMonth(int year, int month){
        List<Object[]> data = regularStatisticsRepository.regularCntByCheckAndMonth(year, month);

        List<Map<String, Object>> finalData = new ArrayList<>();
        for(Object[] row : data){
            RegStatus regularCheck = (RegStatus) row[0];
            Long count = (Long) row[1];

            Map<String, Object> middleData = new HashMap<>();

            try{
                if(regularCheck != null){
                    if(regularCheck.equals(RegStatus.GOOD)){
                        middleData.put("id", "양호");
                        middleData.put("label", "양호");
                    } else if(regularCheck.equals(RegStatus.BAD)){
                        middleData.put("id", "불량");
                        middleData.put("label", "불량");
                    } else if(regularCheck.equals(RegStatus.NA)){
                        middleData.put("id", "NA");
                        middleData.put("label", "NA");
                    }
                }
                else {
                    middleData.put("id", "error");
                    middleData.put("label", "error");
                    log.error("에러발생");
                }
            } catch(Exception e){
                e.printStackTrace();
            };

            middleData.put("value", count);

            finalData.add(middleData);
        }
        return finalData;
    }


    //(pieChart) 월간 정기점검 체크 값: GOOD ()건, BAD ()건 - sort
    public List<Map<String, Object>> RegularCntByCheckAndMonthSort(int year, int month, String regularInsName){
        List<Object[]> data = regularStatisticsRepository.regularCntByCheckAndMonthSortName(year, month, regularInsName);

        List<Map<String, Object>> finalData = new ArrayList<>();
        for(Object[] row : data){
            RegStatus regularCheck = (RegStatus) row[0];
            Long count = (Long) row[1];

            Map<String, Object> middleData = new HashMap<>();
            try{
                if(regularCheck != null) {
                    if (regularCheck.equals(RegStatus.GOOD)) {
                        middleData.put("id", "양호");
                        middleData.put("label", "양호");
                    } else if (regularCheck.equals(RegStatus.BAD)) {
                        middleData.put("id", "불량");
                        middleData.put("label", "불량");
                    } else if (regularCheck.equals(RegStatus.NA)) {
                        middleData.put("id", "NA");
                        middleData.put("label", "NA");
                    }
                } else{
                    middleData.put("id", "error");
                    middleData.put("label", "error");
                    log.error("에러발생");
                }
            } catch(Exception e){
                e.printStackTrace();
            };

            middleData.put("value", count);

            finalData.add(middleData);
        }
        return finalData;
    }

    //(pieChart) 월간 정기점검 체크 값 드롭다운 생성
    public List<String> RegularNameList(){

        List<String> specialPartList = regularStatisticsRepository.RegularNameList();

        return specialPartList;
    }


    //(radarChart) 월간 영역별 정기점검 건수 통계 - 선택 제외, 직접입력은 '기타'로 일원화
    @Transactional
    public List<Map<String, Object>> regularDetailListByPartAndMonth(int year, int month){
        List<Object[]> regularList = regularStatisticsRepository.regularListByPartAndMonth(year, month); //전체 리스트 가져오기

        //'기타(직접입력)'에 값 수정: 통계로 보여줄 때 기타(직접입력)으로 보여주기 위함.
        Long allValue = regularStatisticsRepository.regularCountByPartAndMonth(year, month);//모든값
        System.out.println("전체값 :   " + allValue);
        Long otherExcluedallValue = regularStatisticsRepository.regularCountOtherExcludedByPartAndMonth(year, month); //모든값-예외값
        System.out.println("모든값-예외값 :   " + otherExcluedallValue);


        //기타 수정값 넣기
        for (Object[] value : regularList) {
            if ("기타(직접입력)".equals(value[0])) { // 값이 기타(직접입력)이면

                value[1] = allValue - otherExcluedallValue; // 기타에 해당하는 값 수정값으로 변경
                String part = (String) value[0];
                Long count = (Long) value[1];
                System.out.println("기타(직접입력)일원화:   "+ part + count);
                break; // 수정 후 루프 종료
            }
        }

        // "선택" 값을 제외한 새로운 리스트 생성
        List<Map<String, Object>> filteredList = new ArrayList<>();
        for (Object[] row : regularList) {
            String part = (String) row[0];
            Long count = (Long) row[1];
            /*            System.out.println("기타(직접입력)일원화:   "+ part + count);*/
            if (!part.equals("선택")) { //"선택" 제거
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("sort", part);
                dataPoint.put("정기점검", count);
                filteredList.add(dataPoint);
            }
        }

        System.out.println(filteredList);
        return filteredList;
    }

    //(엑셀용) 월간 점검종류별 점검건수
    public List<Map<String, Map<String,Long>>> regularCntListByNameAndYearForExcel(int year, int month){
        List<Object[]> regularList = regularStatisticsRepository.regularListByNameAndMonthForExcel(year, month);

        Map<String, Map<String, Long>> grouping = new HashMap<>();
        List<Map<String, Map<String,Long>>> resultList = new ArrayList<>();

        for(Object[] row: regularList){
            if(row[0] != null && row[1] != null && row[2] != null) {
                String name = (String) row[0];
                String value = ((RegStatus) row[1]).toString();
                Long count = (Long) row[2];

                Map<String, Long> dataPoint = grouping.getOrDefault(name, new HashMap<>());
                dataPoint.put(value, count);
                grouping.put(name, dataPoint);
            } else {
                Map<String, Long> dataPoint = grouping.getOrDefault("error", new HashMap<>());
                dataPoint.put("error", 0L);
                grouping.put("error", dataPoint);
            }
        }
        resultList.add(grouping);

        return resultList;
    }

    //연간
    //1~12월까지 월간 정기점검종류별 점검건수
    @Transactional
    public List<Map<String,Object>> regularCountListByNameAndYear(int year){
        List<Object[]> regularList = regularStatisticsRepository.regularDetailListByNameAndYear(year);

        Map<Integer, Map<String, Object>> dataByYear = new HashMap<>();

        for(Object[] row : regularList){

            Integer month = (Integer) row[0];
            String regularName = (String) row[1];
            Long count = (Long) row[2];

            if(!dataByYear.containsKey(month)){
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("month", month); //형태: "month":9월
                dataByYear.put(month, dataPoint); //형태: 9월:"month":9월
            }
            Map<String, Object> dataPoint = dataByYear.get(month);
            dataPoint.put(regularName, count);
            //dataPoin 형태 =    "month": 9월
            //                  "중대재해": 7
        }
        List<Map<String, Object>> finalData = new ArrayList<>(dataByYear.values());

        return finalData;
    }
    //정기점검 상세 보기
    public List<RegularDetailDTO> getRegularCheckList(String regularId){
        List<RegularDetailDTO> regularDetailDTOList = new ArrayList<>();


        //regularInstpection 가져오기
        RegularInspection regularInspection = regularInspectionRepository.findById(regularId).orElseThrow();

        //regularInstpection에 등록된 체크리스트 불러오기
        List<RegularInspectionCheck> regularInspectionCheckList = regularCheckRepository.findByRegularInspection(regularInspection);

        //regularInstpection에 등록된 파일 가져오기


        for(RegularInspectionCheck regularInspectionCheck : regularInspectionCheckList){
            RegularList regularList = regularListRepository.findById(regularInspectionCheck.getRegularListId()).orElseThrow();

            String checklist = regularList.getRegularList();

            RegStatus regStatus = regularInspectionCheck.getRegularCheck();


            if(regularInspectionBadRepository.findByRegularInspectionCheck(regularInspectionCheck) != null){
                RegularInspectionBad regularInspectionBad = regularInspectionBadRepository.findByRegularInspectionCheck(regularInspectionCheck);
                RegStatus regularComplete = regularInspectionBad.getRegularComplete();

                List<String> beforeFileNameList = regularFileRepository.getRegularFileName(regularList.getRegularId(),regularInspection,"조치전");
                List<String> afterFileNameList = regularFileRepository.getRegularFileName(regularList.getRegularId(),regularInspection,"조치후");

                String regularActContent = regularInspectionBad.getRegularActContent();
                String regularActEmail = regularInspectionBad.getRegularActEmail();
                String regularActPerson = regularInspectionBad.getRegularActPerson();
                Long regularBadId = regularInspectionBad.getRegularBadId();

                regularDetailDTOList.add(new RegularDetailDTO(regularBadId,regularInspectionCheck.getRegularListId(),regStatus ,checklist,regularActContent,regularActPerson,regularActEmail,regularComplete,beforeFileNameList,afterFileNameList));
            }else{
                regularDetailDTOList.add(new RegularDetailDTO(null,regularInspectionCheck.getRegularListId(),regStatus ,checklist,null,null,null,null,null,null));
            }


        }
        return regularDetailDTOList;
    }


}


