package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
public class EduServiceTest {

    @Autowired
    private EduService eduService;
/*
    @Test
    public void testShowEduTimeStatis(){
        Long result = eduService.showMonthEduTimeStatis(edustate2.CREW, 7);
        System.out.println(result);
    }
*/

/*
    @Test
    public void testSumShowEduTime(){
        List<Integer> result = eduService.showMonthEduTimeStatis(7);
        System.out.println(result);
    }
*/

    @Test
    public void testRunEduList(){
        Page<Object[]> result = eduService.getRunEduListByMonth(7, 0,10);
        System.out.println(result);
    }
}
