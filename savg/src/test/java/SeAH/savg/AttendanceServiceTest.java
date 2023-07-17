package SeAH.savg;

import SeAH.savg.repository.AttendanceRepository;
import SeAH.savg.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@RequiredArgsConstructor
public class AttendanceServiceTest {

    @Autowired
    private AttendanceService attendanceService;

    @Test
    @Rollback(false)
    public void testAttend(){
    attendanceService.attendEdu("소형압연팀", "김홍돌", "12345");
    
    System.out.println("얏호");
    }
}
