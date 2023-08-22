package SeAH.savg.controller;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.MasterDataRepository;
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
//@CrossOrigin(origins = "http://localhost:3000")
//@CrossOrigin(origins = "http://172.20.20.252:3000")   // 세아
public class MasterDataController {

    private final MasterDataRepository masterDataRepositor;
    private final MasterDataService masterDataService;
    private final EmailService emailService;


    // 기준정보- 영역 드롭다운으로 불러오기
    @GetMapping("/master/partdropdown")
    public ResponseEntity<?> viewPartList(){
        Map<String, Object> responseData = new HashMap<>();
        List<String> specialPartList = masterDataService.findSpecialPartList();

        responseData.put("specialPartList", specialPartList);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    //기준정보 카테고리(영역) 선택 조회
    @GetMapping("/master/sortbypart")
    public ResponseEntity<?> sortByPart(@RequestParam("part") String part){

        String plusPart = "["+part+"]";
        List<String[]> responseData = masterDataService.sortByPart(plusPart);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // 기준정보- 설비리스트 조회
    @GetMapping("/master/viewfacilities")
    public ResponseEntity<?> viewFacilityList(){
        Map<String, Object> responseData = new HashMap<>();
        List<MasterData> facilityList = masterDataService.findAllFacilities();
        responseData.put("facilityList", facilityList);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // 기준정보- 이메일목록 조회
    @GetMapping("/master/viewemail")
    public ResponseEntity<?> viewEmailList(){
        Map<String, Object> responseData = new HashMap<>();
        List<Email> emailList = masterDataService.findAllEmail();
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
