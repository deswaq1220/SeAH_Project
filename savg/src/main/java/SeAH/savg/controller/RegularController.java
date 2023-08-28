package SeAH.savg.controller;

import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.SpeicalFileRepository;
import SeAH.savg.service.MakeIdService;
import SeAH.savg.service.RegularInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import SeAH.savg.service.MakeIdService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://172.20.10.5:3000")
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://172.20.20.252:3000")  // 세아
public class RegularController {

    private final RegularInspectionRepository regularInspectionRepository;
    private final RegularInspectionService regularInspectionService;
    private final MakeIdService makeIdService;

   //--------------------------------------통계 관련

        //월간 정기점검 건수
        @GetMapping("/regular/statistics/monthcount")
        public ResponseEntity<?> getRegularCountByMonth(@RequestParam("yearmonth") String yearMonth){
            int year = Integer.parseInt(yearMonth.substring(0, 4));
            int month = Integer.parseInt(yearMonth.substring(5, 7));

            int statisticsCount = regularInspectionRepository.regularCountByMonth(year, month);
            return ResponseEntity.ok(statisticsCount);
        }

        //연간 정기점검 건수
        @GetMapping("/regular/statistics/yearcount")
        public ResponseEntity<?> getRegularCountByYear(@RequestParam("year") int year){
            int statisticsCount = regularInspectionRepository.regularCountByYear(year);
            return ResponseEntity.ok(statisticsCount);
        }


    /*
     *//* 1~12월 내 발생한 월간 정기점검 현황 통계 조회 - 종류별
         * 형태: 중대재해예방 일반점검, 작업장 일반, 추락예방, 이동장비(지게차,트럭), 이동장비(크레인)...  + 점검건수 리스트
         * ex : 1월 중대재해예방 1건, 2월 중대재해예방 2건/ 작업장 일반 1건  ...
         *//*
        @GetMapping("/regular/statistics/detailname")
        public ResponseEntity<List<Map<String, Object>>> getDetailRegularListByName(@RequestParam("year") int year) {
            List<Map<String, Object>> statisticsList = regularInspectionService.regularDetailListByName(year);
            return ResponseEntity.ok(statisticsList);
        }*/


    //정기점검 항목 리스트
    @GetMapping("/regularname")
    public ResponseEntity<Map<String, Object>> regularNameListSelect() {
        Map<String, Object> responseData = new HashMap<>();
        List<String> regularNameList = regularInspectionService.selectRegularName();
        responseData.put("regularNameList", regularNameList);

        return ResponseEntity.ok(responseData);
    }

    //정기점검 항목에 따른 체크리스트 세팅
    @GetMapping("/regularcheck")
    public ResponseEntity<Map<String, List<String>>> regularcheck(@RequestParam int regularNum) {
        Map<String, List<String>> responseData = new HashMap<>();

        List<String> checklist = regularInspectionService.selectRegularListByNum(regularNum);
        responseData.put("checklist", checklist);

        return ResponseEntity.ok(responseData);
    }

    //정기점검 등록
    @PostMapping("/regular/new")
    public ResponseEntity<String> createRegularInspection(@RequestBody RegularDetailDTO regularDetailDTO, @RequestBody RegularDTO regularDTO) {
        regularInspectionService.createRegular(regularDetailDTO, regularDTO);
        return ResponseEntity.ok("정기점검 등록 성공");
    }


    //정기점검 목록 조회
    @GetMapping("/regularlist")
    public ResponseEntity<List<RegularDTO>> viewRegularList(@RequestParam int year, @RequestParam int month){
        List<RegularInspection> regularInspectionList = regularInspectionService.getRegularByDate(year, month);

        List<RegularDTO> regularDTOList = new ArrayList<>();
        for (RegularInspection regularInspection : regularInspectionList){
            regularDTOList.add(regularInspectionService.getRegularById(regularInspection.getRegularId()));
        }
        return ResponseEntity.ok(regularDTOList);


    }



}
