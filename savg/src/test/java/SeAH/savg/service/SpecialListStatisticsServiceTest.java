package SeAH.savg.service;



import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
@RequiredArgsConstructor
public class SpecialListStatisticsServiceTest {

    @Autowired
    private SpecialInspectionService specialInspectionService;

/*    @Test
    public void testSpecialDetailListByDanger(){

        if (specialInspectionService == null) {
            System.out.println("specialInspectionService is null!");
        } else {
            List<Map<String, Object>> result = specialInspectionService.specialDetailListByDanger(2023);
            System.out.println(result);
        }
    }*/


/*    @Test
    public void testSpecialCountList(){
        List<Map<String, Object>> result = specialInspectionService.setSpecialCountList(2023);
        System.out.println(result);

    }*/
}

