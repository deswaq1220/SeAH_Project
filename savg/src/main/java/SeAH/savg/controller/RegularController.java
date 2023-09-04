package SeAH.savg.controller;

import SeAH.savg.dto.RegularDTO;
import SeAH.savg.dto.RegularDetailDTO;
import SeAH.savg.dto.RegularFileDTO;
import SeAH.savg.entity.Email;
import SeAH.savg.entity.RegularInspection;
import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.SpeicalFileRepository;
import SeAH.savg.service.RegularInspectionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://172.20.10.5:3000")
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://172.20.20.252:3000")  // 세아
public class RegularController {

    private final RegularInspectionRepository regularInspectionRepository;
    private final RegularInspectionService regularInspectionService;


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
        Map<String, Object> responseData = new HashMap<>();
        List<Email> emailList = regularInspectionService.selectEmail();
        responseData.put("emailList", emailList);

        return ResponseEntity.ok(responseData);
    }

    //정기점검 등록
    @PostMapping(value = "/user/regular/new")
        public ResponseEntity<String> createRegularInspection(@ModelAttribute RegularDTO regularDTO)throws Exception {





        regularInspectionService.createRegular(regularDTO);
        return ResponseEntity.ok("정기점검 등록 성공");
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
    public ResponseEntity<RegularDetailDTO> viewRegularDetail(@PathVariable String regularId){
        RegularDetailDTO regularDetailDTO = regularInspectionService.getRegularById(regularId);
        if (regularDetailDTO == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(regularDetailDTO);
    }




}
