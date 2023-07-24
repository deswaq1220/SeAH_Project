package SeAH.savg.repository;

import SeAH.savg.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    //(관리자) 출석리스트 조회
    List<Attendance> findAllByEduId(Long eduId);

}