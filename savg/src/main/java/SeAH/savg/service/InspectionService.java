package SeAH.savg.service;

import SeAH.savg.repository.RegularInspectionRepository;
import SeAH.savg.repository.SpecialInspectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class InspectionService {

    private final RegularInspectionRepository regularInspectionRepository;
    private final SpecialInspectionRepository specialInspectionRepository;


    //(lineChart) 1~12월까지 연간 수시점검 건수
    public List<Map<String, Object>> setCountList(int year){
        List<Object[]> regularCountList = regularInspectionRepository.regularCountList(year);
        List<Object[]> specialCountList = specialInspectionRepository.specialCountList(year);



       /* List<Map<Integer, Long>> regularDataPoints = new ArrayList<>();*/
        Map<Integer, Long> regularCountListMap = new HashMap<>();
        for(Object[] regularData : regularCountList){

            Integer month = (Integer) regularData[0];
            Long count = (Long) regularData[1];
            regularCountListMap.put(month, count);
        }


        List<Map<String, Object>> resultList = new ArrayList<>();
        for(Object[] specialData : specialCountList){

            Integer month = (Integer)specialData[0];
            Long regularCount = regularCountListMap.getOrDefault(month, 0L);//정기점검 건수
            Long specialCount = (Long)specialData[1];  //수시점검 건수

            Map<String, Object> finalDate = new HashMap<>();
            finalDate.put("month", month);
            finalDate.put("정기점검", regularCount);
            finalDate.put("수시점검", specialCount);

            resultList.add(finalDate);
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
