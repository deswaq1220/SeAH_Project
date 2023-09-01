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
public class RegularInspectionService {

    private final RegularInspectionRepository regularInspectionRepository;


    //(lineChart) 1~12월까지 연간 수시점검 건수
    public List<Map<String, Object>> setRegularCountList(int year){
        List<Object[]> statisticsList = regularInspectionRepository.regularCountList(year);

        List<Map<String, Object>> dataPoints = new ArrayList<>();

        for(Object[] row : statisticsList){

            Integer month = (Integer) row[0];
            Long count = (Long) row[1];

            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("month", month);
            dataPoint.put("y", count);

            dataPoints.add(dataPoint);
        }

        Map<String, Object> finalDate = new HashMap<>();
        finalDate.put("id", "정기점검");
        finalDate.put("data", dataPoints);

        List<Map<String, Object>> resultList = new ArrayList<>();
        resultList.add(finalDate);


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
