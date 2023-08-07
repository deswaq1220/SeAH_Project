package SeAH.savg.controller;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.repository.SpecialInspectionRepository;
import SeAH.savg.service.SpecialInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class SpecialController {
    private final SpecialInspectionService specialInspectionService;
    private final SpecialInspectionRepository specialInspectionRepository;

    // 등록화면 조회 : 프론트연결용
//    @GetMapping(value = "/special/new/{masterdataFacility}")
//    public ResponseEntity<?> speForm(@RequestBody Map<String, Object> requestData){
//        return new ResponseEntity<>(specialInspectionService.findEmail(requestData), HttpStatus.OK);
//    }

    // 수시점검 저장화면(조회) : 테스트용
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

    // 전체 현황 조회
    @GetMapping("/special/list")
    public ResponseEntity<?> speList(){
        return new ResponseEntity<>(specialInspectionService.findSpeAll(), HttpStatus.OK);
    }

    // 설비별 현황 조회
    @GetMapping("/special/list/{masterdataPart}/{masterdataFacility}")
    public ResponseEntity<?> speListOfFac(@PathVariable String masterdataFacility){
        return new ResponseEntity<>(specialInspectionService.findListOfFac(masterdataFacility), HttpStatus.OK);
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

    // 월별 수시점검현황
//    @GetMapping("/special/{masterdataPart}/{masterdataFacility}")
    @GetMapping("/userselectInspection")
    public ResponseEntity<?> speDaily(){
        return new ResponseEntity<>(specialInspectionService.findSpeMonthly(), HttpStatus.OK);
    }




}
