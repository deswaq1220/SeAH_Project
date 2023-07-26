package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
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

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
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


    //(관리자) 월별교육통계 조회하기
    public List<EduStatisticsDTO> showMonthEduStatis(edustate eduCategory, int month){
        List<Object[]> results = eduRepository.selectMonthEduStatis(eduCategory, month);

        List<EduStatisticsDTO> eduStatisticsDTOList = new ArrayList<>();

        for(Object[] result : results){
            EduStatisticsDTO eduStatisticsDTO = new EduStatisticsDTO();
            eduStatisticsDTO.setEduCategory((edustate)result[0]);
            eduStatisticsDTO.setEduStartTime((LocalDateTime) result[1]);
            eduStatisticsDTO.setEduSumTime((String) result[2]);
            eduStatisticsDTO.setAttenName((String) result[3]);
            eduStatisticsDTO.setAttenEmployeeNumber((String) result[4]);
            eduStatisticsDTO.setAttenDepartment((String) result[5]);

            eduStatisticsDTOList.add(eduStatisticsDTO);
        }
        return eduStatisticsDTOList;
    }


    //상세조회
    public Edu getEduById(Long eduId) {
        return eduRepository.findByEduId(eduId);
    }

}
