package SeAH.savg.service;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionBad;
import SeAH.savg.entity.RegularInspectionCheck;
import SeAH.savg.repository.RegularCheckRepository;
import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.RegularInspectionBadRepository;
import SeAH.savg.repository.RegularStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
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


    // 정기점검 항목 불러오기
    public List<String> selectRegularName(){
        List<String> regularNameList = regularInspectionRepository.regularInsNameList();
        return regularNameList;
    }

    //정기점검 항목에 맞는 체크리스트 세팅
    public List<String> selectRegularListByNum(int regularNum) {
        List<String> regularNameList = regularInspectionRepository.regularInsNameList();

        if (regularNum == 1) {
            List<String> regularList1 = regularInspectionRepository.regular1List();
            regularNameList.addAll(regularList1);
        } else if (regularNum == 2) {
            List<String> regularList2 = regularInspectionRepository.regular2List();
            regularNameList.addAll(regularList2);
        } else if (regularNum == 3) {
            List<String> regularList3 = regularInspectionRepository.regular3List();
            regularNameList.addAll(regularList3);
        }else if (regularNum == 4) {
            List<String> regularList4 = regularInspectionRepository.regular4List();
            regularNameList.addAll(regularList4);
        }else if (regularNum == 5) {
            List<String> regularList5 = regularInspectionRepository.regular5List();
            regularNameList.addAll(regularList5);
        }else if (regularNum == 6) {
            List<String> regularList6 = regularInspectionRepository.regular6List();
            regularNameList.addAll(regularList6);
        }else if (regularNum == 7) {
            List<String> regularList7 = regularInspectionRepository.regular7List();
            regularNameList.addAll(regularList7);
        }else if (regularNum == 8) {
            List<String> regularList8 = regularInspectionRepository.regular8List();
            regularNameList.addAll(regularList8);
        }else if (regularNum == 9) {
            List<String> regularList9 = regularInspectionRepository.regular9List();
            regularNameList.addAll(regularList9);
        }else if (regularNum == 10) {
            List<String> regularList10 = regularInspectionRepository.regular10List();
            regularNameList.addAll(regularList10);
        }else if (regularNum == 11) {
            List<String> regularList11 = regularInspectionRepository.regular11List();
            regularNameList.addAll(regularList11);
        }

        return regularNameList;
    }


    //정기점검 등록
    public void createRegular(RegularDetailDTO regularDetailDTO, RegularDTO regularDTO) {
        RegularInspection regularInspection = regularDTO.createRegular();
        RegularInspection savedRegularInspection = regularInspectionRepository.save(regularInspection);

        RegularInspectionCheck regularInspectionCheck = regularDetailDTO.createRegularDetail();

        regularInspectionCheck.setRegularInspection(savedRegularInspection);

        RegularInspectionCheck saveCheck = regularCheckRepository.save(regularInspectionCheck);

        if (regularDetailDTO.getRegularCheck() == RegStatus.BAD) {
            RegularInspectionBad regularInspectionBadEntity = new RegularInspectionBad();
            regularInspectionBadEntity.setRegularActContent(regularDetailDTO.getRegularActContent());
            regularInspectionBadEntity.setRegularActPerson(regularDetailDTO.getRegularActPerson());
            regularInspectionBadEntity.setRegularActEmail(regularDetailDTO.getRegularActEmail());
            regularInspectionBadEntity.setRegularDate(regularDetailDTO.getRegularDate());
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

/*    //상세조회
    public RegularDetailDTO getRegularById(String regularId) {
        RegularInspectionCheck regularInspectionCheck = regularCheckRepository.

    }*/

//----------------------------------------------------통계 관련
    //(pieChart) 월간 정기점검 체크 값: GOOD ()건, BAD ()건
    public List<Map<String, Object>> RegularCntByCheckAndMonth(int year, int month){
        List<Object[]> data = regularStatisticsRepository.regularCntByCheckAndMonth(year, month);

        List<Map<String, Object>> finalData = new ArrayList<>();
        for(Object[] row : data){
            RegStatus regularCheck = (RegStatus) row[0];
            Long count = (Long) row[1];

            Map<String, Object> middleData = new HashMap<>();
            middleData.put("id", regularCheck);
            middleData.put("label", regularCheck);
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
            middleData.put("id", regularCheck);
            middleData.put("label", regularCheck);
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
