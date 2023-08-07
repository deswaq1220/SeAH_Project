package SeAH.savg.controller;


import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.dto.EduSumStatisticsDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


//@Controller
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://172.20.10.5:3000")
//@CrossOrigin(origins = "http://127.0.0.1:3000")
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
            eduRepository.save(edu); // Edu 엔티티 저장


            if (eduDTO.getFiles() == null || eduDTO.getFiles().isEmpty()) {
                log.info("파일 없음");
            } else {
                log.info("파일 있음: ");
                // 데이터 처리 로직: 파일 업로드 및 파일 정보 저장
                List<EduFile> uploadedFiles = eduFileService.uploadFile(eduDTO);
                for (EduFile eduFile : uploadedFiles) {
                    eduFile.setEdu(edu); // EduFile 엔티티와 연결
                }
            }


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
    public ResponseEntity<EduDTO> getEduDetail(@PathVariable String eduId) {
        EduDTO eduDTO = eduService.getEduById(eduId);

        if (eduDTO == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(eduDTO);
    }

    // 교육일지 수정
    @PostMapping("/edudetails/{eduId}")
    public ResponseEntity<?> handleEduModify(@PathVariable String eduId, EduDTO eduDTO) {
        try {

            eduDTO.setEduId(eduId);
            eduService.update(eduDTO);

//            if (edu == null) {
//                return ResponseEntity.notFound().build();
//            }

            // 기존 파일 삭제 및 수정된 파일 업로드
//            if (eduDTO.getFiles() != null && !eduDTO.getFiles().isEmpty()) {
//                List<EduFile> existingFiles = eduFileRepository.findByEdu(edu);
//                if (existingFiles != null && !existingFiles.isEmpty()) {
//                    for (EduFile existingFile : existingFiles) {
//                        String fileName = existingFile.getEduFileName();
//                        eduFileService.deleteFile(fileName);
//                    }
//                }
//                List<EduFile> uploadedFiles = eduFileService.uploadFile(eduDTO);
//                for (EduFile eduFile : uploadedFiles) {
//                    eduFile.setEdu(edu);
//                }
//            }
//            edu.setEduCategory(eduDTO.getEduCategory());
//            edu.setEduTitle(eduDTO.getEduTitle());
//            edu.setEduInstructor(eduDTO.getEduInstructor());
//            edu.setEduPlace(eduDTO.getEduPlace());
//            edu.setEduStartTime(eduDTO.getEduStartTime());
//            edu.setEduSumTime(eduDTO.getEduSumTime());
//            edu.setEduTarget(eduDTO.getEduTarget());
//            edu.setEduContent(eduDTO.getEduContent());
//            edu.setEduWriter(eduDTO.getEduWriter());



            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //(관리자) 월별 교육참석자 조회하기(카테고리별/ 카테고리+부서별/ 카테고리+성명 구분가능)
    //department, name 값을 필요 시 프론트에서 넘겨줘야함
    @GetMapping("/edustatistics/getmonth") //나중에 주소를 /edu/statistics/getmonth 등으로 바꿔야 할듯
    public ResponseEntity<List<EduStatisticsDTO>> viewMonthEduStatis(@RequestParam(required = false) edustate eduCategory,
                                                                     @RequestParam(required = false) int month,
                                                                     @RequestParam(required = false) String department,
                                                                     @RequestParam(required = false) String name) {
        System.out.println("department" + department);
        if(department != null ){
            if(department.equals("부서")){
                department = null;
            };
            System.out.println("테스트 :" + department);
        };

        List<EduStatisticsDTO> statisticsList = eduService.showMonthEduTraineeStatics(eduCategory, month, department, name);
        return ResponseEntity.ok(statisticsList);

    }


    //(관리자) 월별 교육실행시간 통계 조회하기(★프론트 연결 필요)
    @PostMapping("/edustatistics/getmonthlyruntime")
    public List<Integer> viewMonthlyEduTimeStatis(@RequestBody Map<String, Integer> requestData){

        int month = requestData.get("month");
        List<Integer> result = eduService.showMonthEduTimeStatis(month);
        System.out.println(result);
        return result;
    }

    //(관리자) 월별 교육실행 시간 통계 조회하기 (html 임시 확인용)
    @GetMapping("/getmonthlyruntime")
    public ModelAndView showGetMonthForm() {
        ModelAndView modelAndView = new ModelAndView("page/getmonthlyruntime");

        return modelAndView;
    }


    //(관리자) 월별 교육실행리스트 통계 조회하기
    //ex)   http://localhost:8081/edustatistics/getmonthlyedulist/7?pageNumber=0&eduCategory=MANAGE
    @GetMapping("/edustatistics/getmonthlyedulist/{month}")
    public Page<Object[]> getEduListByMonth(@PathVariable int month,
                                            @RequestParam(defaultValue = "0") int pageNumber,
                                            @RequestParam(defaultValue = "10") int pageSize,
                                            @RequestParam(required = false) String eduCategory) {
        if (eduCategory != null && !eduCategory.isEmpty()) {
            return eduService.getRunEduListByMonthAndCategory(month, pageNumber, pageSize, eduCategory);
        } else {
            return eduService.getRunEduListByMonth(month, pageNumber, pageSize);
        }
    }
}


