package SeAH.savg.repository;

import SeAH.savg.entity.RegularInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegularStatisticsRepository extends JpaRepository<RegularInspection, String> {

    //월간
    //정기점건 건수
    @Query("SELECT COUNT(r) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regularDate) = :year AND MONTH(r.regularDate) = :month")
    int regularCountByMonth(@Param("year") int year, @Param("month") int month);



    //월별 점검카테고리에 따른 양호,불량,NA 건수 표시(구분(양호,불량,NA),값)
    @Query("SELECT c.regularCheck, COUNT(c) " +
            "FROM RegularInspectionCheck c " +
            "LEFT JOIN RegularInspection i " +
            "ON YEAR(i.regularDate) = :year AND MONTH(i.regularDate) = :month " +
            "AND i.regularInsName = :regularInsName " +
            "GROUP BY c.regularCheck")
    List<Object[]>regularCntByCheckAndMonthSortName(@Param("year") int year, @Param("month") int month, @Param("regularInsName") String regularInsName);

    //연간
    //(LineChart) 1~12월 총 수시점검 건수
    @Query("SELECT COUNT(r) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regularDate) = :year")
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