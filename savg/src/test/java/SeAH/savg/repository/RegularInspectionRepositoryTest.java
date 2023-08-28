
package SeAH.savg.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RegularInspectionRepositoryTest {

    @Autowired
    private RegularStatisticsRepository regularStatisticsRepository;

    @Test
    public void testSpecialListByPart(){
       List<Object[]> result = regularStatisticsRepository.regularCntByCheckAndMonthSortName();
/*       for(Object[] res : result) {
           String regularCheck = (String) res[0];
           Long count = (Long) res[1];
           System.out.println("구분: "+regularCheck+"  "+"건수: " +count);
       }*/
    }





}

