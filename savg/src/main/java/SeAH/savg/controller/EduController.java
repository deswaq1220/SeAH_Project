package SeAH.savg.controller;


import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.repository.EduRepository;
import SeAH.savg.service.EduFileService;
import SeAH.savg.service.EduService;
import SeAH.savg.service.MakeIdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;




@Controller
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://172.20.10.5:3000")
//@CrossOrigin(origins = "http://127.0.0.1:3000")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2

public class EduController {

    private final EduRepository eduRepository;
    private final EduService eduService;
    private final EduFileService eduFileService;
    private final EduFileRepository eduFileRepository;
    private final MakeIdService makeIdService;


    public EduController(EduRepository eduRepository, EduService eduService, EduFileService eduFileService,
                         EduFileRepository eduFileRepository, MakeIdService makeIdService) {
        this.eduRepository = eduRepository;
        this.eduService = eduService;
        this.eduFileService = eduFileService;
        this.eduFileRepository=eduFileRepository;
        this.makeIdService = makeIdService;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }

    // 교육, 수시, 정기 카테고리 저장할 함수
    private String categoryType = "E";

    //안전교육 일지 등록
    @PostMapping("/edureg")
    public ResponseEntity<?> handleEduReg(EduDTO eduDTO) {
        log.info("여기 되나?");
        System.out.println(eduDTO);
        try {
            // 데이터 처리 로직: 유효성 검사
            if (eduDTO.getEduContent() == null || eduDTO.getEduContent().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Content is required.");
            }
            if (eduDTO.getEduInstructor() == null || eduDTO.getEduInstructor().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Instructor is required.");
            }
//        log.info(eduDTO.getFiles());
            // 데이터 처리 로직: 데이터 저장
            // DTO에 아이디 세팅
            eduDTO.setEduId(makeIdService.makeId(categoryType));
            Edu edu = eduDTO.toEntity();

            if (eduDTO.getFiles() == null || eduDTO.getFiles().isEmpty()) {
                log.info("파일 없음");
            } else {
                log.info("파일 있음: ");
                // 데이터 처리 로직: 파일 업로드 및 파일 정보 저장
                List<EduFile> uploadedFiles = eduFileService.uploadFile(eduDTO.getFiles());
                for (EduFile eduFile : uploadedFiles) {
                    eduFile.setEdu(edu); // EduFile 엔티티와 연결
                }
            }

            eduRepository.save(edu); // Edu 엔티티 저장

            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    //교육일지 목록 조회
    @GetMapping("/edumain")
    public ResponseEntity<List<EduDTO>> getEduList() {
        List<Edu> eduList = eduService.getEdu(); // Edu 엔티티 리스트를 가져옴

        List<EduDTO> eduDTOList = new ArrayList<>();
        for (Edu edu : eduList) {
            eduDTOList.add(new EduDTO(edu));
        }

        return ResponseEntity.ok(eduDTOList);
    }



    //상세 페이지
    @GetMapping("/edudetails/{eduId}")
    public ResponseEntity<EduDTO> getEduDetail(@PathVariable Long eduId) {
        Edu edu = eduService.getEduById(eduId);
        if (edu == null) {
            return ResponseEntity.notFound().build();
        }

        EduDTO eduDTO = new EduDTO(edu);
        return ResponseEntity.ok(eduDTO);
    }



    //(관리자) 월별 교육 통계 조회하기(★프론트 연결되는지 확인요청(경원님))
    @PostMapping("/edustatistics/getmonth")
    public ResponseEntity<?> viewMonthEduStatis(@RequestBody Map<String, Object> requestData){
        edustate eduCategory = (edustate)requestData.get("eduCategory");
        int month = (int)requestData.get("month");
        eduService.showMonthEduTraineeStatis(eduCategory, month);

        return ResponseEntity.ok().build();
    }

    /*(html 임시 확인용)
    @PostMapping("/edustatistics/getmonth")
    public String viewMonthEduStatis(@RequestParam("eduCategory") edustate eduCategory
                                                    ,@RequestParam("month") int month
                                                    ,Model model){
        List<EduStatisticsDTO> results = eduService.showMonthEduStatis(eduCategory, month);
        System.out.println(results);
        model.addAttribute("results", results);
        return "/page/edustatisresult";
    }

    @GetMapping("/getmonth")
    public String showGetMonthForm() {
        return "page/getmonth";
    }
    */

}


