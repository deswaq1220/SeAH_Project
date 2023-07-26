package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.EduRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    //(관리자) 월별교육통계 조회하기
    public List<EduStatisticsDTO> showMonthEduStatis(edustate eduCategory, int month){
        List<Object[]> results = eduRepository.selectMonthEduStatis(eduCategory, month);

        List<EduStatisticsDTO> eduStatisticsDTOList = new ArrayList<>();

        for(Object[] result : results){
            EduStatisticsDTO eduStatisticsDTO = new EduStatisticsDTO();
            eduStatisticsDTO.setEduCategory((edustate)result[0]);
            eduStatisticsDTO.setEduStartTime((LocalDateTime) result[1]);
            eduStatisticsDTO.setEduSumTime((LocalDateTime) result[2]);
            eduStatisticsDTO.setAttenName((String) result[3]);
            eduStatisticsDTO.setAttenEmployeeNumber((String) result[4]);
            eduStatisticsDTO.setAttenDepartment((String) result[5]);

            eduStatisticsDTOList.add(eduStatisticsDTO);
        }
        return eduStatisticsDTOList;
    }

}
