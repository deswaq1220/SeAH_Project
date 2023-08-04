package SeAH.savg.service;



import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RequiredArgsConstructor
public class SpecialListStatisticsServiceTest {

    @Autowired
    private SpecialInspectionService specialInspectionService;

    @Test
    public void testSpecialDetailListByDangerAndMonth(){

        List<Map<String, Object>> result = specialInspectionService.SpecialDetailListByDangerAndYear(2023);

            System.out.println(result);
    }
}

