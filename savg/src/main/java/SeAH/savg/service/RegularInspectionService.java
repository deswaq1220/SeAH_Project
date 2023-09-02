package SeAH.savg.service;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.entity.*;
import SeAH.savg.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.time.LocalDateTime;
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
    private final RegularListRepository regularListRepository;
    private final EduFileService eduFileService;

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
            regularDetailDTOList.add(new RegularDetailDTO(regularList.getRegular1Id(),regularList.getRegularList()));
        }

        return regularDetailDTOList;
    }

    //정기점검 조치자 이메일 리스트
    public List<Email> selectEmail(){
        List<Email> regularEmail = emailRepository.regularEmailList();
        return regularEmail;
    }


    private String categoryType = "R";

    //정기점검 등록
    public void createRegular(RegularDTO regularDTO) throws Exception {
        //정기점검 ID 부여 -> ex.R2308-00
        regularDTO.setRegularId(makeIdService.makeId(categoryType));
        RegularInspection regularInspection = regularDTO.createRegular();
        regularInspection.setRegularDate(LocalDateTime.now());
        regularInspection.setRegularEmail(regularDTO.getRegularEmail());


        RegularInspection savedRegularInspection = regularInspectionRepository.save(regularInspection);

        for(RegularDetailDTO regularDetailDTO:  regularDTO.getRegularDetailDTOList()){
                   //상세정보 등록
            RegularInspectionCheck regularInspectionCheck = regularDetailDTO.createRegularDetail();
            regularInspectionCheck.setRegularInspection(savedRegularInspection);
            regularInspectionCheck.setRegularCheck(regularDetailDTO.getRegularCheck());

            RegularInspectionCheck saveCheck = regularCheckRepository.save(regularInspectionCheck);

            if (regularDetailDTO.getRegularCheck() == RegStatus.BAD) {
                RegularInspectionBad regularInspectionBadEntity = regularDetailDTO.createRegularBad();
                regularInspectionBadEntity.setRegularComplete(RegStatus.NO);
                regularInspectionBadEntity.setRegularInspectionCheck(saveCheck);

                regularInspectionBadEntity.setRegularActContent(regularDetailDTO.getRegularActContent());
                regularInspectionBadEntity.setRegularActPerson(regularDetailDTO.getRegularActPerson());
                regularInspectionBadEntity.setRegularActEmail(regularDetailDTO.getRegularActEmail());
//                regularInspectionBadEntity.setRegularActDate(regularDetailDTO.getRegularActDate());

                regularInspectionBadRepository.save(regularInspectionBadEntity);
//                eduFileService.uploadFile2(regularInspection, regularDetailDTO);

            }
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
//        regularDetailDTO.setRegularInsName(inspection.getRegularInsName());
//        regularDetailDTO.setRegularDate(inspection.getRegularDate());
//        regularDetailDTO.setRegularPerson(inspection.getRegularPerson());
//        regularDetailDTO.setRegularEmpNum(inspection.getRegularEmpNum());
//        regularDetailDTO.setRegularEmail(inspection.getRegularEmail());
//        regularDetailDTO.setRegularPart(inspection.getRegularPart());

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
