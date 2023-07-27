package SeAH.savg.service;

import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@AllArgsConstructor
@Log4j2
public class EduService {

    private final EduRepository eduRepository;
    private final EduFileService eduFileService;

    //교육 목록
    public List<Edu> getEdu() {
        return eduRepository.findAll();
    }


    //상세조회
    public Edu getEduById(Long eduId) {
        return eduRepository.findByEduId(eduId);
    }
}
