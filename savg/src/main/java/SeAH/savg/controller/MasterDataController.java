package SeAH.savg.controller;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.service.EmailService;
import SeAH.savg.service.MasterDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@Controller
@RequiredArgsConstructor
public class MasterDataController {
    private final MasterDataService masterDataService;
    private final EmailService emailService;


    // 기준정보 조회 + 이메일목록 조회
    @GetMapping("/master")
    public ResponseEntity<?> masterAndEmailForm(){
        Map<String, Object> responseData = new HashMap<>();

        List<MasterData> masterDataList = masterDataService.findAll();
        List<Email> emailList = emailService.findAll();

        responseData.put("masterData", masterDataList);
        responseData.put("email", emailList);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    // 설비등록
    @PostMapping( "/master")
    public ResponseEntity<?> masterNew(@RequestBody MasterDataFormDTO masterDataFormDTO){
        return new ResponseEntity<>(masterDataService.saveMaster(masterDataFormDTO), HttpStatus.CREATED);
    }

    // 설비삭제
    @DeleteMapping("/master/delete/{masterdataId}")
    public ResponseEntity<String> masterDelete(@PathVariable Integer masterdataId){
        masterDataService.deleteMaster(masterdataId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 이메일등록
    @PostMapping("/master/email")
    public ResponseEntity<?> emailNew(@RequestBody EmailFormDTO emailFormDTO){
        return new ResponseEntity<>(emailService.saveEmail(emailFormDTO), HttpStatus.CREATED);
    }

    // 이메일수정
    @PutMapping("/master/email/update/{emailId}")
    public ResponseEntity<?> emailUpdate(@RequestBody EmailFormDTO emailFormDTO, @PathVariable Long emailId){
        return new ResponseEntity<>(emailService.updateEmail(emailFormDTO, emailId), HttpStatus.CREATED);
    }



}
