package SeAH.savg.repository;

import SeAH.savg.entity.Edu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EduRepository extends JpaRepository<Edu, String> {

    List<Edu> findAll(Sort sort);

   ////관리자
    //1. 월별 집체교육 조회
    //@Query("SELECT eduCatergory, eduStartTime, eduSumTime FROM Edu join Attendance ")
    //List<Edu> WHERE : =AND
}
