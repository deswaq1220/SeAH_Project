package SeAH.savg.controller;

import SeAH.savg.dto.EmailFormDTO;
import SeAH.savg.dto.MasterDataDepartmentDTO;
import SeAH.savg.dto.MasterDataFormDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.MasterData;
import SeAH.savg.repository.MasterDataDepartmentRepository;

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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MasterDataController {


    private final MasterDataDepartmentRepository masterDataDepartmentRepository;
    private final MasterDataService masterDataService;
    private final EmailService emailService;


    //------------------------------------------설비 관리  //나중에 viewDropdownPart로 메소드명 변경 필요
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
    public ResponseEntity<String> masterDelete(@PathVariable String masterdataId){
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




    //------------------------------------------부서 관리

    //부서 목록 조회(화면에 전체 뿌리기)
    @GetMapping("/master/department/view")
    public ResponseEntity<?> viewDepartList(){
        return new ResponseEntity<>(masterDataService.DepartList(), HttpStatus.OK);
    }

    //부서별 목록 드롭다운으로 뿌리기
    @GetMapping("/master/departdropdown")
    public ResponseEntity<?> viewDropdownDepart(){

        Map<String, Object> responseData = new HashMap<>();
        List<String> departmentList = masterDataDepartmentRepository.dropDownListByDepart();

        responseData.put("departmentList", departmentList);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    //부서별 목록 조회(카테고리용)
    @GetMapping("/master/department/sort")
    public ResponseEntity<List<MasterDataDepartmentDTO>> sortByDepart(@RequestParam("departmentid") Long departmentId){
        return new ResponseEntity<>(masterDataService.sortDepartList(departmentId), HttpStatus.OK);
    }

    //부서 등록
    @PostMapping("/master/department/reg")
    public ResponseEntity<?> regDepart(@RequestBody MasterDataDepartmentDTO departmentDTO){

        return new ResponseEntity<>(masterDataService.saveDepart(departmentDTO), HttpStatus.CREATED);
    }

    //부서 삭제
    @PostMapping("/master/department/del/{departmentId}")
    public ResponseEntity<?> delDepart(@PathVariable Long departmentId){

        masterDataService.delDepart(departmentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //부서 수정
    @PostMapping("/master/department/update/{departId}")
    public ResponseEntity<?> updateDepart(@PathVariable Long departId,
                                          @RequestBody MasterDataDepartmentDTO departmentDTO){

        masterDataService.updateDepart(departmentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
