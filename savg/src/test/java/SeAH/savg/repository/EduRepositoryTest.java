package SeAH.savg.repository;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.dto.EduSumStatisticsDTO;
import SeAH.savg.entity.Edu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EduRepositoryTest {

    @Autowired
    private EduRepository eduRepository;

    @Test
    public void testShowEduStatis() {
        List<Object[]> results = eduRepository.selectMonthEduTraineeStatis(edustate.CREW, 7);
        List<EduStatisticsDTO> eduStatisticsDTOList = new ArrayList<>();

        for (Object[] result : results) {
        EduStatisticsDTO eduStatisticsDTO = new EduStatisticsDTO();
        eduStatisticsDTO.setEduCategory((edustate) result[0]);
        eduStatisticsDTO.setEduTitle((String) result[1]);
        eduStatisticsDTO.setEduStartTime((LocalDateTime) result[2]);
        eduStatisticsDTO.setEduSumTime((String) result[3]);
        eduStatisticsDTO.setAttenName((String) result[4]);
        eduStatisticsDTO.setAttenEmployeeNumber((String) result[5]);
        eduStatisticsDTO.setAttenDepartment((String) result[6]);

        eduStatisticsDTOList.add(eduStatisticsDTO);
        }
        if(!eduStatisticsDTOList.isEmpty()) {
            System.out.println(eduStatisticsDTOList);
        } else {
            System.out.println("조횟값이 없습니다");
        }
    }


    @Test
    public void testShhowEduTimeStatis(){
        List<Object[]> results = eduRepository.selectMonthEduTimeList(edustate.CREW, 7); //실행한 교육의 리스트를 들고옴
        List<EduStatisticsDTO> timeList = new ArrayList<>();

        for(Object[] result : results){
            EduStatisticsDTO eduStatisticsDTO = new EduStatisticsDTO();
            eduStatisticsDTO.setEduSumTime((String) result[0]);

            timeList.add(eduStatisticsDTO);
        }
        System.out.println(timeList);

    }

    @Test
    public void testShhowEduTimeStatis2(){
        List<Object[]> results = eduRepository.selectSumMonthEduTime(7); //실행한 교육의 리스트를 들고옴
        List<EduSumStatisticsDTO> timeList = new ArrayList<>();

        for(Object[] result : results){
            EduSumStatisticsDTO eduSumStatisticsDTO = new EduSumStatisticsDTO();
            eduSumStatisticsDTO.setEduCategory((edustate) result[0]);
            eduSumStatisticsDTO.setEduSumTime((String) result[1]);

            timeList.add(eduSumStatisticsDTO);
        }
        System.out.println(timeList);

    }
}