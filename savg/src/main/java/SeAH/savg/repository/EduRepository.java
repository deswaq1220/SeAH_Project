package SeAH.savg.repository;

import SeAH.savg.constant.edustate;
import SeAH.savg.dto.EduDTO;
import SeAH.savg.dto.EduStatisticsDTO;
import SeAH.savg.entity.Edu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduRepository extends JpaRepository<Edu, Long> {

    List<Edu> findAll(Sort sort);


   ////관리자
    //1. 월별 교육통계 조회하기
    @Query("SELECT e.eduCategory, e.eduTitle, e.eduStartTime, e.eduSumTime, a.attenName, a.attenEmployeeNumber, a.attenDepartment " +
            "FROM Edu e " +
            "JOIN Attendance a ON e.eduId = a.eduId " +
            "WHERE e.eduCategory = :eduCategory " +
            "AND MONTH(e.eduStartTime) = :month")
    List<Object[]> selectMonthEduStatis(@Param("eduCategory") edustate eduCategory , @Param("month") int month);
    

    Edu findByEduId(Long eduId);

}
