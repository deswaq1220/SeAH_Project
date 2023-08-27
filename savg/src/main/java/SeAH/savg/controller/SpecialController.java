package SeAH.savg.controller;

import SeAH.savg.dto.SpeInsFormDTO;
import SeAH.savg.service.SpecialInspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SpecialController {
    private final SpecialInspectionService specialInspectionService;

//    // 등록화면 조회 : 프론트연결용
//    @GetMapping(value = "/special")
//    public ResponseEntity<?> speForm(@RequestBody Map<String, Object> requestData){
//        return new ResponseEntity<>(specialInspectionService.findEmail(requestData), HttpStatus.OK);
//    }

    // 조회 : 테스트용
    @GetMapping("/special/new/{masterdataId}")
    public ResponseEntity<?> speForm(){
        return new ResponseEntity<>(specialInspectionService.findEmail(), HttpStatus.OK);
    }

    // 등록화면 저장
    @PostMapping("/special/new/{masterdataId}")
    public ResponseEntity<?> speNew(@ModelAttribute SpeInsFormDTO speInsFormDTO) throws Exception {
        return new ResponseEntity<>(specialInspectionService.speCreate(speInsFormDTO), HttpStatus.CREATED);
    }

    // 전체 현황 조회



    //





}
