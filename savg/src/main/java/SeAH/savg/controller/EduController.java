package SeAH.savg.controller;


import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduFormDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.repository.EduRepository;
import SeAH.savg.service.EduFileService;
import SeAH.savg.service.EduService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


@Controller
@RestController

public class EduController {

    private final EduRepository eduRepository;
    private final EduService eduService;
    private final EduFileService eduFileService;
    private final EduFileRepository eduFileRepository;


    public EduController(EduRepository eduRepository, EduService eduService, EduFileService eduFileService,
                         EduFileRepository eduFileRepository) {
        this.eduRepository = eduRepository;
        this.eduService = eduService;
        this.eduFileService = eduFileService;
        this.eduFileRepository=eduFileRepository;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

    }

    //안전교육 일지 등록
    @PostMapping("/edureg")
    public ResponseEntity<?> handleEduReg(@ModelAttribute  EduDTO eduDTO, @RequestParam("file") MultipartFile file) {
        try {
            // 데이터 처리 로직: 유효성 검사
            if (eduDTO.getEduContent() == null || eduDTO.getEduContent().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Content is required.");
            }
            if (eduDTO.getEduInstructor() == null || eduDTO.getEduInstructor().isEmpty()) {
                return ResponseEntity.badRequest().body("Edu Instructor is required.");
            }

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




    @GetMapping("/edureg")
    public String showEduForm(Model model) {
        model.addAttribute("eduFormDTO", new EduFormDTO());
        return "redirect:/edudetails";
    }


    //(관리자) 월별 교육 통계 조회하기

    //@PostMapping()


}
