package SeAH.savg.service;

import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.EduRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EduService {

    private final EduRepository eduRepository;

    public EduService(EduRepository eduRepository) {
        this.eduRepository = eduRepository;
    }

    //교육등록
/*    public Long createEdu(EduDTO eduDTO) throws Exception{
        Edu edu = eduDTO.createEdu();
        eduRepository.save(edu);

        return edu.getEduId();
    }*/

    //

}
