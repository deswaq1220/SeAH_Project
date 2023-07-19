package SeAH.savg.service;

import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.EduRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@Transactional
public class EduService {

    private final EduRepository eduRepository;
    private final EduFileService eduFileService;

    public EduService(EduRepository eduRepository, EduFileService eduFileService) {
        this.eduRepository = eduRepository;
        this.eduFileService = eduFileService;
    }

}
