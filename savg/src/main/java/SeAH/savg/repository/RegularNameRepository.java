package SeAH.savg.repository;

import SeAH.savg.entity.RegularName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularNameRepository extends JpaRepository<RegularName, String> {


    //연간
        //연간(1~12월) 총 수시점검 건수(정기점검 테이블 생기면 가동)
/*        @Query("SELECT COUNT(r) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.speDate) = :year ")
        int regularCountByYear(@Param("year") int year);*/

        //정기점검 1~12월 종류별 점검건수 통계
        @Query("SELECT MONTH(s.speDate), s.speDanger, COALESCE(COUNT(s), 0) " +
                "FROM SpecialInspection s " +
                "WHERE YEAR(s.speDate) = :year " +
                "GROUP BY s.speDanger, MONTH(s.speDate)")
        List<Object[]> specialDetailListByDanger(@Param("year") int year);

}