package SeAH.savg.repository;

import SeAH.savg.dto.AttendanceDTO;
import SeAH.savg.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

   ////관리자
    //1. 출석리스트 조회
    List<Attendance> findAllByEduId(Long eduId);

}