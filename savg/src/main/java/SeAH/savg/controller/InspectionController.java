package SeAH.savg.controller;

import SeAH.savg.service.InspectionService;
import SeAH.savg.service.RegularInspectionService;
import SeAH.savg.service.SpecialInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://172.20.20.252:3000")   // 세아
//@CrossOrigin(origins = "http://127.0.0.1:3000")

//기타 점검관련 사항 관리하는 컨트롤러
public class InspectionController {

    private final SpecialInspectionService specialInspectionService;
    private final RegularInspectionService regularInspectionService;
    private final InspectionService inspectionService;



    /* 통계관련:   1~12월 정기점검, 수시점검 건수
     * 형태: 수시점검 + 점검건수 리스트
     * ex : 수시점검: 1월, 7건 2월 1건...
     *      정기점검: 1월, 2건 2월 1건...
     */
/*    @GetMapping("/statistics/inspectioncount")
    public ResponseEntity<List<Map<String, Object>>> getInspectionCountList(@RequestParam("year") int year){
        List<Map<String, Object>> specialStatisticsList = specialInspectionService.setSpecialCountList(year);
        List<Map<String, Object>> regularStatisticsList = regularInspectionService.setRegularCountList(year);
        specialStatisticsList.addAll(regularStatisticsList);

        return new ResponseEntity<>(specialStatisticsList, HttpStatus.OK);
    }*/

    @GetMapping("/statistics/inspectioncount")
    public ResponseEntity<List<Map<String, Object>>> getInspectionCountList(@RequestParam("year") int year){
        List<Map<String, Object>> specialStatisticsList = inspectionService.setCountList(year);

        return new ResponseEntity<>(specialStatisticsList, HttpStatus.OK);
    }

}
