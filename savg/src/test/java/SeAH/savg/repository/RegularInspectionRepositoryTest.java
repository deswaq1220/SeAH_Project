
package SeAH.savg.repository;

import SeAH.savg.constant.RegStatus;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@RequiredArgsConstructor
public class RegularInspectionRepositoryTest {

    @Autowired
    private RegularStatisticsRepository regularStatisticsRepository;

    @Test
    public void testSpecialListByPart(){
      List<Object[]> result = regularStatisticsRepository.regularCntByCheckAndMonthSortName(2023, 8, "중대재해예방 일반점검");

       for(Object[] res : result) {
           RegStatus regularCheck = (RegStatus) res[0];
           Long count = (Long) res[1];
           System.out.println("구분: "+regularCheck+"  "+"건수: " +count);
       }
    }

/*
    @Test
    public void test() {

       int result =  regularStatisticsRepository.regularCountByYear(2023);
        System.out.println(result);
    }
*/


}

