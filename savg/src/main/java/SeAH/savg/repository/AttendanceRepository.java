package SeAH.savg.repository;

import SeAH.savg.dto.AttendanceDTO;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Attendance;
import SeAH.savg.entity.Edu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

   ////관리자
   //1. 출석리스트 조회
   @Query("SELECT a FROM Attendance a WHERE a.eduId = :eduId ORDER BY a.attenDepartment ASC, a.attenName ASC")
   List<Attendance> findAllByEduId(@Param("eduId") String eduId);


}