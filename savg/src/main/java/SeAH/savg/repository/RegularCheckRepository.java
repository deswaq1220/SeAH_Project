package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspection;
import SeAH.savg.entity.RegularInspectionCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularCheckRepository extends JpaRepository<RegularInspectionCheck, String> {


    //상세조회
    @Query("SELECT i, b FROM RegularInspection i " +
            "LEFT JOIN RegularInspectionBad b ON i.regularId = b.regularInspectionCheck.regularInspection.regularId " +
            "WHERE i.regularId = :regularId")
    List<Object[]> getRegularInspectionDetail(String regularId);
}