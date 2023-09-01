package SeAH.savg.service;

import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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


    //(lineChart) 1~12월까지 연간 수시점검 건수
    public List<Map<String, Object>> setRegularCountList(int year){
        List<Object[]> statisticsList = regularInspectionRepository.regularCountList(year);

        List<Map<String, Object>> dataPoints = new ArrayList<>();

        for(Object[] row : statisticsList){

            Integer month = (Integer) row[0];
            Long count = (Long) row[1];

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("month", month);
            dataPoint.put("y", count);

            dataPoints.add(dataPoint);
        }

        Map<String, Object> finalDate = new HashMap<>();
        finalDate.put("id", "정기점검");
        finalDate.put("data", dataPoints);

        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(finalDate);


        return resultList;
    }






    //1~12월까지 월간 정기점검 건수(barChart용)
/*      public List<Map<String,Object>> regularDetailListByName(int year){
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
    }*/


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
}
