package SeAH.savg.controller;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.repository.SpecialInspectionRepository;
import SeAH.savg.service.SpecialInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SpecialController {
    private final SpecialInspectionService specialInspectionService;
    private final SpecialInspectionRepository specialInspectionRepository;

    // ------------ 사용자 ------------------------------------------

    // 월별 수시점검현황: 수시점검 qr찍고 첫번째 페이지
    @GetMapping("/special/{masterdataPart}/{masterdataFacility}")
    public ResponseEntity<?> speMonthly(){
        return new ResponseEntity<>(specialInspectionService.findSpeMonthly(), HttpStatus.OK);
    }


    // 설비별 현황 조회: 수시점검 qr찍고 두번째 페이지
    @GetMapping("/special/list/{masterdataPart}/{masterdataFacility}")
    public ResponseEntity<?> speListOfFac(@PathVariable String masterdataFacility){
        return new ResponseEntity<>(specialInspectionService.findListOfFac(masterdataFacility), HttpStatus.OK);
    }


    // 수시점검 저장화면(조회)
    @GetMapping("/special/new/{masterdataPart}/{masterdataFacility}")
    public ResponseEntity<?> speForm(@PathVariable String masterdataPart){    // @PathVariable String masterdataFacility로 파라미터 받아서 해도될것같은디
        return new ResponseEntity<>(specialInspectionService.findCreateMenu(masterdataPart), HttpStatus.OK);
    }

    // 수시점검 저장
    @PostMapping("/special/new/{masterdataPart}/{masterdataFacility}")
    public ResponseEntity<?> speNew(@PathVariable String masterdataPart,
                                    @PathVariable String masterdataFacility,
                                    @RequestBody Map<String, Object> requestData) throws Exception {
        return new ResponseEntity<>(specialInspectionService.speCreate(masterdataPart, masterdataFacility, requestData), HttpStatus.CREATED);
    }


    // 상세조회
    @GetMapping("/special/detail/{speId}")
    public ResponseEntity<?> speDetail(@PathVariable String speId){
        return new ResponseEntity<>(specialInspectionService.findSpeDetail(speId), HttpStatus.OK);
    }



    // 완료처리(update)
    @PostMapping("/special/detail/{speId}")
    public ResponseEntity<?> speComplete(@PathVariable String speId, SpeInsFormDTO speInsFormDTO) throws Exception {
        return new ResponseEntity<>(specialInspectionService.speUpdate(speId, speInsFormDTO), HttpStatus.CREATED);
    }




/*    @GetMapping("/special/statistics")
    public ModelAndView viewSpecialStatistics(){

        //int today = LocalDate.now(ZoneId.of("Asia/Seoul")).getDayOfMonth();;
        //List<Object[]> result = specialInspectionRepository.specialListByPart(today);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(result);
        modelAndView.setViewName("specialListSatistics");
        return modelAndView;
    }*/






// --------------------------- 관리자

    //월별 수시점검 현황 통계 조회 - 카테고리별
   @GetMapping("/special/statistics")
    public ResponseEntity<List<Object[]>> getSpecialListStatistics(@RequestParam("month") int month){
        List<Object[]> statisticsList = specialInspectionRepository.specialListByPart(month);

        return ResponseEntity.ok(statisticsList);
    }



    // 관리자 : 전체 현황 조회
    @GetMapping("/special/list")
    public ResponseEntity<?> speList(){
        return new ResponseEntity<>(specialInspectionService.findSpeAll(), HttpStatus.OK);
    }



}
