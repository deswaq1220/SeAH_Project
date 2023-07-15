package SeAH.savg.controller;


import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduFormDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.EduRepository;
import SeAH.savg.service.EduService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EduController {

    private final EduRepository eduRepository;
    private final EduService eduService;

    public EduController(EduRepository eduRepository, EduService eduService) {
        this.eduRepository = eduRepository;
        this.eduService = eduService;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }


    @PostMapping("/edureg")
    public ResponseEntity<?> handleEduReg(@RequestBody EduDTO eduDTO) {
        try {
            // 데이터 처리 로직: 유효성 검사
            if (eduDTO.getEduContent() == null || eduDTO.getEduContent().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Content is required.");
            }
            if (eduDTO.getEduInstructor() == null || eduDTO.getEduInstructor().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Instructor is required.");
            }
//            eduDTO.setEduStartTime(eduDTO.getEduStartTime());
//            eduDTO.setEduEndTime(eduDTO.getEduSumTime().getEduEndTime());


//                System.out.println("안녕하세요 테스트" + eduDTO.getEduSumTime().getEduEndTime() );




            // 데이터 처리 로직: 데이터 저장
            Edu edu = eduDTO.toEntity();
            eduRepository.save(edu); // 데이터베이스에 저장
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }






//    //교육 등록
//    @PostMapping("/edureg")
//    public String EduWrite(@Valid EduDTO eduDTO, Model model) throws Exception {
//
//        Long eduId = eduService.createEdu(eduDTO);
//        model.addAttribute("eduFormDTO", new EduFormDTO());
//        return "EduDTO";
//    }

    @GetMapping("/edureg")
    public String showEduForm(Model model) {
        model.addAttribute("eduFormDTO", new EduFormDTO());
        return "redirect:/edudetails";
    }
}
