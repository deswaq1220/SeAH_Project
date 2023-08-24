package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularInspectionRepository extends JpaRepository<RegularInspection, String> {


    //연간
        //(LineChart) 1~12월 총 수시점검 건수(정기점검 테이블 생기면 가동)
        @Query("SELECT COUNT(r) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.regularDate) = :year ")
        int regularCountByYear(@Param("year") int year);

        //(LineChart) 전체 월별(1~12월) 정기점검 건수
        @Query("SELECT MONTH(r.regularDate), COALESCE(COUNT(r), 0) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.regularDate) = :year " +
                "GROUP BY MONTH(r.regularDate)")
        List<Object[]> regularCountList(@Param("year") int year);

/*        //1~12월 종류별 정기점검 점검건수 통계
        @Query("SELECT MONTH(r.speDate), r.regularInsName, COUNT(r) " +
                "FROM RegularInspection r " +
                "WHERE YEAR(r.speDate) = :year " +
                "GROUP BY r.regularInsName, MONTH(r.speDate)")
        List<Object[]> regularDetailListByName(@Param("year") int year);*/

}