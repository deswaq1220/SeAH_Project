package SeAH.savg.controller;


import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@Controller
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://172.20.20.252:3000") //세아

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


    //교육일지 목록 조회
    @GetMapping("/edumain")
    public ResponseEntity<List<EduDTO>> getEduList(@RequestParam int year, @RequestParam int month) {
        List<Edu> eduList = eduService.getEduByYearAndMonth(year, month);

        int i = 0;

        List<EduDTO> eduDTOList = new ArrayList<>();

        for (Edu edu : eduList) {
            eduDTOList.add(eduService.getEduById(edu.getEduId()));
            log.info("테스트"+eduDTOList.get(i).getEduFiles());
        }

        return ResponseEntity.ok(eduDTOList);
    }


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


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //교육삭제
    @DeleteMapping("/edudetails/{eduId}")
    public ResponseEntity<?> deleteEduAndFiles(@PathVariable String eduId) {
        try {
            eduService.deleteEdu(eduId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 대시보드 (통계)
    //1. 월별 교육실행시간 통계 조회하기 (3000/edustatics)
    @GetMapping("/edustatistics/getmonthlyruntime")
    public ResponseEntity <List<Integer>> viewMonthlyEduTimeStatis(@RequestParam(required = false) int year,@RequestParam(required = false) int month){
        List<Integer> result = eduService.showMonthEduTimeStatis(year, month);
        return ResponseEntity.ok(result);
    }


    // 1-1. 월별 교육 리스트 통계 조회하기 (3000/edustatics)
    @GetMapping("/edustatistics/getmonthlyedulist")
    public ResponseEntity<List<Object[]>> viewMonthlyCategory(@RequestParam int year,
                                                              @RequestParam int month,
                                                              @RequestParam(required = false) edustate eduCategory) {
        List<Object[]> statisticsList;

        if (eduCategory != null) {
            statisticsList = eduService.showMonthlyCategory(year, month, eduCategory);
        } else if(eduService.getEduByYearAndMonth(year, month).isEmpty()) {
            return ResponseEntity.badRequest().build();

        }else {
            List<Edu> eduList = eduService.getEduByYearAndMonth(year, month);
            System.out.println(eduList.get(0).getEduId());
            // Object 배열로 변환해서 반환
            statisticsList = new ArrayList<>();
            for (Edu edu : eduList) {
                Object[] eduData = {edu.getEduCategory(), edu.getEduTitle(), edu.getEduStartTime(), edu.getEduSumTime()};
                statisticsList.add(eduData);
            }
        }

        return ResponseEntity.ok(statisticsList);
    }


    // 2. 월별 교육참석자 조회하기(카테고리별/ 카테고리+부서별/ 카테고리+성명) (3000/edustatics/atten)
    @GetMapping("/edustatistics/atten")
    public ResponseEntity<HashMap<String,List<Object>>> viewMonthEduStatis(@RequestParam(name = "eduCategory", required = false) edustate eduCategory,
                                                                     @RequestParam(name = "year") int year,
                                                                     @RequestParam(name = "month") int month,
                                                                     @RequestParam(name = "department", defaultValue = "") String department,
                                                                     @RequestParam(name = "name") String name) {
        System.out.println("이름: " + name);
        System.out.println("카테고리: " + eduCategory);
        if(department != null ){
            if(department.equals("부서")){
                department = null;
            };
        };

        HashMap<String,List<Object>> statisticsList = eduService.showMonthEduTraineeStatics(eduCategory, year, month, department, name);
        return ResponseEntity.ok(statisticsList);

    }


}


