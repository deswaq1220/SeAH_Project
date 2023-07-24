package SeAH.savg.controller;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.service.SpecialInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class SpecialController {
    private final SpecialInspectionService specialInspectionService;

//    // 조회 : 프론트연결용
//    @GetMapping(value = "/special")
//    public ResponseEntity<?> speForm(@RequestBody Map<String, Object> requestData){
//        return new ResponseEntity<>(specialInspectionService.findEmail(requestData), HttpStatus.OK);
//    }

    // 조회 : 테스트용
    @GetMapping("/special")
    public ResponseEntity<?> speForm(){
        return new ResponseEntity<>(specialInspectionService.findEmail(), HttpStatus.OK);
    }

    // 저장
    @PostMapping("/special")
    public ResponseEntity<?> speNew(@RequestBody SpeInsFormDTO speInsFormDTO){

        return new ResponseEntity<>(specialInspectionService.speCreate(speInsFormDTO), HttpStatus.CREATED);
    }

}
