package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import SeAH.savg.entity.EduFile;
import SeAH.savg.repository.EduFileRepository;
import SeAH.savg.repository.EduRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

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

    //상세조회
    public EduDTO getEduById(String eduId) {
        Edu edu = eduRepository.findByEduId(eduId);

        List<EduFile> eduFileList = eduFileRepository.findByEdu(edu);
        EduDTO eduDTO = new EduDTO(edu);
        log.info("시간 체크 " +eduDTO.getEduUpdateTime());

        if (!eduFileList.isEmpty()) {
            List<String> imageUrls = new ArrayList<>(); // 이미지 url

            for (EduFile eduFile : eduFileList) {
                String imagePath = eduFile.getEduFileUrl();
                imageUrls.add(imagePath);
            }

            eduDTO.setEduimgurls(imageUrls);
        }
        eduDTO.setEduFileList(eduFileList);

        return eduDTO;
    }

    //교육수정
    public void update(EduDTO eduDTO, String eduCategory)throws Exception{
//        eduDTO.setEduRegTime(eduDTO.getEduRegTime());
        Edu edu = eduDTO.toEntity();

//        edu.setEduRegTime(LocalDateTime.now());

        List<EduFile> eduFileList = eduFileRepository.findByEdu(edu);
        if(eduDTO.getEduFileIds()!=null){
//        if(!eduDTO.getEduFileIds().isEmpty()){
            for(Long eduFileId: eduDTO.getEduFileIds()){
                for(EduFile eduFile : eduFileList){
                    if(eduFile.getEduFileId() == eduFileId){
                        eduFileRepository.delete(eduFile);
                    }
                }
            }
        }

        if(eduDTO.getFiles()==null){
//        if(eduDTO.getFiles().isEmpty()){
            eduRepository.save(edu);
        }else {
            eduFileService.uploadFile(eduDTO, eduCategory);
            eduRepository.save(edu);
        }

    }



    //교육 삭제
    public void deleteEdu(String  eduId){
        Edu edu = eduRepository.findByEduId(eduId);
        List<EduFile> eduFileList = eduFileRepository.findByEdu(edu);
        for (EduFile eduFile : eduFileList) {
            eduFileRepository.delete(eduFile);
            eduFileService.deleteFile(eduFile.getEduFileName());
        }
        eduRepository.deleteById(eduId);
    }


    //(관리자)
    // 1. 월별교육통계 조회하기 - 교육 실행시간 조회
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



    // 2. 월별교육통계 조회하기 - 카테고리에 따른 교육참가자 조회 or 카테고리/부서에 따른 참가자 조회
    public HashMap<String, List<Object>> showMonthEduTraineeStatics(edustate eduCategory, int year, int month, String department, String name) {
        HashMap<String, List<Object>> showMonthEduTraineeStatics = new HashMap<>();

        List<Object[]> results;

        if ((department == null || department.isEmpty()) && (name == null || name.isEmpty()) && (eduCategory == null || eduCategory.isEmpty())) {
            results = eduRepository.selectMonthStatic(year,month); // 날짜
        } else if ((department == null || department.isEmpty()) && (eduCategory == null || eduCategory.isEmpty())){
            results = eduRepository.selectMonthAndName(year,month, name); //날짜 + 이름
        } else if ((name == null || name.isEmpty()) && (eduCategory == null || eduCategory.isEmpty())) {
            results=eduRepository.selectMonthAndDepartment(year, month, department); // 날짜 + 부서
        } else if ((department == null || department.isEmpty()) && (name == null || name.isEmpty())) {
            results = eduRepository.selectMonthEduTraineeStatis(eduCategory, year,month); // 월 + 카테고리
        } else if((eduCategory == null || eduCategory.isEmpty())){
            results = eduRepository.selectDepartName(year, month, department, name); // 부서+이름+날짜
        } else if ((department == null || department.isEmpty())) {
            results = eduRepository.eduTraineeStatisByName(eduCategory, year,month, name); // 카테고리 + 이름
        } else if (name == null || name.isEmpty()) {
            results = eduRepository.eduTraineeStatisByDepart(eduCategory, year,month, department); // 카테고리 + 부서
        } else {
            results = eduRepository.eduTraineeStatisByNameAndDepart(eduCategory, year,month, name, department); // 카테고리+부서+이름
        }

        int sumCategories = 0;
        List<EduStatisticsDTO> eduStatisticsDTOList = new ArrayList<>();
        List<Integer> attenNameSumEduTimeList = new ArrayList<>(Collections.nCopies(5, 0));

        for (Object[] result : results) {
            EduStatisticsDTO eduStatisticsDTO = new EduStatisticsDTO();
            eduStatisticsDTO.setEduTitle((String) result[0]);
            eduStatisticsDTO.setEduStartTime((LocalDateTime) result[1]);
            eduStatisticsDTO.setEduSumTime((String) result[2]);
            eduStatisticsDTO.setAttenName((String) result[3]);
            eduStatisticsDTO.setAttenEmployeeNumber((String) result[4]);
            eduStatisticsDTO.setAttenDepartment((String) result[5]);


            int eduSumTime = Integer.parseInt(eduStatisticsDTO.getEduSumTime());

            sumCategories += eduSumTime;


            if("[크루미팅]".equals(result[0])){
                attenNameSumEduTimeList.set(1, attenNameSumEduTimeList.get(1)+Integer.parseInt((String)result[2]));
            }else if("[DM미팅]".equals(result[0])){
                attenNameSumEduTimeList.set(2, attenNameSumEduTimeList.get(2)+Integer.parseInt((String)result[2]));
            }else if("[관리감독]".equals(result[0])){
                attenNameSumEduTimeList.set(3, attenNameSumEduTimeList.get(3)+Integer.parseInt((String)result[2]));
            }else{
                attenNameSumEduTimeList.set(4, attenNameSumEduTimeList.get(4)+Integer.parseInt((String)result[2]));
            }
            eduStatisticsDTOList.add(eduStatisticsDTO);
        }
        attenNameSumEduTimeList.set(0, sumCategories);

        // attenNameSumEduTimeList에는 [전체, 크루, dm, 관리감독, 기타] 카테고리별 합산 시간이 들어있음
        System.out.println("전체: " + attenNameSumEduTimeList.get(0));
        System.out.println("크루: " + attenNameSumEduTimeList.get(1));
        System.out.println("dm: " + attenNameSumEduTimeList.get(2));
        System.out.println("관리감독: " + attenNameSumEduTimeList.get(3));
        System.out.println("기타: " + attenNameSumEduTimeList.get(4));
        showMonthEduTraineeStatics.put("eduStatisticsDTO",new ArrayList<>(eduStatisticsDTOList));
        showMonthEduTraineeStatics.put("attenNameSumEduTimeList", new ArrayList<>(attenNameSumEduTimeList));

        return showMonthEduTraineeStatics;
    }


    // 4. 월별교육통계 조회하기 - 각 카테고리별 교육 목록 조회
    public List<Object[]> showMonthlyCategory(int year, int month, edustate eduCategory){
        return eduRepository.runMonthEduListByCategory(year, month, eduCategory);

    }


}
