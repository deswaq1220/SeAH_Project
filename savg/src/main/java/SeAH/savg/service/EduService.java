package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.repository.EduRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    /*
    // 1. 월별교육통계 조회하기 - 카테고리에 따른 교육참가자 조회
    public List<EduStatisticsDTO> showMonthEduTraineeStatics(edustate eduCategory, int month){
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
  */
    // 1-1. 월별교육통계 조회하기 - 카테고리에 따른 교육참가자 조회 or 카테고리/부서에 따른 참가자 조회
    public List<EduStatisticsDTO> showMonthEduTraineeStatics(edustate eduCategory, int month, String department, String name) {

        List<Object[]> results;

        if ((department == null || department.isEmpty()) && (name == null || name.isEmpty()) && (eduCategory == null || eduCategory.isEmpty())) {
            results = eduRepository.selectMonth(month); // 월에 따른 데이터 조회
        } else if ((department == null || department.isEmpty()) && (name == null || name.isEmpty())) {
            results = eduRepository.selectMonthEduTraineeStatis(eduCategory, month); // 카테고리에 따른 교육 참가자 조회
        } else if ((name != null && !name.isEmpty()) && (department == null || department.isEmpty())) {
            results = eduRepository.eduTraineeStatisByName(eduCategory, month, name); // 카테고리, 이름에 따른 참가자 조회
        } else if ((department != null && !department.isEmpty()) && (name == null || name.isEmpty())) {
            results = eduRepository.eduTraineeStatisByDepart(eduCategory, month, department); // 카테고리, 부서에 따른 참가자 조회
        } else {
            results = eduRepository.eduTraineeStatisByNameAndDepart(eduCategory, month, name, department); // 카테고리, 부서, 이름에 따른 참가자 조회
        }

        List<EduStatisticsDTO> eduStatisticsDTOList = new ArrayList<>();
        for (Object[] result : results) {
            EduStatisticsDTO eduStatisticsDTO = new EduStatisticsDTO();
            eduStatisticsDTO.setEduTitle((String) result[0]);
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

    //3-1. 월별교육실행목록 조회하기(Paging, Sort 사용)
    public Page<Object[]> getRunEduListByMonth(int month, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return eduRepository.selectRunMonthEduList(month, pageable);
    }

    //3-2. 월별교육실행목록 조회하기(dropDown 사용 등)
    public Page<Object[]> getRunEduListByMonthAndCategory(int month, int pageNumber, int pageSize, String eduCategory) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        try {
            return eduRepository.runMonthEduListByCategory(month, edustate.valueOf(eduCategory), pageable);
        } catch (IllegalArgumentException e) {
            // category 값을 edustate로 변환하는데 예외가 발생한 경우
            // 이 부분에 대한 예외 처리를 수행하거나, 기본 처리 방법을 정의할 수 있습니다.
            // 예를 들면, 이 경우에는 전체 리스트를 반환하도록 기본적인 처리를 하거나, 에러 메시지를 반환하는 등의 방법이 있을 수 있습니다.
            // 여기서는 기본적으로 전체 리스트를 반환하도록 처리합니다.
            return eduRepository.selectRunMonthEduList(month, pageable);
        }
    }

/*
    // 4. 월별교육통계 조회하기 - 각 카테고리별 교육 목록 조회
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
    public Edu getEduById(String eduId) {
        return eduRepository.findByEduId(eduId);
    }



}
