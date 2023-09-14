package SeAH.savg.repository;

import SeAH.savg.constant.RegStatus;
import SeAH.savg.entity.RegularInspectionCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularCheckRepository extends JpaRepository<RegularInspectionCheck, Long> {


    //상세조회
    @Query("SELECT i.regularId FROM RegularInspection i " +
            "LEFT JOIN RegularInspectionBad b ON i.regularId = b.regularInspectionCheck.regularInspection.regularId " +
            "WHERE i.regularId = :regularId")
    List<Object[]> getRegularInspectionDetail(@Param("regularId") String regularId);

    @Query("SELECT COUNT(ri) FROM RegularInspectionCheck ri " +
            "WHERE DATE_FORMAT(ri.regularInspection.regTime, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m') " +
            "AND ri.regularCheck =? 1")
    int countByRegularCheck(RegStatus regStatus);

}
