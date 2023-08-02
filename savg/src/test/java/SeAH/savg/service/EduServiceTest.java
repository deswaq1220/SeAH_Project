package SeAH.savg.service;

import SeAH.savg.constant.edustate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class EduServiceTest {

    @Autowired
    private EduService eduService;

    @Test
    public void testShowEduTimeStatis(){
        Long result = eduService.showMonthEduTimeStatis(edustate.CREW, 7);
        System.out.println(result);
    }

}
