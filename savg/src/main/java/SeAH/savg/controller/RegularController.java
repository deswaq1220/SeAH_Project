package SeAH.savg.controller;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.dto.*;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.RegularStatisticsRepository;
import SeAH.savg.repository.SpeicalFileRepository;
import SeAH.savg.service.RegularInspectionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = "http://172.20.10.5:3000")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://172.20.20.252:3000")  // 세아
public class RegularController {

    private final RegularInspectionRepository regularInspectionRepository;
    private final RegularInspectionService regularInspectionService;
    private final RegularStatisticsRepository regularStatisticsRepository;


    //정기점검 항목 리스트
    @GetMapping("/user/regularname")
    public ResponseEntity<Map<String, Object>> regularNameListSelect() {
        Map<String, Object> responseData = new HashMap<>();
        List<String> regularNameList = regularInspectionService.selectRegularName();
        responseData.put("regularNameList", regularNameList);

        return ResponseEntity.ok(responseData);
    }

    //정기점검 영역 리스트(주조, 영역 등)

    @GetMapping("/user/regularpart")
    public ResponseEntity<Map<String, Object>> regularPartListSelect(){
        Map<String, Object> responseData = new HashMap<>();
        List<String> regularPartList = regularInspectionService.selectRegularPart();
        responseData.put("regularPartList", regularPartList);

        return ResponseEntity.ok(responseData);
    }


    //정기점검 항목에 따른 체크리스트 세팅

    @GetMapping("/user/regularcheck")
    public ResponseEntity<List<RegularDetailDTO>> regularcheck(@RequestParam int regularNum) {
        Map<String, List<RegularDetailDTO>> responseData = new HashMap<>();

        List<RegularDetailDTO> checklist = regularInspectionService.selectRegularListByNum(regularNum);



        return ResponseEntity.ok(checklist);
    }


    //정기점검 이메일 리스트(조치 담당자)
    @GetMapping("/user/regularemail")
    public ResponseEntity<Map<String, Object>> regularEmailList(){
       Map<String, Object> responseData = regularInspectionService.selectEmail();

        return ResponseEntity.ok(responseData);
    }

    //정기점검 등록
    @PostMapping(value = "/user/regular/new")
        public ResponseEntity<Map<String, Object>> createRegularInspection(RegularDTO regularDTO)throws Exception {
        Map<String, Object> regularDate = regularInspectionService.createRegular(regularDTO);

        // 응답 데이터 생성
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("message", "정기점검 등록 성공");
        responseData.putAll(regularDate);

        return ResponseEntity.ok(responseData);
    }


    //정기점검 목록 조회

    @GetMapping("/user/regularlist")
    public ResponseEntity<List<RegularDTO>> viewRegularList(@RequestParam int year, @RequestParam int month) {
        List<RegularInspection> regularInspectionList = regularInspectionService.getRegularByDate(year, month);

        List<RegularDTO> regularDTOList = new ArrayList<>();
        for (RegularInspection regularInspection : regularInspectionList) {
            RegularDTO regularDTO = new RegularDTO();
            regularDTO.setRegularId(regularInspection.getRegularId());
            regularDTO.setRegularInsName(regularInspection.getRegularInsName());
            regularDTO.setRegularDate(regularInspection.getRegularDate());
            regularDTO.setRegularPart(regularInspection.getRegularPart());
            regularDTOList.add(regularDTO);
        }

        return ResponseEntity.ok(regularDTOList);
    }


    //정기점검 상세조회
    @GetMapping("/user/regular/detail/{regularId}")
    public ResponseEntity<RegularDetailDTO> viewRegularDetail(@PathVariable("regularId") String regularId){
        RegularDetailDTO regularDetailDTO = regularInspectionService.getRegularById(regularId);
        if (regularDetailDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regularDetailDTO);
    }

    //--------------------------------------전체현황 조회 관련
    @GetMapping("/user/searchregularlist")
    public ResponseEntity<?> searchRegularList( @RequestParam(value= "regularPart", required = false) String regularPart
                                               ,@RequestParam(value= "regularInsName", required = false) String regularInsName
                                               ,@RequestParam(value= "regularStartDate", required = false) LocalDate regularStartDate
                                               ,@RequestParam(value= "regularEndDate", required = false) LocalDate regularEndDate
                                               ,@RequestParam(value= "regularEmpNum", required = false) String regularEmpNum
                                               ,@RequestParam(value= "regularPerson", required = false) String regularPerson
                                               ,@RequestParam(value= "regularComplete", required = false) RegStatus regularComplete){

        //날짜 변환 localDate -> localDateTime
        LocalDateTime regularStartDateTime = null;
        LocalDateTime regularEndDateTime = null;

        if(regularStartDate != null && regularEndDate != null){
            regularStartDateTime = regularStartDate.atTime(LocalTime.MIN);
            regularEndDateTime = regularEndDate.atTime(LocalTime.MAX);
        }

        RegularSearchDTO dto = new RegularSearchDTO();
        dto.setRegularPart(regularPart);
        dto.setRegularInsName(regularInsName);
        dto.setRegularStartTime(regularStartDateTime);
        dto.setRegularEndTime(regularEndDateTime);
        dto.setRegularEmpNum(regularEmpNum);
        dto.setRegularPerson(regularPerson);
        dto.setRegularComplete(regularComplete);
        System.out.println(dto);

        List<RegularSearchResultDTO> serviceData = regularInspectionService.searchRegularList(dto);


        Map<String, Object> searchResult = new HashMap<>();
        searchResult.put("searchResult", serviceData);

        return ResponseEntity.ok(searchResult);
    }

    //--------------------------------------통계 관련
    //월간 분석
    //월간 정기점검 건수
    @GetMapping("/admin/regular/statistics/monthcount")
    public ResponseEntity<?> getRegularCountByMonth(@RequestParam("yearmonth") String yearMonth){
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));

        int statisticsCount = regularStatisticsRepository.regularCountByMonth(year, month);
        return ResponseEntity.ok(statisticsCount);
    }

    // (엑셀용) 월간 점검영역별 - 직접입력한 기타 내용 모두 출력
    @GetMapping("/admin/regular/statistics/partandmonthforexcel")
    public ResponseEntity<?> getRegularListByPartAndMonthForExcel(@RequestParam("yearmonth") String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));

        List<Object[]> statisticsList = regularStatisticsRepository.regularListByPartAndMonthForExcel(year, month);

        return ResponseEntity.ok(statisticsList);
    }


    // (엑셀용) 월간 점검종류별 위험성평가 건수
    @GetMapping("/admin/regular/statistics/nameandmonthforexcel")
    public ResponseEntity<?> getRegularListByNameAndMonthForExcel(@RequestParam("yearmonth") String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));

        List<Map<String, Map<String,Long>>> statisticsList = regularInspectionService.regularCntListByNameAndYearForExcel(year, month);

        return ResponseEntity.ok(statisticsList);
    }

    
    //(pieChart) 월간 정기점검 위험성평가분석 데이터 값(전체)
    @GetMapping("/admin/regular/statistics/checkvaluecount")
    public ResponseEntity<?> getRegularCntByCheckAndMonth(@RequestParam("yearmonth") String yearMonth){
        String[] yearMonthParts = yearMonth.split("-");
        int year = Integer.parseInt(yearMonthParts[0]);
        int month = Integer.parseInt(yearMonthParts[1]);
        System.out.println(year);
        System.out.println(month);

        List<Map<String, Object>> statisticsCount  = regularInspectionService.RegularCntByCheckAndMonth(year, month);
        return ResponseEntity.ok(statisticsCount);
    }

    //(pieChart) 월간 정기점검 위험성평가분석 데이터 값(sort한 값)
    @GetMapping("/admin/regular/statistics/checkvaluecountsort")
    public ResponseEntity<?> getRegularCntByCheckAndMonthSort(@RequestParam("yearmonth") String yearMonth,
                                                              @RequestParam("regularinsname") String regularInsName){
        String[] yearMonthParts = yearMonth.split("-");
        int year = Integer.parseInt(yearMonthParts[0]);
        int month = Integer.parseInt(yearMonthParts[1]);
        System.out.println(year);
        System.out.println(month);

        List<Map<String, Object>> statisticsCount  = regularInspectionService.RegularCntByCheckAndMonthSort(year, month, regularInsName);
        return ResponseEntity.ok(statisticsCount);
    }

    //(pieChart) 월간 정기점검 위험성평가분석 드롭다운
    @GetMapping("/admin/regular/statistics/namedropdown")
    public ResponseEntity<?> viewDropdownRegularNameList(){
        Map<String, Object> responseData = new HashMap<>();
        List<String> regularNameList = regularInspectionService.RegularNameList();

        responseData.put("regularNameList", regularNameList);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //(radarChart) 월간 점검영역별 그래프 데이터값 전송
    @GetMapping("/admin/regular/statistics/partandmonth")
    public ResponseEntity<?> getRegularListByPartAndMonth(@RequestParam("yearmonth") String yearMonth) {
        int year = Integer.parseInt(yearMonth.substring(0, 4));
        int month = Integer.parseInt(yearMonth.substring(5, 7));

        List<Map<String, Object>> statisticsList = regularInspectionService.regularDetailListByPartAndMonth(year, month);
        return ResponseEntity.ok(statisticsList);
    }


    //연간 분석
    //연간 정기점검 건수
    @GetMapping("/admin/regular/statistics/yearcount")
    public ResponseEntity<?> getRegularCountByYear(@RequestParam("year") int year){
        int statisticsCount = regularStatisticsRepository.regularCountByYear(year);
        return ResponseEntity.ok(statisticsCount);
    }
    //(barChart) 연간 점검종류별 점검 건수
    @GetMapping("/admin/regular/statistics/nameandyear")
    public ResponseEntity<List<Map<String, Object>>> getRegularCountByNameAndYear(@RequestParam("year") int year) {
        List<Map<String, Object>> statisticsList = regularInspectionService.regularCountListByNameAndYear(year);
        return ResponseEntity.ok(statisticsList);
    }

}
