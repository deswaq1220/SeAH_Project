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
import lombok.extern.log4j.Log4j2;
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

import java.util.ArrayList;
import java.util.List;


@Controller
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2

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
    public ResponseEntity<?> handleEduReg(@ModelAttribute EduDTO eduDTO) {
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

            if (eduDTO.getFiles() == null || eduDTO.getFiles().isEmpty()) {
                log.info("파일 없음");
            } else {
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

    //교육일지 리스트 조회
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






}
