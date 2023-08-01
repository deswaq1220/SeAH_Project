package SeAH.savg.repository;

import SeAH.savg.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

   ////관리자
    //1. 출석리스트 조회
    List<Attendance> findAllByEduId(String eduId);

}