package SeAH.savg.repository;

import SeAH.savg.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    //void registerEdu(){
    //}
}
