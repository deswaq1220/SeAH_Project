package SeAH.savg.service;

import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.RegularStatisticsRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class InspectionService {

    private final RegularInspectionRepository regularInspectionRepository;
    private final RegularStatisticsRepository regularStatisticsRepository;
    private final SpecialInspectionRepository specialInspectionRepository;


    //(lineChart) 1~12월까지 연간 수시점검 건수
    public List<Map<String, Object>> setCountList(int year){
        List<Object[]> regularCountList = regularStatisticsRepository.regularCountList(year);
        List<Object[]> specialCountList = specialInspectionRepository.specialCountList(year);

        Map<Integer, Long> regularCountListMap = new HashMap<>();
        for (Object[] regularData : regularCountList) {
            Integer month = (Integer) regularData[0];
            Long count = (Long) regularData[1];
            regularCountListMap.put(month, count);
            System.out.println("체크" + regularCountListMap);
        }

        Map<Integer, Long> specialCountListMap = new HashMap<>();
        for (Object[] specialData : specialCountList) {
            Integer month = (Integer) specialData[0];
            Long count = (Long) specialData[1];
            specialCountListMap.put(month, count);
            System.out.println("체크" + specialCountListMap);
        }

        List<Map<String, Object>> resultList = new ArrayList<>();
        Set<Integer> allMonths = new HashSet<>(); //Set은 중복된 값을 허용하지 않는 자료구조
        allMonths.addAll(regularCountListMap.keySet());
        allMonths.addAll(specialCountListMap.keySet());

        for (Integer month : allMonths) {
            Long regularCount = regularCountListMap.getOrDefault(month, 0L);
            Long specialCount = specialCountListMap.getOrDefault(month, 0L);

            Map<String, Object> finalData = new HashMap<>();
            finalData.put("month", month);
            finalData.put("정기점검", regularCount);
            finalData.put("수시점검", specialCount);

            resultList.add(finalData);
        }

        return resultList;
    }






    //1~12월까지 월간 정기점검 건수(barChart용)
/*      public List<Map<String,Object>> regularDetailListByName(int year){
        List<Object[]> specialList = specialInspectionRepository.specialDetailListByDanger(year);

        Map<Integer, Map<String, Object>> dataByMonth = new HashMap<>();


        for(Object[] row : specialList){

            Integer month = (Integer) row[0];
            String dangerKind = (String) row[1];
            Long count = (Long) row[2];

            if(!dataByMonth.containsKey(month)){
                Map<String, Object> dataPoint = new HashMap<>();
                dataPoint.put("month", month);
                dataByMonth.put(month, dataPoint);
            }
            Map<String, Object> dataPoint = dataByMonth.get(month);
            dataPoint.put(dangerKind, count);
        }
        List<Map<String, Object>> finalData = new ArrayList<>(dataByMonth.values());

        return finalData;
    }*/
}
