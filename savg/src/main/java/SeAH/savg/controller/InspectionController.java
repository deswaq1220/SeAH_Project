package SeAH.savg.controller;

import SeAH.savg.service.InspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//기타 점검관련 사항 관리하는 컨트롤러
public class InspectionController {

    private final InspectionService inspectionService;

    @GetMapping("/admin/statistics/inspectioncount")
    public ResponseEntity<List<Map<String, Object>>> getInspectionCountList(@RequestParam("year") int year){
        List<Map<String, Object>> specialStatisticsList = inspectionService.setCountList(year);

        return new ResponseEntity<>(specialStatisticsList, HttpStatus.OK);
    }

}
