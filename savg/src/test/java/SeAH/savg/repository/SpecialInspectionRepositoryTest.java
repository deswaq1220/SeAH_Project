/*
package SeAH.savg.repository;

import SeAH.savg.dto.SpeInsStatisticsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SpecialInspectionRepositoryTest {

    @Autowired
    private SpecialInspectionRepository specialInspectionRepository;

    @Test
    public void testSpecialListByPart(){
       List<Object[]> result = specialInspectionRepository.specialListByPart(7);
       for(Object[] res : result) {
           String part = (String) res[0];
           Long count = (Long) res[1];
           System.out.println("파트: "+part+"  "+"점건건수: " +count);
       }
    }


}
*/