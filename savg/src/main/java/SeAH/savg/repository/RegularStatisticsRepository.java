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
            "WHERE YEAR(r.regTime) = :year AND MONTH(r.regTime) = :month")
    int regularCountByMonth(@Param("year") int year, @Param("month") int month);


    //(radar) 월간 영역별 점검 건수(0건인 것들도 함께 나옴)
    // 1. 전체 리스트 뽑기
    @Query("SELECT p.partMenu, COUNT(r) " +
            "FROM RegularPart p " +
            "LEFT JOIN RegularInspection r ON r.regularPart = p.partMenu AND YEAR(r.regTime) = :year AND MONTH(r.regTime) = :month " +
            "GROUP BY p.partMenu")
    List<Object[]> regularListByPartAndMonth(@Param("year") int year, @Param("month") int month);

    //2.영역별 발생 건 수 -전체 총 건수(기타(직접입력)으로 데이터가 일원화 안됨)
    @Query("SELECT COUNT(r) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regTime) = :year AND MONTH(r.regTime) = :month ")
    Long regularCountByPartAndMonth(@Param("year") int year, @Param("month") int month);

    //3. 영역별 발생 건 수 -기타 제외 총 건수
    @Query("SELECT COUNT(r) " +
            "FROM RegularPart p " +
            "LEFT JOIN RegularInspection r ON r.regularPart = p.partMenu AND YEAR(r.regTime) = :year AND MONTH(r.regTime) = :month ")
    Long regularCountOtherExcludedByPartAndMonth(@Param("year") int year, @Param("month") int month);

    //(엑셀용) 월간 영역별 점검 건수(0건인 것들도 함께 나옴)
    @Query("SELECT r.regularPart, COUNT(r) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regTime) = :year AND MONTH(r.regTime) = :month " +
            "GROUP BY r.regularPart")
    List<Object[]> regularListByPartAndMonthForExcel(@Param("year") int year, @Param("month") int month);


    //(pieChart) 월간 양호,불량,NA 건수 표시(구분(양호,불량,NA),값) - 전체
    @Query("SELECT c.regularCheck, COUNT(c) " +
            "FROM RegularInspectionCheck c " +
            "WHERE YEAR(c.regularInspection.regTime) = :year AND MONTH(c.regularInspection.regTime) = :month " +
            "GROUP BY c.regularCheck")
    List<Object[]>regularCntByCheckAndMonth(@Param("year") int year, @Param("month") int month);

    //(pieChart) 월간 점검카테고리(Sort)에 따른 양호,불량,NA 건수 표시(구분(양호,불량,NA),값)
    @Query("SELECT c.regularCheck, COUNT(c) " +
            "FROM RegularInspectionCheck c " +
            "WHERE YEAR(c.regularInspection.regTime) = :year AND MONTH(c.regularInspection.regTime) = :month " +
            "AND c.regularInspection.regularInsName = :regularinsname " +
            "GROUP BY c.regularCheck")
    List<Object[]>regularCntByCheckAndMonthSortName(@Param("year") int year, @Param("month") int month, @Param("regularinsname") String regularInsName);

    //(pieChart) 월간 점검 드롭다운 생성하기
    @Query("SELECT n.regularInsName FROM RegularName n ORDER BY n.regularNum asc")
    List<String> RegularNameList();

    //(엑셀용) 월간 점검종류별 위험성평가 건수
    @Query("SELECT r.regularInsName, i.regularCheck, COUNT(r) " +
            "FROM RegularInspection r " +
            "JOIN RegularInspectionCheck i ON i.regularInspection.regularId = r.regularId AND YEAR(r.regTime) = :year AND MONTH(r.regTime) = :month " +
            "GROUP BY r.regularInsName, i.regularCheck")
    List<Object[]> regularListByNameAndMonthForExcel(@Param("year") int year, @Param("month") int month);


    //연간
    //(LineChart) 1~12월 총 수시점검 건수
    @Query("SELECT COUNT(r) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regTime) = :year")
    int regularCountByYear(@Param("year") int year);

    //(LineChart) 전체 월별(1~12월) 정기점검 건수
    @Query("SELECT MONTH(r.regTime), COALESCE(COUNT(r), 0) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regTime) = :year " +
            "GROUP BY MONTH(r.regTime)")
    List<Object[]> regularCountList(@Param("year") int year);

    //(barChart) 1~12월 점검종류별 점검건수 통계
    @Query("SELECT MONTH(r.regTime), r.regularInsName, COUNT(r) " +
            "FROM RegularInspection r " +
            "WHERE YEAR(r.regTime) = :year " +
            "GROUP BY r.regularInsName, MONTH(r.regTime)")
    List<Object[]> regularDetailListByNameAndYear(@Param("year") int year);


}