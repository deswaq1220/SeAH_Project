package SeAH.savg.repository;

import SeAH.savg.constant.SpeStatus;
import SeAH.savg.entity.SpecialInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpecialInspectionRepository extends JpaRepository<SpecialInspection, String> {
    // 설비로 찾기
    List<SpecialInspection> findAllBySpeFacility(String masterdataFacility);

    // DB에서 id들 들고와서 '-'기준으로 잘른 뒤에꺼의 가장 마지막 번호를 얻은 다음(int로 변환해서 비교해야할거같음) 가장 큰 숫자 구하기
    @Query("select MAX(substring(s.speId, 7)) from SpecialInspection s where substring(s.speId,2,4) =? 1")
    int findAllByMaxSeq(String todayYearAndMonth);

    // 아이디로 찾기
    SpecialInspection findAllBySpeId(String speId);

    // monthly: 전체 점검 건수
    @Query("SELECT COUNT(s) FROM SpecialInspection s WHERE DATE_FORMAT(s.speDate, '%Y-%m') = DATE_FORMAT(?1, '%Y-%m') AND s.speId IS NOT NULL AND s.speDate > ?1")
    int countAllBySpeDateAndSpeIdIsNotNullSpeDateAfter(LocalDateTime startOfToday);

    // monthly: 완료 갯수
    @Query("SELECT COUNT(s) FROM SpecialInspection s WHERE DATE_FORMAT(s.speActDate, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m') AND s.speComplete = ?1 ")
    int countBySpeActDateAndSpeComplete(SpeStatus speComplete, LocalDateTime startOfToday);

    // daily 완료 갯수


    //통계
    //월별 수시점검 통계 조회 - 영역별 건 수
    @Query("SELECT s.spePart, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.spePart")
    List<Object[]> specialListByPartAndMonth(@Param("month") int month);

    //월별 수시점검 통계 조회 - 위험분류 발생 건 수
    @Query("SELECT s.speDanger, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speDanger")
    List<Object[]> specialListByDangerAndMonth(@Param("month") int month);

    /*@Query("SELECT dangers.speDanger, COALESCE(COUNT(s), 0) " +
            "FROM (SELECT 'A' AS speDanger UNION SELECT 'B' AS speDanger UNION SELECT 'C' AS speDanger) AS dangers " +
            "LEFT JOIN SpecialInspection s ON dangers.speDanger = s.speDanger AND MONTH(s.speDate) = :month " +
            "GROUP BY dangers.speDanger")
    List<Object[]> specialListByDangerAndMonth(@Param("month") int month);*/


    //월별 수시점검 통계 조회 - 위험원인별 발생 건 수
    @Query("SELECT s.speCause, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speCause")
    List<Object[]> specialListBySpeCauseAndMonth(@Param("month") int month);

    //월별 수시점검 통계 조회 - 실수함정별 발생 건 수
    @Query("SELECT s.speTrap, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speTrap")
    List<Object[]> specialListBySpeTrapAndMonth(@Param("month") int month);

    //월별 수시점검 통계 조회 - 부상부위별 발생 건 수
    @Query("SELECT s.speInjure, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speInjure")
    List<Object[]> specialListBySpeInjureAndMonth(@Param("month") int month);

    //월별 수시점검 통계 조회 - 위험성 평가별 발생 건 수
    @Query("SELECT s.speRiskAssess, COUNT(s) FROM SpecialInspection s WHERE MONTH(s.speDate) = :month GROUP BY s.speRiskAssess")
    List<Object[]> specialListBySpeRiskAssessAndMonth(@Param("month") int month);

    // monthly: 이번달 deadline 중 미완료건수
    @Query("SELECT COUNT(s) FROM SpecialInspection s " +
            "WHERE DATE_FORMAT(s.speDeadline, '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m')" +
            "AND s.speComplete =? 1")
    int countBySpeDeadlineAndSpeComplete(SpeStatus speComplete);
}
