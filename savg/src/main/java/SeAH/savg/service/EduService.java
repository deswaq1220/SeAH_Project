package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduFileDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.repository.EduRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
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
    private final EduFileRepository eduFileRepository;


    //교육 목록
    public List<Edu> getEduByYearAndMonth(int year, int month) {
        return eduRepository.findAllByYearAndMonth(year, month);
    }



    //(관리자)
    // 1. 월별교육통계 조회하기 - 카테고리에 따른 교육참가자 조회 or 카테고리/부서에 따른 참가자 조회
    public List<EduStatisticsDTO> showMonthEduTraineeStatics(edustate eduCategory, int year, int month, String department, String name) {

        List<Object[]> results;

        if ((department == null || department.isEmpty()) && (name == null || name.isEmpty()) && (eduCategory == null || eduCategory.isEmpty())) {
            results = eduRepository.selectMonthStatic(year,month); // 월에 따른 데이터 조회
        } else if ((department == null || department.isEmpty()) && (eduCategory == null || eduCategory.isEmpty())){
            results = eduRepository.selectMonthAndName(year,month, name);
        } else if ((department == null || department.isEmpty()) && (name == null || name.isEmpty())) {
            results = eduRepository.selectMonthEduTraineeStatis(eduCategory, year,month); // 카테고리에 따른 교육 참가자 조회
        } else if ((name != null && !name.isEmpty()) && (department == null || department.isEmpty())) {
            results = eduRepository.eduTraineeStatisByName(eduCategory, year,month, name); // 카테고리, 이름에 따른 참가자 조회
        } else if ((department != null && !department.isEmpty()) && (name == null || name.isEmpty())) {
            results = eduRepository.eduTraineeStatisByDepart(eduCategory, year,month, department); // 카테고리, 부서에 따른 참가자 조회
        } else {
            results = eduRepository.eduTraineeStatisByNameAndDepart(eduCategory, year,month, name, department); // 카테고리, 부서, 이름에 따른 참가자 조회
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
    public List<Integer> showMonthEduTimeStatis(int year, int month){

        List<Object[]> results = eduRepository.selectSumMonthEduTime(year, month); //교육 시행 시간 리스트
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
    public Page<Object[]> getRunEduListByMonth(int year ,int month, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return eduRepository.selectRunMonthEduList(year ,month, pageable);
    }

    //3-2. 월별교육실행목록 조회하기(dropDown 사용 등)
/*    public Page<Object[]> getRunEduListByMonthAndCategory(int year, int month, int pageNumber, int pageSize, String eduCategory) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        try {
            return eduRepository.runMonthEduListByCategory(month, edustate.valueOf(eduCategory), pageable);
        } catch (IllegalArgumentException e) {
            return eduRepository.selectRunMonthEduList(year,month, pageable);
        }
    }*/

    public List<Object[]> showMonthlyCategory(int year, int month, edustate eduCategory){
        return eduRepository.runMonthEduListByCategory(year, month, eduCategory);

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
    public EduDTO getEduById(String eduId) {
        Edu edu = eduRepository.findByEduId(eduId);

        List<EduFile> eduFileList = eduFileRepository.findByEdu(edu);
        List<EduFileDTO> eduFileDTOList = new ArrayList<>();

        EduDTO eduDTO = new EduDTO(edu);
        List<String> filesName = new ArrayList<>();
        for(EduFile file : eduFileList){
            EduFileDTO eduFileDTO = EduFileDTO.of(file);
            eduFileDTOList.add(eduFileDTO);
         filesName.add(eduFileDTO.getEduFileOriName());

        }
        eduDTO.setEduFiles(filesName);



        return eduDTO;
    }

    public void update(EduDTO eduDTO)throws Exception{
        Edu edu = eduDTO.toEntity();
        List<EduFile> eduFileList = eduFileRepository.findByEdu(edu);

        for(EduFile eduFile : eduFileList){
            eduFileRepository.delete(eduFile);
        }

        eduFileService.uploadFile(eduDTO);

        eduRepository.save(edu);
    }

}
