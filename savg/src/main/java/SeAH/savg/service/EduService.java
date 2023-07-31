package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.EduRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static SeAH.savg.constant.edustate.*;


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


    //(관리자)
    // 1. 월별교육통계 조회하기 - 카테고리에 따른 교육참가자 조회
    public List<EduStatisticsDTO> showMonthEduTraineeStatis(edustate eduCategory, int month){
        List<Object[]> results = eduRepository.selectMonthEduTraineeStatis(eduCategory, month);

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


    // 2. 월별교육통계 조회하기 - 교육 실행시간 조회
    // sumMonthlyEduTimeList = [CREW시간총계, MANAGE시간총계, DM시간총계, ETC시간총계, 전체시간총계]
    public List<Integer> showMonthEduTimeStatis(int month){

        List<Object[]> results = eduRepository.selectSumMonthEduTime(month); //교육 시행 시간 리스트
        List<Integer> sumMonthlyEduTimeList = new ArrayList<>(Collections.nCopies(5, 0));

        for(Object[] result : results){
            edustate eduCategory = (edustate)result[0];
            String time = (String)result[1];
            Integer timeValue = Integer.valueOf(time);

            if(eduCategory == CREW){
                sumMonthlyEduTimeList.set(1, sumMonthlyEduTimeList.get(1)+timeValue);
            }else if(eduCategory == MANAGE){
                sumMonthlyEduTimeList.set(2, sumMonthlyEduTimeList.get(2)+timeValue);
            }else if(eduCategory == DM){
                sumMonthlyEduTimeList.set(3, sumMonthlyEduTimeList.get(3)+timeValue);
            }else if(eduCategory == ETC){
                sumMonthlyEduTimeList.set(4, sumMonthlyEduTimeList.get(4)+timeValue);
            }

        }
        //전체시간 합산하기
        sumMonthlyEduTimeList.set(0, sumMonthlyEduTimeList.get(1)+sumMonthlyEduTimeList.get(2)
                                    +sumMonthlyEduTimeList.get(3)+sumMonthlyEduTimeList.get(4));
        return sumMonthlyEduTimeList;
    }

    //Paging, Sort 메소드
    //Sort eduTimeDepartmentSort =  Sort.by("department").descending();
/*
    // 3. 월별교육통계 조회하기 - 각 카테고리별 교육 목록 조회
    public Long showMonthEduTimeStatis2(edustate eduCategory, int month){

        List<Object[]> results = eduRepository.selectMonthEduTimeList(eduCategory, month); //교육 시행 시간 리스트
        List<EduStatisticsDTO> MonthlyEduTimeList = new ArrayList<>();
        Long eduSumTime = 0L;

        for(Object[] result : results){
            String time = (String)result[0];

            try {
                Long timeValue = Long.parseLong(time);
                eduSumTime += timeValue;
            } catch (NumberFormatException e) {
                System.out.println("월별 교육 실행시간 조회: 합산할 수 없습니다(Long타입 아님)");
            }

        }
        return eduSumTime;
    }
*/

    //상세조회
    public Edu getEduById(Long eduId) {
        return eduRepository.findByEduId(eduId);
    }



}
