package SeAH.savg.service;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import SeAH.savg.repository.EmailRepository;
import SeAH.savg.repository.RegularCheckRepository;
import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.RegularInspectionBadRepository;
import SeAH.savg.repository.RegularStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    //정기점검 조치자 이메일 리스트
    public List<Email> selectEmail(){
        List<Email> regularEmail = emailRepository.regularEmailList();
        return regularEmail;
    }


    //정기점검 항목에 맞는 체크리스트 세팅
    public List<String> selectRegularListByNum(int regularNum) {
        List<String> checklist = new ArrayList<>();

        if (regularNum == 1) {
            List<String> regularList1 = regularInspectionRepository.regular1List();
            checklist.addAll(regularList1);
        } else if (regularNum == 2) {
            List<String> regularList2 = regularInspectionRepository.regular2List();
            checklist.addAll(regularList2);
        } else if (regularNum == 3) {
            List<String> regularList3 = regularInspectionRepository.regular3List();
            checklist.addAll(regularList3);
        }else if (regularNum == 4) {
            List<String> regularList4 = regularInspectionRepository.regular4List();
            checklist.addAll(regularList4);
        }else if (regularNum == 5) {
            List<String> regularList5 = regularInspectionRepository.regular5List();
            checklist.addAll(regularList5);
        }else if (regularNum == 6) {
            List<String> regularList6 = regularInspectionRepository.regular6List();
            checklist.addAll(regularList6);
        }else if (regularNum == 7) {
            List<String> regularList7 = regularInspectionRepository.regular7List();
            checklist.addAll(regularList7);
        }else if (regularNum == 8) {
            List<String> regularList8 = regularInspectionRepository.regular8List();
            checklist.addAll(regularList8);
        }else if (regularNum == 9) {
            List<String> regularList9 = regularInspectionRepository.regular9List();
            checklist.addAll(regularList9);
        }else if (regularNum == 10) {
            List<String> regularList10 = regularInspectionRepository.regular10List();
            checklist.addAll(regularList10);
        }else if (regularNum == 11) {
            List<String> regularList11 = regularInspectionRepository.regular11List();
            checklist.addAll(regularList11);
        }

        return checklist;
    }



    private String categoryType = "R";

    //정기점검 등록
    public void createRegular(RegularDetailDTO regularDetailDTO, RegularDTO regularDTO) {
        //정기점검 ID 부여 -> ex.R2308-00
        regularDTO.setRegularId(makeIdService.makeId(categoryType));
        RegularInspection regularInspection = regularDTO.createRegular();
        RegularInspection savedRegularInspection = regularInspectionRepository.save(regularInspection);

        //상세정보 등록
        RegularInspectionCheck regularInspectionCheck = regularDetailDTO.createRegularDetail();
        regularInspectionCheck.setRegularInspection(savedRegularInspection);

        RegularInspectionCheck saveCheck = regularCheckRepository.save(regularInspectionCheck);

        if (regularDetailDTO.getRegularCheck() == RegStatus.BAD) {
            RegularInspectionBad regularInspectionBadEntity = new RegularInspectionBad();
            regularInspectionBadEntity.setRegularActContent(regularDetailDTO.getRegularActContent());
            regularInspectionBadEntity.setRegularActPerson(regularDetailDTO.getRegularActPerson());
            regularInspectionBadEntity.setRegularActEmail(regularDetailDTO.getRegularActEmail());
            regularInspectionBadEntity.setRegularActDate(regularDetailDTO.getRegularActDate());
            regularInspectionBadEntity.setRegularComplete(regularDetailDTO.getRegularCheck());
            regularInspectionBadEntity.setRegularInspectionCheck(saveCheck);

            regularInspectionBadRepository.save(regularInspectionBadEntity);
        }
    }

    //정기점검 목록 조회
    public List<RegularInspection> getRegularByDate(int year, int month){
        return regularInspectionRepository.findAllByRegularDate(year, month);
    }

    //상세조회
    public RegularDetailDTO getRegularById(String regularId) {
        List<Object[]> result = regularCheckRepository.getRegularInspectionDetail(regularId);
        if (result.isEmpty()) {
            return null;
        }

        RegularDetailDTO regularDetailDTO = new RegularDetailDTO();
        Object[] data = result.get(0);

        //불량일때
        RegularInspectionBad bad = (RegularInspectionBad) data[1];
        regularDetailDTO.setRegularActContent(bad.getRegularActContent());
        regularDetailDTO.setRegularActPerson(bad.getRegularActPerson());
        regularDetailDTO.setRegularActEmail(bad.getRegularActEmail());

        //양호, N/A
        RegularInspection inspection = (RegularInspection) data[0];
        regularDetailDTO.setRegularInsName(inspection.getRegularInsName());
        regularDetailDTO.setRegularDate(inspection.getRegularDate());
        regularDetailDTO.setRegularPerson(inspection.getRegularPerson());
        regularDetailDTO.setRegularEmpNum(inspection.getRegularEmpNum());
        regularDetailDTO.setRegularEmail(inspection.getRegularEmail());
        regularDetailDTO.setRegularPart(inspection.getRegularPart());

        return regularDetailDTO;
    }

//----------------------------------------------------통계 관련
    //(pieChart) 월간 정기점검 체크 값: GOOD ()건, BAD ()건
    public List<Map<String, Object>> RegularCntByCheckAndMonth(int year, int month){
        List<Object[]> data = regularStatisticsRepository.regularCntByCheckAndMonth(year, month);

        List<Map<String, Object>> finalData = new ArrayList<>();
        for(Object[] row : data){
            RegStatus regularCheck = (RegStatus) row[0];
            Long count = (Long) row[1];

            Map<String, Object> middleData = new HashMap<>();

            if(regularCheck.equals(RegStatus.GOOD)){
                middleData.put("id", "양호");
                middleData.put("label", "양호");
            } else if(regularCheck.equals(RegStatus.BAD)){
                middleData.put("id", "불량");
                middleData.put("label", "불량");
            } else if(regularCheck.equals(RegStatus.NA)){
                middleData.put("id", "NA");
                middleData.put("label", "NA");
            } else{
                middleData.put("id", "error");
                middleData.put("label", "error");
                log.error("에러발생");
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

            if(regularCheck.equals(RegStatus.GOOD)){
                middleData.put("id", "양호");
                middleData.put("label", "양호");
            } else if(regularCheck.equals(RegStatus.BAD)){
                middleData.put("id", "불량");
                middleData.put("label", "불량");
            } else if(regularCheck.equals(RegStatus.NA)){
                middleData.put("id", "NA");
                middleData.put("label", "NA");
            } else{
                middleData.put("id", "error");
                middleData.put("label", "error");
                log.error("에러발생");
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

}
