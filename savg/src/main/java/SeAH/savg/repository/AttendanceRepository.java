package SeAH.savg.repository;

import SeAH.savg.dto.AttendanceDTO;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.entity.Attendance;
import SeAH.savg.entity.Edu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, String> {

   ////관리자
    //1. 출석리스트 조회
    List<Attendance> findAllByEduId(String eduId);

    //참석자별 교육 조회
    @Query("SELECT a.edu FROM Attendance a WHERE a.attenName = :name")
    List<EduDTO> getEduListByAttenName(String name);

}