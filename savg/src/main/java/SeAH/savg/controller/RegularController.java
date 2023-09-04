package SeAH.savg.controller;

import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.SpeicalFileRepository;
import SeAH.savg.service.RegularInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://172.20.10.5:3000")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://172.20.20.252:3000")  // 세아
public class RegularController {

    private final RegularInspectionRepository regularInspectionRepository;
    private final RegularInspectionService regularInspectionService;

   //--------------------------------------통계 관련


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

}
