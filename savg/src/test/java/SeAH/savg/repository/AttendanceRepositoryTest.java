package SeAH.savg.repository;

import SeAH.savg.entity.Attendance;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    public void testShowAttendList(){
        List<Attendance> result = attendanceRepository.findAllByEduId("1");

        for(Attendance res :result){
            System.out.println(res);
        }
    }

}
